import handler.BSCalculatorHandler;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BSCalculatorHandlerTest {
    private final BSCalculatorHandler handler = createHandler();

    @Test
    void getToken() {
        List<String> tokens = Arrays.asList("1", "+", "1");
        assertEquals(handler.getTokensForTest("1+1"), tokens);
    }

    @Test
    void calculate() {
        assertEquals(-16, handler.calculate("-(2+2)*4"));
    }

    @Test
    void multiply() {
        assertEquals(4, handler.getMultiplyForTest("8/2"));
    }

    @Test
    void factor() {
        assertEquals(4, handler.getFactorForTest("(2+2)*4"));
    }

    private BSCalculatorHandler createHandler() {
        return new BSCalculatorHandler();
    }
}
