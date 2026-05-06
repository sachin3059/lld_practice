import java.util.*;

public class ParkingLot {

    // levelId -> ParkingLevel
    private final Map<Integer, ParkingLevel> levels  = new LinkedHashMap<>();
    // licensePlate -> active ParkingTicket
    private final Map<String, ParkingTicket> tickets = new HashMap<>();

    // -------------------------------------------------------------------------
    // Level management
    // -------------------------------------------------------------------------

    public void addLevel(int levelId, int motorcycleSlots,
                         int carSlots, int busSlots) {
        if (levels.containsKey(levelId)) {
            System.out.println("Level " + levelId + " already exists.");
            return;
        }
        ParkingLevel level = new ParkingLevel(levelId);
        level.addSlots(SlotType.MOTORCYCLE, motorcycleSlots);
        level.addSlots(SlotType.CAR,        carSlots);
        level.addSlots(SlotType.BUS,        busSlots);
        levels.put(levelId, level);
        System.out.printf("Level %d added with %d motorcycle slots, "
                        + "%d car slots, %d bus slots.%n",
                levelId, motorcycleSlots, carSlots, busSlots);
    }

    public void removeLevel(int levelId) {
        ParkingLevel level = levels.get(levelId);
        if (level == null) {
            System.out.println("Level " + levelId + " not found.");
            return;
        }
        boolean hasOccupied = level.getSlots().stream()
                                   .anyMatch(ParkingSlot::isOccupied);
        if (hasOccupied) {
            System.out.println("Cannot remove level " + levelId
                    + ": it still has occupied slots.");
            return;
        }
        levels.remove(levelId);
        System.out.println("Level " + levelId + " removed.");
    }

    public void addSlots(int levelId, SlotType type, int count) {
        ParkingLevel level = levels.get(levelId);
        if (level == null) {
            System.out.println("Level " + levelId + " not found.");
            return;
        }
        level.addSlots(type, count);
        System.out.println(count + " " + type
                + " slot(s) added to level " + levelId + ".");
    }

    public void removeSlots(int levelId, SlotType type, int count) {
        ParkingLevel level = levels.get(levelId);
        if (level == null) {
            System.out.println("Level " + levelId + " not found.");
            return;
        }
        level.removeSlots(type, count);
    }

    // -------------------------------------------------------------------------
    // Vehicle operations
    // -------------------------------------------------------------------------

    public void parkVehicle(Vehicle vehicle) {
        String plate = vehicle.getLicensePlate();

        if (tickets.containsKey(plate)) {
            System.out.println("Vehicle " + plate + " is already parked.");
            return;
        }

        for (ParkingLevel level : levels.values()) {
            Optional<ParkingSlot> available = level.findAvailableSlot(vehicle);
            if (available.isPresent()) {
                ParkingSlot slot = available.get();
                slot.park();
                String ticketId = UUID.randomUUID().toString()
                                      .substring(0, 8).toUpperCase();
                ParkingTicket ticket = new ParkingTicket(
                        ticketId, vehicle, slot, level.getLevelId());
                tickets.put(plate, ticket);
                System.out.printf("%s parked at level %d, slot %s.%n",
                        vehicle, level.getLevelId(), slot.getSlotId());
                return;
            }
        }

        // No suitable slot found anywhere
        System.out.println("No available " + vehicle.getType()
                + " slot for " + vehicle + ". Parking lot is full for this type.");
    }

    public void exitVehicle(String licensePlate) {
        ParkingTicket ticket = tickets.remove(licensePlate);
        if (ticket == null) {
            System.out.println("Vehicle " + licensePlate
                    + " not found. It may not have entered.");
            return;
        }
        ticket.checkout(System.currentTimeMillis());
        ticket.getSlot().vacate();
        System.out.printf("%s exited from level %d, slot %s. Fee: $%.2f%n",
                ticket.getVehicle(),
                ticket.getLevelId(),
                ticket.getSlot().getSlotId(),
                ticket.getFee());
    }

    // -------------------------------------------------------------------------
    // Status
    // -------------------------------------------------------------------------

    public void viewStatus() {
        if (levels.isEmpty()) {
            System.out.println("No levels configured.");
            return;
        }
        for (ParkingLevel level : levels.values()) {
            System.out.printf(
                "Level %d: %d/%d motorcycle slots, "
                + "%d/%d car slots, %d/%d bus slots available.%n",
                level.getLevelId(),
                level.freeSlots(SlotType.MOTORCYCLE),
                level.totalSlots(SlotType.MOTORCYCLE),
                level.freeSlots(SlotType.CAR),
                level.totalSlots(SlotType.CAR),
                level.freeSlots(SlotType.BUS),
                level.totalSlots(SlotType.BUS));
        }
    }
}