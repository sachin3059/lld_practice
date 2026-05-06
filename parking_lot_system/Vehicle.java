public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    protected Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType()    { return type; }

    @Override
    public String toString() {
        String typeName = type.name().charAt(0) + type.name().substring(1).toLowerCase();
        return typeName + " [" + licensePlate + "]";
    }
}