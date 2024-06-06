package ra.jpa.exception;

public class UsernamePasswordException extends RuntimeException{ // checked exception

    public UsernamePasswordException(String message) {
        super(message);
    }
}
