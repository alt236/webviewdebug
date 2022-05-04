package uk.co.alt236.webviewdebug.webviewclient;

import android.util.Log;

class LogEngine {
    private final String tag;

    public LogEngine(final String tag) {
        this.tag = tag;
    }

    public void log(final String message) {
        Log.i(tag, message);
    }

    public void logError(final String message) {
        Log.e(tag, message);
    }

    public void logSecurity(final String message) {
        Log.w(tag, message);
    }

    public void logKeyEvent(final String message) {
        Log.e(tag, message);
    }
}
