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
import android.webkit.WebViewClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = Build.VERSION_CODES.O)
public class DebugWebViewClientTest {

    private WebView webView;
    private DebugWebViewClient debugClient;
    private WebViewClient wrappedClient;

    @Before
    public void setUp() {
        webView = Mockito.mock(WebView.class);
        wrappedClient = Mockito.mock(WebViewClient.class);
        debugClient = new DebugWebViewClient(wrappedClient);
    }

    @Test
    public void onReceivedError() throws Exception {
        final int code = 500;
        final String message = "foo";
        final String url = "bar";

        debugClient.onReceivedError(webView, code, message, url);
        verifyWrappedClient().onReceivedError(webView, code, message, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onReceivedError_api23() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceError error = Mockito.mock(WebResourceError.class);

        debugClient.onReceivedError(webView, request, error);
        verifyWrappedClient().onReceivedError(webView, request, error);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onReceivedHttpError() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);
        final WebResourceResponse response = Mockito.mock(WebResourceResponse.class);

        debugClient.onReceivedHttpError(webView, request, response);
        verifyWrappedClient().onReceivedHttpError(webView, request, response);
    }

    @Test
    public void onReceivedSslError() throws Exception {
        final SslErrorHandler errorHandler = Mockito.mock(SslErrorHandler.class);
        final SslError sslError = Mockito.mock(SslError.class);

        debugClient.onReceivedSslError(webView, errorHandler, sslError);
        verifyWrappedClient().onReceivedSslError(webView, errorHandler, sslError);
    }

    @Test
    public void shouldOverrideUrlLoading() throws Exception {
        final String url = "foo";

        debugClient.shouldOverrideUrlLoading(webView, url);
        verifyWrappedClient().shouldOverrideUrlLoading(webView, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void shouldOverrideUrlLoading1() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        debugClient.shouldOverrideUrlLoading(webView, request);
        verifyWrappedClient().shouldOverrideUrlLoading(webView, request);
    }

    @Test
    public void onLoadResource() throws Exception {
        final String url = "foo";

        debugClient.onLoadResource(webView, url);
        verifyWrappedClient().onLoadResource(webView, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Test
    public void onPageCommitVisible() throws Exception {
        final String url = "foo";

        debugClient.onPageCommitVisible(webView, url);
        verifyWrappedClient().onPageCommitVisible(webView, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldInterceptRequest() throws Exception {
        final String url = "foo";

        debugClient.shouldInterceptRequest(webView, url);
        verifyWrappedClient().shouldInterceptRequest(webView, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldInterceptRequest_api21() throws Exception {
        final WebResourceRequest request = Mockito.mock(WebResourceRequest.class);

        debugClient.shouldInterceptRequest(webView, request);
        verifyWrappedClient().shouldInterceptRequest(webView, request);
    }

    @Test
    public void onTooManyRedirects() throws Exception {
        final Message cancelMsg = Mockito.mock(Message.class);
        final Message continueMsg = Mockito.mock(Message.class);

        debugClient.onTooManyRedirects(webView, cancelMsg, continueMsg);
        verifyWrappedClient().onTooManyRedirects(webView, cancelMsg, continueMsg);
    }

    @Test
    public void onReceivedHttpAuthRequest() throws Exception {
        final HttpAuthHandler handler = Mockito.mock(HttpAuthHandler.class);
        final String host = "foo";
        final String realm = "bar";

        debugClient.onReceivedHttpAuthRequest(webView, handler, host, realm);
        verifyWrappedClient().onReceivedHttpAuthRequest(webView, handler, host, realm);
    }

    @Test
    public void onPageStarted() throws Exception {
        final String url = "foo";
        final Bitmap bitmap = Mockito.mock(Bitmap.class);

        debugClient.onPageStarted(webView, url, bitmap);
        verifyWrappedClient().onPageStarted(webView, url, bitmap);
    }

    @Test
    public void onPageFinished() throws Exception {
        final String url = "foo";

        debugClient.onPageFinished(webView, url);
        verifyWrappedClient().onPageFinished(webView, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void onReceivedClientCertRequest() throws Exception {
        final ClientCertRequest request = Mockito.mock(ClientCertRequest.class);

        debugClient.onReceivedClientCertRequest(webView, request);
        verifyWrappedClient().onReceivedClientCertRequest(webView, request);
    }

    @Test
    public void onFormResubmission() throws Exception {
        final Message dontResend = Mockito.mock(Message.class);
        final Message resend = Mockito.mock(Message.class);

        debugClient.onFormResubmission(webView, dontResend, resend);
        verifyWrappedClient().onFormResubmission(webView, dontResend, resend);
    }

    @Test
    public void doUpdateVisitedHistory() throws Exception {
        final String url = "foo";
        final boolean reload = true;

        debugClient.doUpdateVisitedHistory(webView, url, reload);
        verifyWrappedClient().doUpdateVisitedHistory(webView, url, reload);
    }

    @Test
    public void shouldOverrideKeyEvent() throws Exception {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        debugClient.shouldOverrideKeyEvent(webView, keyEvent);
        verifyWrappedClient().shouldOverrideKeyEvent(webView, keyEvent);
    }

    @Test
    public void onUnhandledKeyEvent() throws Exception {
        final KeyEvent keyEvent = Mockito.mock(KeyEvent.class);

        debugClient.onUnhandledKeyEvent(webView, keyEvent);
        verifyWrappedClient().onUnhandledKeyEvent(webView, keyEvent);
    }

    @Test
    public void onScaleChanged() throws Exception {
        final float oldscale = 1.0f;
        final float newscale = 2.0f;

        debugClient.onScaleChanged(webView, oldscale, newscale);
        verifyWrappedClient().onScaleChanged(webView, oldscale, newscale);
    }

    @Test
    public void onReceivedLoginRequest() throws Exception {
        final String realm = "realm";
        final String account = "account";
        final String args = "args";

        debugClient.onReceivedLoginRequest(webView, realm, account, args);
        verifyWrappedClient().onReceivedLoginRequest(webView, realm, account, args);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void onRenderProcessGone() throws Exception {
        final RenderProcessGoneDetail detail = Mockito.mock(RenderProcessGoneDetail.class);
        debugClient.onRenderProcessGone(webView, detail);
        verifyWrappedClient().onRenderProcessGone(webView, detail);
    }

    private WebViewClient verifyWrappedClient() {
        return Mockito.verify(wrappedClient, Mockito.times(1));
    }
}