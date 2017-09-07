package com.platzi.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.platzi.platzigram.R;
import com.platzi.platzigram.adapter.PictureAdapterRecyclerView;
import com.platzi.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseCrash.log("Iniciando" + TAG);

        showToolbar("", false, view);
        RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.prictureProfileRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView
                (buildPictures(), R.layout.cardview_picture, getActivity());

        pictureRecycler.setAdapter(pictureAdapterRecyclerView);
        return view;
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
}
