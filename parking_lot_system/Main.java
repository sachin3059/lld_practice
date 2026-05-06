public class Main {
    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler();

        // Setup
        handler.handle("ADD_LEVEL 1 2 3 1");
        handler.handle("ADD_LEVEL 2 1 2 1");
        handler.handle("VIEW_STATUS");

        System.out.println("--- Parking vehicles ---");
        handler.handle("PARK_VEHICLE CAR KA-01-HH-1234");
        handler.handle("PARK_VEHICLE MOTORCYCLE MH-12-AB-9876");
        handler.handle("PARK_VEHICLE BUS DL-03-XY-0001");
        handler.handle("VIEW_STATUS");

        System.out.println("--- Edge cases ---");
        handler.handle("PARK_VEHICLE CAR KA-01-HH-1234");   // already parked
        handler.handle("EXIT_VEHICLE KA-99-ZZ-0000");        // never entered
        handler.handle("EXIT_VEHICLE KA-01-HH-1234");        // normal exit
        handler.handle("VIEW_STATUS");

        System.out.println("--- Admin operations ---");
        handler.handle("ADD_SLOTS 1 CAR 2");
        handler.handle("REMOVE_SLOTS 1 MOTORCYCLE 1");
        handler.handle("REMOVE_LEVEL 1");                    // should work, no occupied slots on L1 now
        handler.handle("VIEW_STATUS");
    }
}