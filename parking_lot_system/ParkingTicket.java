public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final int levelId;
    private final long entryTimeMs;
    private long exitTimeMs;
    private double fee;

    public ParkingTicket(String ticketId, Vehicle vehicle,
                         ParkingSlot slot, int levelId) {
        this.ticketId    = ticketId;
        this.vehicle     = vehicle;
        this.slot        = slot;
        this.levelId     = levelId;
        this.entryTimeMs = System.currentTimeMillis();
    }

    public void checkout(long exitTimeMs) {
        this.exitTimeMs  = exitTimeMs;
        long durationMs  = exitTimeMs - entryTimeMs;
        // Round up to nearest hour; minimum 1 hour
        long hours       = Math.max(1, (long) Math.ceil(durationMs / (1000.0 * 60 * 60)));
        this.fee         = hours * vehicle.getType().hourlyRate();
    }

    public String getTicketId()  { return ticketId; }
    public Vehicle getVehicle()  { return vehicle; }
    public ParkingSlot getSlot() { return slot; }
    public int getLevelId()      { return levelId; }
    public double getFee()       { return fee; }
}