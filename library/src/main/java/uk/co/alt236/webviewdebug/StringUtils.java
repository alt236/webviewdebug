package uk.co.alt236.webviewdebug;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceResponse;

import java.util.Arrays;

class StringUtils {

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
    public static String toString(final WebResourceResponse response) {
        if (response == null) {
            return "<NULL>";
        } else {
            return response.getStatusCode() + " " + response.getReasonPhrase();
        }
    }
}
