package com.library.exceptions;

public class MemberNotEligibleException extends RuntimeException {
    public MemberNotEligibleException(String memberId, String reason) {
        super("Member " + memberId + " cannot checkout: " + reason);
    }
}


