package com.shpp.p2p.cs.nsigov.assignment11;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class contains the logic for evaluating the expression of a parsed formula.
 * Also has a method to return the numerical value of variables
 */
public class CalculateManager {

    /**
     * The formula is parsed according to the Dijkstra's algorithm
     */
    private final ArrayList<String> PARSED_FORMULA;

    /**
     * HashMap of parsed values of variables
     */
    private final HashMap<String, Double> VARIABLES;

    /**
     * The output computed value of the expression
     */
    private double result;

    /**
     * The formula obtained from the command line arguments in "pure" form
     */
    private String formula;

    /**
     * The class constructor defines the PARSED_FORMULA and VARIABLES class fields
     *
     * @param parsedFormula  The formula is parsed according to the Dijkstra's algorithm
     * @param parseVariables HashMap of parsed values of variables
     * @param formula        The formula obtained from the command line arguments in "pure" form
     */
    CalculateManager(ArrayList<String> parsedFormula, HashMap<String, Double> parseVariables, String formula) {
        this.PARSED_FORMULA = parsedFormula;
        this.VARIABLES = parseVariables;
        this.formula = formula;
    }

    /**
     * The method performs the final calculation of the formula, substitutes their values into the variables
     * Method of work - values are read from a parsed formula and entered into the counting stack.
     * If an operator comes to the stack, the operands in the stack are calculated
     */
    void calculate() {
        // Argument for expressions. If we calculate an expression with binary operator then use two argument
        // For unary expression - one
        double arg2 = 0;
        double arg1 = 0;

        ArrayList<String> stack = new ArrayList<>();

        while (PARSED_FORMULA.size() != 0) {
            if (!PARSED_FORMULA.get(0).matches("\\+?-?\\*?/?\\^?(sqrt)?(sin)?(cos)?(tan)?(atan)?(log10)?(log2)?")) {
                stack.add(PARSED_FORMULA.get(0));
            } else { // if operator is sin,cos,tan(unary)
                if (PARSED_FORMULA.get(0).matches("(-?sqrt)?(-?sin)?(-?cos)?(-?tan)?(-?atan)?(-?log10)?(-?log2)?")) {
                    arg1 = checkVariables(stack.get(stack.size() - 1), VARIABLES);
                } else { // if binary operator
                    try {
                        arg1 = checkVariables(stack.get(stack.size() - 2), VARIABLES);
                        arg2 = checkVariables(stack.get(stack.size() - 1), VARIABLES);
                        stack.remove(stack.size() - 2);
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Incorrect operators input(double operators, double dots, unknown operator)");
                        System.exit(0);
                    }
                }
                stack.remove(stack.size() - 1);
                stack.add(String.valueOf(calculateExp(PARSED_FORMULA.get(0), arg1, arg2)));
            }
            PARSED_FORMULA.remove(0);
        }
        try {
            if (checkStartWithMinus()) {
                result = -Double.parseDouble(stack.get(0)); //return result
            } else {
                result = Double.parseDouble(stack.get(0));
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("You forgot to enter the formula");
            System.exit(0);
        } catch (NumberFormatException e) {
            System.err.println("In your expression only undefined variable");
            System.exit(0);
        }
    }

    /**
     * The method calculates a simple mathematical expression
     * Either binary with two arguments or unary with one
     *
     * @param s    - String contains operator like + - * sin ...
     * @param args - arguments for calculating
     * @return result of expression calculating
     */
    private double calculateExp(String s, double... args) {
        boolean unaryMinus = s.length() > 1 && s.startsWith("-");

        switch (unaryMinus ? s.substring(1) : s) {
            case "+":
                return args[0] + args[1];
            case "-":
                return args[0] - args[1];
            case "*":
                return args[0] * args[1];
            case "/":
                if (args[1] == 0) {
                    System.err.println("Division by zero");
                    System.exit(0);
                } else {
                    return args[0] / args[1];
                }
            case "^":
                return Math.pow(args[0], args[1]);
            case "sqrt":
                return unaryMinus ? -Math.sqrt(args[0]) : Math.sqrt(args[0]);
            case "sin":
                return unaryMinus ? -Math.sin(args[0]) : Math.sin(args[0]);
            case "cos":
                return unaryMinus ? -Math.cos(args[0]) : Math.cos(args[0]);
            case "tan":
                return unaryMinus ? -Math.tan(args[0]) : Math.tan(args[0]);
            case "atan":
                return unaryMinus ? -Math.atan(args[0]) : Math.atan(args[0]);
            case "log10":
                return unaryMinus ? -Math.log10(args[0]) : Math.log10(args[0]);
            case "log2":
                return unaryMinus ? -Math.log(args[0]) / Math.log(2) : Math.log(args[0]) / Math.log(2);
        }
        // no one case worked
        System.err.println("Unknown operator");
        System.exit(0);
        return 0.0;
    }


    /**
     * The method checks variables and operators for unary minus and substitutes the values of the variables HashMap
     *
     * @param s         - operator to check
     * @param variables HashMap containing the specified variables
     * @return meaning of operator with or without unary minus
     */
    private double checkVariables(String s, HashMap<String, Double> variables) {
        boolean minus = false;

        if (s.startsWith("-")) { // Checking the unary minus
            minus = true;
            s = s.substring(1);
        }
        if (variables.containsKey(s)) { // checking a variable in HashMap
            if (minus) {
                return -variables.get(s);
            } else {
                return variables.get(s);
            }
        } else {  // if HasMap doesn't have this variable
            try {
                if (minus) {
                    return -Double.parseDouble(s);
                } else {
                    return Double.parseDouble(s);
                }
            } catch (NumberFormatException e) {
                System.err.println("Variables are incorrectly defined");
                System.exit(1);
                return 0;
            }
        }
    }

    /**
     * The method takes a formula as input and determines
     * whether it starts with a minus for further operations
     */
    private boolean checkStartWithMinus() {
        if (formula.startsWith("-") && formula.length() > 1 && formula.charAt(1) == '(' && formula.endsWith(")")) {
            formula = formula.substring(1);
            return true;
        }
        if (formula.startsWith("-") && formula.length() > 1 && formula.charAt(1) == '(' && !formula.endsWith(")")) {
            formula = "0" + formula;
        }
        return false;
    }

    /**
     * The method is used to output result of calculating to the console.
     *
     * @return String witch contains result of calculating
     */
    @Override
    public String toString() {
        return "Result = " + result;
    }
}
