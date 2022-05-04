package uk.co.alt236.webviewdebugsampleapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.alt236.webviewdebug.webviewclient.DebugWebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView webView = new WebView(this);
        setContentView(webView);

        final DebugWebViewClient debugWebViewClient = new DebugWebViewClient(new WebViewClient());
        debugWebViewClient.setLoggingEnabled(true);
        webView.setWebViewClient(debugWebViewClient);
        webView.loadUrl("https://www.google.com");
    }
}
