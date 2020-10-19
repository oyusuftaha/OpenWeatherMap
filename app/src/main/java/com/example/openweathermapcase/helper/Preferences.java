package com.example.openweathermapcase.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    public final int MODE_LIGHT = 0;
    public final int MODE_DARK = 1;

    private SharedPreferences prefs;
    private final String PREF_FILE_NAME = "openweathermapcase_pref";
    private final String PREF_THEME = "theme";

    Context context;

    public Preferences(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }


    public int getThemeMode(){
        return prefs.getInt(PREF_THEME,0);
    }


    public void setThemeMode(int mode) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();

        editor.putInt(PREF_THEME,mode);

        editor.apply();

    }

    public void clear(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
    }
}