public class ParkingSlot {
    private final String slotId;
    private final SlotType slotType;
    private boolean isOccupied;

    public ParkingSlot(String slotId, SlotType slotType) {
        this.slotId     = slotId;
        this.slotType   = slotType;
        this.isOccupied = false;
    }

    public boolean canAccommodate(Vehicle vehicle) {
        return !isOccupied && slotType.canAccommodate(vehicle.getType());
    }

    public void park()   { isOccupied = true; }
    public void vacate() { isOccupied = false; }

    public boolean isOccupied()   { return isOccupied; }
    public SlotType getSlotType() { return slotType; }
    public String getSlotId()     { return slotId; }
}