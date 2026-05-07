package com.library.models;

/**
 * Librarian extends Person (not Member).
 * Has admin capabilities â€” add/remove books, block members.
 * Interview point: Librarian and Member are separate roles,
 * not the same entity with a flag.
 */
public class Librarian extends Person {
    private final String employeeId;

    public Librarian(String employeeId, String name, String email, String phone) {
        super(name, email, phone);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() { return employeeId; }

    @Override
    public String toString() {
        return String.format("Librarian[id=%s, name='%s']", employeeId, name);
    }
}


