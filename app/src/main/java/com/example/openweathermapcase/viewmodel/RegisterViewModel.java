package com.example.openweathermapcase.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweathermapcase.helper.auth.AuthManager;
import com.example.openweathermapcase.helper.auth.IMobileService;
import com.example.openweathermapcase.model.User;

public class RegisterViewModel extends ViewModel {


    AuthManager authManager;

    MutableLiveData<User> user = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();

    public RegisterViewModel() {
        authManager = AuthManager.getInstance();
    }

    public void register(String email, String password) {
        authManager.setOnRegisterResultListener(new IMobileService.OnRegisterResultListener() {
            @Override
            public void onRegisterSuccess() {
                user.setValue(authManager.getCurrentUser());
            }

            @Override
            public void onRegisterFail(String errorMessage) {
                error.setValue(errorMessage);
                Log.d("RegisterViewModel", "failed: " + errorMessage);
            }
        });
        authManager.register(email, password);

        Log.d("RegisterViewModel", email + " " + password);
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
