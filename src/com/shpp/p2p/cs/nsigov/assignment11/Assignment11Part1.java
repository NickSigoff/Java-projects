package com.shpp.p2p.cs.nsigov.assignment11;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The program implements a calculator that will calculate mathematical expressions.
 * You can also enter variables that will contain some value.
 * Contains the main method and command sequence
 */
public class Assignment11Part1 {
    private static HashMap<String, ArrayList<String>> memory = new HashMap<>();

    /**
     * The main method contains the necessary sequence of actions to execute the program
     *
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            FormulaParser formulaParser = new FormulaParser(args);
            VariablesManager variablesManager = new VariablesManager(args);

            //Checking whether the program memory contains data on such a formula
            if (!memory.containsKey(formulaParser.getFormula())) {

                System.out.println("Formula received from command line arguments in 'pure' form: " + formulaParser.getFormula());
                formulaParser.parseFormula();
                System.out.println("Parsed formula: " + formulaParser.getParsedFormula());
                variablesManager.parseVariables();


                CalculateManager calculateManager = new CalculateManager(formulaParser.getParsedFormula(),
                        variablesManager.getVariables(), formulaParser.getFormula());
                calculateManager.calculate();

                memory.put(formulaParser.getFormula(), formulaParser.getParsedFormula()); // write the new formula into memory
                System.out.println(calculateManager);  // output result in console
            } else {
                variablesManager.parseVariables();
                CalculateManager calculateManager = new CalculateManager(memory.get(formulaParser.getFormula()),
                        variablesManager.getVariables(),
                        formulaParser.getFormula());
                calculateManager.calculate();
                System.out.println(calculateManager); // output result in console
            }
        } else {
            System.err.println("You haven't entered anything");
            System.exit(0);
        }
    }
}