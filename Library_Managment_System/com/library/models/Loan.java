package com.library.models;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Loan ties a Member to a specific BookItem for a time period.
 * returnDate is null until the book is returned.
 */
public class Loan {
    private final String loanId;
    private final Member member;
    private final BookItem bookItem;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;   // null until returned

    public static final int LOAN_PERIOD_DAYS = 14;

    public Loan(Member member, BookItem bookItem) {
        this.loanId       = UUID.randomUUID().toString();
        this.member       = member;
        this.bookItem     = bookItem;
        this.checkoutDate = LocalDate.now();
        this.dueDate      = checkoutDate.plusDays(LOAN_PERIOD_DAYS);
    }


    public String    getLoanId()      { return loanId; }
    public Member    getMember()      { return member; }
    public BookItem  getBookItem()    { return bookItem; }
    public LocalDate getCheckoutDate(){ return checkoutDate; }
    public LocalDate getDueDate()     { return dueDate; }
    public LocalDate getReturnDate()  { return returnDate; }

    public boolean isActive() { return returnDate == null; }

    public boolean isOverdue() {
        return isActive() && LocalDate.now().isAfter(dueDate);
    }

    public long overdueDays() {
        if (!isActive()) {
            return returnDate.isAfter(dueDate)
                ? java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate)
                : 0;
        }
        LocalDate today = LocalDate.now();
        return today.isAfter(dueDate)
            ? java.time.temporal.ChronoUnit.DAYS.between(dueDate, today)
            : 0;
    }

    public void markReturned() { this.returnDate = LocalDate.now(); }

    @Override
    public String toString() {
        return String.format("Loan[id=%s, member=%s, book='%s', due=%s, returned=%s]",
            loanId, member.getMemberId(),
            bookItem.getBook().getTitle(), dueDate,
            returnDate != null ? returnDate : "active");
    }
}


