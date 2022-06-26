package parser;

import exception.BSUnexpectedTokenException;
import validator.BSArithmeticExpressionValidator;
import exception.BSUnexpectedCharacterException;
import validator.BSValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BSArithmeticExpressionParser implements BSParser {
    private final String expression;
    private final BSValidator validator;

    public BSArithmeticExpressionParser(String expression) {
        this.expression = expression.replace(" ", "");
        this.validator = new BSArithmeticExpressionValidator(expression);
    }

    @Override
    public List<String> parse() {
        return getTokens();
    }

    private List<String> getTokens() {
        if (validator.validate()) {
            List<String> tokens = new ArrayList<>();
            List<String> temp = Arrays.asList(expression.split(""));
            int position = 0;
            while (position < temp.size()) {
                String current = temp.get(position);
                if (validator.isOperator(current)) {
                    tokens.add(current);
                    position++;
                } else if (validator.isDigit(current)) {
                    StringBuilder builder = new StringBuilder();
                    do {
                        builder.append(current);
                        position ++;
                        if (position >= expression.length()) {
                            break;
                        }
                        current = temp.get(position);
                    } while (!validator.isOperator(current));
                    if (validator.isDigit(builder.toString())) {
                        tokens.add(builder.toString());
                    } else {
                        throw new BSUnexpectedTokenException("Неожиданный токен в выражении");
                    }
                }
            }
            return tokens;
        }
        throw new BSUnexpectedCharacterException(
                "Выражение должно содержать только целые или дробные числа, скобки и/или арифметические операнды"
        );
    }

}
