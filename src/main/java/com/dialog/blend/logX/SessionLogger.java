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
 * SessionLogger is a utility class for logging messages with different log
 * levels and timestamps.
 * 
 * -It provides functionality to log messages to both console and log files.
 *
 * @author Ashutosh Pandey
 */
public class SessionLogger implements Logger {

    private String sessionLogFilePath;
    private String sessionLogFolderPath;

    private LogOperator logger = LogOperator.getLogger();

    /**
     * Creates a SessionLogger instance with the given session identifier and
     * additional information.
     *
     * @param session The session identifier.
     * @param extra   Additional information.
     */
    public SessionLogger(String botWorkSpace, String session, String extra) {
        this.sessionLogFolderPath = botWorkSpace + File.separator + APP_NAME
                + this.getClass().getSimpleName() + File.separator + sessionLogFolderDate() + File.separator;
                
        this.sessionLogFilePath = sessionLogFolderPath + session.replaceAll("\\.", "_") + "_" + extra + "_"
                + formattedCurrentTimestamp() + ".log";

        this.createSessionLogFolder();
    }

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
     * Main log method that appends log messages based on the log level.
     */
    private void log(LogLevel level, String message, int lineNumber) {
        if (level.ordinal() >= logLevel.ordinal()) {
            String className = Thread.currentThread().getStackTrace()[3].getClassName();
            String formattedMessage = String.format(LOG_FORMAT, getCurrentTimestamp(), level,
                    className, lineNumber, message);

            logger.traceBackAppender(formattedMessage, level);
            this.fileAppender(formattedMessage);
        }
    }

    /**
     * Writes a header banner to the log file and console.
     * Banner encoded in Base64 - change the encoded string for custom header
     */
    public SessionLogger writeHeaderBanner() {
        try {
            String decodedBanner = new String(Base64.getDecoder().decode(BANNER));
            this.fileAppender(decodedBanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Helper method to append log messages to the log file.
     */
    public void fileAppender(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sessionLogFilePath, true))) {
            writer.write(logMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the date for the session log folder.
     */
    private String sessionLogFolderDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }

    /**
     * Generates the formatted timestamp for the log file name.
     */
    private String formattedCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
    }

    /**
     * Gets the current timestamp in a formatted string.
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * Creates the session log folder if it does not exist.
     */
    private void createSessionLogFolder() {
        try {
            File file = new File(sessionLogFolderPath);

            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
