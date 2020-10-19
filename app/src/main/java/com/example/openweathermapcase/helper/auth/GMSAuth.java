package com.example.openweathermapcase.helper.auth;

import androidx.annotation.NonNull;

import com.example.openweathermapcase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GMSAuth implements IMobileService {
    private FirebaseAuth auth;
    private OnRegisterResultListener onRegisterResultListener;
    private OnLoginResultListener onLoginResultListener;

    public GMSAuth() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void setOnRegisterResultListener(OnRegisterResultListener onRegisterResultListener) {
        this.onRegisterResultListener = onRegisterResultListener;
    }

    @Override
    public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener) {
        this.onLoginResultListener = onLoginResultListener;
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            return new User(firebaseUser.getUid(), firebaseUser.getEmail());
        }
        return null;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onRegisterResultListener.onRegisterSuccess();
                        } else {
                            onRegisterResultListener.onRegisterFail(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginResultListener.onLoginSuccess();
                        } else {
                            onLoginResultListener.onLoginFail(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    @Override
    public void logout() {
        auth.signOut();
    }


}
