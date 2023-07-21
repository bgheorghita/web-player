package dev.gb.webplayerserver.configs.loggers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class CustomForegroundCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT -> ANSIConstants.RED_FG;
            case Level.INFO_INT -> ANSIConstants.BLUE_FG;
            case Level.DEBUG_INT -> ANSIConstants.MAGENTA_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
