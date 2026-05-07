package com.library.models;

import com.library.enums.MemberStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private final String memberId;
    private MemberStatus status;
    private final LocalDate dateOfMembership;
    private int totalBooksCheckedOut;
    private List<String> notificationEmails; // Observer pattern hook

    public static final int MAX_BOOKS_LIMIT = 5;

    public Member(String memberId, String name, String email, String phone) {
        super(name, email, phone);
        this.memberId           = memberId;
        this.status             = MemberStatus.ACTIVE;
        this.dateOfMembership   = LocalDate.now();
        this.totalBooksCheckedOut = 0;
        this.notificationEmails = new ArrayList<>();
    }

    public String       getMemberId()             { return memberId; }
    public MemberStatus getStatus()               { return status; }
    public LocalDate    getDateOfMembership()     { return dateOfMembership; }
    public int          getTotalBooksCheckedOut() { return totalBooksCheckedOut; }

    public boolean canCheckout() {
        return status == MemberStatus.ACTIVE
            && totalBooksCheckedOut < MAX_BOOKS_LIMIT;
    }

    public void incrementCheckout() { totalBooksCheckedOut++; }
    public void decrementCheckout() {
        if (totalBooksCheckedOut > 0) totalBooksCheckedOut--;
    }

    public void setStatus(MemberStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Member[id=%s, name='%s', status=%s, books=%d/%d]",
            memberId, name, status, totalBooksCheckedOut, MAX_BOOKS_LIMIT);
    }
}



