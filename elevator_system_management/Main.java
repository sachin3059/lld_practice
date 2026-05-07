public class Main {

    public static void main(String[] args) {

        System.out.println("=== Scenario 1: Basic hall call + destination ===");
        ElevatorController c1 = new ElevatorController();

        c1.requestElevator(5, RequestType.PICKUP_UP);
        // passenger boards E1 and presses floor 8
        // (in a real system the controller would track which elevator opened its doors;
        //  here we hard-code index 0 for the demo)

        for (int tick = 1; tick <= 10; tick++) {
            System.out.println("Tick " + tick);
            c1.step();
        }

        System.out.println("\n=== Scenario 2: SCAN — concurrent requests ===");
        ElevatorController c2 = new ElevatorController();

        c2.requestElevator(3, RequestType.PICKUP_DOWN);
        c2.requestElevator(7, RequestType.PICKUP_UP);

        for (int tick = 1; tick <= 15; tick++) {
            System.out.println("Tick " + tick);
            c2.step();
        }

        System.out.println("\n=== Scenario 3: Edge cases ===");
        ElevatorController c3 = new ElevatorController();

        System.out.println(c3.requestElevator(-1, RequestType.PICKUP_UP));  // false
        System.out.println(c3.requestElevator(10, RequestType.PICKUP_UP));  // false
        System.out.println(c3.requestElevator(5,  RequestType.DESTINATION));// false
        System.out.println(c3.requestElevator(0,  RequestType.PICKUP_UP));  // true, no-op (floor 0)
    }
}