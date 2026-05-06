public enum SlotType {
    MOTORCYCLE, CAR, BUS;

    public boolean canAccommodate(VehicleType vehicleType) {
        // Strict: exact type match only
        return this.name().equals(vehicleType.name());
    }
}