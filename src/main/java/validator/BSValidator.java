package validator;

public interface BSValidator {
    boolean validate();
    boolean isDigit(String in);
    boolean isOperator(String in);
}
