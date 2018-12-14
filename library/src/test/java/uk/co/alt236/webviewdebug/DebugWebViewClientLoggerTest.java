package uk.co.alt236.webviewdebug;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = Build.VERSION_CODES.O)
public class DebugWebViewClientLoggerTest {

    private WebView webView;
    private LogEngine logEngine;
    private DebugWebViewClientLogger logger;

    @Before
    public void setUp() {
        webView = Mockito.mock(WebView.class);
        logEngine = Mockito.mock(LogEngine.class);
        logger = new DebugWebViewClientLogger(logEngine);
    }

    @After
    public void tearDown() {
        webView = null;
        logEngine = null;
        logger = null;
    }

    @Test
    public void onReceivedError() {
        final int code = 500;
        final String message = "foo";
        final String url = "bar";

        logger.setLoggingEnabled(false);
        logger.onReceivedError(webView, code, message, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedError(webView, code, message, url);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @Test
    public void onReceivedError_api23() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceError error = Mockito.mock(WebResourceError.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedError(webView, request, error);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedError(webView, request, error);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @Test
    public void onReceivedHttpError() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedHttpError(webView, request, response);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedHttpError(webView, request, response);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @Test
    public void onReceivedSslError() {
        final SslErrorHandler errorHandler = Mockito.mock(SslErrorHandler.class);
        final SslError sslError = Mockito.mock(SslError.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedSslError(webView, errorHandler, sslError);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedSslError(webView, errorHandler, sslError);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @Test
    public void shouldOverrideUrlLoading() {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.shouldOverrideUrlLoading(webView, url, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldOverrideUrlLoading(webView, url, false);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void shouldOverrideUrlLoading1() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        logger.setLoggingEnabled(false);
        logger.shouldOverrideUrlLoading(webView, request, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldOverrideUrlLoading(webView, request, false);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onLoadResource() {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onLoadResource(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onLoadResource(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onPageCommitVisible() {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onPageCommitVisible(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onPageCommitVisible(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void shouldInterceptRequest() {
        final String url = "foo";
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        logger.setLoggingEnabled(false);
        logger.shouldInterceptRequest(webView, url, response);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldInterceptRequest(webView, url, response);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void shouldInterceptRequest_api21() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        logger.setLoggingEnabled(false);
        logger.shouldInterceptRequest(webView, request, response);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldInterceptRequest(webView, request, response);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onTooManyRedirects() {
        final Message cancelMsg = Mockito.mock(Message.class);
        final Message continueMsg = Mockito.mock(Message.class);

        logger.setLoggingEnabled(false);
        logger.onTooManyRedirects(webView, cancelMsg, continueMsg);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onTooManyRedirects(webView, cancelMsg, continueMsg);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @Test
    public void onReceivedHttpAuthRequest() {
        final HttpAuthHandler handler = Mockito.mock(HttpAuthHandler.class);
        final String host = "foo";
        final String realm = "bar";

        logger.setLoggingEnabled(false);
        logger.onReceivedHttpAuthRequest(webView, handler, host, realm);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedHttpAuthRequest(webView, handler, host, realm);
        verifyLogEngine().logSecurity(Mockito.anyString());
    }

    @Test
    public void onPageStarted() {
        final String url = "foo";
        final Bitmap bitmap = Mockito.mock(Bitmap.class);

        logger.setLoggingEnabled(false);
        logger.onPageStarted(webView, url, bitmap);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onPageStarted(webView, url, bitmap);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onPageFinished() {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onPageFinished(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onPageFinished(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onReceivedClientCertRequest() {
        final ClientCertRequest request = Mockito.mock(ClientCertRequest.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedClientCertRequest(webView, request);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedClientCertRequest(webView, request);
        verifyLogEngine().logSecurity(Mockito.anyString());
    }

    @Test
    public void onFormResubmission() {
        final Message dontResend = Mockito.mock(Message.class);
        final Message resend = Mockito.mock(Message.class);

        logger.setLoggingEnabled(false);
        logger.onFormResubmission(webView, dontResend, resend);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onFormResubmission(webView, dontResend, resend);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void doUpdateVisitedHistory() {
        final String url = "foo";
        final boolean reload = true;

        logger.setLoggingEnabled(false);
        logger.doUpdateVisitedHistory(webView, url, reload);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.doUpdateVisitedHistory(webView, url, reload);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void shouldOverrideKeyEvent() {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(false);
        logger.shouldOverrideKeyEvent(webView, keyEvent, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(true);
        logger.shouldOverrideKeyEvent(webView, keyEvent, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(false);
        logger.shouldOverrideKeyEvent(webView, keyEvent, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(true);
        logger.shouldOverrideKeyEvent(webView, keyEvent, false);
        verifyLogEngine().logKeyEvent(Mockito.anyString());
    }

    @Test
    public void onUnhandledInputEvent() {
        final InputEvent inputEvent = Mockito.mock(InputEvent.class);

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(false);
        logger.onUnhandledInputEvent(webView, inputEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(true);
        logger.onUnhandledInputEvent(webView, inputEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(false);
        logger.onUnhandledInputEvent(webView, inputEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(true);
        logger.onUnhandledInputEvent(webView, inputEvent);
        verifyLogEngine().logKeyEvent(Mockito.anyString());
    }

    @Test
    public void onUnhandledKeyEvent() {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(false);
        logger.onUnhandledKeyEvent(webView, keyEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(false);
        logger.setLogKeyEventsEnabled(true);
        logger.onUnhandledKeyEvent(webView, keyEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(false);
        logger.onUnhandledKeyEvent(webView, keyEvent);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.setLogKeyEventsEnabled(true);
        logger.onUnhandledKeyEvent(webView, keyEvent);
        verifyLogEngine().logKeyEvent(Mockito.anyString());
    }

    @Test
    public void onScaleChanged() {
        final float oldScale = 1.0f;
        final float newScale = 2.0f;

        logger.setLoggingEnabled(false);
        logger.onScaleChanged(webView, oldScale, newScale);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onScaleChanged(webView, oldScale, newScale);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onReceivedLoginRequest() {
        final String realm = "realm";
        final String account = "account";
        final String args = "args";

        logger.setLoggingEnabled(false);
        logger.onReceivedLoginRequest(webView, realm, account, args);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedLoginRequest(webView, realm, account, args);
        verifyLogEngine().logSecurity(Mockito.anyString());
    }

    @Test
    public void onRenderProcessGone() {
        final RenderProcessGoneDetail detail = Mockito.mock(RenderProcessGoneDetail.class);

        logger.setLoggingEnabled(false);
        logger.onRenderProcessGone(webView, detail, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onRenderProcessGone(webView, detail, false);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onSafeBrowsingHit() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final SafeBrowsingResponse callback = Mockito.mock(SafeBrowsingResponse.class);
        final int threatType = -1;

        logger.setLoggingEnabled(false);
        logger.onSafeBrowsingHit(webView, request, threatType, callback);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onSafeBrowsingHit(webView, request, threatType, callback);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void testGettersAndSetters() {
        assertFalse(logger.isLoggingEnabled());
        logger.setLoggingEnabled(true);
        assertTrue(logger.isLoggingEnabled());

        assertFalse(logger.isLogKeyEventsEnabled());
        logger.setLogKeyEventsEnabled(true);
        assertTrue(logger.isLogKeyEventsEnabled());
    }

    private void verifyLogNotCalled() {
        Mockito.verify(logEngine, Mockito.never()).log(Mockito.anyString());
        Mockito.verify(logEngine, Mockito.never()).logError(Mockito.anyString());
        Mockito.verify(logEngine, Mockito.never()).logSecurity(Mockito.anyString());
        Mockito.verify(logEngine, Mockito.never()).logKeyEvent(Mockito.anyString());
    }

    private LogEngine verifyLogEngine() {
        return Mockito.verify(logEngine, Mockito.atLeastOnce());
    }
}
