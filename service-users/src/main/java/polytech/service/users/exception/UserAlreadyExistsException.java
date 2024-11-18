package polytech.service.users.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAlreadyExistsException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAlreadyExistsException.class);
    
    public UserAlreadyExistsException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
