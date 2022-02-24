package com.shpp.p2p.cs.nsigov.assignment11;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class is responsible for handling the variables received from the command line.
 * The class has methods for parsing, returning a value and printing the result to the console for testing
 */
public class VariablesManager {

    /**
     * The whole array of arguments from the command line
     */
    private final String[] ARGS;

    /**
     * HashMap of parsed values of variables
     */
    private final HashMap<String, Double> VARIABLES = new HashMap<>();

    /**
     * The class constructor defines the ARGS class field
     *
     * @param args The whole array of arguments from the command line
     */
    VariablesManager(String[] args) {
        this.ARGS = args;
    }

    /**
     * This method creates HashMap, which contains all the variables
     * specified in the arguments. Where the variable name is a key
     */
    void parseVariables() {
        ArrayList<String> allVariables = getVariablesFromArgs();

        for (String variableAndValue : allVariables) {
            String[] separatedVariableAndValue = variableAndValue.split("=");  //Split the string by character - '='
            try {
                String variable = separatedVariableAndValue[0];
                double meaning = Double.parseDouble(separatedVariableAndValue[1]);
                VARIABLES.put(variable, meaning);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Invalid expression or variables input");
                System.exit(0);
            } catch (NumberFormatException e) {
                System.err.println("Invalid expression input(missing operator)");
                System.exit(0);
            }
        }
    }

    /**
     * Method for extracting variables with them meanings from a string of arguments
     *
     * @return ArrayList contains variables
     */
    private ArrayList<String> getVariablesFromArgs() {
        StringBuilder arguments = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        String varNameValue;
        // String without spaces
        for (String s : ARGS) {
            arguments.append(s);
        }
        if (arguments.indexOf("=") != -1) {
            String variables = arguments.substring(arguments.indexOf("=") - 1); // made string
            // if string contains "="
            while (variables.contains("=")) {
                varNameValue = "";
                int indexCharEquals = variables.indexOf('=');
                // add a name of variable and "="
                try {
                    if (!variables.substring(indexCharEquals - 1, indexCharEquals).matches("[a-z]")) {
                        System.err.println("You forgot the name of the variable");
                        System.exit(0);
                    }
                    varNameValue = varNameValue + variables.charAt(indexCharEquals - 1) + variables.charAt(indexCharEquals);
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Invalid variables input");
                    System.exit(0);
                }
                // iterates over the following characters until meets a letter
                for (int i = variables.indexOf('=') + 1; i < variables.length(); i++) {
                    if (variables.substring(i, i + 1).matches("[\\d.?]")) {
                        varNameValue += variables.substring(i, i + 1);
                    } else {
                        break;
                    }
                }
                variables = variables.replaceFirst("[a-z]=\\d+\\.?\\d*", "");
                result.add(varNameValue);
            }
            return result;
        }
        // if symbol "=" is not found
        return result;
    }

    /**
     * Method returns the value of HashMap variables
     *
     * @return HashMap of parsed values of variables
     */
    HashMap<String, Double> getVariables() {
        return VARIABLES;
    }
}
