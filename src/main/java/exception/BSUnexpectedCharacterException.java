package exception;

public class BSUnexpectedCharacterException extends IllegalArgumentException {
    public BSUnexpectedCharacterException(String message) {
        super(message);
    }
}
