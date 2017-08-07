package uk.co.alt236.webviewdebug;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

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

    @Test
    public void onReceivedError() throws Exception {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onReceivedError_api23() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceError error = Mockito.mock(WebResourceError.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedError(webView, request, error);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedError(webView, request, error);
        verifyLogEngine().logError(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onReceivedHttpError() throws Exception {
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
    public void onReceivedSslError() throws Exception {
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
    public void shouldOverrideUrlLoading() throws Exception {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.shouldOverrideUrlLoading(webView, url, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldOverrideUrlLoading(webView, url, false);
        verifyLogEngine().log(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void shouldOverrideUrlLoading1() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        logger.setLoggingEnabled(false);
        logger.shouldOverrideUrlLoading(webView, request, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldOverrideUrlLoading(webView, request, false);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onLoadResource() throws Exception {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onLoadResource(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onLoadResource(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onPageCommitVisible() throws Exception {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onPageCommitVisible(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onPageCommitVisible(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldInterceptRequest() throws Exception {
        final String url = "foo";
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        logger.setLoggingEnabled(false);
        logger.shouldInterceptRequest(webView, url, response);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.shouldInterceptRequest(webView, url, response);
        verifyLogEngine().log(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldInterceptRequest_api21() throws Exception {
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
    public void onTooManyRedirects() throws Exception {
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
    public void onReceivedHttpAuthRequest() throws Exception {
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
    public void onPageStarted() throws Exception {
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
    public void onPageFinished() throws Exception {
        final String url = "foo";

        logger.setLoggingEnabled(false);
        logger.onPageFinished(webView, url);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onPageFinished(webView, url);
        verifyLogEngine().log(Mockito.anyString());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void onReceivedClientCertRequest() throws Exception {
        final ClientCertRequest request = Mockito.mock(ClientCertRequest.class);

        logger.setLoggingEnabled(false);
        logger.onReceivedClientCertRequest(webView, request);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onReceivedClientCertRequest(webView, request);
        verifyLogEngine().logSecurity(Mockito.anyString());
    }

    @Test
    public void onFormResubmission() throws Exception {
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
    public void doUpdateVisitedHistory() throws Exception {
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
    public void shouldOverrideKeyEvent() throws Exception {
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
    public void onUnhandledKeyEvent() throws Exception {
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
    public void onScaleChanged() throws Exception {
        final float oldscale = 1.0f;
        final float newscale = 2.0f;

        logger.setLoggingEnabled(false);
        logger.onScaleChanged(webView, oldscale, newscale);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onScaleChanged(webView, oldscale, newscale);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onReceivedLoginRequest() throws Exception {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void onRenderProcessGone() throws Exception {
        final RenderProcessGoneDetail detail = Mockito.mock(RenderProcessGoneDetail.class);

        logger.setLoggingEnabled(false);
        logger.onRenderProcessGone(webView, detail, false);
        verifyLogNotCalled();

        logger.setLoggingEnabled(true);
        logger.onRenderProcessGone(webView, detail, false);
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