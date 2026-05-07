import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── Setup ──────────────────────────────────────────────────────────────
        List<Compartment> compartments = Arrays.asList(
            new Compartment("C1", Size.SMALL),
            new Compartment("C2", Size.MEDIUM),
            new Compartment("C3", Size.MEDIUM),
            new Compartment("C4", Size.LARGE)
        );
        Locker locker = new Locker(compartments);

        // ── Flow 1: Driver deposits a medium package ───────────────────────────
        System.out.println("=== Deposit ===");
        String code = locker.depositPackage(Size.MEDIUM);
        System.out.println("Access token: " + code);

        // ── Flow 2: Customer picks up with correct code ────────────────────────
        System.out.println("\n=== Pickup (valid) ===");
        locker.pickup(code);
        System.out.println("Pickup successful.");

        // ── Flow 3: Same code used again → invalid ─────────────────────────────
        System.out.println("\n=== Pickup (already used code) ===");
        try {
            locker.pickup(code);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ── Flow 4: No compartment of requested size ───────────────────────────
        System.out.println("\n=== Deposit (no space) ===");
        locker.depositPackage(Size.MEDIUM);   // fills C3
        try {
            locker.depositPackage(Size.MEDIUM); // C2 and C3 both occupied now
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ── Flow 5: Staff opens expired compartments ───────────────────────────
        System.out.println("\n=== Open Expired Compartments ===");
        locker.openExpiredCompartments();     // none expired yet — silent
        System.out.println("(No expired tokens currently)");
    }
}