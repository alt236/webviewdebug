# WEBVIEW DEBUG

Provides a logging wrapper around a WebViewClient, in order to figure out what is going on.

This happens by creating a `DebugWebViewClient` which logs events and passes them to an enclosed `WebViewClient`.

## Warning
The `DebugWebViewClient` is implementing all `WebViewClient` up to API 26. If your `WebViewClient` is implementing a method that the `DebugWebViewClient` does not, and that method is critical for your business logic, then your app will probably not work properly.

When a `DebugWebViewClient` is initialised, it will print in log a list of all methods that are declared in the passed `WebViewClient` class and any parents and are NOT overridden.

For as long as your app is does not need any of the listed, non-overridden, methods, then there won't be a problem.

## Usage

Output in logcat uses this tag: `DebugWVClient`.

### Debugging a WebViewClient
###### 1. Fast way if you already have a WebViewClient
If you already have a `WebViewClient` implementation, wrap it with `DebugWebViewClient` before assigning it to the WebView.

```
final DebugWebViewClient debugWebViewClient = new DebugWebViewClient(new MyCustomWebViewClient());
debugWebViewClient.setLoggingEnabled(true);
webView.setWebViewClient(debugWebViewClient);
```

###### 2. You have a custom WebViewClient but want more control
You can use `DebugWebViewClientLogger` to log things as needed in your own `WebViewClient`.

Make sure you pass the parameters and any return values of your own `WebViewClient` to the equivalent methods of the `DebugWebViewClientLogger`.

###### 3. You don't have a WebViewClient but you want to know what is going on
Just instantiate and assign a `DebugWebViewClient`to the WebView.

```
final DebugWebViewClient debugWebViewClient = new DebugWebViewClient();
debugWebViewClient.setLoggingEnabled(true);
webView.setWebViewClient(debugWebViewClient);
```

### Controlling output
Both `DebugWebViewClient`  and `DebugWebViewClientLogger` implemetn `LogControl` which contains the following signatures:

* `isLoggingEnabled()`: Check if logging is globally enabled
* `setLoggingEnabled(boolean)`: Enable or disable logging
* `isLogKeyEventsEnabled()`: Check if logging of `KeyEvent` related methods is enabled
* `setLogKeyEventsEnabled(boolean)`: Enable or disable logging of `KeyEvent` related methods is enabled

`KeyEvent` related methods have more granularity due to privacy concerns, as all keystrokes will be logged.

`setLoggingEnabled(boolean)` is a global switch which overrides `setLogKeyEventsEnabled(boolean)`

