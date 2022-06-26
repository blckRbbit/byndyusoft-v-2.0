package handler;

import parser.BSArithmeticExpressionParser;
import parser.BSParser;
import exception.BSUnexpectedCharacterException;
import exception.BSUnexpectedTokenException;

import java.util.List;

public class BSCalculatorHandler implements BSHandler {

    private int position;

    public BSCalculatorHandler() {
        this.position = 0;
    }

    @Override
    public double calculate(String expression) {
        List<String> tokens = getTokens(expression);
        double result = multiply(expression);

        while (position < tokens.size()) {
            String operator = tokens.get(position);
            if (!operator.equals("+") && !operator.equals("-")) {
                break;
            } else {
                position++;
            }
            double number = multiply(expression);
            if (operator.equals("+")) {
                result += number;
            } else {
                result -= number;
            }
        }
        return result;
    }

    private double multiply(String expression) {
        List<String> tokens = getTokens(expression);
        double result = factor(expression);

        while (position < tokens.size()) {
            String operator = tokens.get(position);
            if (!operator.equals("*") && !operator.equals("/")) {
                break;
            } else {
                position++;
            }
            double number = factor(expression);
            if (operator.equals("*")) {
                result *= number;
            } else {
                result /= number;
            }
        }
        return result;
    }

    private double factor(String expression) {
        List<String> tokens = getTokens(expression);
        String next = tokens.get(position);
        double result;
        if (next.equals("-")) {
            position++;
            result = factor(expression);
            return -result;
        }
        if (next.equals("(")) {
            position++;
            result = calculate(expression);
            String closingBracket;
            if (position < tokens.size()) {
                closingBracket = tokens.get(position);
            } else {
                throw new BSUnexpectedCharacterException("Неожиданный конец выражения");
            }
            if (closingBracket.equals(")")) {
                position++;
                return result;
            }
            throw new BSUnexpectedTokenException(String.format("Ожидалось ')', однако, получено: %s", closingBracket));
        }
        position++;
        return Double.parseDouble(next);
    }

    private List<String> getTokens(String expression) {
        expression = expression.replace(" ", "");
        BSParser parser = new BSArithmeticExpressionParser(expression);
        return parser.parse();
    }

    /**
     * Геттеры для модульного тестирования.
     */

    public double getMultiplyForTest(String expression) {
        return multiply(expression);
    }

    public double getFactorForTest(String expression) {
        return factor(expression);
    }

    public List<String> getTokensForTest(String expression) {
        return getTokens(expression);
    }
}
