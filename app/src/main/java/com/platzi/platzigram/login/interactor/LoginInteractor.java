package com.platzi.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Julian on 9/1/17.
 */

public interface LoginInteractor {

    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth);

}
