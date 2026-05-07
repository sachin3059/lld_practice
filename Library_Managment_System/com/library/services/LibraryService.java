package com.library.services;

import com.library.enums.BookStatus;
import com.library.enums.MemberStatus;
import com.library.exceptions.BookNotAvailableException;
import com.library.exceptions.MemberNotEligibleException;
import com.library.exceptions.NotFoundException;
import com.library.models.*;
import com.library.patterns.FineCalculator;
import com.library.patterns.NotificationService;

import java.util.*;

/**
 * LibraryService â€” Facade Pattern.
 *
 * Single entry point for all library operations.
 * Orchestrates Member, BookItem, Loan, Reservation, and Fine.
 * Dependencies (FineCalculator, NotificationService) are injected
 * so they can be swapped without touching this class.
 *
 * Interview tip: Walk the interviewer through checkoutBook() step
 * by step â€” it covers guard clauses, state mutation, and side effects.
 */
public class LibraryService {

    private final Catalog catalog;
    private final FineCalculator fineCalculator;
    private final NotificationService notificationService;

    // In-memory stores (replace with DB repositories in production)
    private final Map<String, Member>      members      = new HashMap<>();
    private final Map<String, Loan>        activeLoans  = new HashMap<>(); // barcode â†’ Loan
    private final Map<String, Fine>        fines        = new HashMap<>(); // fineId â†’ Fine
    // isbn â†’ queue of pending reservations (FIFO)
    private final Map<String, Queue<Reservation>> reservations = new HashMap<>();

