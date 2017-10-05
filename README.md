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
