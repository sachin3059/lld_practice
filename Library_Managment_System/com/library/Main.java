package com.library;

import com.library.models.*;
import com.library.patterns.*;
import com.library.services.*;

import java.util.List;
import java.util.Optional;

/**
 * End-to-end demo of the Library Management System.
 * Compile & run:
 *   javac -d out $(find src -name "*.java")
 *   java -cp out com.library.Main
 */
public class Main {
    public static void main(String[] args) {

        // â”€â”€ Bootstrap â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        Catalog catalog = new Catalog();
        LibraryService library = new LibraryService(
            catalog,
            new TieredFineCalculator(),          // swap for FlatRateFineCalculator anytime
            new ConsoleNotificationService()
        );

        // â”€â”€ Create Books â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        Author effectiveJavaAuthor = new Author("Joshua Bloch", "Java expert");
        Book effectiveJava = new Book(
            "978-0134685991", "Effective Java", "Programming",
            "Addison-Wesley", List.of(effectiveJavaAuthor)
        );

        Author cleanCodeAuthor = new Author("Robert C. Martin", "Clean code advocate");
        Book cleanCode = new Book(
            "978-0132350884", "Clean Code", "Programming",
            "Prentice Hall", List.of(cleanCodeAuthor)
        );

        catalog.addBook(effectiveJava);
        catalog.addBook(cleanCode);

        // â”€â”€ Add physical copies (BookItems) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        BookItem copy1 = new BookItem("EJ-001", effectiveJava, 799.0);
        BookItem copy2 = new BookItem("EJ-002", effectiveJava, 799.0);
        BookItem cc1   = new BookItem("CC-001", cleanCode,     699.0);

        library.addBookItem(copy1);
        library.addBookItem(copy2);
        library.addBookItem(cc1);

        // â”€â”€ Register Members â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        Member alice = new Member("M001", "Alice", "alice@example.com", "9900001111");
        Member bob   = new Member("M002", "Bob",   "bob@example.com",   "9900002222");
        library.registerMember(alice);
        library.registerMember(bob);

        System.out.println("\nâ”€â”€â”€ CHECKOUT â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        Loan aliceLoan = library.checkoutBook("M001", "EJ-001");
        Loan bobLoan   = library.checkoutBook("M002", "CC-001");
        System.out.println("Alice: " + aliceLoan);
        System.out.println("Bob:   " + bobLoan);

        System.out.println("\nâ”€â”€â”€ SEARCH CATALOG â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        List<Book> found = catalog.searchByAuthor("Joshua Bloch");
        found.forEach(b -> System.out.println("Found: " + b + " | available copies: " + b.availableCopies()));

        System.out.println("\nâ”€â”€â”€ RESERVATION (no copies left for EJ) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        // Check out copy2 so both EJ copies are loaned
        Loan aliceLoan2 = library.checkoutBook("M001", "EJ-002");
        // Now Bob tries to reserve â€” both copies are loaned
        Reservation bobRes = library.reserveBook("M002", "978-0134685991");
        System.out.println("Bob reserved: " + bobRes);

        System.out.println("\nâ”€â”€â”€ RETURN (triggers reservation notification) â”€â”€â”€â”€â”€");
        // Alice returns EJ-001 â€” Bob should be notified
        Optional<Fine> fineOpt = library.returnBook("EJ-001");
        fineOpt.ifPresentOrElse(
            f -> System.out.println("Fine generated: " + f),
            ()  -> System.out.println("No fine â€” returned on time.")
        );

        System.out.println("\nâ”€â”€â”€ RETURN WITH FINE â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        // Simulate overdue by returning â€” in a real test we'd mock the clock
        // For demo: just return Bob's Clean Code (no fine in real time)
        Optional<Fine> bobFine = library.returnBook("CC-001");
        bobFine.ifPresentOrElse(
            f -> {
                System.out.println("Bob's fine: " + f);
                library.payFine(f.getFineId());
            },
            () -> System.out.println("No fine â€” returned on time.")
        );

        System.out.println("\nâ”€â”€â”€ BLOCK MEMBER â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        library.blockMember("M001");
        try {
            library.checkoutBook("M001", "EJ-002"); // should throw
        } catch (Exception e) {
            System.out.println("[EXPECTED ERROR] " + e.getMessage());
        }

        System.out.println("\nâ”€â”€â”€ ACTIVE LOANS â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        library.getActiveLoans().forEach(System.out::println);

        System.out.println("\nâ”€â”€â”€ ALL BOOKS IN CATALOG â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€");
        catalog.getAllBooks().forEach(b ->
            System.out.printf("  %s â€” available: %d/%d copies%n",
                b.getTitle(), b.availableCopies(), b.getBookItems().size()));
    }
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
