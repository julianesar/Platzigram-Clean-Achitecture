package com.platzi.platzigram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platzi.platzigram.R;
import com.platzi.platzigram.model.Picture;
import com.platzi.platzigram.post.view.PictureDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Julian on 8/31/17.
 */

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder>{

    //Este es el elemento que viene de internet o database...objetos POJO en base al model creado
    private ArrayList<Picture> pictures;
    //ahora el recurso o layout...nuestro cardview
    private int resource;
    //Pasaremos como parametro la Activity desde donde se está llamando esta clase
    private Activity activity;

    //Este es el constructor de la clase
    public PictureAdapterRecyclerView(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
    }

    //este metodo va a inicializar la clase inner que se encarga de los views: viewholder
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PictureViewHolder(view);
    }

    //Este medoto trabaja con toda la lista de elemntos, el array de Pictures declarado al inicio...aqui se hace
    //el paso de datos de cada objeto de la lista de Pictures...Asigna los objetos a cada item de la lista de RecyclerView
    //segun la posicion
    // Este recorre la lista de datos externos...y asigna los cardViews
    //Settea lo que debe aparecer en los Views
    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);
        holder.usernameCard.setText(picture.getUserName());
        holder.timeCard.setText(picture.getTime());
        holder.likeNumberCard.setText(picture.getLike_number());
        //esto es cómo traer imagenes desde internet:
        Picasso.with(activity).load(picture.getPicture()).into(holder.pictureCard);

        holder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PictureDetailActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, activity.getString(R.string.transitionname_picture)).toBundle());
                }else {
                    activity.startActivity(intent);
                }
            }
        });

    }

    //Este metodo es el que dice cuantas veces debe recorer o generar CardViews
    @Override
    public int getItemCount() {
        return pictures.size();
    }


    //La inner class ViewHolder que se encarga de los Views
    public class PictureViewHolder extends RecyclerView.ViewHolder{

        //Aqui se definen los views que componen al CardView
        private ImageView pictureCard;
        private TextView usernameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.pictureCard);
            usernameCard = (TextView) itemView.findViewById(R.id.userNameCard);
            timeCard = (TextView) itemView.findViewById(R.id.timeCard);
            likeNumberCard = (TextView) itemView.findViewById(R.id.likeNumberCard);

        }
    }
}
