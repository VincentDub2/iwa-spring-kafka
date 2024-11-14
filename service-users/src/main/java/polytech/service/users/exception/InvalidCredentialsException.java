package polytech.service.users.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidCredentialsException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidCredentialsException.class);


    public InvalidCredentialsException(String message) {
        LOGGER.error(message);
        super(message);

    }
}
