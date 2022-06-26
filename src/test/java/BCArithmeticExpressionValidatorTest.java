import validator.BSArithmeticExpressionValidator;
import exception.BSEmptyExpressionException;
import exception.BSUnexpectedCharacterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BCArithmeticExpressionValidatorTest {

    @Test
    void expressionIsNotEmpty() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("+-");
        assertTrue(validator.expressionIsNotEmptyForTest());
    }

    @Test
    void expressionIsEmpty() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("   ");
        assertThrows(BSEmptyExpressionException.class, validator::expressionIsNotEmptyForTest);
    }

    @Test
    void isDigit() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("some");
        assertTrue(validator.isDigit("1.1"));
        assertTrue(validator.isDigit("-0.1"));
        assertTrue(validator.isDigit("0.1"));
        assertTrue(validator.isDigit("0"));
    }

    @Test
    void isNotDigit() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("some");
        assertThrows(BSUnexpectedCharacterException.class, () -> validator.isDigit("-.1"));
        assertThrows(BSUnexpectedCharacterException.class, () -> validator.isDigit("1.1.1"));
    }

    @Test
    void expressionBeginIsMinus() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("-");
        assertTrue(validator.expressionBeginIsMinusForTest());
    }

    @Test
    void expressionBeginIsNotMinus() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator(")");
        assertFalse(validator.expressionBeginIsMinusForTest());
    }

    @Test
    void expressionBeginIsBracket() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(");
        assertTrue(validator.expressionBeginIsBracketForTest());
    }

    @Test
    void expressionBeginIsNotBracket() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator(")");
        assertFalse(validator.expressionBeginIsBracketForTest());
    }

    @Test
    void expressionBeginIsCorrect() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(-1");
        BSArithmeticExpressionValidator validator1 = new BSArithmeticExpressionValidator("-1");
        BSArithmeticExpressionValidator validator2 = new BSArithmeticExpressionValidator("0.1");
        BSArithmeticExpressionValidator validator3 = new BSArithmeticExpressionValidator("-0");
        assertTrue(validator.expressionBeginIsCorrectForTest());
        assertTrue(validator1.expressionBeginIsCorrectForTest());
        assertTrue(validator2.expressionBeginIsCorrectForTest());
        assertTrue(validator3.expressionBeginIsCorrectForTest());
    }

    @Test
    void expressionBeginIsNotCorrect() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator(")-1");
        BSArithmeticExpressionValidator validator1 = new BSArithmeticExpressionValidator("a");
        BSArithmeticExpressionValidator validator2 = new BSArithmeticExpressionValidator("*0");
        BSArithmeticExpressionValidator validator3 = new BSArithmeticExpressionValidator("-+");
        assertThrows(BSUnexpectedCharacterException.class, validator::expressionBeginIsCorrectForTest);
        assertThrows(BSUnexpectedCharacterException.class, validator1::expressionBeginIsCorrectForTest);
        assertThrows(BSUnexpectedCharacterException.class, validator2::expressionBeginIsCorrectForTest);
        assertThrows(BSUnexpectedCharacterException.class, validator3::expressionBeginIsCorrectForTest);
    }

    @Test
    void expressionEndIsCorrect() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("1)");
        assertTrue(validator.expressionEndIsCorrectForTest());
    }

    @Test
    void expressionEndIsNotCorrect() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator(".(");
        assertThrows(BSUnexpectedCharacterException.class, validator::expressionEndIsCorrectForTest);
    }

    @Test
    void isContainsValidSymbols() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670)+-*/89");
        assertTrue(validator.isContainsValidSymbolsForTest());
    }

    @Test
    void isContainsNotValidSymbolsWhenOrderSymbolsIsWrong() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator(")12345670)+-*/89");
        assertThrows(BSUnexpectedCharacterException.class, validator::isContainsValidSymbolsForTest);
    }

    @Test
    void isContainsNotValidSymbolsWhenUnexpectedSymbol() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670O)+-*/89");
        assertFalse(validator.isContainsValidSymbolsForTest());
    }

    @Test
    void isOperand() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670O)+-*/89");
        assertTrue(validator.isOperator("("));
        assertTrue(validator.isOperator(")"));
        assertTrue(validator.isOperator("*"));
        assertTrue(validator.isOperator("/"));
        assertTrue(validator.isOperator("+"));
        assertTrue(validator.isOperator("-"));
    }

    @Test
    void isNotOperand() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670O)+-*/89");
        assertFalse(validator.isOperator("."));
        assertFalse(validator.isOperator("1"));
        assertFalse(validator.isOperator("1.1"));
        assertFalse(validator.isOperator("-0.5"));
        assertFalse(validator.isOperator("a"));
    }

    @Test
    void validate() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670)+-*/89");
        assertTrue(validator.validate());
    }

    @Test
    void validateWhenExpressedSymbol() {
        BSArithmeticExpressionValidator validator = new BSArithmeticExpressionValidator("(12345670O)+-*/89");
        assertThrows(BSUnexpectedCharacterException.class, validator::validate);
    }
}