    public LibraryService(Catalog catalog,
                          FineCalculator fineCalculator,
                          NotificationService notificationService) {
        this.catalog             = catalog;
        this.fineCalculator      = fineCalculator;
        this.notificationService = notificationService;
    }

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
        System.out.println("[REGISTER] " + member);
    }

    public Member getMember(String memberId) {
        return Optional.ofNullable(members.get(memberId))
            .orElseThrow(() -> new NotFoundException("Member", memberId));
    }

    public void blockMember(String memberId) {
        getMember(memberId).setStatus(MemberStatus.BLOCKED);
        System.out.println("[BLOCK] Member " + memberId + " blocked.");
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  BOOK MANAGEMENT  (Librarian actions)
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    public void addBookItem(BookItem item) {
        item.getBook().addBookItem(item);
        System.out.println("[ADD] " + item);
    }

    public void removeBookItem(String barcode) {
        catalog.findBookItemByBarcode(barcode).ifPresent(item -> {
            if (item.getStatus() == BookStatus.LOANED) {
                throw new IllegalStateException("Cannot remove a loaned book: " + barcode);
            }
            item.setStatus(BookStatus.LOST); // mark as removed
            System.out.println("[REMOVE] BookItem " + barcode + " removed from catalog.");
        });
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  CHECKOUT
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /**
     * checkoutBook â€” core flow:
     * 1. Validate member eligibility
     * 2. Validate book availability
     * 3. Create Loan, update state
     * 4. Return Loan object
     */
    public Loan checkoutBook(String memberId, String barcode) {
        // Step 1: Member validation
        Member member = getMember(memberId);
        if (member.getStatus() == MemberStatus.BLOCKED) {
            throw new MemberNotEligibleException(memberId, "account is blocked");
        }
        if (!member.canCheckout()) {
            throw new MemberNotEligibleException(memberId,
                "checkout limit reached (" + Member.MAX_BOOKS_LIMIT + " books)");
        }

        // Step 2: BookItem validation
        BookItem bookItem = catalog.findBookItemByBarcode(barcode)
            .orElseThrow(() -> new NotFoundException("BookItem", barcode));

        if (!bookItem.isAvailable()) {
            throw new BookNotAvailableException(barcode);
        }

        // Step 3: Create Loan and mutate state atomically
        Loan loan = new Loan(member, bookItem);
        bookItem.setStatus(BookStatus.LOANED);
        bookItem.setDueDate(loan.getDueDate());
        member.incrementCheckout();
        activeLoans.put(barcode, loan);

        System.out.println("[CHECKOUT] " + loan);
        return loan;
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  RETURN
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /**
     * returnBook â€” core flow:
     * 1. Find active loan
     * 2. Mark returned, update book state
     * 3. Calculate fine if overdue
     * 4. Notify next reservation holder if any
     * 5. Return Optional<Fine>
     */
    public Optional<Fine> returnBook(String barcode) {
        Loan loan = Optional.ofNullable(activeLoans.remove(barcode))
            .orElseThrow(() -> new NotFoundException("Active loan for barcode", barcode));

        loan.markReturned();
        loan.getMember().decrementCheckout();

        // Step 3: Fine calculation
        Fine fine = null;
        double fineAmount = fineCalculator.calculate(loan);
        if (fineAmount > 0) {
            fine = new Fine(loan, fineAmount);
            fines.put(fine.getFineId(), fine);
            notificationService.notifyFineGenerated(loan.getMember(), fineAmount);
            System.out.println("[FINE] " + fine);
        }

        // Step 4: Fulfil pending reservation or mark available
        Queue<Reservation> queue = reservations.get(loan.getBookItem().getBook().getIsbn());
        if (queue != null && !queue.isEmpty()) {
            Reservation next = queue.poll();
            loan.getBookItem().setStatus(BookStatus.RESERVED);
            next.complete();
            notificationService.notifyBookAvailable(next.getMember(), next.getBook());
            System.out.println("[RESERVATION-FULFILLED] " + next);
        } else {
            loan.getBookItem().setStatus(BookStatus.AVAILABLE);
            loan.getBookItem().setDueDate(null);
        }

        System.out.println("[RETURN] " + loan);
        return Optional.ofNullable(fine);
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  RESERVATION
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    public Reservation reserveBook(String memberId, String isbn) {
        Member member = getMember(memberId);
        if (member.getStatus() == MemberStatus.BLOCKED) {
            throw new MemberNotEligibleException(memberId, "account is blocked");
        }

        Book book = catalog.searchByIsbn(isbn)
            .orElseThrow(() -> new NotFoundException("Book", isbn));

        // Don't reserve if a copy is available right now
        if (book.availableCopies() > 0) {
            throw new IllegalStateException(
                "Book has available copies â€” checkout instead of reserving.");
        }

        Reservation reservation = new Reservation(member, book);
        reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(reservation);
        System.out.println("[RESERVE] " + reservation);
        return reservation;
    }

    public boolean cancelReservation(String memberId, String isbn) {
        Queue<Reservation> queue = reservations.get(isbn);
        if (queue == null) return false;
        boolean found = queue.stream()
            .filter(r -> r.getMember().getMemberId().equals(memberId)
                      && r.getStatus() == com.library.enums.ReservationStatus.PENDING)
            .findFirst()
            .map(r -> { r.cancel(); return true; })
            .orElse(false);
        if (found) queue.removeIf(r -> r.getStatus() == com.library.enums.ReservationStatus.CANCELLED);
        return found;
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  RENEWAL
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /**
     * renewBook â€” extend due date by LOAN_PERIOD_DAYS
     * if no pending reservation exists for this book.
     */
    public boolean renewBook(String barcode) {
        Loan loan = Optional.ofNullable(activeLoans.get(barcode))
            .orElseThrow(() -> new NotFoundException("Active loan for barcode", barcode));

        String isbn = loan.getBookItem().getBook().getIsbn();
        Queue<Reservation> queue = reservations.get(isbn);
        if (queue != null && !queue.isEmpty()) {
            System.out.println("[RENEW DENIED] Pending reservations exist for " + isbn);
            return false;
        }

        // Extend via reflection on the dueDate â€” or use a mutable field
        // Here we create a fresh Loan-like update; for simplicity we extend the BookItem due date
        java.time.LocalDate newDue = loan.getDueDate().plusDays(Loan.LOAN_PERIOD_DAYS);
        loan.getBookItem().setDueDate(newDue);
        System.out.printf("[RENEW] Loan %s extended to %s%n", loan.getLoanId(), newDue);
        return true;
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  FINE PAYMENT
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    public void payFine(String fineId) {
        Fine fine = Optional.ofNullable(fines.get(fineId))
            .orElseThrow(() -> new NotFoundException("Fine", fineId));
        if (fine.isPaid()) {
            System.out.println("[FINE] Already paid: " + fineId);
            return;
        }
        fine.pay();
        System.out.println("[FINE PAID] " + fine);
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  QUERIES
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    public List<Loan> getActiveLoans() {
        return new ArrayList<>(activeLoans.values());
    }

    public List<Fine> getUnpaidFines() {
        List<Fine> unpaid = new ArrayList<>();
        fines.values().stream().filter(f -> !f.isPaid()).forEach(unpaid::add);
        return unpaid;
    }
}
