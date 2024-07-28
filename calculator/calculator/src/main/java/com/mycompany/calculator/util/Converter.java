package com.mycompany.calculator.util;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//pseudo code

/**
 * At calculator user always provide the prefix expression;
 * We have to convert the prefix to the postfix by using the precedence rule
 * After converting the prefix expression to the postfix we evaluate the postfix expression by using the stack adding (operand2  operator operand1)
 */
public class Converter {

    // Retrieve the precedence of the provided operator
    private static int precedence(char chr) {
        switch (chr) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return -1;
        }
    }

    // method to convert the infix expression to the postfix;
    public static String infixToPostFix(String exp) {
        /**
         * 1: find matcher alphabet letter if there is alphabet char exist throw error
         * 2: If not exist then process
         * 3: After processing get current element in the format of the char
         * check precedence if more than 0 then it means the element is operator and just before the operator add the white space
         * in the stack push the element until the stack isEmpty() and current precedence is smaller than operator that is on the top
         * of the stack precedence and contact to the postFix field;
         * 4: if the char is the opening bracket push to the stack if closing bracket then again pop each element from the stack and
         * concat to the postfix field
         * 4: If not simply add to the stack because that is the operand not operator;
         * 5: If all done then simply pop one by one element from the stack and concat to the postfix
         */
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) throw new IllegalArgumentException("Invalid input is given");
        Stack<Character> stack = new Stack<>();
        StringBuilder postFix = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            char current = exp.charAt(i);
           
            if (precedence(current) > 0) {
                postFix.append(' ');  // Add space before operator
                while (!stack.empty() && precedence(current) <= precedence(stack.peek())) {
                    postFix.append(stack.pop()).append(' ');
                }
                stack.push(current);
            } else if (current == '(') {
                stack.push(current);
            } else if (current == ')') {
                while (!stack.empty() && stack.peek() != '(') {
                    postFix.append(' ').append(stack.pop());
                }
                stack.pop(); // Remove '(' from stack
            } else {
                postFix.append(current);
            }
        }
        while (!stack.empty()) {
            postFix.append(' ').append(stack.pop());
        }
        return postFix.toString().trim();
    }

    public static double evaluatePostfix(String postfix) {

        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {  // Check if the token is a number (integer or floating point)
                stack.push(Double.parseDouble(token));
            } else {
                // Operator case: +, -, *, /
                double operand2 = stack.pop();
                double operand1 = stack.pop();

                switch (token.charAt(0)) {
                    case '+':
                        stack.push(operand1 + operand2);
                        break;
                    case '-':
                        stack.push(operand1 - operand2);
                        break;
                    case '*':
                        stack.push(operand1 * operand2);
                        break;
                    case '/':
                        // Ensure non-zero divisor for division
                        if (operand2 != 0) {
                            stack.push(operand1 / operand2);
                        } else {
                            throw new ArithmeticException("Division by zero");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token);
                }
            }
        }
        // Result of postfix evaluation
        return stack.pop();
    }

}
