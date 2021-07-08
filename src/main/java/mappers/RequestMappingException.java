package mappers;

public class RequestMappingException extends RuntimeException {
    public RequestMappingException(String message) {
        super(message);
    }

    public RequestMappingException() {
    }

    public RequestMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