## Sample output
```
D/DebugWVClient: All methods implemented :)
I/DebugWVClient: ---> onPageStarted() http://www.google.com/
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET http://www.google.com/
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8, Upgrade-Insecure-Requests=1}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() http://www.google.com/
I/DebugWVClient: ---> onPageStarted() http://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET http://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=true, forMainFrame=true, hasGesture=false
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient: ---> onPageStarted() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=true, forMainFrame=true, hasGesture=false
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient:      doUpdateVisitedHistory() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl, isReload: false
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.co.uk/images/hpp/gsa_super_g-64.gif
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.google.co.uk/images/hpp/gsa_super_g-64.gif
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.co.uk/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.google.co.uk/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://ssl.gstatic.com/gb/images/qi2_00ed8ca1.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://ssl.gstatic.com/gb/images/qi2_00ed8ca1.png
I/DebugWVClient:      onPageCommitVisible() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.co.uk/images/nav_logo242_hr.webp
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.google.co.uk/images/nav_logo242_hr.webp
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.co.uk/images/branding/product/1x/gsa_android_144dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient: <--- onPageFinished() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=sGrWWdSvNq_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      onLoadResource() https://www.google.co.uk/images/branding/product/1x/gsa_android_144dp.png
I/DebugWVClient: All methods implemented :)
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET http://www.google.com/
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8, Upgrade-Insecure-Requests=1}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient: ---> onPageStarted() http://www.google.com/
I/DebugWVClient:      onLoadResource() http://www.google.com/
I/DebugWVClient: ---> onPageStarted() http://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET http://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=true, forMainFrame=true, hasGesture=false
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient: ---> onPageStarted() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=true, forMainFrame=true, hasGesture=false
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient:      doUpdateVisitedHistory() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl, isReload: false
I/DebugWVClient:      onPageCommitVisible() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.co.uk/images/branding/product/1x/gsa_android_144dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient: <--- onPageFinished() https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl
I/DebugWVClient:      onLoadResource() https://www.google.co.uk/images/branding/product/1x/gsa_android_144dp.png
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET https://www.google.com/url?q=https://store.google.com/gb/%3Fhl%3Den-GB%26countryRedirect%3Dtrue%26utm_source%3Dhpp%26utm_medium%3Dgoogle_oo%26utm_campaign%3DGS100077&source=hpp&id=19003843&ct=3&usg=AFQjCNGBVJRo84BJtfajQmUdhNeb8iYuSQ&sa=X&ved=0ahUKEwjbxISSgNrWAhUKJ8AKHetwDJ0Q8IcBCAk
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=false, forMainFrame=true, hasGesture=true
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.google.com/url?q=https://store.google.com/gb/%3Fhl%3Den-GB%26countryRedirect%3Dtrue%26utm_source%3Dhpp%26utm_medium%3Dgoogle_oo%26utm_campaign%3DGS100077&source=hpp&id=19003843&ct=3&usg=AFQjCNGBVJRo84BJtfajQmUdhNeb8iYuSQ&sa=X&ved=0ahUKEwjbxISSgNrWAhUKJ8AKHetwDJ0Q8IcBCAk
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Referer=https://www.google.co.uk/?gfe_rd=cr&dcr=0&ei=AGvWWePPO6_38AfK37roAg&gws_rd=ssl, Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8, Upgrade-Insecure-Requests=1}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient: ---> onPageStarted() https://www.google.com/url?q=https://store.google.com/gb/%3Fhl%3Den-GB%26countryRedirect%3Dtrue%26utm_source%3Dhpp%26utm_medium%3Dgoogle_oo%26utm_campaign%3DGS100077&source=hpp&id=19003843&ct=3&usg=AFQjCNGBVJRo84BJtfajQmUdhNeb8iYuSQ&sa=X&ved=0ahUKEwjbxISSgNrWAhUKJ8AKHetwDJ0Q8IcBCAk
I/DebugWVClient:      onLoadResource() https://www.google.com/url?q=https://store.google.com/gb/%3Fhl%3Den-GB%26countryRedirect%3Dtrue%26utm_source%3Dhpp%26utm_medium%3Dgoogle_oo%26utm_campaign%3DGS100077&source=hpp&id=19003843&ct=3&usg=AFQjCNGBVJRo84BJtfajQmUdhNeb8iYuSQ&sa=X&ved=0ahUKEwjbxISSgNrWAhUKJ8AKHetwDJ0Q8IcBCAk
I/DebugWVClient: ---> onPageStarted() https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077
I/DebugWVClient:      shouldOverrideUrlLoading() 1/4 CALL       : GET https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077
I/DebugWVClient:      shouldOverrideUrlLoading() 2/4 CALL INFO  : redirect=true, forMainFrame=true, hasGesture=false
I/DebugWVClient:      shouldOverrideUrlLoading() 3/4 REQ HEADERS: null
I/DebugWVClient:      shouldOverrideUrlLoading() 4/4 OVERRIDE   : false
I/DebugWVClient:      doUpdateVisitedHistory() https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077, isReload: false
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.googletagmanager.com/ns.html?id=GTM-MX89MJ
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077, Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8, Upgrade-Insecure-Requests=1}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.googletagmanager.com/ns.html?id=GTM-MX89MJ
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://fonts.gstatic.com/s/productsans/v9/HYvgU2fE2nRJvZ5JFAumwRampu5_7CjHW5spxoeN3Vs.woff2
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Origin=https://store.google.com, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077, Accept=*/*}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://fonts.gstatic.com/s/productsans/v9/HYvgU2fE2nRJvZ5JFAumwRampu5_7CjHW5spxoeN3Vs.woff2
I/DebugWVClient:      onPageCommitVisible() https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://fonts.gstatic.com/s/productsans/v9/N0c8y_dasvG2CzM7uYqPLshHwsiXhsDb0smKjAA7Bek.woff2
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Origin=https://store.google.com, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077, Accept=*/*}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://fonts.gstatic.com/s/productsans/v9/N0c8y_dasvG2CzM7uYqPLshHwsiXhsDb0smKjAA7Bek.woff2
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://lh3.googleusercontent.com/bxB6wR8V43WB9bMVZ0ILjriCFgCT-MNn2Mz9wPxlH1PyaWbCgBsV-EAPzbyRSfxHRNE=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://lh3.googleusercontent.com/QJ8E3sNKLviIyxol6UNAjnwmAvlta6fzl94f2Hxqj1vnbvB9LyXKfcT1XatulWFgkbvm=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://lh3.googleusercontent.com/bxB6wR8V43WB9bMVZ0ILjriCFgCT-MNn2Mz9wPxlH1PyaWbCgBsV-EAPzbyRSfxHRNE=w96-h96
I/DebugWVClient:      onLoadResource() https://lh3.googleusercontent.com/QJ8E3sNKLviIyxol6UNAjnwmAvlta6fzl94f2Hxqj1vnbvB9LyXKfcT1XatulWFgkbvm=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://lh3.googleusercontent.com/k3ZARl_vWPzBgKaFIL279g2_IQRSWbvCK-eQn52APeZavVDw7__iMRZ9h5Tn9YdKc4s=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://lh3.googleusercontent.com/JB70a3qUIFB2OiIfgNc7qB69N2N3m68oX1XHTRSuaJWNXJY5ITm2m62lqQ_qD5NDcpU=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://lh3.googleusercontent.com/k3ZARl_vWPzBgKaFIL279g2_IQRSWbvCK-eQn52APeZavVDw7__iMRZ9h5Tn9YdKc4s=w96-h96
I/DebugWVClient:      onLoadResource() https://lh3.googleusercontent.com/JB70a3qUIFB2OiIfgNc7qB69N2N3m68oX1XHTRSuaJWNXJY5ITm2m62lqQ_qD5NDcpU=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://lh3.googleusercontent.com/GlrKLWut6gWN52kyEtFK85r0ER-paG3TdSDfEGqsNfeYW0gcgLUW0ARYyassu3Y-pxs=w96-h96
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://fonts.gstatic.com/s/productsans/v9/N0c8y_dasvG2CzM7uYqPLtCODO6R-QMzjsZRstdx6VU.woff2
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Origin=https://store.google.com, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077, Accept=*/*}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://fonts.gstatic.com/s/productsans/v9/N0c8y_dasvG2CzM7uYqPLtCODO6R-QMzjsZRstdx6VU.woff2
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.gstatic.com/images/icons/material/system/2x/arrow_forward_googblue_24dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.gstatic.com/images/icons/material/system/2x/arrow_forward_googblue_24dp.png
I/DebugWVClient:      doUpdateVisitedHistory() https://www.googletagmanager.com/ns.html?id=GTM-MX89MJ, isReload: false
I/DebugWVClient: <--- onPageFinished() https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.gstatic.com/images/branding/product/1x/googleg_16dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.gstatic.com/images/branding/product/1x/googleg_16dp.png
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.gstatic.com/images/branding/product/1x/googleg_32dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.gstatic.com/images/branding/product/1x/googleg_32dp.png
I/DebugWVClient:      shouldInterceptRequest() 1/3 CALL       : GET https://www.gstatic.com/images/branding/product/1x/googleg_96dp.png
I/DebugWVClient:      shouldInterceptRequest() 2/3 REQ HEADERS: {User-Agent=Mozilla/5.0 (Linux; Android 8.0.0; Android SDK built for x86 Build/OSR1.170720.005; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.125 Mobile Safari/537.36, Accept=image/webp,image/*,*/*;q=0.8, Referer=https://store.google.com/gb/?hl=en-GB&countryRedirect=true&utm_source=hpp&utm_medium=google_oo&utm_campaign=GS100077}
I/DebugWVClient:      shouldInterceptRequest() 3/3 INTERCEPT  : false
I/DebugWVClient:      onLoadResource() https://www.gstatic.com/images/branding/product/1x/googleg_96dp.png

```

## License
    Copyright (C) 2017 Alexandros Schillings

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
