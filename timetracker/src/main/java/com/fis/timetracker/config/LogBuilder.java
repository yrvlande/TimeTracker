package com.fis.timetracker.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

public class LogBuilder {

    private static final Pattern whiteSpacePattern = Pattern.compile("[\t\n\r]+", Pattern.MULTILINE);

    HashMap<String, String> details = new HashMap<>();

    public static LogBuilder logBuilder(String eventCode) {
        LogBuilder lb = new LogBuilder();
        lb.eventCode(eventCode);
        return lb;
    }

    public LogBuilder eventCode(String eventCode) {
        details.put("eventCode", eventCode);
        return this;
    }

    public LogBuilder logEvent(String logEvent) {
        details.put("event", logEvent);
        return this;
    }

    public LogBuilder throwable(Throwable t) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        t.printStackTrace(printWriter);
        printWriter.flush();

        details.put("throwable", whiteSpacePattern.matcher(writer.toString()).replaceAll("\\\\n"));
        return this;
    }

    public LogBuilder message(String message) {
        details.put("message", message);
        return this;
    }

    public String toString() {
        return details.toString();
    }
}