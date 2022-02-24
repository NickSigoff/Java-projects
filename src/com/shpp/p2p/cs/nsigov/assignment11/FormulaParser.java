package com.shpp.p2p.cs.nsigov.assignment11;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The class performs all the necessary operations with the formula obtained
 * from the command line arguments to transform it to a form convenient for computer calculations
 * First, the formula is transformed into a form that is convenient for parsing.
 * Then, using the logic of Dijkstra's algorithm, the formula is transformed into a postfix notation
 */
public class FormulaParser {

    /**
     * Counter for left parentheses
     */
    int counterLeftParentheses = 0;

    /**
     * Counter for right parentheses
     */
    int counterRightParentheses = 0;

    /**
     * Field for storing
     */
    private StringBuilder charBuffer = new StringBuilder();

    /**
     * formula received from the command line
     */
    private String formula;

    /**
     * Arguments received from the command line
     */
    private final String[] ARGS;

    /**
     * Formula prepared for parsing. It is an arrayList in each cell of
     * which has an operator or an operand(with unary minus) or a bracket or variable
     */
    private final ArrayList<String> PREPARED_FOR_PARSING = new ArrayList<>();

    /**
     * The formula is parsed according to the Dijkstra's algorithm
     */
    private final ArrayList<String> OUTPUT_RESULT = new ArrayList<>();

    /**
     * Class constructor, defines a class field formula and ARGS
     *
     * @param ARGS Arguments received from the command line
     */
    FormulaParser(String[] ARGS) {
        this.ARGS = ARGS;
        getFormulaFromArgs();
    }

    /**
     * Method for extracting a formula from a string of arguments
     */
    private void getFormulaFromArgs() {
        StringBuilder arguments = new StringBuilder();

        for (String s : ARGS) {
            arguments.append(s);
        }

        if (arguments.indexOf("=") != -1) {
            formula = arguments.substring(0, arguments.indexOf("=") - 1);
        } else {
            formula = arguments.substring(0);
        }
    }

    /**
     * The Method fills in constant PREPARED_FOR_PARSING according to rule -  each cell
     * corresponds to an operator, a bracket,an operand,a variable. Adds unary minuses to its values
     */
    private void preparingForParsing() {

        char[] formulaChar = checkStartWithMinus().toCharArray();

        for (int i = 0; i < formulaChar.length; i++) {
            char c = formulaChar[i];
            if ((String.valueOf(c)).matches("[()]")) {  // if the char is "(" or ")"
                processParentheses(c);

            } else if ((String.valueOf(c)).matches("\\+?-?\\*?/?\\^?")) {   // if char is an operator
                processOperators(i, c, formulaChar);

            } else if ((String.valueOf(c)).matches("[\\w.]")) {  // if a letter or a digit
                charBuffer.append(c);
            }
        }
        checkParentheses();

        PREPARED_FOR_PARSING.add(charBuffer.toString());  // for the last charBuffer
        removeEmpty();
    }

    /**
     * This method checks parentheses in the inputted formula, closes program if the counters are not equal
     */
    private void checkParentheses() {
        if (counterLeftParentheses != counterRightParentheses) {
            System.out.println(formula);
            System.err.println("Missing a bracket");
            System.exit(0);
        }
    }

    /**
     * This method adds to the charBuffer a character if the inputted character is +-*^//
     *
     * @param i           index of the character
     * @param operator    operator to add
     * @param formulaChar formula as a sequence of characters
     */
    private void processOperators(int i, char operator, char[] formulaChar) {
        if (i == 0) {
            charBuffer.append(operator);
        } else {
            if (formulaChar[i - 1] == '(') {
                charBuffer.append(operator);
            } else {
                PREPARED_FOR_PARSING.add(charBuffer.toString());
                charBuffer.delete(0, charBuffer.length());
                PREPARED_FOR_PARSING.add(String.valueOf(operator));
            }
        }
    }

    /**
     * This method adds to the charBuffer a character if the inputted character is ( or )
     *
     * @param parentheses a character ( or )
     */
    private void processParentheses(char parentheses) {

        if (parentheses == '(') {  //counting the number of brackets
            counterLeftParentheses++;
        } else {
            counterRightParentheses++;
        }
        PREPARED_FOR_PARSING.add(charBuffer.toString());
        charBuffer.delete(0, charBuffer.length());
        PREPARED_FOR_PARSING.add(String.valueOf(parentheses));
    }

