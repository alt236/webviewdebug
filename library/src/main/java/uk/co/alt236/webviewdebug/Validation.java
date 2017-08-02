package uk.co.alt236.webviewdebug;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*package*/ final class Validation {
    private final boolean strict;

    public Validation() {
        this(true);
    }

    public Validation(final boolean strict) {
        this.strict = strict;
    }

    public <T> void ensureAllMethodsAreOverridden(final Class<T> baseClass,
                                                  final Class<?> childClass) {

        final Method[] baseClassMethods = baseClass.getDeclaredMethods();

        Method candidate;
        for (final Method method : baseClassMethods) {
            if (!(Modifier.isFinal(method.getModifiers()) || Modifier.isPrivate(method.getModifiers()))) {
                candidate = getMethod(childClass, method);
                if (strict && candidate == null) {
                    throw new IllegalStateException("Class not implementing method: " + method);
                }
            }
        }
    }

    @Nullable
    private Method getMethod(final Class<?> clazz,
                             final Method method) {
        Method retVal;

        try {
            retVal = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (strict) {
                Log.d("REFLECTION", "Method found: " + method);
            }
        } catch (NoSuchMethodException e) {
            Log.e("REFLECTION", "Method not found: " + method);
            retVal = null;
        }

        return retVal;
    }
}
