package fr.aallouv.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.net.URISyntaxException;

public class Logger {

    private final FileWriter writer;

    public Logger() throws IOException {
        File jarDir = getJarDirectory();

        File logDir = new File(jarDir, "logs_swingy");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logFile = new File(logDir, /*LocalDateTime.now().toString()*/ "log");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        this.writer = new FileWriter(logFile, true); // true = append
    }

    public void log(String log) {
        String finalLogMessage = LocalDateTime.now().toString() + " [LOGS]: " + log + "\n";
        try {
            writer.write(finalLogMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void space() {
        try {
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        writer.close();
    }

    public File getJarDirectory() {
        try {
            return new File(
                    Logger.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            ).getParentFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to locate the jar", e);
        }
    }

}
