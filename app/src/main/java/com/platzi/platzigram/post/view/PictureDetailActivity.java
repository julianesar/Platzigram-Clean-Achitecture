package com.platzi.platzigram.post.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.StorageReference;
import com.platzi.platzigram.PlatzigramApplication;
import com.platzi.platzigram.R;
import com.squareup.picasso.Picasso;

public class PictureDetailActivity extends AppCompatActivity {

    private static final String PHOTO_NAME = "JPEG_20170902_18-01-52_547411362.jpg";
    private static final String TAG = "PictureDetailActivity";
    private ImageView imgHeader;
    private PlatzigramApplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        FirebaseCrash.log("Inicializando" + TAG);

        //Obtenemos la REFERENCIA de almacenamiento de firebase es decir la URL principal
        app = (PlatzigramApplication) getApplicationContext();
        storageReference = app.getStorageReference();

        imgHeader = (ImageView) findViewById(R.id.imgHeader);

        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(new Fade());
        }

        showData();

    }

    //metodo para mostrar los datos traidos
    private void showData() {
        //Vamos a traer la img de firebase y mostrarla con Picasso
        storageReference.child("postImages/"+PHOTO_NAME)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(PictureDetailActivity.this).load(uri.toString()).into(imgHeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PictureDetailActivity.this, "Ocurrió un Error al traer la Foto", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
        });
    }

    public void showToolbar(String title, Boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Para soportar versiones anteriores:
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        //Para ver si tiene o no boton de regreso...si es true lo pone, sino no lo hace
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

}
