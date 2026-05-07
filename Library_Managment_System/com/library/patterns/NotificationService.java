package com.library.patterns;

import com.library.models.Member;
import com.library.models.Book;

/**
 * Observer Pattern: NotificationService.
 * LibraryService calls notify() â€” actual delivery (email, SMS)
 * is the implementor's concern. Decoupled from core logic.
 */
public interface NotificationService {
    void notifyBookAvailable(Member member, Book book);
    void notifyFineGenerated(Member member, double amount);
    void notifyDueDateReminder(Member member, String bookTitle, java.time.LocalDate dueDate);
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
