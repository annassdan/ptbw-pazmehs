package tnc.at.brpl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@SuppressWarnings("unused")
public class ResourceInternalServerErrorException extends RuntimeException {
    public ResourceInternalServerErrorException() {
        super();
    }

    public ResourceInternalServerErrorException(String message) {
        super(message);
    }

    public ResourceInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
