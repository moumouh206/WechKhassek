package com.mba2dna.wechkhassek.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.RenderVous;

import java.util.List;
import java.util.Random;

/**
 * Created by MBA2DNA on 30/04/2015.
 */
public class FavorieAdapter extends RecyclerView.Adapter<FavorieAdapter.FavorieViewHolder>{
    private List<RenderVous> favorieList;
    private int lastPosition = -1;
    public FavorieAdapter(List<RenderVous> favorieList) {
        this.favorieList = favorieList;
    }


    @Override
    public void onBindViewHolder(FavorieViewHolder volViewHolder, int i) {
        RenderVous ci = favorieList.get(i);
        volViewHolder.vEtat.setText(ci.Etat);
        volViewHolder.vCompany.setText(ci.nom_prenom);
        volViewHolder.vTimes.setText(ci.times);
        volViewHolder.vDistination.setText(ci.distination);
        volViewHolder.vTitle.setText(ci.name );
        setAnimation(volViewHolder.itemView, i);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return favorieList.size();
    }


    @Override
    public FavorieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.favorite_artisant_layout, viewGroup, false);

        return new FavorieViewHolder(itemView);
    }

    public static class FavorieViewHolder extends RecyclerView.ViewHolder {

        protected TextView vEtat;
        protected TextView vCompany;
        protected TextView vTimes;
        protected TextView vDistination;
        protected TextView vTitle;
        protected RatingBar ratingBar;

        public FavorieViewHolder(View v) {
            super(v);

            Typeface tf = Typeface.createFromAsset(
                    v.getContext().getAssets(),
                    Constants.NexaLight);
            Typeface tl = Typeface.createFromAsset(v.getContext().getAssets(),
                    Constants.NexaBold);
            vEtat =  (TextView) v.findViewById(R.id.title);
            vEtat.setTypeface(tl);
            vCompany = (TextView)  v.findViewById(R.id.rating);
            vCompany.setTypeface(tl);
            vTimes = (TextView)  v.findViewById(R.id.genre);
            vTimes.setTypeface(tl);
            vDistination = (TextView)  v.findViewById(R.id.releaseYear);
            vDistination.setTypeface(tl);
            vTitle = (TextView) v.findViewById(R.id.Calltxt);
            vTitle.setTypeface(tf);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            Random r = new Random();
            int i1 = r.nextInt(5 - 1);
            ratingBar.setRating(i1);
        }
    }
}

