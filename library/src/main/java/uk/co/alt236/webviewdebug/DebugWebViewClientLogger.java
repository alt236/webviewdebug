package uk.co.alt236.webviewdebug;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class DebugWebViewClientLogger implements LogControl {
    private static final Locale LOCALE = Locale.US;
    private static final String IN = "--->";
    private static final String OUT = "<---";
    private static final String SPACE = "    ";
    private static final String DEFAULT_TAG = BuildConfig.DEFAULT_LOG_TAG;

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
            final Uri url = request.getUrl();
            final String method = request.getMethod();
            final int code = error.getErrorCode();

            logger.logError(String.format(LOCALE, "%s onReceivedError() 1/3 CALL       : %d %s %s", SPACE, code, method, url));
            logger.logError(String.format(LOCALE, "%s onReceivedError() 2/3 REQ HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.logError(String.format(LOCALE, "%s onReceivedError() 3/3 ERR DESC   : %s", SPACE, error.getDescription()));
        }
    }

    public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%s onReceivedError() 1/2 CALL: %d %s", SPACE, errorCode, failingUrl));
            logger.logError(String.format(LOCALE, "%s onReceivedError() 2/2 ERR : %s", SPACE, description));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedHttpError(final WebView view, final WebResourceRequest request, final WebResourceResponse errorResponse) {
        if (loggingEnabled) {
            final Uri url = request.getUrl();
            final int code = errorResponse.getStatusCode();
            final String method = request.getMethod();

            logger.logError(String.format(LOCALE, "%s onReceivedHttpError() 1/4 CALL       : %d %s %s", SPACE, code, method, url));
            logger.logError(String.format(LOCALE, "%s onReceivedHttpError() 2/4 REQ HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.logError(String.format(LOCALE, "%s onReceivedHttpError() 3/4 ERR DESC   : %s", SPACE, errorResponse.getReasonPhrase()));
            logger.logError(String.format(LOCALE, "%s onReceivedHttpError() 4/4 ERR HEADERS: %s", SPACE, errorResponse.getResponseHeaders()));
        }
    }

    public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%s onReceivedSslError() ERR: %s", SPACE, error));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void shouldOverrideUrlLoading(WebView view, WebResourceRequest request, boolean retVal) {
        if (loggingEnabled) {
            final Uri url = request.getUrl();
            final String method = request.getMethod();
            final boolean redirect = request.isRedirect();
            final boolean mainframe = request.isForMainFrame();
            final boolean gesture = request.hasGesture();

            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 1/4 CALL       : %s %s", SPACE, method, url));
            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=%s, forMainFrame=%s, hasGesture=%s", SPACE, redirect, mainframe, gesture));
            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 3/4 REQ HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 4/4 OVERRIDE   : %s", SPACE, retVal));
        }
    }

    public void shouldOverrideUrlLoading(WebView view, String url, boolean retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 1/2 CALL    : %s", SPACE, url));
            logger.log(String.format(LOCALE, "%s shouldOverrideUrlLoading() 2/2 OVERRIDE: %s", SPACE, retVal));
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onLoadResource() %s", SPACE, url));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onPageCommitVisible(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onPageCommitVisible() %s", SPACE, url));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, String url, WebResourceResponse retVal) {
        if (loggingEnabled) {
            final String result = retVal == null ? "false" : StringUtils.toString(retVal);

            logger.log(String.format(LOCALE, "%s shouldInterceptRequest() 1/2 CALL    : %s", SPACE, url));
            logger.log(String.format(LOCALE, "%s shouldInterceptRequest() 2/2 OVERRIDE: %s", SPACE, url, result));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, WebResourceRequest request, final WebResourceResponse retVal) {
        if (loggingEnabled) {
            final Uri url = request.getUrl();
            final String method = request.getMethod();
            final String result = retVal == null ? "false" : StringUtils.toString(retVal);

            logger.log(String.format(LOCALE, "%s shouldInterceptRequest() 1/3 CALL       : %s %s", SPACE, method, url));
            logger.log(String.format(LOCALE, "%s shouldInterceptRequest() 2/3 REQ HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.log(String.format(LOCALE, "%s shouldInterceptRequest() 3/3 INTERCEPT  : %s", SPACE, result));
        }
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (loggingEnabled) {
            logger.logError(String.format(LOCALE, "%s onTooManyRedirects()", SPACE));
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%s onReceivedHttpAuthRequest() %s %s %s", SPACE, host, realm, handler));
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap facIcon) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onPageStarted() %s", IN, url));
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onPageFinished() %s", OUT, url));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%s onReceivedClientCertRequest() %s", SPACE, StringUtils.toString(request)));
        }
    }

    public void onFormResubmission(final WebView view, final Message dontResend, final Message resend) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onFormResubmission()", SPACE));
        }
    }

    public void doUpdateVisitedHistory(final WebView view, final String url, final boolean isReload) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s doUpdateVisitedHistory() %s, isReload: %s", SPACE, url, isReload));
        }
    }

    public void shouldOverrideKeyEvent(final WebView view, final KeyEvent event, boolean retVal) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logger.logKeyEvent(String.format(LOCALE, "%s shouldOverrideKeyEvent() 1/2 EVENT   : %s", SPACE, event));
            logger.logKeyEvent(String.format(LOCALE, "%s shouldOverrideKeyEvent() 2/2 OVERRIDE: %s", SPACE, retVal));
        }
    }

    public void onUnhandledInputEvent(final WebView view, final InputEvent event) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logger.logKeyEvent(String.format(LOCALE, "%s onUnhandledInputEvent() %s", SPACE, event));
        }
    }

    public void onUnhandledKeyEvent(final WebView view, final KeyEvent event) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logger.logKeyEvent(String.format(LOCALE, "%s onUnhandledKeyEvent() %s", SPACE, event));
        }
    }

    public void onScaleChanged(final WebView view, final float oldScale, final float newScale) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onScaleChanged() old: %s, new: %s", SPACE, oldScale, newScale));
        }
    }

    public void onReceivedLoginRequest(final WebView view, final String realm, final String account, final String args) {
        if (loggingEnabled) {
            logger.logSecurity(String.format(LOCALE, "%s onReceivedLoginRequest() %s, %s, %s", SPACE, realm, account, args));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onRenderProcessGone(final WebView view, final RenderProcessGoneDetail detail, boolean retVal) {
        if (loggingEnabled) {
            logger.log(String.format(LOCALE, "%s onRenderProcessGone() 1/2 DETAIL: %s", SPACE, detail));
            logger.log(String.format(LOCALE, "%s onRenderProcessGone() 2/2 RESULT: %s", SPACE, retVal));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    public void onSafeBrowsingHit(final WebView view, final WebResourceRequest request, final int threatType, final SafeBrowsingResponse callback) {
        if (loggingEnabled) {
            final Uri url = request.getUrl();
            final String method = request.getMethod();
            final String threat = StringUtils.resolveThreatType(threatType);

            logger.log(String.format(LOCALE, "%s onSafeBrowsingHit() 1/3 CALL       : %s %s", SPACE, method, url));
            logger.log(String.format(LOCALE, "%s onSafeBrowsingHit() 2/3 REQ HEADERS: %s", SPACE, request.getRequestHeaders()));
            logger.log(String.format(LOCALE, "%s onSafeBrowsingHit() 3/3 THREAT     : %s", SPACE, threat));
        }
    }

    @Override
    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    @Override
    public void setLoggingEnabled(final boolean enabled) {
        this.loggingEnabled = enabled;
    }

    @Override
    public boolean isLogKeyEventsEnabled() {
        return logKeyEventsEnabled;
    }

    @Override
    public void setLogKeyEventsEnabled(final boolean enabled) {
        this.logKeyEventsEnabled = enabled;
    }
}
