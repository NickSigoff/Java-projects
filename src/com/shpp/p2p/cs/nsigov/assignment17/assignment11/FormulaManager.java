package com.shpp.p2p.cs.nsigov.assignment17.assignment11;

import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyArrayList;
import java.util.Objects;

/**
 * The class performs all the necessary operations with the formula obtained
 * from the command line arguments to transform it to a form convenient for computer calculations
 * First, the formula is transformed into a form that is convenient for parsing.
 * Then, using the logic of Dijkstra's algorithm, the formula is transformed into a postfix notation
 */
public class FormulaManager {

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
    private final MyArrayList<String> PREPARED_FOR_PARSING = new MyArrayList<>();

    /**
     * The formula is parsed according to the Dijkstra's algorithm
     */
    private final MyArrayList<String> OUTPUT_RESULT = new MyArrayList<>();

    /**
     * Class constructor, defines a class field formula and ARGS
     *
     * @param ARGS Arguments received from the command line
     */
    FormulaManager(String[] ARGS) {
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
    void preparingForParsing() {
        StringBuilder word = new StringBuilder();
        int counterLeftBrackets = 0;
        int counterRightBrackets = 0;
        char[] formulaChar = checkStartWithMinus().toCharArray();

        for (int i = 0; i < formulaChar.length; i++) {
            char c = formulaChar[i];
            if ((String.valueOf(c)).matches("[()]")) {  // if char is "(" or ")"
                if (c == '(') {  //counting the number of brackets
                    counterLeftBrackets++;
                } else {
                    counterRightBrackets++;
                }
                PREPARED_FOR_PARSING.add(word.toString());
                word.delete(0, word.length());
                PREPARED_FOR_PARSING.add(String.valueOf(c));
            } else if ((String.valueOf(c)).matches("\\+?-?\\*?/?\\^?")) {   // if char is operator
                if (i == 0) {
                    word.append(c);
                } else {
                    if (formulaChar[i - 1] == '(') {
                        word.append(c);
                    } else {
                        PREPARED_FOR_PARSING.add(word.toString());
                        word.delete(0, word.length());
                        PREPARED_FOR_PARSING.add(String.valueOf(c));
                    }
                }
            } else if ((String.valueOf(c)).matches("[\\w.]")) {  // if letter or digit
                word.append(c);
            }
        }
        if (counterLeftBrackets != counterRightBrackets) {
            System.out.println(formula);
            System.err.println("Missing a bracket");
            System.exit(0);
        }
        PREPARED_FOR_PARSING.add(word.toString());  // for the last word
        removeEmpty();
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
        //This boolean variable is used to "bind" unary minuses to their values.
        boolean flag = false;
        MyArrayList<String> stack = new MyArrayList<>();
        for (String word : PREPARED_FOR_PARSING) {
            if (word.matches("(-?\\d+\\.?\\d*)") || word.matches("-?\\w")) { //  // if that is an argument
                OUTPUT_RESULT.add(word);
            } else if (word.matches("\\(")) { // if "("
                stack.add(word);
            } else if (word.matches("\\)")) { // if ")"
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
                // if operator like + - * sin cos...
            } else if (word.matches("\\+?-?\\*?/?\\^?(sqrt)?(sin)?(cos)?(tan)?(atan)?(log10)?(log2)?")) {
                if (stack.size() == 0) {
                    stack.add(word);
                } else {
                    for (int k = stack.size() - 1; k >= 0; k--) {
                        String wordFromStack = stack.get(k);
                        if (getPriority(word) <= getPriority(wordFromStack) && !wordFromStack.matches("\\(")) {
                            OUTPUT_RESULT.add(wordFromStack);
                            stack.remove(k);
                            flag = true;
                        } else {
                            stack.add(word);
                            break;
                        }
                    }
                }
                if (flag && stack.size() == 0) {
                    stack.add(word);
                    flag = false;
                }
            }
        }
        // Copying stack items to the resulting ArrayList
        if (stack.size() != 0) {
            for (int i = stack.size() - 1; i >= 0; i--) {
                String s = stack.get(i);
                OUTPUT_RESULT.add(s);
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
        int priority = switch (s) {
            case "(", ")" -> 5;
            case "sin", "cos", "tan", "atan", "log10", "log2" -> 4;
            case "^", "sqrt" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
        return priority;
    }

    /**
     * The method returns the result of all operations of the class
     *
     * @return Parsed formula for calculating
     */
    MyArrayList<String> getParsedFormula() {
        return OUTPUT_RESULT;
    }


    /**
     * The method returns the formula received from the command line
     */
    public String getFormula() {
        return formula;
    }
}



