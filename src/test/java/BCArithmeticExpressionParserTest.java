import parser.BSArithmeticExpressionParser;
import exception.BSUnexpectedCharacterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BCArithmeticExpressionParserTest {

    @Test
    void parse() {
        BSArithmeticExpressionParser parser1 = new BSArithmeticExpressionParser("(-1.1 + 1)");
        BSArithmeticExpressionParser parser2 = new BSArithmeticExpressionParser("3 + 2");
        BSArithmeticExpressionParser parser3 = new BSArithmeticExpressionParser("-3 + 2");
        assertEquals(6, parser1.parse().size());
        assertEquals(3, parser2.parse().size());
        assertEquals(4, parser3.parse().size());
    }

    @Test
    void parseWhenUnexpectedSymbol() {
        BSArithmeticExpressionParser parser1 = new BSArithmeticExpressionParser("(1.1.)");
        BSArithmeticExpressionParser parser2 = new BSArithmeticExpressionParser("(1.1a)");
        assertThrows(BSUnexpectedCharacterException.class, parser1::parse);
        assertThrows(BSUnexpectedCharacterException.class, parser2::parse);
    }
}
