package validator;

import exception.BSEmptyExpressionException;
import exception.BSUnexpectedCharacterException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BSArithmeticExpressionValidator implements BSValidator {
    private final String expression;
    private final Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/", "(", ")"));

    public BSArithmeticExpressionValidator(String expression) {
        this.expression = expression.replace(" ", "");
    }

    @Override
    public boolean validate() {
        if (isContainsValidSymbols()) {
            return true;
        }
        throw new BSUnexpectedCharacterException(
                "Выражение должно содержать только целые или дробные числа, скобки и/или арифметические операнды"
        );
    }

    @Override
    public boolean isOperator(String in) {
        return operators.contains(in);
    }

    private boolean isContainsValidSymbols() {
        if (expressionEndIsCorrect()) {
            StringBuilder builder = new StringBuilder();
            for (String operator : operators) {
                builder.append(operator);
            }
            String regex = String.format("[.0123456789%s]+", builder);
            return expression.matches(regex);
        }
        throw new RuntimeException("Произошла непредвиденная ошибка.");
    }

    private boolean expressionEndIsCorrect() {
        if (expressionBeginIsCorrect()) {
            String endSymbol = String.valueOf(expression.charAt(expression.length() - 1));
            return endSymbol.equals(")") || isDigit(endSymbol);
        }
        return expressionBeginIsCorrect();
    }

    private boolean expressionBeginIsCorrect() {
        String temp;
        String begin;
        if (expressionBeginIsMinus()) {
            temp = expression.substring(1);
            if (temp.charAt(0) == '(') {
                begin = temp.charAt(1) == '-' ? temp.substring(2, 3) : temp.substring(1, 2);
            } else {
                begin = temp.substring(0, 1);
            }
            return isDigit(begin);
        }

        temp = expressionBeginIsBracket() ? expression.substring(1) : expression;
        if (temp.length() > 1) {
            begin = temp.charAt(0) == '-' ? temp.substring(0, 2) : temp.substring(0, 1);
            return isDigit(begin);
        } else {
            return isDigit(temp);
        }
    }

    private boolean expressionBeginIsBracket() {
        if (expressionIsNotEmpty()) {
            String begin = expression.split("")[0];
            return begin.equals("(");
        }
        return expressionIsNotEmpty();
    }

    private boolean expressionBeginIsMinus() {
        if (expressionIsNotEmpty()) {
            String begin = expression.split("")[0];
            return begin.equals("-");
        }
        return expressionIsNotEmpty();
    }

    @Override
    public boolean isDigit(String input) {
        if (expressionIsNotEmpty()) {
            String regex = "^-*(?=[^.])\\d*\\.?((?=[^.])\\d*)$";
            if (!input.matches(regex)) {
                throw new BSUnexpectedCharacterException("Недопустимый символ в арифметическом выражении");
            }
            return input.matches(regex);
        }
        return expressionIsNotEmpty();
    }

    private boolean expressionIsNotEmpty() {
        if (this.expression.isEmpty()) {
            throw new BSEmptyExpressionException("Выражение не должно быть пустым");
        }
        return true;
    }

    /**
     * Геттеры для модульного тестирования.
     */

    public boolean isContainsValidSymbolsForTest() {
        return isContainsValidSymbols();
    }

    public boolean expressionEndIsCorrectForTest() {
        return expressionEndIsCorrect();
    }

    public boolean expressionBeginIsCorrectForTest() {
        return expressionBeginIsCorrect();
    }

    public boolean expressionBeginIsBracketForTest() {
        return expressionBeginIsBracket();
    }

    public boolean expressionBeginIsMinusForTest() {
        return expressionBeginIsMinus();
    }

    public boolean expressionIsNotEmptyForTest() {
        return expressionIsNotEmpty();
    }
}
