package manu.util;

public class LoggerUtil {

    public static void logError(String origem, Exception e) {
        System.err.println("[ERRO] " + origem + ": " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            System.err.println("    at " + element);
        }
    }
}
