package com.shpp.p2p.cs.nsigov.assignment10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * The program implements a simple calculator that will calculate mathematical expressions.
 * Operators of addition, subtraction, multiplication, division, exponentiation are available to you.
 * You can also enter variables that will contain some value. There are some rules for entering an expression:
 * 1. The expression must not contain spaces. Write the elements of the expression in a row.
 * 2. All specified variables in the expression must be defined after it through a space
 * 3. There are no brackets
 * 4. Do not use operators other than those indicated
 * 5. Variables single letter ond lowercase
 * 6. Variables are set in this format a=1
 * 7. Fractional numbers are entered through a dot
 * 8. Do not write more than two operators together
 */

public class Assignment10Part1 {

    // All operations available for the calculator
    private static final String ALL_OPERATIONS = "^*/-+";

    public static void main(String[] args) {

        HashMap<String, Double> variables;
        ArrayList<String> formula;

        if (checkArgs(args)) {
            variables = parseVariables(args);
            formula = parseFormula(args[0]);
            // Output final result
            System.out.println("Solution " + args[0] + " = " + calculate(formula, variables));
        }
    }

    /**
     * This method returns HashMap, which contains all the variables
     * specified in the arguments. Where the variable name is a key
     *
     * @param args an array of parameters received from the command line
     * @return returns a HashMap containing all the variables
     */
    private static HashMap<String, Double> parseVariables(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            //Split the string by character - '='
            String[] words = args[i].split("=");
            try {
                variables.put(words[0], Double.parseDouble(words[1]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.err.println("Invalid variables");
                System.exit(0);
            }
        }
        return variables;
    }

    /**
     * The method receives an array of characters as an input,
     * processes it, separates numbers, their potential, operators.
     * Based on this data, forms an ArrayList
     *
     * @param formula expression received from the command line
     */
    private static ArrayList<String> parseFormula(String formula) {
        //translates the string into an array of characters
        char[] formulaArray = formula.toCharArray();

        ArrayList<String> result = new ArrayList<>();
        StringBuilder currentMeaning = new StringBuilder();
        StringBuilder operand = new StringBuilder();
        boolean previousOperand = false;

        // In the case of a sign at the beginning of the formula
        if (formulaArray[0] == '-' || formulaArray[0] == '+') {
            currentMeaning.append(formulaArray[0]);
        }

        for (int i = (formulaArray[0] == '-' || formulaArray[0] == '+') ? 1 : 0; i < formulaArray.length; i++) {
            /* saves numbers and variables in the array cells together
             * with their unary operators, binary operators in separate cells
             */
            if (ALL_OPERATIONS.indexOf(formulaArray[i]) != -1) {
                result.add(String.valueOf(currentMeaning));
                currentMeaning.setLength(0);
                operand.append(formulaArray[i]);

                if (previousOperand) {
                    currentMeaning.append(operand);
                    operand.setLength(0);
                    continue;
                }
                result.add(String.valueOf(operand));
                operand.setLength(0);
                previousOperand = true;

            } else {
                currentMeaning.append(formulaArray[i]);
                previousOperand = false;
            }
        }
        result.add(String.valueOf(currentMeaning));
        return removeEmpty(result);
    }

    /**
     * The method removes empty cells from ArrayList
     *
     * @param result Arraylist with empty cells
     * @return ArrayList without empty cells
     */
    private static ArrayList<String> removeEmpty(ArrayList<String> result) {
        for (int i = 0; i < result.size(); i++) {
            if (Objects.equals(result.get(i), "")) {
                result.remove(i);
            }
        }
        return result;
    }

    /**
     * The method calculates all exponentiations in the formula.
     * Since exponentiation is read from right to left, the cycle counts from the end
     *
     * @param variables HashMap containing all the variables
     * @param formula   formula obtained from command line arguments
     * @return returns an ArrayList with the calculated exponentiation operators
     */

    private static ArrayList<String> calculatingPow(HashMap<String, Double> variables, ArrayList<String> formula) {
        for (int i = formula.size() - 1; i > 0; i--) {
            // Find the symbol and perform operations with the right and left operand
            if (formula.get(i).equals("^")) {
                double arg1 = processingArgument(variables, i + 1, formula); // exponent
                double arg2 = processingArgument(variables, i - 1, formula); // base
                // The result is stored in the operator's cell, the operands are deleted
                formula.set(i, Math.pow(arg2, arg1) + "");
                formula.remove(i + 1);
                formula.remove(i - 1);
            }
        }
        return formula;
    }

    /**
     * The method checks the argument array if it is empty or not
     *
     * @param args Array of arguments from command line
     * @return true - the array has elements, false - doesn't
     */

    private static boolean checkArgs(String[] args) {
        if (args.length == 0) {
            System.err.println("You haven't entered anything");
            return false;
        }
        return true;
    }

    /**
     * This method contains all the main calculations and output of the result take place.
     *
     * @param formula   Parsed formula. Where each element (operator, operand) occupies its own cell
     * @param variables HashMap containing the specified variables
     * @return the result of all math operations
     */
    private static double calculate(ArrayList<String> formula, HashMap<String, Double> variables) {

        ArrayList<String> calculatedPow = calculatingPow(variables, formula);

        ArrayList<String> calculatedDivMul = calculatingDivMul(variables, calculatedPow);

        calculatingAddSub(variables, calculatedDivMul);

        return Double.parseDouble(formula.get(0));
    }

    /**
     * The method calculates all addition and subtraction in the formula.
     *
     * @param variables         HashMap containing all the variables
     * @param calculatingDivMul formula with calculated exponentiation, multiplication and division operators
     */

    private static void calculatingAddSub(HashMap<String, Double> variables, ArrayList<String> calculatingDivMul) {

        for (int i = 0; i < calculatingDivMul.size(); i++) {
            if (calculatingDivMul.get(i).equals("+") || calculatingDivMul.get(i).equals("-")) {
                double arg1 = processingArgument(variables, i + 1, calculatingDivMul); // right
                double arg2 = processingArgument(variables, i - 1, calculatingDivMul); // left
                // The result is stored in the operator's cell, the operands are deleted
                if ((calculatingDivMul.get(i).equals("+"))) {
                    calculatingDivMul.set(i, String.valueOf(arg2 + arg1));
                } else {
                    calculatingDivMul.set(i, String.valueOf(arg2 - arg1));
                }
                calculatingDivMul.remove(i + 1);
                calculatingDivMul.remove((i--) - 1);
            }
        }
    }

    /**
     * The method calculates all addition and subtraction in the formula.
     *
     * @param variables     HashMap containing all the variables
     * @param calculatedPow formula with calculated exponentiation operators
     * @return returns an ArrayList with the calculated all multiplication and division operators
     */
    private static ArrayList<String> calculatingDivMul(HashMap<String, Double> variables, ArrayList<String> calculatedPow) {

        for (int i = 0; i < calculatedPow.size(); i++) {
            if (calculatedPow.get(i).equals("*") || calculatedPow.get(i).equals("/")) {
                double arg1 = processingArgument(variables, i + 1, calculatedPow); // exponent
                double arg2 = processingArgument(variables, i - 1, calculatedPow); // base

                if ((calculatedPow.get(i).equals("*"))) {
                    calculatedPow.set(i, String.valueOf(arg2 * arg1));
                } else {
                    calculatedPow.set(i, String.valueOf(arg2 / arg1));
                }
                calculatedPow.remove(i + 1);
                calculatedPow.remove((i--) - 1);
            }
        }
        return calculatedPow;
    }

    /**
     * The method checks the elements for the presence of a unary minus.
     * Checks variables and substitutes their values
     *
     * @param variables HashMap containing all the variables
     * @param i         the index of the item being checked
     * @param formula   equation formula
     * @return Returns the value of the function argument, if necessary with a unary minus or the value of a variable
     */
    private static double processingArgument(HashMap<String, Double> variables, int i, ArrayList<String> formula) {
        boolean minus = false;
        String argument;
        try {
            // Checking the unary minus
            if (formula.get(i).startsWith("-")) {
                minus = true;
                argument = formula.get(i).substring(1); // ignores the unary minus
            } else {
                argument = formula.get(i);
            }
            // checking a variable in HashMap
            if (variables.containsKey(argument)) {
                if (minus) {
                    return -variables.get(argument);
                }
                return variables.get(argument);
            } else {
                if (minus) {
                    return -Double.parseDouble(argument);
                }
                return Double.parseDouble(argument);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Misspelled operators or variables");
            System.exit(0);
            return 0;
        }
    }
}
