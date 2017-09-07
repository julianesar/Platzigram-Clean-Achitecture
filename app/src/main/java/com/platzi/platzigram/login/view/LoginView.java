package com.platzi.platzigram.login.view;

/**
 * Created by Julian on 9/1/17.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showPgrogressBar();
    void hideProgressBar();

    void loginError(String error);

    void goHome();

}
