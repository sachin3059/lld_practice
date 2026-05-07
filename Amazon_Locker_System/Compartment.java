public class Compartment {

    private final String compartmentId;
    private final Size   size;
    private       boolean occupied;

    public Compartment(String compartmentId, Size size) {
        this.compartmentId = compartmentId;
        this.size          = size;
        this.occupied      = false;
    }

    public void open() {
        // Triggers hardware unlock — door auto-closes after ~30 seconds
        System.out.println("[HARDWARE] Compartment " + compartmentId + " unlocked.");
    }

    public void markOccupied() { this.occupied = true; }
    public void markFree()     { this.occupied = false; }

    public boolean isOccupied()      { return occupied; }
    public Size    getSize()         { return size; }
    public String  getCompartmentId(){ return compartmentId; }
}