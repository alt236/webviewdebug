package uk.co.alt236.webviewdebug;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.Locale;

/*package*/ class DebugWebViewClientLogger {
    private static final Locale LOCALE = Locale.US;
    private static final String IN = "---> ";
    private static final String OUT = "<--- ";
    private static final String SPACE = "     ";
    private static final String DEFAULT_TAG = "DEBUGCLIENT";

    private final LogEngine logger;
    private boolean loggingEnabled;
    private boolean logKeyEventsEnabled;

    public DebugWebViewClientLogger() {
        this(DEFAULT_TAG);
    }

    public DebugWebViewClientLogger(@NonNull final String tag) {
        this(new LogEngine(tag));
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected DebugWebViewClientLogger(@NonNull final LogEngine logEngine) {
        this.logger = logEngine;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
        if (loggingEnabled) {
            final int code = error.getErrorCode();
            final String method = request.getMethod();

            logger.logError(String.format(LOCALE, "%sonReceivedError() 1/3 URL    : %d %s %s", SPACE, code, method, request.getUrl()));
            logger.logError(String.format(LOCALE, "%sonReceivedError() 2/3 HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.logError(String.format(LOCALE, "%sonReceivedError() 3/3 DESC   : %s", SPACE, error.getDescription()));
        }
    }

    public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%sonReceivedError() 1/2 %d %s", SPACE, errorCode, failingUrl));
            logger.logError(String.format(LOCALE, "%sonReceivedError() 2/2 %s", SPACE, description));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedHttpError(final WebView view, final WebResourceRequest request, final WebResourceResponse errorResponse) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%sonReceivedHttpError() 1/2 REQ: %s", SPACE, StringUtils.toString(request)));
            logger.logError(String.format(LOCALE, "%sonReceivedHttpError() 2/2 ERR: %s", SPACE, StringUtils.toString(errorResponse)));
        }
    }

    public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%sonReceivedSslError() %s", SPACE, error));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void shouldOverrideUrlLoading(WebView view, WebResourceRequest request, boolean retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sshouldOverrideUrlLoading() %s, %s", SPACE, retVal, StringUtils.toString(request)));
        }
    }

    public void shouldOverrideUrlLoading(WebView view, String url, boolean retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sshouldOverrideUrlLoading() %s, %s", SPACE, retVal, url));
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonLoadResource() %s", SPACE, url));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onPageCommitVisible(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonPageCommitVisible() %s", SPACE, url));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, String url, WebResourceResponse retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sshouldInterceptRequest() %s %s", SPACE, url, StringUtils.toString(retVal)));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, WebResourceRequest request, final WebResourceResponse retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sshouldInterceptRequest() 1/2 REQ: %s", SPACE, StringUtils.toString(request)));
            logger.log(String.format(LOCALE, "%sshouldInterceptRequest() 2/2 RES: %s", SPACE, StringUtils.toString(retVal)));
        }
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%sonTooManyRedirects()", SPACE));
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%sonReceivedHttpAuthRequest() %s %s %s", SPACE, host, realm, handler));
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap facIcon) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonPageStarted()  %s", IN, url));
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonPageFinished() %s", OUT, url));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%sonReceivedClientCertRequest() %s", SPACE, StringUtils.toString(request)));
        }
    }

    public void onFormResubmission(final WebView view, final Message dontResend, final Message resend) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonFormResubmission()", SPACE));
        }
    }

    public void doUpdateVisitedHistory(final WebView view, final String url, final boolean isReload) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sdoUpdateVisitedHistory() %s, isReload: %s", SPACE, url, isReload));
        }
    }

    public void shouldOverrideKeyEvent(final WebView view, final KeyEvent event, boolean retVal) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logger.logKeyEvent(String.format(LOCALE, "%sshouldOverrideKeyEvent() 1/2 Event : %s", SPACE, event));
            logger.logKeyEvent(String.format(LOCALE, "%sshouldOverrideKeyEvent() 1/2 Result: %s", SPACE, retVal));
        }
    }

    public void onUnhandledKeyEvent(final WebView view, final KeyEvent event) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logger.logKeyEvent(String.format(LOCALE, "%sonUnhandledKeyEvent() %s", SPACE, event));
        }
    }

    public void onScaleChanged(final WebView view, final float oldScale, final float newScale) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonScaleChanged() old: %s, new: %s", SPACE, oldScale, newScale));
        }
    }

    public void onReceivedLoginRequest(final WebView view, final String realm, final String account, final String args) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%sonReceivedLoginRequest() %s, %s, %s", SPACE, realm, account, args));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onRenderProcessGone(final WebView view, final RenderProcessGoneDetail detail, boolean retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%sonRenderProcessGone(): %s", SPACE, retVal));
        }
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(final boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLogKeyEventsEnabled() {
        return logKeyEventsEnabled;
    }

    public void setLogKeyEventsEnabled(final boolean enabled) {
        this.logKeyEventsEnabled = enabled;
    }
}