    /**
     * The method takes a formula as input and determines
     * whether it starts with a minus for further operations
     */
    private String checkStartWithMinus() {
        if (formula.startsWith("-") && formula.length() > 1 && formula.charAt(1) == '(' && formula.endsWith(")")) {
            return formula.substring(1);
        }

        if (formula.startsWith("-") && formula.length() > 1 && formula.charAt(1) == '(' && !formula.endsWith(")")) {
            return "0" + formula;
        }
        return formula;
    }

    /**
     * The method removes empty cells from ArrayList
     */
    private void removeEmpty() {
        for (int i = 0; i < PREPARED_FOR_PARSING.size(); i++) {
            if (Objects.equals(PREPARED_FOR_PARSING.get(i), "")) {
                PREPARED_FOR_PARSING.remove(i);
            }
        }
    }

    /**
     * The main method for parsing a string. The parsing is based on Dijkstra's algorithm.
     * The algorithm uses two stacks for operands and operators, which are filled in a specific way
     */
    void parseFormula() {
        preparingForParsing();
        boolean flag = false;
        ArrayList<String> stack = new ArrayList<>();

        for (String word : PREPARED_FOR_PARSING) {
            if (word.matches("(-?\\d+\\.?\\d*)") || word.matches("-?\\w")) { //  // if that is an argument
                OUTPUT_RESULT.add(word);
            } else if (word.matches("\\(")) { // if "("
                stack.add(word);
            } else if (word.matches("\\)")) { // if ")"
                popFromStack(stack);

                // if operator is: +, -, *, sin, cos...
            } else if (word.matches("\\+?-?\\*?/?\\^?(sqrt)?(sin)?(cos)?(tan)?(atan)?(log10)?(log2)?")) {
                if (stack.size() == 0) {
                    stack.add(word);
                } else {
                    flag = pushStack(stack, word, flag);
                }
                if (flag && stack.size() == 0) {
                    stack.add(word);
                    flag = false;
                }
            }
        }
        madeOutputResult(stack);

    }

    /**
     * This method copies stack's items to the resulting ArrayList
     *
     * @param stack stack operator and operands
     */
    private void madeOutputResult(ArrayList<String> stack) {

        if (stack.size() != 0) {
            for (int i = stack.size() - 1; i >= 0; i--) {
                OUTPUT_RESULT.add(stack.get(i));
            }
        }
    }

    /**
     * This method pushes to the stack the inputted value
     * If the priority of the previous operator in the stack below, pops it into OUTPUT_RESULT
     *
     * @param stack stack operator and operands
     * @param word  the inputted value
     * @param flag  if the method came in and pops from the stack  - true
     * @return flag
     */
    private Boolean pushStack(ArrayList<String> stack, String word, boolean flag) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            String wordFromStack = stack.get(i);
            if (getPriority(word) <= getPriority(wordFromStack) && !wordFromStack.matches("\\(")) {
                OUTPUT_RESULT.add(wordFromStack);
                stack.remove(i);
                flag = true;
            } else {
                stack.add(word);
                break;
            }
        }
        return flag;
    }

    /**
     * This method pops values from the stack until it finds a parenthesis character
     *
     * @param stack stack operator and operands
     */
    private void popFromStack(ArrayList<String> stack) {
        for (int j = stack.size() - 1; j >= 0; j--) {
            String wordStack = stack.get(j);
            if (!wordStack.matches("\\(")) {
                OUTPUT_RESULT.add(wordStack);
                stack.remove(j);
            } else {
                stack.remove(j);
                break;
            }
        }
    }

    /**
     * The method calculates the priority of the operation to properly fill the stack
     *
     * @param s String contains operator like + - * sin ...
     * @return int number of priority. The higher the number, the higher the priority!!!
     */
    private int getPriority(String s) {
        return switch (s) {
            case "(", ")" -> 5;
            case "sin", "cos", "tan", "atan", "log10", "log2" -> 4;
            case "^", "sqrt" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    /**
     * The method returns the result of all operations of the class
     *
     * @return Parsed formula for calculating
     */
    ArrayList<String> getParsedFormula() {
        return OUTPUT_RESULT;
    }


    /**
     * The method returns the formula received from the command line
     */
    public String getFormula() {
        return formula;
    }
}



