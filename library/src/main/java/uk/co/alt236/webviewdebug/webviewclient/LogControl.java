package uk.co.alt236.webviewdebug.webviewclient;

interface LogControl {
    boolean isLoggingEnabled();

    void setLoggingEnabled(boolean enabled);

    boolean isLogKeyEventsEnabled();

    void setLogKeyEventsEnabled(boolean enabled);
}
