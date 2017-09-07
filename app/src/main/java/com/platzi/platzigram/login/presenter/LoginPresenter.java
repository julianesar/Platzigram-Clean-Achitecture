package com.platzi.platzigram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Julian on 9/1/17.
 */

public interface LoginPresenter {

    void signIn(String usermane, String password, Activity activity, FirebaseAuth firebaseAuth);
    void loginSuccess();
    void loginError(String error);

}
