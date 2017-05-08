package perfectneeds.android.forecastapp.utils;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import static java.lang.String.valueOf;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public static String url = "";


    public static String getUrl() {
        url = String.format(
                Locale.getDefault(),
                Constants.URL_JSON_OBJECT
        );
        try {
            url = valueOf(new URI(url.replaceAll(" ", "%20")));
            return url;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
