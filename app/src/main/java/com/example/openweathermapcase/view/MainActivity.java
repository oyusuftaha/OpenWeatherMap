package com.example.openweathermapcase.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.openweathermapcase.R;
import com.example.openweathermapcase.adapter.WeatherAdapter;
import com.example.openweathermapcase.helper.Preferences;
import com.example.openweathermapcase.helper.gps.GPSTracker;
import com.example.openweathermapcase.helper.gps.IGPSTracker;
import com.example.openweathermapcase.helper.map.MapManager;
import com.example.openweathermapcase.model.User;
import com.example.openweathermapcase.model.weatherForecast.WeatherForecast;
import com.example.openweathermapcase.viewmodel.MainViewModel;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IGPSTracker {


    @BindView(R.id.btnLogout)
    Button btnLogout;

    @BindView(R.id.rvWeather)
    RecyclerView rvWeather;

    @BindView(R.id.btnThemeMode)
    Button btnThemeMode;


    private SupportMapFragment supportMapFragment;

    private MainViewModel mainViewModel;

    MapManager mapManager = MapManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getThemeMode(new Preferences(getApplicationContext()));

        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        GPSTracker gpsTracker = new GPSTracker(this,getApplicationContext());
        gpsTracker.requestLocation();



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.logout();
            }
        });

        btnThemeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.changeThemeMode(new Preferences(getApplicationContext()));
            }
        });

        mainViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user !=null) {

                }else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });

        mainViewModel.getWeatherForecast().observe(this, new Observer<WeatherForecast>() {
            @Override
            public void onChanged(WeatherForecast weatherForecast) {
                rvWeather.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvWeather.setAdapter(new WeatherAdapter(MainActivity.this, weatherForecast.getDaily()));
            }
        });

    }

    @Override
    public void hasNoLocationPermission(String[] permissions) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Warning")
                .setMessage("Please allow location services.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissions, GPSTracker.LOCATION_PERMISSION_CODE);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void noProviderEnabled() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Warning")
                .setMessage("Please enable location provider and try again.")
                .setPositiveButton("OK",null)
                .create()
                .show();
    }

    @Override
    public void onLocationHandled(Location location) {
        mapManager.setMapLocation(location);
        mapManager.setMapZoom(7.0f);
        mapManager.getMap(supportMapFragment);

        mainViewModel.requestWeather(location.getLatitude(),location.getLongitude());
    }
}