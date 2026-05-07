import java.util.HashSet;
import java.util.Set;

public class Elevator {

    private static final int MIN_FLOOR = 0;
    private static final int MAX_FLOOR = 9;

    private int currentFloor;
    private Direction direction;
    private final Set<Request> requests;

    public Elevator() {
        this.currentFloor = 0;
        this.direction    = Direction.IDLE;
        this.requests     = new HashSet<>();
    }

    public boolean addRequest(Request request) {
        if (request.getFloor() < MIN_FLOOR || request.getFloor() > MAX_FLOOR) {
            return false;
        }
        if (request.getFloor() == currentFloor) {
            return true;  // already here, no-op
        }
        if (requests.contains(request)) {
            return false; // duplicate, ignore
        }
        requests.add(request);
        return true;
    }

    public void step() {

        // Case 1: nothing to do
        if (requests.isEmpty()) {
            direction = Direction.IDLE;
            return;
        }

        // Case 2: idle — pick direction toward nearest request
        // deterministic tiebreak: lower floor wins
        if (direction == Direction.IDLE) {
            Request nearest     = null;
            int     minDistance = Integer.MAX_VALUE;

            for (Request req : requests) {
                int distance = Math.abs(req.getFloor() - currentFloor);
                if (distance < minDistance
                        || (distance == minDistance
                            && nearest != null
                            && req.getFloor() < nearest.getFloor())) {
                    minDistance = distance;
                    nearest     = req;
                }
            }
            direction = (nearest.getFloor() > currentFloor) ? Direction.UP : Direction.DOWN;
        }

        // Case 3: stop at current floor if there is a matching request
        RequestType pickupType    = (direction == Direction.UP) ? RequestType.PICKUP_UP
                                                                 : RequestType.PICKUP_DOWN;
        Request     pickupRequest = new Request(currentFloor, pickupType);
        Request     destRequest   = new Request(currentFloor, RequestType.DESTINATION);

        if (requests.contains(pickupRequest) || requests.contains(destRequest)) {
            requests.remove(pickupRequest);
            requests.remove(destRequest);
            if (requests.isEmpty()) {
                direction = Direction.IDLE;
            }
            return; // don't move on the same tick we stop
        }

        // Case 4: no requests ahead — reverse
        if (!hasRequestsAhead(direction)) {
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
            return; // don't move on the same tick we reverse
        }

        // Case 5: move one floor
        if (direction == Direction.UP) {
            currentFloor++;
        } else {
            currentFloor--;
        }
    }

    // Used by ElevatorController to decide if this elevator can serve a request en-route
    public boolean hasRequestsAtOrBeyond(int floor, Direction dir) {
        for (Request req : requests) {
            if (dir == Direction.UP && req.getFloor() >= floor) {
                if (req.getType() == RequestType.PICKUP_UP
                        || req.getType() == RequestType.DESTINATION) {
                    return true;
                }
            }
            if (dir == Direction.DOWN && req.getFloor() <= floor) {
                if (req.getType() == RequestType.PICKUP_DOWN
                        || req.getType() == RequestType.DESTINATION) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasRequestsAhead(Direction dir) {
        for (Request req : requests) {
            if (dir == Direction.UP   && req.getFloor() > currentFloor) return true;
            if (dir == Direction.DOWN && req.getFloor() < currentFloor) return true;
        }
        return false;
    }

    public int       getCurrentFloor() { return currentFloor; }
    public Direction getDirection()    { return direction;    }

    @Override
    public String toString() {
        return "Elevator[floor=" + currentFloor + ", dir=" + direction
               + ", requests=" + requests + "]";
    }
}