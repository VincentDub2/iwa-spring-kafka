package polytech.service.users.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);
    public UserNotFoundException(String message) {
        LOGGER.error(message);
        super(message);
    }
}
