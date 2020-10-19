package com.example.openweathermapcase.helper.auth;

import com.example.openweathermapcase.model.User;

public class AuthManager {
    private static AuthManager instance;
    private IMobileService service;

    private AuthManager(IMobileService service) {
        this.service = service;
    }

    public void setOnRegisterResultListener(IMobileService.OnRegisterResultListener onRegisterResultListener){
        service.setOnRegisterResultListener(onRegisterResultListener);
    }

    public void setOnLoginResultListener(IMobileService.OnLoginResultListener onLoginResultListener){
        service.setOnLoginResultListener(onLoginResultListener);
    }

    public void register(String email, String password){
        service.register(email, password);
    }

    public void login(String email, String password){
        service.login(email,password);
    }

    public void logout(){
        service.logout();
    }

    public User getCurrentUser(){
        return service.getCurrentUser();
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            // GMS veya HMS tercihi burada yapılabilir
            instance = new AuthManager(new GMSAuth());
        }
        return instance;
    }
}
