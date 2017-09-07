package com.platzi.platzigram.post.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.platzi.platzigram.PlatzigramApplication;
import com.platzi.platzigram.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private ImageView imgPhoto;
    private Button btnCreatePost;

    private String photoPath;
    //Podremos una Entidad de PlatzigramApplication para traer referencias inicializadas allí
    private PlatzigramApplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //Esto es para pasarle la "referencia" que se inicializa en PlatzigramActivity
        app = (PlatzigramApplication) getApplicationContext();
        storageReference = app.getStorageReference();

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        if (getIntent().getExtras() != null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

    }

    //metodo para subir la foto a Firebase Storage
    private void uploadPhoto() {
        //Extraemos la imagen del ImageView...poniendola en un caché
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        //Aqui tomamos la foto que puso en el Caché y la pasamos a un Bitmap
        Bitmap bitmap = imgPhoto.getDrawingCache();
        //Lo siguiente lo usaremos para la escritura del archivo en Firebase
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //comprimimos un poco la imagen y la cargamos a "baos"
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] photoByte = baos.toByteArray();

        //ahora trabajaremos el nombre del archivo
        //Lo haremos con substrings(lastindexof) que se trae un string y nosotros le indicamos desde donde hasta donde
        //aquí el extraemos el nombre del archivo desde el PATH que tenemos, el nombre empieza en el primer "/ + 1" hasta el
        //final del path
        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1, photoPath.length());

        //Empezamos a pedir "hijos" a storageReference que es la URL base de Storage de Firebase...los hijos son carpetas
        //creadas en Firebase Storage
        //y creamos una ruta donde quedará guardada la img o foto cargandola a un Objeto tipo StorageReference
        StorageReference photoReference = storageReference.child("postImages/"+photoName);

        //ahora usamos la clase uploadtask para subir el archivo
        UploadTask uploadTask = photoReference.putBytes(photoByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error al subir la foto" + e.toString());
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri uriPhoto = taskSnapshot.getDownloadUrl();
                String photoURL = uriPhoto.toString();
                Log.w(TAG, "URL Photo > " + photoURL);
                finish();
            }
        });

    }

    private void showPhoto(){
        Picasso.with(this).load(photoPath).into(imgPhoto);
    }

}
