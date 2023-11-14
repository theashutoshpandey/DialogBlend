package com.dialog.blend.logX;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * This class represents a custom logger implementation, providing various
 * logging levels
 * and supporting both console and file logging.
 * <p>
 * The logs are formatted with timestamp, log level, class name, line number,
 * and log message.
 * The logs are appended to a log file and optionally displayed in the console.
 * <p>
 * The default log level is ALL, and you can change it using
 * {@link #setLogLevel(LogLevel)} method.
 * The logger provides methods for logging different levels of messages such as
 * TRACE, DEBUG, INFO, WARN, ERROR, and FATAL.
 * The log file is automatically rotated when it exceeds a specified size limit.
 * Additionally, this class can handle logging exceptions with stack traces.
 * <p>
 * This logger is designed as a singleton, and you can get an instance using
 * {@link #getLogger()}.
 *
 * @author Ashutosh Pandey
 */
public class LogOperator implements Logger {

    // Singleton instance
    private static LogOperator instance;

    private LogOperator() {
    }

    /**
     * Retrieves the singleton instance of the LogOperator class.
     */
    public static LogOperator getLogger() {
        if (instance == null)
            instance = new LogOperator();
        return instance;
    }

    private static final String LOG_FILE_PATH = System.getProperty("user.home") + File.separator + APP_NAME
            + "Logs.log";

    private LogLevel logLevel = LogLevel.ALL; // Default log level is ALL

    /**
     * Sets the log level for filtering log messages.
     */
    @Override
    public void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    /**
     * Supported log levels.
     */
    @Override
    public void trace(String trace) {
        log(LogLevel.TRACE, trace, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    @Override
    public void debug(String debug) {
        log(LogLevel.DEBUG, debug, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    @Override
    public void info(String info) {
        log(LogLevel.INFO, info, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    @Override
    public void warn(String warn) {
        log(LogLevel.WARN, warn, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    @Override
    public void error(String error) {
        log(LogLevel.ERROR, error, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    /**
     * Logs an ERROR level message with the stack trace of the provided exception.
     */
    @Override
    public void error(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log(LogLevel.ERROR, sw.toString(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    @Override
    public void fatal(String fatal) {
        log(LogLevel.FATAL, fatal, Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    /**
     * Main log method that appends log messages based on the log level
     */
    private void log(LogLevel level, String message, int lineNumber) {
        if (level.ordinal() >= logLevel.ordinal()) {
            String className = Thread.currentThread().getStackTrace()[3].getClassName();
            // String methodName =
            // Thread.currentThread().getStackTrace()[3].getMethodName();
            String formattedMessage = String.format(LOG_FORMAT, getCurrentTimestamp(), level,
                    className, lineNumber, message);

            this.fileAppender(formattedMessage);
            this.consoleAppender(formattedMessage);
        }
    }

    /**
     * Writes a header banner to the log file and console.
     * Banner encoded in Base64 - change the encoded string for custom header
     */
    public LogOperator writeHeaderBanner() {
        try {
            String decodedBanner = new String(Base64.getDecoder().decode(BANNER));
            this.fileAppender(decodedBanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Helper method to get the current timestamp
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * Helper method to append log trace messages to the console and file for
     * session whole logs
     */
    public void traceBackAppender(String logMessage, LogLevel level) {
        if (level.ordinal() >= logLevel.ordinal()) {
            this.fileAppender(logMessage);
            this.consoleAppender(logMessage);
        }
    }

    /**
     * Helper method to append log messages to the console
     */
    private void consoleAppender(String logMessage) {
        System.out.print(logMessage);
    }

    /**
     * Helper method to append log messages to the log file
     */
    private void fileAppender(String logMessage) {
        checkFileSizeAndRotate();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to check the file size and rotate logs if needed
     */
    private void checkFileSizeAndRotate() {
        try {
            File currentLogFile = new File(LOG_FILE_PATH);

            if (currentLogFile == null || currentLogFile.length() >= MAX_FILE_SIZE)
                backupLogFile(currentLogFile); // backup logs

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Backs up the log file by renaming it with a timestamp.
     */
    private void backupLogFile(File logFile) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyy_MM_dd_HH_mm_ss"));
        File backupFile = new File(logFile.getParent(), APP_NAME + timestamp + ".log");
        logFile.renameTo(backupFile);
    }

}
