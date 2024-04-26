package com.tibame201020.backend.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * log highlight
 */
public class CustomLoggerHighlighting extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.DEBUG_INT -> ANSIConstants.CYAN_FG;
            case Level.ERROR_INT, Level.TRACE_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
