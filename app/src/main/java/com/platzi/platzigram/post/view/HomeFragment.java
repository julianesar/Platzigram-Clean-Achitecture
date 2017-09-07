package com.platzi.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.platzi.platzigram.R;
import com.platzi.platzigram.adapter.PictureAdapterRecyclerView;
import com.platzi.platzigram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    //En un fragment no se llama onCreate ya que vive dentro del activty como si fuera un View más
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);
        RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);
        fabCamera = (FloatingActionButton) view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView
                (buildPictures(), R.layout.cardview_picture, getActivity());

        pictureRecycler.setAdapter(pictureAdapterRecyclerView);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return view;

    }

    //Abre la camara para tomar la foto
    private void takePicture() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){  //Verificacion de si el device tiene camara

            //Creamos un archivo temp donde se guardará la foto
            File photoFile = null;

            try{
                photoFile = createImageFile();
            }catch (Exception e){
                e.printStackTrace();
                //Crash Reporting para firebase:
                FirebaseCrash.report(e);
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.platzi.platzigram", photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);    //lo que se hace es iniciar una Actividad y esperar un resultado
            }
        }
    }

    //Aqui se recibe el resultado del Activity de la camara
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("HomeFragment", "CAMERA OK!!");
            Intent i = new Intent(getActivity(), NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
            startActivity(i);
        }

    }

    //Este metodo alimenta la info que se muestra en los CardViews
    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Uriel Ramirez", "4 días", "3 Me Gusta"));
        pictures.add(new Picture("https://cdn.pixabay.com/photo/2015/02/18/11/50/mountain-landscape-640617_960_720.jpg", "Juan Pablo", "3 días", "10 Me Gusta"));
        pictures.add(new Picture("https://cdn.pixabay.com/photo/2017/06/29/07/20/chill-2453257_960_720.jpg", "Anahí Salgado", "2 días", "9 Me Gusta"));
        return pictures;
    }

    public void showToolbar(String title, Boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //Para soportar versiones anteriores:
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        //Para ver si tiene o no boton de regreso...si es true lo pone, sino no lo hace
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private File createImageFile() throws IOException {

        // Para asignar un nombre usaremos timeStamp ya que es la única forma de dar un nombre único a cada archivo
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Log.e("HomeFragment", storageDir.toString());

        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPathTemp = "file:" + photo.getAbsolutePath();

        return photo;
    }
}
