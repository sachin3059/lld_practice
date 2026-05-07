import java.time.LocalDateTime;
import java.util.UUID;

public class AccessToken {

    private final String        code;
    private final LocalDateTime expiration;
    private final Compartment   compartment;

    public AccessToken(Compartment compartment) {
        this.code        = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.expiration  = LocalDateTime.now().plusDays(7);
        this.compartment = compartment;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration);
    }

    public String      getCode()       { return code; }
    public Compartment getCompartment(){ return compartment; }
    public LocalDateTime getExpiration(){ return expiration; }
}