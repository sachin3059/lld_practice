import java.util.*;

public class ParkingLevel {
    private final int levelId;
    private final List<ParkingSlot> slots;

    public ParkingLevel(int levelId) {
        this.levelId = levelId;
        this.slots   = new ArrayList<>();
    }

    public void addSlots(SlotType type, int count) {
        int start = slots.size() + 1;
        for (int i = 0; i < count; i++) {
            slots.add(new ParkingSlot("L" + levelId + "-S" + (start + i), type));
        }
    }

    public void removeSlots(SlotType type, int count) {
        int removed = 0;
        Iterator<ParkingSlot> it = slots.iterator();
        while (it.hasNext() && removed < count) {
            ParkingSlot slot = it.next();
            if (slot.getSlotType() == type && !slot.isOccupied()) {
                it.remove();
                removed++;
            }
        }
        if (removed < count) {
            System.out.println("Warning: only " + removed + " free "
                    + type + " slots removed from level " + levelId + ".");
        }
    }

    // Strict: only exact slot type match, no upgrade/downgrade
    public Optional<ParkingSlot> findAvailableSlot(Vehicle vehicle) {
        return slots.stream()
                    .filter(s -> s.canAccommodate(vehicle))
                    .findFirst();
    }

    public int getLevelId() { return levelId; }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public long freeSlots(SlotType type) {
        return slots.stream()
                    .filter(s -> s.getSlotType() == type && !s.isOccupied())
                    .count();
    }

    public long totalSlots(SlotType type) {
        return slots.stream()
                    .filter(s -> s.getSlotType() == type)
                    .count();
    }
}