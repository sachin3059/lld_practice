package com.library.models;

import com.library.enums.ReservationStatus;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Reservation is placed against a Book (not a BookItem).
 * When any copy becomes available, the reservation is fulfilled.
 * Expires after EXPIRY_DAYS if not picked up.
 */
public class Reservation {
    private final String reservationId;
    private final Member member;
    private final Book book;
    private ReservationStatus status;
    private final LocalDate creationDate;
    private LocalDate expiryDate;

    public static final int EXPIRY_DAYS = 3;

    public Reservation(Member member, Book book) {
        this.reservationId = UUID.randomUUID().toString();
        this.member        = member;
        this.book          = book;
        this.status        = ReservationStatus.PENDING;
        this.creationDate  = LocalDate.now();
        this.expiryDate    = creationDate.plusDays(EXPIRY_DAYS);
    }

    public String            getReservationId() { return reservationId; }
    public Member            getMember()         { return member; }
    public Book              getBook()           { return book; }
    public ReservationStatus getStatus()         { return status; }
    public LocalDate         getCreationDate()   { return creationDate; }
    public LocalDate         getExpiryDate()     { return expiryDate; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate)
            && status == ReservationStatus.PENDING;
    }

    public void complete()  { this.status = ReservationStatus.COMPLETED; }
    public void cancel()    { this.status = ReservationStatus.CANCELLED; }

    @Override
    public String toString() {
        return String.format("Reservation[id=%s, member=%s, book='%s', status=%s]",
            reservationId, member.getMemberId(),
            book.getTitle(), status);
    }
}


