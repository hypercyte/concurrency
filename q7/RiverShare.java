package q7;

/**
 * Concurrency Coursework: Java Implementation (Family Feuds)
 * by Mujahid Ahmed
 * 
 * RiverShare.java
 * 
 * This class contains the main method. Run using this class.
 * Therefore compile:   "javac q7/*.java"
 * then run:            "java q7.RiverShare"
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RiverShare {

    // ANSI escape codes for changing text colour.
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\033[0;95m";

    Thread flag_a;
    Thread flag_b;

    River r = new River();

    FlagA hatfields = new FlagA(this, r);
    FlagB mccoys    = new FlagB(this, r);

    RiverShare() {
        // Threads created when RiverShare object is initalised.
        flag_a = new Thread(hatfields);
        flag_b = new Thread(mccoys);
    }

    public int getFlagStatusA() {
        return r.getFlagStatusA();
    }

    public int getFlagStatusB() {
        return r.getFlagStatusB();
    }

    public int getIndicator() {
        return r.getIndicator();
    }

    // Prints current status of the system.
    public void printStatus() {
        System.out.println(PURPLE + "Family at River: "  + r.getAtRiver() + " (" + r.getFamilyName(r.getAtRiver()) + ")" + RESET);
        System.out.println("Hatfields' Flag Status: "    + r.getFlagStatusA() + " (" + r.flagStatusText(r.getFlagStatusA()) +")");
        System.out.println("McCoys' Flag Status: "       + r.getFlagStatusB() + " (" + r.flagStatusText(r.getFlagStatusB()) +")");
        System.out.println("Indicator Status: "          + r.getIndicator() + '\n');
        System.out.println("Enter command:");
    }

    public static void main(String[] args) throws IOException {
        RiverShare rivershare = new RiverShare();
        rivershare.flag_a.start();
        rivershare.flag_b.start();
        
        // Reader to read input commands continuously.
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in)
        );
        
        // Welcome message.
        System.out.println(CYAN + "\nWelcome to the Hatfields and McCoys river sharing system.\n" + 
                        PURPLE + "Type \"help\" for a list of commands.\n" +
                        RESET + "Enter command:");

        // Continously scan for new input.
        while (true) {
            String command = reader.readLine();
            
            // Command switch.
            switch(command) {
                case "raise a":
                    rivershare.hatfields.requestRaise();
                    break;
                case "raise b":
                    rivershare.mccoys.requestRaise();
                    break;
                case "lower a":
                    rivershare.hatfields.requestLower();
                    break;
                case "lower b":
                    rivershare.mccoys.requestLower();
                    break;
                case "status":
                    System.out.print('\n');
                    rivershare.printStatus();
                    break;
                case "help":
                    System.out.println(CYAN + "\nList of commands: (format: command - information)\n" + RESET +
                                        PURPLE + "status "  + RESET + "- See the status of both flags and which family is currently allowed at the river.\n" +
                                        PURPLE + "raise a " + RESET + "- Raise the Hatfields' family flag.\n" +
                                        PURPLE + "lower a " + RESET + "- Lower the Hatfields' family flag.\n" +
                                        PURPLE + "raise b " + RESET + "- Raise the McCoys' family flag.\n" +
                                        PURPLE + "lower b " + RESET + "- Lower the McCoys' family flag.\n" +
                                        PURPLE + "help "    + RESET + "- See the list of commands again.\n");
                    System.out.println("Enter command:");
                    break;
                default:
                    System.out.println(RED + "Error: Invalid command. Type help for a list of commands.\n" + RESET);
                    System.out.println("Enter command:");
            }
        }
    }

}
