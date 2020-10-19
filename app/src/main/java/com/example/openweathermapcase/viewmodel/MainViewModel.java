package com.example.openweathermapcase.viewmodel;

import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweathermapcase.helper.Preferences;
import com.example.openweathermapcase.helper.auth.AuthManager;
import com.example.openweathermapcase.model.User;
import com.example.openweathermapcase.model.weatherForecast.WeatherForecast;
import com.example.openweathermapcase.network.ApiClient;
import com.example.openweathermapcase.network.RestInterface;
import com.example.openweathermapcase.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final String TAG = "MainViewModel";

    private AuthManager authManager;

    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<WeatherForecast> weatherForecast = new MutableLiveData<>();


    public MainViewModel(){
        authManager = AuthManager.getInstance();
        user.setValue(authManager.getCurrentUser());
    }

    public void logout(){
        authManager.logout();
        user.setValue(null);
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<WeatherForecast> getWeatherForecast() {
        return weatherForecast;
    }


    public void changeThemeMode(Preferences preferences){
        if (preferences.getThemeMode() == preferences.MODE_DARK){
            preferences.setThemeMode(preferences.MODE_LIGHT);
        }else {
            preferences.setThemeMode(preferences.MODE_DARK);
        }
        getThemeMode(preferences);
    }

    public void getThemeMode(Preferences preferences){
        if (preferences.getThemeMode() == preferences.MODE_DARK){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void requestWeather(Double lat, Double lon){

        RestInterface restInterface = ApiClient.getClient().create(RestInterface.class);
        Call<WeatherForecast> call = restInterface.getWeather(lat, lon, Constants.excludeItems, Constants.openWeatherMapAppID,Constants.units);
        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                if (response.code() == 200){
                    weatherForecast.setValue(response.body());
                    Log.d(TAG, "onResponse: " + weatherForecast.getValue());
                }else {

                    Log.d(TAG, "onFail: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {

                Log.d(TAG, "onFail: " + t.getCause());
            }
        });
    }



}
