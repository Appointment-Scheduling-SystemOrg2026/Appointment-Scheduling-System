package com.scheduling.app;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A custom log formatter for displaying log messages on the console.
 * This class formats log records to output only the log message followed by a newline.
 * It extends the standard Formatter class to provide a simple format for console logging.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class SimpleConsoleFormatter extends Formatter {
    
    /**
     * Formats a log record into a string that will be displayed on the console.
     * This implementation returns only the log message followed by a newline.
     * 
     * @param record The log record to be formatted.
     * @return A string representing the log message.
     */
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + System.lineSeparator();
    }
}