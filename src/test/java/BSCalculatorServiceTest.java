import org.junit.jupiter.api.Test;
import service.BSCalculatorService;

import static org.junit.jupiter.api.Assertions.*;

public class BSCalculatorServiceTest {

    @Test
    void isNotQuit() {
        assertFalse(BSCalculatorService.isNotQuitForTest("q"));
        assertFalse(BSCalculatorService.isNotQuitForTest("Q"));
    }

    @Test
    void greet() {
        BSCalculatorService.greetForTest();
    }

    @Test
    void toInvite() {
        BSCalculatorService.toInviteForTest();
    }

    @Test
    void sayGoodbye() {
        BSCalculatorService.sayGoodbyeForTest();
    }

    @Test
    void getExpression() {
        String expression = "1 + 1";
        String result = BSCalculatorService.getExpressionForTest(expression);
        assertEquals(result, expression);
    }
}
