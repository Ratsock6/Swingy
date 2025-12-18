package fr.aallouv;

import fr.aallouv.utils.GenerateRandom;
import fr.aallouv.utils.Logger;

import java.io.IOException;


public class App {

    private static App app;
    private final Logger logger;

    public App() throws IOException {
        app = this;
        logger = new Logger();
        System.out.println(GenerateRandom.random(100)  + " / " + GenerateRandom.random(0) + " / " + GenerateRandom.random(10));
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Hello from My Java Application!");
        System.out.println("This JAR was built with Maven using 'mvn clean package'");

        new App();
    }

    public static App getApp() {
        return app;
    }

    public Logger getLogger() {
        return logger;
    }
}
