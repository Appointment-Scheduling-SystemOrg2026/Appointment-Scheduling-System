package com.scheduling.app;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimpleConsoleFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + System.lineSeparator();
    }
}