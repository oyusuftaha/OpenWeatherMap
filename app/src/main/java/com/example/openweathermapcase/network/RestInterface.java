package com.example.openweathermapcase.network;

import com.example.openweathermapcase.model.weatherForecast.WeatherForecast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("onecall")
    Call<WeatherForecast> getWeather(@Query("lat") Double lat,
                                     @Query("lon") Double lon,
                                     @Query("exclude") String exclude,
                                     @Query("appid") String appID,
                                     @Query("units") String units);

}
