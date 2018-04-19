package uk.co.alt236.webviewdebug;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.InputEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OnUnhandledInputEventMethodProxy {
    private static final String TAG = BuildConfig.DEFAULT_LOG_TAG;
    private static final String METHOD_NAME = "onUnhandledInputEvent";
    private final WebViewClient client;
    private final Method method;

    OnUnhandledInputEventMethodProxy(@NonNull final WebViewClient client) {
        this.client = client;
        this.method = getMethod();
    }

    public boolean onUnhandledInputEvent(final WebView view, final InputEvent event) {
        boolean executed = false;
        if (method != null) {
            try {
                method.invoke(client, view, event);
                executed = true;
            } catch (IllegalAccessException e) {
                Log.e(TAG, "IllegalAccessException() " + e.getMessage(), e);
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                Log.e(TAG, "InvocationTargetException() " + e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return executed;
    }

    public boolean isMethodPresent() {
        return method != null;
    }

    @SuppressWarnings({"JavaReflectionMemberAccess", "PrivateApi"})
    // necessary for debugging- should not be used in production
    private Method getMethod() {
        try {
            return WebViewClient.class.getDeclaredMethod(METHOD_NAME, WebView.class, InputEvent.class);
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "WebViewClient does not implement " + METHOD_NAME);
            return null;
        }
    }
}
