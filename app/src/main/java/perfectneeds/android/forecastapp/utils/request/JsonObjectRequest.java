package perfectneeds.android.forecastapp.utils.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import perfectneeds.android.forecastapp.model.AllDays;
import perfectneeds.android.forecastapp.model.Today;
import perfectneeds.android.forecastapp.utils.Utils;
import perfectneeds.android.forecastapp.utils.response.ErrorResponse;
import perfectneeds.android.forecastapp.utils.response.ForecastResponse;


public class JsonObjectRequest extends ForecatBaseRequest {
    private static final String LOG_TAG = JsonObjectRequest.class.getSimpleName();
    public JsonObjectRequest() {
        super(Method.GET,
                Utils.getUrl(),
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(LOG_TAG ,"json object  " +jsonObject);
                        ArrayList<Today> todays= new ArrayList<Today>();
                        ArrayList<AllDays> allDayses= new ArrayList<AllDays>();
                        try {
                            JSONObject forecast = jsonObject.getJSONObject("forecast");

                            JSONObject txt_forecast = forecast.getJSONObject("txt_forecast");
                            JSONArray forecastday = txt_forecast.getJSONArray("forecastday");
                            for (int i=0 ; i< forecastday.length() ; i++){
                                JSONObject object = (JSONObject) forecastday.get(i);
                                int period = object.getInt("period");
                                int periodDay = (int) (period+2)/ (int) 2;
                                String name = object.getString("title");
                                String icon = object.getString("icon_url");
                                String description = object.getString("icon");
                               Today today= new Today(period , name , icon ,description ,periodDay);
                                todays.add(today);
                            }


                            JSONObject simpleforecast = forecast.getJSONObject("simpleforecast");
                            JSONArray forecastday2 = simpleforecast.getJSONArray("forecastday");
                            for (int i=0 ; i< forecastday2.length() ; i++){
                                JSONObject object = (JSONObject) forecastday2.get(i);
                                int period = object.getInt("period");
                                String icon = object.getString("icon_url");
                                String description = object.getString("icon");


                                JSONObject dateObject = object.getJSONObject("date");
                                String date = dateObject.getString("pretty");
                                String title = dateObject.getString("weekday");

                                JSONObject high = object.getJSONObject("high");
                                String fehHigh = high.getString("fahrenheit");
                                String celHigh = high.getString("celsius");

                                JSONObject low = object.getJSONObject("low");
                                String fehLow = high.getString("fahrenheit");
                                String celLow = high.getString("celsius");

                                AllDays allDays = new AllDays(period,title,date,icon,description,fehHigh,celHigh,fehLow,celLow);
                                allDayses.add(allDays);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ForecastResponse  forecastResponse = new ForecastResponse();
                        forecastResponse.setToday(todays);
                        forecastResponse.setAllDays(allDayses);
                        EventBus.getDefault().postSticky(forecastResponse);

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(LOG_TAG ,"volleyError  " +volleyError.getMessage().toString());
                            ErrorResponse errorResponse = new ErrorResponse();
                            errorResponse.setVolleyError(volleyError);
                            EventBus.getDefault().postSticky(errorResponse);

                    }
                });


    }
}

