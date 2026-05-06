public class CommandHandler {
    private final ParkingLot parkingLot = new ParkingLot();

    public void handle(String commandLine) {
        String[] parts = commandLine.trim().split("\\s+");
        if (parts.length == 0) return;

        try {
            switch (parts[0].toUpperCase()) {

                case "ADD_LEVEL":
                    // ADD_LEVEL <id> <motorcycle> <car> <bus>
                    parkingLot.addLevel(
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]));
                    break;

                case "REMOVE_LEVEL":
                    parkingLot.removeLevel(Integer.parseInt(parts[1]));
                    break;

                case "PARK_VEHICLE":
                    // PARK_VEHICLE <type> <plate>
                    Vehicle v = VehicleFactory.create(parts[1], parts[2]);
                    parkingLot.parkVehicle(v);
                    break;

                case "EXIT_VEHICLE":
                    // EXIT_VEHICLE <plate>
                    parkingLot.exitVehicle(parts[1]);
                    break;

                case "VIEW_STATUS":
                    parkingLot.viewStatus();
                    break;

                case "ADD_SLOTS":
                    // ADD_SLOTS <levelId> <slotType> <count>
                    parkingLot.addSlots(
                        Integer.parseInt(parts[1]),
                        SlotType.valueOf(parts[2].toUpperCase()),
                        Integer.parseInt(parts[3]));
                    break;

                case "REMOVE_SLOTS":
                    // REMOVE_SLOTS <levelId> <slotType> <count>
                    parkingLot.removeSlots(
                        Integer.parseInt(parts[1]),
                        SlotType.valueOf(parts[2].toUpperCase()),
                        Integer.parseInt(parts[3]));
                    break;

                default:
                    System.out.println("Unknown command: " + parts[0]);
            }

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid command format: " + commandLine);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}