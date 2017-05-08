package perfectneeds.android.forecastapp.utils.response;


import com.android.volley.VolleyError;

public class ErrorResponse {

    private VolleyError volleyError;

    public VolleyError getVolleyError() {
        return volleyError;
    }

    public void setVolleyError(VolleyError volleyError) {
        this.volleyError = volleyError;
    }
}
