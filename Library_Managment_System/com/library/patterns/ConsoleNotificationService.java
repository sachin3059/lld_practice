package com.library.patterns;

import com.library.models.Member;
import com.library.models.Book;
import java.time.LocalDate;

/** Prints to console â€” swap with EmailNotificationService in prod. */
public class ConsoleNotificationService implements NotificationService {

    @Override
    public void notifyBookAvailable(Member member, Book book) {
        System.out.printf("[NOTIFY] %s (%s): '%s' is now available!%n",
            member.getName(), member.getEmail(), book.getTitle());
    }

    @Override
    public void notifyFineGenerated(Member member, double amount) {
        System.out.printf("[NOTIFY] %s (%s): Fine of â‚¹%.2f generated.%n",
            member.getName(), member.getEmail(), amount);
    }

    @Override
    public void notifyDueDateReminder(Member member, String bookTitle, LocalDate dueDate) {
        System.out.printf("[NOTIFY] %s (%s): '%s' is due on %s.%n",
            member.getName(), member.getEmail(), bookTitle, dueDate);
    }
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
