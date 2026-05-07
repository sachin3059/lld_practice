package com.library.services;

import com.library.models.Author;
import com.library.models.Book;
import com.library.models.BookItem;

import java.util.*;

/**
 * Catalog maintains in-memory indexes for fast search.
 * Uses HashMap so lookup is O(1) for ISBN, O(1) for title/author key.
 *
 * Interview point: "In production this would be backed by a DB with
 * full-text search (Elasticsearch), but for LLD the HashMap captures
 * the intent correctly."
 */
public class Catalog {
    // isbn â†’ Book (primary store)
    private final Map<String, Book> booksByIsbn = new HashMap<>();

    // title (lower) â†’ list of Books (multiple books can share a keyword)
    private final Map<String, List<Book>> booksByTitle = new HashMap<>();

    // author name (lower) â†’ list of Books
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();

    // â”€â”€ Add / Remove â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public void addBook(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
        index(booksByTitle, book.getTitle().toLowerCase(), book);
        book.getAuthors().forEach(a ->
            index(booksByAuthor, a.getName().toLowerCase(), book));
    }

    public boolean removeBook(String isbn) {
        Book book = booksByIsbn.remove(isbn);
        if (book == null) return false;
        booksByTitle.getOrDefault(book.getTitle().toLowerCase(), Collections.emptyList())
                    .remove(book);
        book.getAuthors().forEach(a ->
            booksByAuthor.getOrDefault(a.getName().toLowerCase(), Collections.emptyList())
                         .remove(book));
        return true;
    }

    // â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public Optional<Book> searchByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    public List<Book> searchByTitle(String titleKeyword) {
        return booksByTitle.getOrDefault(titleKeyword.toLowerCase(), Collections.emptyList());
    }

    public List<Book> searchByAuthor(String authorName) {
        return booksByAuthor.getOrDefault(authorName.toLowerCase(), Collections.emptyList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    // â”€â”€ BookItem lookup â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public Optional<BookItem> findBookItemByBarcode(String barcode) {
        return booksByIsbn.values().stream()
            .flatMap(b -> b.getBookItems().stream())
            .filter(item -> item.getBarcode().equals(barcode))
            .findFirst();
    }

    // â”€â”€ Private helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    private void index(Map<String, List<Book>> map, String key, Book book) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(book);
    }
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
