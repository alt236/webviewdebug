package uk.co.alt236.webviewdebug;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebViewClient;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

final class Validation {
    private static final String TAG = BuildConfig.DEFAULT_LOG_TAG;

    // We need to validate that the DebugClient overrides all methods
    // overridden in the wrapped client.

    public boolean validate(
            final Class<? extends WebViewClient> wrappedClient,
            final Class<? extends DebugWebViewClient> debugClient) {

        final List<Method> unimplementedMethods = new ArrayList<>();
        final Method[] wrappedClientMethods = wrappedClient.getMethods();
        Method candidate;

        for (final Method method : wrappedClientMethods) {
            if (!isIgnorable(method)) {
                candidate = getMethod(debugClient, method);
                if (candidate == null
                        && (getMethod(Object.class, method) == null)) {
                    unimplementedMethods.add(method);
                }
            }
        }

        if (unimplementedMethods.isEmpty()) {
            Log.i(TAG, "All methods implemented :)");
        } else {
            Log.e(TAG, "-----------------------------");
            for (final Method method : unimplementedMethods) {
                Log.e(TAG, debugClient.getSimpleName() + " does not implement: " + method);
            }
            Log.e(TAG, "-----------------------------");
        }

        return unimplementedMethods.isEmpty();
    }


    private static boolean isIgnorable(@NonNull final Method method) {
        return Modifier.isPrivate(method.getModifiers()) || Modifier.isStatic(method.getModifiers());
    }

    @Nullable
    private static Method getMethod(final Class<?> clazz, final Method method) {
        try {
            return clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
