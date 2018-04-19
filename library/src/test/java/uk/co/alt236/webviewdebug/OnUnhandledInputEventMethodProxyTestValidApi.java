package uk.co.alt236.webviewdebug;

import android.view.InputEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = {21, 22, 23, 24, 25, 26})
public class OnUnhandledInputEventMethodProxyTestValidApi {

    private OnUnhandledInputEventMethodProxy proxy;

    @Before
    public void setUp() {
        WebViewClient client = Mockito.mock(WebViewClient.class);
        proxy = new OnUnhandledInputEventMethodProxy(client);
    }

    @Test
    public void testOnUnhandledInputEvent() {
        final WebView webView = Mockito.any(WebView.class);
        final InputEvent inputEvent = Mockito.any(InputEvent.class);

        final boolean handled = proxy.onUnhandledInputEvent(webView, inputEvent);

        assertTrue("Method should have been present", proxy.isMethodPresent());
        assertTrue("Method call should have been handled", handled);
    }
}
