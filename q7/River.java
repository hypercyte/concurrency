package q7;

/**
 * Concurrency Coursework: Java Implementation (Family Feuds)
 * by Mujahid Ahmed
 * 
 * River.java
 * 
 * This class is mapped the model by the RIVER process
 * acting as the Monitor for the system.
 */

public class River {

    private int flag_a = 0;
    private int flag_b = 0;
    private int indicator = 1;

    private int at_river = 0; // Equivalent of "a.washcows" or "b.washcows" actions in the model.
                              // Indicating which family is allowed to be at the river currently.
                              // 0 = None, 1 = Hatfields, 2 = McCoys.
    
    synchronized void raiseFlagA() throws InterruptedException {
        
        // Mapped to choice 1 in the RIVER monitor model. (a.raise -> a.washcows)
        // Flag A raised, indicator set to other family, and Family A goes to the river as no other flags were raised.
        if (flag_b == 0 && indicator == 1) {
            while(!(flag_a==0 && flag_b == 0 && indicator == 1)) wait();
            flag_a = 1;
            flag_b = 0;
            indicator = 2;
            at_river = 1; // a.washcows - Hatfields can go to the river and wash cows.
            System.out.println(RiverShare.GREEN + "Success: Flag A (Hatfields) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

        // Mapped to choice 5 in the RIVER monitor model. (a.raise -> a.washcows)
        // Flag A raised, and Family A goes to the river as no other flags were raised. Indicator already set.
        else if (flag_b == 0 && indicator == 2) {
            while(!(flag_a==0 && flag_b == 0 && indicator == 2)) wait();
            flag_a = 1;
            flag_b = 0;
            indicator = 2;
            at_river = 1; // a.washcows - Hatfields can go to the river and wash cows.
            System.out.println(RiverShare.GREEN + "Success: Flag A (Hatfields) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

        // Mapped to choice 7 in the RIVER monitor model. (a.raise)
        // Flag A raised but Flag B was already raised. Family A must wait before going to the river.
        else if (flag_b == 1 && indicator == 1) {
            while(!(flag_a==0 && flag_b == 1 && indicator == 1)) wait();
            flag_a = 1;
            flag_b = 1;
            indicator = 2;
            System.out.println(RiverShare.GREEN + "Success: Flag A (Hatfields) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

    }

    synchronized void lowerFlagA() throws InterruptedException {

        // Mapped to choice 3 in the RIVER monitor model. (a.lower -> b.washcows)
        // Flag B was raised while Flag A was raised. So Family B goes to the river after flag A is lowered.
        if (flag_b == 1 && indicator==1) {
            while(!(flag_a==1 && flag_b==1 && indicator==1)) wait();
            flag_a = 0;
            flag_b = 1;
            indicator = 1;
            at_river = 2; // b.washcows - McCoys flag is raised in queue, therefore it is their turn to go to the river.
            System.out.println(RiverShare.GREEN + "Success: Flag A (Hatfields) has been lowered.\n" + RiverShare.RESET);
            notifyAll();
        }

        // Mapped to choice 4 in the RIVER monitor model. (a.lower)
        // Flag A lowered when no other flags are raised.
        else if (flag_b==0 && indicator==2) {
            while(!(flag_a==1 && flag_b==0 && indicator==2));
            flag_a = 0;
            at_river = 0; // No flags raised anymore, so no family can wash their cows until a flag is raised.
            System.out.println(RiverShare.GREEN + "Success: Flag A (Hatfields) has been lowered.\n" + RiverShare.RESET);
            notifyAll();
        }
    }

    synchronized void raiseFlagB() throws InterruptedException {

        // Mapped to choice 6 in the RIVER monitor model. (b.raise -> b.washcows)
        // Flag B raised, indicator set to other family, and Family B goes to the river as no other flags were raised.
        if (flag_a == 0 && indicator == 2) {
            while(!(flag_a==0 && flag_b == 0 && indicator == 2)) wait();
            flag_a = 0;
            flag_b = 1;
            indicator = 1;
            at_river = 2; // b.washcows - McCoys can go to the river and wash cows.
            System.out.println(RiverShare.GREEN + "Success: Flag B (McCoys) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

        // Mapped to choice 10 in the RIVER monitor model. (b.raise -> b.washcows)
        // Flag B raised, and Family B goes to the river as no other flags were raised. Indicator already set.
        else if (flag_a == 0 && indicator == 1) {
            while(!(flag_a==0 && flag_b == 0 && indicator == 1)) wait();
            flag_a = 0;
            flag_b = 1;
            indicator = 1;
            at_river = 2; // b.washcows - McCoys can go to the river and wash cows.
            System.out.println(RiverShare.GREEN + "Success: Flag B (McCoys) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

        // Mapped to choice 2 in the RIVER monitor model. (b.raise)
        // Flag B raised while Flag A is already raised.
        else if (flag_a == 1 && indicator == 2) {
            while(!(flag_a==1 && flag_b==0 && indicator == 2)) wait();
            flag_a = 1;
            flag_b = 1;
            indicator = 1;
            System.out.println(RiverShare.GREEN + "Success: Flag B (McCoys) has been raised.\n" + RiverShare.RESET);
            notifyAll();
        }

    }

    synchronized void lowerFlagB() throws InterruptedException {

        // Mapped to choice 8 in the RIVER monitor model. (b.lower -> a.washcows)
        // Flag A was raised while Flag B was raised. So Family A goes to the river after flag B is lowered.
        if (flag_a == 1 && indicator==2) {
            while(!(flag_a==1 && flag_b==1 && indicator==2)) wait();
            flag_a = 1;
            flag_b = 0;
            indicator = 2;
            at_river = 1; // a.washcows - Hatfields flag is raised in queue, therefore it is their turn to go to the river.
            System.out.println(RiverShare.GREEN + "Success: Flag B (McCoys) has been lowered.\n" + RiverShare.RESET);
            notifyAll();
        }
        // Mapped to choice 9 in the RIVER monitor model. (b.lower)
        // Lower flag B (when flag A was not raised)
        else if (flag_a==0 && indicator==1) {
            while(!(flag_a==0 && flag_b==1 && indicator==1));
            flag_b = 0;
            at_river = 0; // No flags raised anymore, so neither family can wash their cows until they've raised their flag.
            System.out.println(RiverShare.GREEN + "Success: Flag B (McCoys) has been lowered.\n" + RiverShare.RESET);
            notifyAll();
        }

    }

    //
    // Getters for status information.
    //

    public int getFlagStatusA() {
        return flag_a;
    }

    public int getFlagStatusB() {
        return flag_b;
    }

    public int getIndicator() {
        return indicator;
    }

    public int getAtRiver() {
        return at_river;
    }

    public String getFamilyName(int n) {
        switch(n) {
            case 1:
                return "Hatfields";
            case 2:
                return "McCoys";
            default:
                return "Neither";
        }
    }

    public String flagStatusText(int n) {
        switch(n) {
            case 0:
                return RiverShare.RED + "Lowered" + RiverShare.RESET;
            case 1:
                return RiverShare.GREEN + "Raised" + RiverShare.RESET;
            default:
                return "Error: Flag status must be either 0 or 1!";
        }
    }
}
