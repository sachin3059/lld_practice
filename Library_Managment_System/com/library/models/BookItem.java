package com.library.models;

import com.library.enums.BookStatus;
import java.time.LocalDate;

/**
 * BookItem = one physical copy of a Book.
 * Has its own barcode, status, and due date.
 *
 * Key interview point: Book != BookItem.
 * The ISBN lives on Book; the shelf-state lives here.
 */
public class BookItem {
    private final String barcode;   // unique per physical copy
    private final Book book;        // back-reference to catalog record
    private BookStatus status;
    private double price;
    private LocalDate dueDate;      // non-null when LOANED

    public BookItem(String barcode, Book book, double price) {
        this.barcode = barcode;
        this.book    = book;
        this.price   = price;
        this.status  = BookStatus.AVAILABLE;
    }

    public String     getBarcode()  { return barcode; }
    public Book       getBook()     { return book; }
    public BookStatus getStatus()   { return status; }
    public double     getPrice()    { return price; }
    public LocalDate  getDueDate()  { return dueDate; }

    public void setStatus(BookStatus status)   { this.status  = status; }
    public void setDueDate(LocalDate dueDate)  { this.dueDate = dueDate; }

    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("BookItem[barcode=%s, title='%s', status=%s]",
            barcode, book.getTitle(), status);
    }
}


