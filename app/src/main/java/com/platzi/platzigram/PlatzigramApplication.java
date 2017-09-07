package com.platzi.platzigram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Julian on 9/2/17.
 */

    // Esta clase es una clase de REFERENCIAS, la creamos con el fin de inicializar algunas cosas antes
    // del Activity Principal, es decir que esto de aquí sucederá antes de que la App muestre todo al usuario
    //Aqui se crean variables que son accesibles desde toda la App

public class PlatzigramApplication extends Application {

    private static final String TAG = "PlatzigramApplication";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseCrash.log("Inicializando variables en PlatzigramApplication");

        FacebookSdk.sdkInitialize(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario Logeado" + firebaseUser.getEmail());
                } else {
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario NO Logeado");
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();
    }

    //Este metodo nos devuelve la REFERENCIA que es el enlace padre del Storage o del almacenamiento de los archivos(la url en firebase de los archivos)
    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }

}
