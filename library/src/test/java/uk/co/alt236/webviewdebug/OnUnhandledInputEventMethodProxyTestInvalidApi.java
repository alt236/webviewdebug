package uk.co.alt236.webviewdebug;

import android.view.InputEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = {17, 18, 19})
public class OnUnhandledInputEventMethodProxyTestInvalidApi {

    private OnUnhandledInputEventMethodProxy proxy;

    @Before
    public void setUp() {
        WebViewClient client = Mockito.mock(WebViewClient.class);
        proxy = new OnUnhandledInputEventMethodProxy(client);
    }

    @After
    public void tearDown() {
        proxy = null;
    }

    @Test
    public void testOnUnhandledInputEvent() {
        final WebView webView = Mockito.mock(WebView.class);
        final InputEvent inputEvent = Mockito.mock(InputEvent.class);

        final boolean handled = proxy.onUnhandledInputEvent(webView, inputEvent);

        assertFalse("Method should not have been present", proxy.isMethodPresent());
        assertFalse("Method call should not have been handled", handled);
    }
}
