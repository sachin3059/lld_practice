import java.util.Objects;

public class Request {

    private final int floor;
    private final RequestType type;

    public Request(int floor, RequestType type) {
        this.floor = floor;
        this.type  = type;
    }

    public int getFloor()        { return floor; }
    public RequestType getType() { return type;  }

    // Required so Set<Request> can deduplicate and do O(1) contains/remove
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request r = (Request) o;
        return floor == r.floor && type == r.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, type);
    }

    @Override
    public String toString() {
        return "Request(floor=" + floor + ", type=" + type + ")";
    }
}