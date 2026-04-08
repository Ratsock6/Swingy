package fr.aallouv.swingy;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.err.println("Usage: java -jar swingy.jar [console|gui]");
            System.exit(1);
        }

        boolean guiMode = args[0].equals("gui");

        if (guiMode) {
            System.out.println("[GUI mode] - not yet implemented");
        } else {
            System.out.println("[Console mode] - not yet implemented");
        }
    }
}