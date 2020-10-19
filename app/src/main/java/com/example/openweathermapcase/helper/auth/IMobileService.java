package com.example.openweathermapcase.helper.auth;

import com.example.openweathermapcase.model.User;

public interface IMobileService {
    interface OnRegisterResultListener {
        void onRegisterSuccess();

        void onRegisterFail(String errorMessage);
    }
    interface OnLoginResultListener {
        void onLoginSuccess();

        void onLoginFail(String errorMessage);
    }


    User getCurrentUser();

    void register(String email, String password);

    void login(String email, String password);

    void logout();

    void setOnRegisterResultListener(OnRegisterResultListener onRegisterResultListener);

    void setOnLoginResultListener(OnLoginResultListener onLoginResultListener);
}
