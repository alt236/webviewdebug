package uk.co.alt236.webviewdebugsampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uk.co.alt236.webviewdebug.DebugWebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView webView = new WebView(this);
        setContentView(webView);

        final DebugWebViewClient debugWebViewClient = new DebugWebViewClient(new WebViewClient());
        debugWebViewClient.setLoggingEnabled(true);
        webView.setWebViewClient(debugWebViewClient);
        webView.loadUrl("http://www.google.com");
    }
}
