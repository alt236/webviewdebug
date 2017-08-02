package uk.co.alt236.webviewdebug;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import java.util.Arrays;

/*package*/class StringUtils {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String toString(final ClientCertRequest request) {
        if (request == null) {
            return "<NULL>";
        } else {
            return request.getHost() + ":" + request.getPort() + " "
                    + Arrays.toString(request.getKeyTypes())
                    + " "
                    + Arrays.toString(request.getPrincipals());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String toString(final WebResourceRequest request) {
        if (request == null) {
            return "<NULL>";
        } else {
            return request.getMethod() + " " + request.getUrl() + " " + request.getRequestHeaders();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String toString(final WebResourceResponse response) {
        if (response == null) {
            return "<NULL>";
        } else {
            return response.getStatusCode() + " " + response.getReasonPhrase();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String toString(final WebResourceError error) {
        if (error == null) {
            return "<NULL>";
        } else {
            return error.getErrorCode() + " " + error.getDescription();
        }
    }
}
