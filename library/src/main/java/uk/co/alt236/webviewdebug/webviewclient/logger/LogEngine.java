package uk.co.alt236.webviewdebug.webviewclient.logger;

public interface LogEngine {
    void log(String message);

    void logError(String message);

    void logSecurity(String message);

    void logKeyEvent(String message);
}
