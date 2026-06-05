package logging_middleware;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class RequestLogger {
    private static final String LOG_FILE = "activity_logs.txt";

    /**
     * Intercepts and logs incoming HTTP request performance metrics.
     * @param endpoint The targeted API URL path
     * @param method The HTTP Verb (GET/POST)
     * @param statusCode The server response code (e.g., 200, 401)
     * @param executionTimeMs The time taken to process the response in milliseconds
     */
    public static void logRequest(String endpoint, String method, int statusCode, long executionTimeMs) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            String logEntry = String.format("[%s] %s %s - Status: %d - Response Time: %dms",
                    LocalDateTime.now(), method, endpoint, statusCode, executionTimeMs);
            
            pw.println(logEntry);
            System.out.println("[Middleware Log Saved]: " + logEntry);
            
        } catch (IOException e) {
            System.err.println("Failed to write to middleware log file: " + e.getMessage());
        }
    }
}
