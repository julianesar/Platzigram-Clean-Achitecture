package com.platzi.platzigram.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.platzi.platzigram.R;
import com.platzi.platzigram.login.presenter.LoginPresenter;

/**
 * Created by Julian on 9/1/17.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private static final String TAG = "LoginRepositoryImpl";
    LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(final String username, String password, final Activity activity, FirebaseAuth firebaseAuth) {

        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Vamos a guardar el Email en SharedPreferences
                            FirebaseUser user = task.getResult().getUser();

                            SharedPreferences preferences = activity.getSharedPreferences("USER", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", user.getEmail());
                            editor.apply();

                            FirebaseCrash.logcat(Log.WARN, TAG, "Usuario Logeado" + user.getEmail());
                            presenter.loginSuccess();
                        } else {
                            FirebaseCrash.logcat(Log.ERROR, TAG, "Ocurrió un Error");
                            presenter.loginError("Ocurrió un Error");
                        }
                    }
                });
    }

}
