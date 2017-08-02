package uk.co.alt236.webviewdebug;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

/*package*/ class DebugWebViewClientLogger {
    private static final String IN = "---> ";
    private static final String OUT = "<--- ";
    private static final String SPACE = "     ";
    private static final String TAG = "DEBUGCLIENT";

    private boolean loggingEnabled;
    private boolean logKeyEventsEnabled;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
        if (loggingEnabled) {
            final int code = error.getErrorCode();
            final String method = request.getMethod();

            logError(SPACE + "onReceivedError() 1/3 URL   : " + code + " " + method + " " + request.getUrl());
            logError(SPACE + "onReceivedError() 2/3 HEADER: " + request.getRequestHeaders());
            logError(SPACE + "onReceivedError() 3/3 DESC  : " + error.getDescription());
        }
    }

    public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {
        if (loggingEnabled) {
            logError(SPACE + "onReceivedError() 1/2 " + errorCode + ", " + failingUrl);
            logError(SPACE + "onReceivedError() 2/2 " + description);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedHttpError(final WebView view, final WebResourceRequest request, final WebResourceResponse errorResponse) {
        if (loggingEnabled) {
            logError(SPACE + "onReceivedHttpError() 1/2 REQ: " + StringUtils.toString(request));
            logError(SPACE + "onReceivedHttpError() 2/2 ERR: " + StringUtils.toString(errorResponse));
        }
    }

    public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
        if (loggingEnabled) {
            logError(SPACE + "onReceivedSslError() " + error);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void shouldOverrideUrlLoading(WebView view, WebResourceRequest request, boolean retVal) {
        if (loggingEnabled) {
            log(SPACE + "shouldOverrideUrlLoading() " + retVal + ", " + StringUtils.toString(request));
        }
    }

    public void shouldOverrideUrlLoading(WebView view, String url, boolean retVal) {
        if (loggingEnabled) {
            log(SPACE + "shouldOverrideUrlLoading() " + retVal + ", " + url);
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (loggingEnabled) {
            log(SPACE + "onLoadResource() " + url);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onPageCommitVisible(WebView view, String url) {
        if (loggingEnabled) {
            log(SPACE + "onPageCommitVisible() " + url);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, String url, WebResourceResponse retVal) {
        if (loggingEnabled) {
            log(SPACE + "shouldInterceptRequest() " + url + " " + StringUtils.toString(retVal));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shouldInterceptRequest(WebView view, WebResourceRequest request, final WebResourceResponse retVal) {
        if (loggingEnabled) {
            log(SPACE + "shouldInterceptRequest() 1/2 REQ: " + StringUtils.toString(request));
            log(SPACE + "shouldInterceptRequest() 2/2 RES: " + StringUtils.toString(retVal));
        }
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (loggingEnabled) {
            logError(SPACE + "onTooManyRedirects()");
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (loggingEnabled) {
            logSecurity(SPACE + "onReceivedHttpAuthRequest() " + host + " " + realm + " " + handler);
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap facIcon) {
        if (loggingEnabled) {
            log(IN + "onPageStarted()  " + url);
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (loggingEnabled) {
            log(OUT + "onPageFinished() " + url);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (loggingEnabled) {
            logSecurity(SPACE + "onReceivedClientCertRequest() " + StringUtils.toString(request));
        }
    }

    public void onFormResubmission(final WebView view, final Message dontResend, final Message resend) {
        if (loggingEnabled) {
            log(SPACE + "onFormResubmission()");
        }
    }

    public void doUpdateVisitedHistory(final WebView view, final String url, final boolean isReload) {
        if (loggingEnabled) {
            log(SPACE + "doUpdateVisitedHistory() " + url + ", isReload: " + isReload);
        }
    }

    public void shouldOverrideKeyEvent(final WebView view, final KeyEvent event, boolean retVal) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logKeyEvent(SPACE + "shouldOverrideKeyEvent() 1/2 Event : " + event);
            logKeyEvent(SPACE + "shouldOverrideKeyEvent() 1/2 Result: " + retVal);
        }
    }

    public void onUnhandledKeyEvent(final WebView view, final KeyEvent event) {
        if (loggingEnabled && logKeyEventsEnabled) {
            logKeyEvent(SPACE + "onUnhandledKeyEvent() " + event);
        }
    }

    public void onScaleChanged(final WebView view, final float oldScale, final float newScale) {
        if (loggingEnabled) {
            log(SPACE + "onScaleChanged() old: " + oldScale + ", new: " + newScale);
        }
    }

    public void onReceivedLoginRequest(final WebView view, final String realm, final String account, final String args) {
        if (loggingEnabled) {
            logSecurity(SPACE + "onReceivedLoginRequest() " + realm + ", " + account + ", " + args);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onRenderProcessGone(final WebView view, final RenderProcessGoneDetail detail, boolean retVal) {
        if (loggingEnabled) {
            log(SPACE + "onRenderProcessGone(): " + retVal);
        }
    }


    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(final boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public boolean isLogKeyEventsEnabled() {
        return logKeyEventsEnabled;
    }

    public void setLogKeyEventsEnabled(final boolean logKeyEventsEnabled) {
        this.logKeyEventsEnabled = logKeyEventsEnabled;
    }

    private void log(final String message) {
        Log.i(TAG, message);
    }

    private void logError(final String message) {
        Log.e(TAG, message);
    }

    private void logSecurity(final String message) {
        Log.w(TAG, message);
    }

    private void logKeyEvent(final String message) {
        if (logKeyEventsEnabled) {
            Log.e(TAG, message);
        }
    }
}
