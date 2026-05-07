import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locker {

    private final List<Compartment>        compartments;
    private final Map<String, AccessToken> accessTokenMapping;  // code → AccessToken

    public Locker(List<Compartment> compartments) {
        this.compartments       = compartments;
        this.accessTokenMapping = new HashMap<>();
    }

    // ── Called by delivery driver ─────────────────────────────────────────────

    public String depositPackage(Size size) {
        Compartment compartment = getAvailableCompartment(size);
        if (compartment == null) {
            throw new IllegalStateException("No available compartment of size: " + size);
        }

        compartment.open();
        compartment.markOccupied();

        AccessToken accessToken = generateAccessToken(compartment);
        accessTokenMapping.put(accessToken.getCode(), accessToken);

        return accessToken.getCode();
    }

    // ── Called by customer at kiosk ───────────────────────────────────────────

    public void pickup(String tokenCode) {
        if (tokenCode == null || tokenCode.isEmpty()) {
            throw new IllegalArgumentException("Invalid access token code");
        }

        AccessToken accessToken = accessTokenMapping.get(tokenCode);
        if (accessToken == null) {
            throw new IllegalArgumentException("Invalid access token code");
        }

        if (accessToken.isExpired()) {
            throw new IllegalStateException("Access token has expired");
        }

        // Valid — open compartment and clean up
        accessToken.getCompartment().open();
        clearDeposit(accessToken);
    }

    // ── Called by staff to handle packages with expired tokens ────────────────

    public void openExpiredCompartments() {
        for (AccessToken accessToken : accessTokenMapping.values()) {
            if (accessToken.isExpired()) {
                accessToken.getCompartment().open();
                // Compartment stays occupied until staff physically removes the package
                // Staff then calls a cleanup method (out of scope) to free compartment
                System.out.println("[STAFF] Expired compartment opened: "
                    + accessToken.getCompartment().getCompartmentId());
            }
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Compartment getAvailableCompartment(Size size) {
        for (Compartment c : compartments) {
            if (c.getSize() == size && !c.isOccupied()) {
                return c;
            }
        }
        return null;
    }

    private AccessToken generateAccessToken(Compartment compartment) {
        return new AccessToken(compartment);
    }

    private void clearDeposit(AccessToken accessToken) {
        accessToken.getCompartment().markFree();
        accessTokenMapping.remove(accessToken.getCode());
    }
}