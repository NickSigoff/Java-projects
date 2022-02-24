package com.shpp.p2p.cs.nsigov.assignment17.assignment11;

import com.shpp.p2p.cs.nsigov.assignment17.MyHashMap;
import com.shpp.p2p.cs.nsigov.assignment17.assignment16.MyArrayList;

/**
 * The program implements a calculator that will calculate mathematical expressions.
 * You can also enter variables that will contain some value.
 * Contains the main method and command sequence
 */
public class Assignment11Part1 {
    private static MyHashMap<String, MyArrayList<String>> memory = new MyHashMap<>();

    /**
     * The main method contains the necessary sequence of actions to execute the program
     *
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        //An object of the class FormulaManager is created in which the formula is prepared for calculation
        try {
            FormulaManager formulaManager = new FormulaManager(args);
            //An object of the class VariablesManager is created in which the variables are parsed into a HashMap for calculation
            VariablesManager variablesManager = new VariablesManager(args);

            //Checking whether the program memory contains data on such a formula
            if (!memory.containsKey(formulaManager.getFormula())) {

                //Sequence of operations for calculation
                System.out.println("Formula received from command line arguments in 'pure' form: " + formulaManager.getFormula());
                formulaManager.preparingForParsing();
                formulaManager.parseFormula();
                System.out.println("Parsed formula: " + formulaManager.getParsedFormula());
                variablesManager.parseVariables();

                //An object of the class CalculateManager is created in which all calculations take place
                CalculateManager calculateManager = new CalculateManager(formulaManager.getParsedFormula(),
                        variablesManager.getVariables(),
                        formulaManager.getFormula());
                calculateManager.calculate();

                memory.put(formulaManager.getFormula(), formulaManager.getParsedFormula()); // write the new formula into memory
                System.out.println(calculateManager);  // output result in console
            } else {
                variablesManager.parseVariables();
                CalculateManager calculateManager = new CalculateManager(memory.get(formulaManager.getFormula()),
                        variablesManager.getVariables(),
                        formulaManager.getFormula());
                calculateManager.calculate();
                System.out.println(calculateManager); // output result in console
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You haven't entered anything");
            System.exit(0);
        }
    }
}