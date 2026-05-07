package com.library.models;

import java.util.List;
import java.util.ArrayList;

/**
 * Book = catalog-level metadata shared by all physical copies.
 * One Book can have many BookItems (physical copies on the shelf).
 */
public class Book {
    private final String isbn;      // unique identifier for the book
    private String title;
    private String subject;
    private String publisher;
    private List<Author> authors;
    private List<BookItem> bookItems; // physical copies

    public Book(String isbn, String title, String subject,
                String publisher, List<Author> authors) {
        this.isbn      = isbn;
        this.title     = title;
        this.subject   = subject;
        this.publisher = publisher;
        this.authors   = authors != null ? authors : new ArrayList<>();
        this.bookItems = new ArrayList<>();
    }

    public String getIsbn()              { return isbn; }
    public String getTitle()             { return title; }
    public String getSubject()           { return subject; }
    public String getPublisher()         { return publisher; }
    public List<Author> getAuthors()     { return authors; }
    public List<BookItem> getBookItems() { return bookItems; }

    public void addBookItem(BookItem item) {
        bookItems.add(item);
    }

    public long availableCopies() {
        return bookItems.stream()
            .filter(item -> item.getStatus() == com.library.enums.BookStatus.AVAILABLE)
            .count();
    }

    @Override
    public String toString() {
        return String.format("Book[isbn=%s, title='%s', copies=%d]",
            isbn, title, bookItems.size());
    }
}


