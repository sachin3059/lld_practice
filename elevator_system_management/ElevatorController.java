import java.util.ArrayList;
import java.util.List;

public class ElevatorController {

    private static final int MIN_FLOOR = 0;
    private static final int MAX_FLOOR = 9;

    private final List<Elevator> elevators;

    public ElevatorController() {
        elevators = new ArrayList<>();
        elevators.add(new Elevator());
        elevators.add(new Elevator());
        elevators.add(new Elevator());
    }

    // Called when someone presses a hall button outside the elevator
    public boolean requestElevator(int floor, RequestType type) {
        if (floor < MIN_FLOOR || floor > MAX_FLOOR) return false;
        if (type == RequestType.DESTINATION)         return false;

        Request  request = new Request(floor, type);
        Elevator best    = selectBestElevator(request);
        if (best == null) return false;
        return best.addRequest(request);
    }

    // Advance every elevator by one tick
    public void step() {
        for (Elevator e : elevators) {
            e.step();
        }
    }

    // ── Dispatch: three-tier priority ────────────────────────────────────
    //   Tier 1: an elevator already committed to pass through that floor
    //   Tier 2: nearest idle elevator
    //   Tier 3: nearest elevator regardless of state (last resort)
    private Elevator selectBestElevator(Request request) {
        Elevator best;

        best = findCommittedToFloor(request);
        if (best != null) return best;

        best = findNearestIdle(request.getFloor());
        if (best != null) return best;

        return findNearest(request.getFloor());
    }

    // Tier 1: elevator moving in the same direction that will pass through the floor
    private Elevator findCommittedToFloor(Request request) {
        int       floor     = request.getFloor();
        Direction direction = (request.getType() == RequestType.PICKUP_UP)
                              ? Direction.UP : Direction.DOWN;

        Elevator best        = null;
        int      minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            // Must be moving in the same direction
            if (e.getDirection() != direction) continue;

            // Must not have already passed the requested floor
            if (direction == Direction.UP   && e.getCurrentFloor() > floor) continue;
            if (direction == Direction.DOWN && e.getCurrentFloor() < floor) continue;

            // Must actually have a stop at or beyond the requested floor
            if (!e.hasRequestsAtOrBeyond(floor, direction)) continue;

            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                best        = e;
            }
        }
        return best;
    }

    // Tier 2: nearest idle elevator
    private Elevator findNearestIdle(int floor) {
        Elevator best        = null;
        int      minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getDirection() != Direction.IDLE) continue;
            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                best        = e;
            }
        }
        return best;
    }

    // Tier 3: nearest elevator, no filter
    private Elevator findNearest(int floor) {
        Elevator best        = elevators.get(0);
        int      minDistance = Math.abs(elevators.get(0).getCurrentFloor() - floor);

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                best        = e;
            }
        }
        return best;
    }
}