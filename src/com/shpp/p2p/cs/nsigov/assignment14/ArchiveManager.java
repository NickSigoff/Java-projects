package com.shpp.p2p.cs.nsigov.assignment14;

/**
 * The class manages archiving and unzipping documents.
 * Based on the input data, the program decides what action
 * to perform archiving or unpacking. If there are no input parameters,
 * the default setting is archiving the test.txt file.
 */
public class ArchiveManager {

    /**
     * Additional parameter coming as the first parameter
     */
    private String additionalParameter = "";

    /**
     * The name of the input file, obtained from the command line or set by default
     */
    private String inputFileName;

    /**
     * The name of the output file, obtained from the command line or set by default
     */
    private String outputFileName;

    /**
     * The class constructor defines the class fields for the input and output file
     *
     * @param args command line arguments
     */
    ArchiveManager(String[] args) {
        switch (args.length) {
            case 0:
                inputFileName = "test.txt";
                outputFileName = "test.par";
                break;
            case 1:
                inputFileName = args[0];
                outputFileName = chooseOutputFileName(args);
                break;
            case 2:
                if (args[0].matches("-[ua]")) {
                    inputFileName = args[1];
                    outputFileName = chooseOutputFileName(args);
                } else {
                    inputFileName = args[0];
                    outputFileName = args[1];
                }
                break;
            case 3:
                if (args[0].matches("-[ua]")) {
                    additionalParameter = args[0];
                    inputFileName = args[1];
                    outputFileName = args[2];
                    break;
                }
            default:
                System.out.println("Invalid input");
        }
        System.out.println("Input file name " + inputFileName);
        System.out.println("Output file name " + outputFileName);
        System.out.println();
    }

    /**
     * The method determines what name to set for the output file
     *
     * @param args command line arguments
     * @return String with output file name
     */
    private String chooseOutputFileName(String[] args) {
        if (args[0].matches(".+\\..+\\.par")) {
            String[] words = args[0].split("\\.");
            return words[0] + "." + words[1];
        } else if (args[0].matches(".+\\.par")) {
            String[] words = args[0].split("\\.");
            return words[0] + ".uar";
        } else if (args[0].matches(".+\\..+")) {
            String[] words = args[0].split("\\.");
            return words[0] + ".par";
        } else if (args[0].matches("-[a]")) {
            String[] words = args[1].split("\\.");
            return words[0] + ".par";
        } else if (args[0].matches("-[u]")) {
            String[] words = args[1].split("\\.");
            return words[0] + ".uar";
        }
        return null;
    }

    /**
     * The method by the input name determines which operation needs to be performed unpacking or archiving
     */
    void chooseAction() {
        if (inputFileName.matches(".+\\.par") && (additionalParameter.equals("") || additionalParameter.equals("-u"))) {
            DataExtractor dataExtractor = new DataExtractor(inputFileName, outputFileName);
            dataExtractor.unarchive();
        } else if (inputFileName.matches(".+\\..+") && (additionalParameter.equals("") || additionalParameter.equals("-a"))) {
            DataCompressor dataCompressor = new DataCompressor(inputFileName, outputFileName);
            dataCompressor.archive();
        }
    }
}