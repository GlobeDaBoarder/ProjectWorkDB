package base.annotation;

public class JsonSerializationException extends Exception {

    public JsonSerializationException() {
    }

    public JsonSerializationException(String message) {
        super(message);
    }

    public JsonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSerializationException(Throwable cause) {
        super(cause);
    }
}
