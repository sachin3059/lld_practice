public class VehicleFactory {
    public static Vehicle create(String type, String licensePlate) {
        switch (type.toUpperCase()) {
            case "CAR":        return new Car(licensePlate);
            case "MOTORCYCLE": return new Motorcycle(licensePlate);
            case "BUS":        return new Bus(licensePlate);
            default:
                throw new IllegalArgumentException(
                        "Unknown vehicle type: " + type);
        }
    }
}