package com.example.openweathermapcase.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweathermapcase.helper.auth.AuthManager;
import com.example.openweathermapcase.helper.auth.IMobileService;
import com.example.openweathermapcase.model.User;

public class LoginViewModel extends ViewModel {


    AuthManager authManager;

    MutableLiveData<User> user = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();

    public LoginViewModel(){
        authManager = AuthManager.getInstance();
    }

    public void login(String email, String password) {
        authManager.setOnLoginResultListener(new IMobileService.OnLoginResultListener() {
            @Override
            public void onLoginSuccess() {
                user.setValue(authManager.getCurrentUser());
            }

            @Override
            public void onLoginFail(String errorMessage) {

                Log.d("LoginViewModel", "failed: " + errorMessage);
                error.setValue(errorMessage);
            }

        });
        authManager.login(email, password);

        Log.d("RegisterViewModel", email + " " + password);
    }

    public MutableLiveData<User> getUser(){
        return user;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
