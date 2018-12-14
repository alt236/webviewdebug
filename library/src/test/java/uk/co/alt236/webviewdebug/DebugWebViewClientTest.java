package uk.co.alt236.webviewdebug;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
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
import android.webkit.WebViewClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 27)
public class DebugWebViewClientTest {

    private WebView webView;
    private DebugWebViewClient debugClient;
    private WebViewClient wrappedClient;
    private DebugWebViewClientLogger logger;

    @Before
    public void setUp() {
        webView = Mockito.mock(WebView.class);
        wrappedClient = Mockito.mock(WebViewClient.class);
        logger = Mockito.mock(DebugWebViewClientLogger.class);
        debugClient = new DebugWebViewClient(wrappedClient, logger);
    }

    @After
    public void tearDown() {
        webView = null;
        wrappedClient = null;
        logger = null;
        debugClient = null;
    }

    @Test
    public void onReceivedError() {
        final int code = 500;
        final String message = "foo";
        final String url = "bar";

        //noinspection deprecation
        debugClient.onReceivedError(webView, code, message, url);
        verifyLogger().onReceivedError(webView, code, message, url);
        verifyWrappedClient().onReceivedError(webView, code, message, url);
    }

    @Test
    public void onReceivedError_api23() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceError error = Mockito.mock(WebResourceError.class);

