package uk.co.alt236.webviewdebug.webviewclient.logger;

import android.util.Log;

public class LogCatLogEngine implements LogEngine {
    private final String tag;

    public LogCatLogEngine(final String tag) {
        this.tag = tag;
    }

    @Override
    public void log(final String message) {
        Log.i(tag, message);
    }

    @Override
    public void logError(final String message) {
        Log.e(tag, message);
    }

    @Override
    public void logSecurity(final String message) {
        Log.w(tag, message);
    }

    @Override
    public void logKeyEvent(final String message) {
        Log.e(tag, message);
    }
}
