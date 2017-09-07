package com.platzi.platzigram.view.fragment;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.crash.FirebaseCrash;
import com.platzi.platzigram.R;
import com.platzi.platzigram.adapter.PictureAdapterRecyclerView;
import com.platzi.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private static final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        FirebaseCrash.log("Inicializando" + TAG);

        RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        pictureRecycler.setLayoutManager(gridLayoutManager);
        pictureRecycler.addItemDecoration(new GridSpacingItemDecoration(2, dpTopx(10), true));

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());
        pictureRecycler.setAdapter(pictureAdapterRecyclerView);

        return view;
    }

    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures= new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Uriel Ramirez", "4 días", "3 Me Gusta"));
        pictures.add(new Picture("https://cdn.pixabay.com/photo/2015/02/18/11/50/mountain-landscape-640617_960_720.jpg", "Juan Pablo", "3 días", "10 Me Gusta"));
        pictures.add(new Picture("https://cdn.pixabay.com/photo/2017/06/29/07/20/chill-2453257_960_720.jpg", "Anahí Salgado", "2 días", "9 Me Gusta"));
        return pictures;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge){
                outRect.left = spacing - column * spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount){
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount)
                    outRect.top = spacing;
            }
        }
    }

    private int dpTopx(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
