package uk.co.alt236.webviewdebug;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebViewClient;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class StringUtils {

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

    public static String resolveThreatType(final int threatType) {
        final String threat;

        switch (threatType) {
            case WebViewClient.SAFE_BROWSING_THREAT_MALWARE:
                threat = "Malware";
                break;
            case WebViewClient.SAFE_BROWSING_THREAT_PHISHING:
                threat = "Phishing";
                break;
            case WebViewClient.SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE:
                threat = "Unwanted Software";
                break;
            case WebViewClient.SAFE_BROWSING_THREAT_UNKNOWN:
            default:
                threat = "Unkwnown";
        }

        return threat + " (" + threatType + ")";
    }
}
