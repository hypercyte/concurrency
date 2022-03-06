package q7;

/**
 * Concurrency Coursework: Java Implementation (Family Feuds)
 * by Mujahid Ahmed
 * 
 * FlagA.java
 */

class FlagA implements Runnable {

    RiverShare view;
    River control;

    private volatile boolean raiseRequest = false;
    private volatile boolean lowerRequest = false;

    // Constructor
    FlagA(RiverShare v, River r) {
        view = v;
        control = r;
    }

    // When raise command is input.
    public void requestRaise() {
        // Guard to prevent raising when already raised.
        if (control.getFlagStatusA()==1) {
            System.out.println(RiverShare.RED + "Error: The Hatfields' flag is already raised!" + RiverShare.RESET + '\n');
            System.out.println("Enter command:");
            return;
        }
        raiseRequest = true;
    }

    // When lower command is input.
    public void requestLower() {
        // Guard to prevent lowering when already lowered.
        if (control.getFlagStatusA()==0) {
            System.out.println(RiverShare.RED + "Error: The Hatfields' flag is already raised!" + RiverShare.RESET + '\n');
            System.out.println("Enter command:");
            return;
        }
        // Guard to prevent lowering when waiting in queue.
        if (control.getFlagStatusA()==1 && control.getFlagStatusB()==1 && control.getIndicator()==2) {
            System.out.println(RiverShare.RED + "Error: Wait for the other flag to be lowered first." + RiverShare.RESET + '\n');
            System.out.println("Enter command:");
            return;
        }
        lowerRequest = true;
    }

    public void run() {
        try {
            while (true) {
                while(!raiseRequest); // While there is no raise flag command, wait until there is before continuing.
                control.raiseFlagA();
                view.printStatus();
                raiseRequest = !raiseRequest; // After flag is raised, reset raise command.

                while(!lowerRequest); // While there is no lower flag command, wait until there is before continuing.
                control.lowerFlagA();
                view.printStatus();
                lowerRequest = !lowerRequest; // After flag is lowered, reset lower command.
            }
        } catch (InterruptedException e) {}
    }

}
