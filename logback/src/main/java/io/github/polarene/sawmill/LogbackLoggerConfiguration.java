package io.github.polarene.sawmill;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import lombok.Builder;
import org.slf4j.event.Level;
import org.slf4j.LoggerFactory;

/**
 * TODO: Document me
 *
 * @author mmirk
 */
@Builder
public class LogbackLoggerConfiguration {

  private final Level level;
  private final String pattern;
  private final boolean console;

  public void configure() {
    LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();

    PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
    logEncoder.setContext(logCtx);
    logEncoder.setPattern(pattern);
    logEncoder.start();

    ConsoleAppender logConsoleAppender = new ConsoleAppender();
    logConsoleAppender.setContext(logCtx);
    logConsoleAppender.setName("console");
    logConsoleAppender.setEncoder(logEncoder);
    logConsoleAppender.start();

    logCtx.reset();
    Logger log = logCtx.getLogger(Logger.ROOT_LOGGER_NAME);
    log.setAdditive(false);
    log.setLevel(ch.qos.logback.classic.Level.valueOf(level.toString()));
    log.addAppender(logConsoleAppender);
  }

  public static void main(String[] args) {
    LogbackLoggerConfiguration.builder()
        .level(Level.INFO)
        .pattern("%-12date{YYYY-MM-dd HH:mm:ss.SSS} %-5level – %msg%n")
        .console(true)
        .build()
        .configure();

    LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).info("test");
  }
}
