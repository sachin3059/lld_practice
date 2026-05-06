public enum VehicleType {
    MOTORCYCLE, CAR, BUS;

    public double hourlyRate() {
        switch (this) {
            case MOTORCYCLE: return 1.0;
            case CAR:        return 2.0;
            case BUS:        return 5.0;
            default: throw new IllegalStateException("Unknown vehicle type");
        }
    }
}