        debugClient.onReceivedError(webView, request, error);
        verifyLogger().onReceivedError(webView, request, error);
        verifyWrappedClient().onReceivedError(webView, request, error);
    }

    @Test
    public void onReceivedHttpError() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        debugClient.onReceivedHttpError(webView, request, response);
        verifyLogger().onReceivedHttpError(webView, request, response);
        verifyWrappedClient().onReceivedHttpError(webView, request, response);
    }

    @Test
    public void onReceivedSslError() {
        final SslErrorHandler errorHandler = Mockito.mock(SslErrorHandler.class);
        final SslError sslError = Mockito.mock(SslError.class);

        debugClient.onReceivedSslError(webView, errorHandler, sslError);
        verifyLogger().onReceivedSslError(webView, errorHandler, sslError);
        verifyWrappedClient().onReceivedSslError(webView, errorHandler, sslError);
    }

    @Test
    public void shouldOverrideUrlLoading() {
        final String url = "foo";

        final boolean retVal = debugClient.shouldOverrideUrlLoading(webView, url);
        verifyLogger().shouldOverrideUrlLoading(webView, url, retVal);
        verifyWrappedClient().shouldOverrideUrlLoading(webView, url);
    }

    @Test
    public void shouldOverrideUrlLoading1() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        final boolean retVal = debugClient.shouldOverrideUrlLoading(webView, request);
        verifyLogger().shouldOverrideUrlLoading(webView, request, retVal);
        verifyWrappedClient().shouldOverrideUrlLoading(webView, request);
    }

    @Test
    public void onLoadResource() {
        final String url = "foo";

        debugClient.onLoadResource(webView, url);
        verifyLogger().onLoadResource(webView, url);
        verifyWrappedClient().onLoadResource(webView, url);
    }

    @Test
    public void onPageCommitVisible() {
        final String url = "foo";

        debugClient.onPageCommitVisible(webView, url);
        verifyLogger().onPageCommitVisible(webView, url);
        verifyWrappedClient().onPageCommitVisible(webView, url);
    }

    @Test
    public void shouldInterceptRequest() {
        final String url = "foo";

        //noinspection deprecation
        final WebResourceResponse retVal = debugClient.shouldInterceptRequest(webView, url);
        verifyLogger().shouldInterceptRequest(webView, url, retVal);
        verifyWrappedClient().shouldInterceptRequest(webView, url);
    }

    @Test
    public void shouldInterceptRequest_api21() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        final WebResourceResponse retVal = debugClient.shouldInterceptRequest(webView, request);
        verifyLogger().shouldInterceptRequest(webView, request, retVal);
        verifyWrappedClient().shouldInterceptRequest(webView, request);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void onTooManyRedirects() {
        final Message cancelMsg = Mockito.mock(Message.class);
        final Message continueMsg = Mockito.mock(Message.class);

        debugClient.onTooManyRedirects(webView, cancelMsg, continueMsg);
        verifyLogger().onTooManyRedirects(webView, cancelMsg, continueMsg);
        verifyWrappedClient().onTooManyRedirects(webView, cancelMsg, continueMsg);
    }

    @Test
    public void onReceivedHttpAuthRequest() {
        final HttpAuthHandler handler = Mockito.mock(HttpAuthHandler.class);
        final String host = "foo";
        final String realm = "bar";

        debugClient.onReceivedHttpAuthRequest(webView, handler, host, realm);
        verifyLogger().onReceivedHttpAuthRequest(webView, handler, host, realm);
        verifyWrappedClient().onReceivedHttpAuthRequest(webView, handler, host, realm);
    }

    @Test
    public void onPageStarted() {
        final String url = "foo";
        final Bitmap bitmap = Mockito.mock(Bitmap.class);

        debugClient.onPageStarted(webView, url, bitmap);
        verifyLogger().onPageStarted(webView, url, bitmap);
        verifyWrappedClient().onPageStarted(webView, url, bitmap);
    }

    @Test
    public void onPageFinished() {
        final String url = "foo";

        debugClient.onPageFinished(webView, url);
        verifyLogger().onPageFinished(webView, url);
        verifyWrappedClient().onPageFinished(webView, url);
    }

    @Test
    public void onReceivedClientCertRequest() {
        final ClientCertRequest request = Mockito.mock(ClientCertRequest.class);

        debugClient.onReceivedClientCertRequest(webView, request);
        verifyLogger().onReceivedClientCertRequest(webView, request);
        verifyWrappedClient().onReceivedClientCertRequest(webView, request);
    }

    @Test
    public void onFormResubmission() {
        final Message dontResend = Mockito.mock(Message.class);
        final Message resend = Mockito.mock(Message.class);

        debugClient.onFormResubmission(webView, dontResend, resend);
        verifyLogger().onFormResubmission(webView, dontResend, resend);
        verifyWrappedClient().onFormResubmission(webView, dontResend, resend);
    }

    @Test
    public void doUpdateVisitedHistory() {
        final String url = "foo";
        final boolean reload = true;

        debugClient.doUpdateVisitedHistory(webView, url, reload);
        verifyLogger().doUpdateVisitedHistory(webView, url, reload);
        verifyWrappedClient().doUpdateVisitedHistory(webView, url, reload);
    }

    @Test
    public void shouldOverrideKeyEvent() {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        final boolean retVal = debugClient.shouldOverrideKeyEvent(webView, keyEvent);
        verifyLogger().shouldOverrideKeyEvent(webView, keyEvent, retVal);
        verifyWrappedClient().shouldOverrideKeyEvent(webView, keyEvent);
    }

    @Test
    public void onUnhandledKeyEvent() {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        debugClient.onUnhandledKeyEvent(webView, keyEvent);
        verifyLogger().onUnhandledKeyEvent(webView, keyEvent);
        verifyWrappedClient().onUnhandledKeyEvent(webView, keyEvent);
    }

    @Test
    public void onScaleChanged() {
        final float oldScale = 1.0f;
        final float newScale = 2.0f;

        debugClient.onScaleChanged(webView, oldScale, newScale);
        verifyLogger().onScaleChanged(webView, oldScale, newScale);
        verifyWrappedClient().onScaleChanged(webView, oldScale, newScale);
    }

    @Test
    public void onReceivedLoginRequest() {
        final String realm = "realm";
        final String account = "account";
        final String args = "args";

        debugClient.onReceivedLoginRequest(webView, realm, account, args);
        verifyLogger().onReceivedLoginRequest(webView, realm, account, args);
        verifyWrappedClient().onReceivedLoginRequest(webView, realm, account, args);
    }

    @Test
    public void onRenderProcessGone() {
        final RenderProcessGoneDetail detail = Mockito.mock(RenderProcessGoneDetail.class);

        final boolean retVal = debugClient.onRenderProcessGone(webView, detail);
        verifyLogger().onRenderProcessGone(webView, detail, retVal);
        verifyWrappedClient().onRenderProcessGone(webView, detail);
    }

    @Test
    public void onSafeBrowsingHit() {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final SafeBrowsingResponse callback = Mockito.mock(SafeBrowsingResponse.class);
        final int threatType = -1;

        debugClient.onSafeBrowsingHit(webView, request, threatType, callback);

        verifyLogger().onSafeBrowsingHit(webView, request, threatType, callback);
        verifyWrappedClient().onSafeBrowsingHit(webView, request, threatType, callback);
    }

    private WebViewClient verifyWrappedClient() {
        return Mockito.verify(wrappedClient, Mockito.times(1));
    }

    private DebugWebViewClientLogger verifyLogger() {
        return Mockito.verify(logger, Mockito.times(1));
    }
}
