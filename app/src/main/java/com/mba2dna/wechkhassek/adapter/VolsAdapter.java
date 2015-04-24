package com.mba2dna.wechkhassek.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.VolInfo;

import java.util.List;

/**
 * Created by MBA2DNA on 14/04/2015.
 */
public class VolsAdapter extends RecyclerView.Adapter<VolsAdapter.VolsViewHolder>{
    private List<VolInfo> Vollist;
    private int lastPosition = -1;
    public VolsAdapter(List<VolInfo> Vollist) {
        this.Vollist = Vollist;
    }


    @Override
    public void onBindViewHolder(VolsViewHolder volViewHolder, int i) {
        VolInfo ci = Vollist.get(i);
        volViewHolder.vEtat.setText(ci.Etat);
        volViewHolder.vCompany.setText(ci.company);
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
        return Vollist.size();
    }


    @Override
    public VolsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.vol_layout, viewGroup, false);

        return new VolsViewHolder(itemView);
    }

    public static class VolsViewHolder extends RecyclerView.ViewHolder {

        protected TextView vEtat;
        protected TextView vCompany;
        protected TextView vTimes;
        protected TextView vDistination;
        protected TextView vTitle;

        public VolsViewHolder(View v) {
            super(v);

            Typeface tf = Typeface.createFromAsset(
                    v.getContext().getAssets(),
                    Constants.NexaLight);
            Typeface tl = Typeface.createFromAsset(v.getContext().getAssets(),
                    Constants.NexaBold);
            vEtat =  (TextView) v.findViewById(R.id.txtEtat);
            vEtat.setTypeface(tl);
            vEtat.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_compass_grey600_18dp, //left
                    0, //top
                    0, //right
                    0);
            vCompany = (TextView)  v.findViewById(R.id.txtCompany);
            vCompany.setTypeface(tl);
            vCompany.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_bank_grey600_18dp, //left
                    0, //top
                    0, //right
                    0);
            vTimes = (TextView)  v.findViewById(R.id.txtTimes);
            vTimes.setTypeface(tl);
            vTimes.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_alarm_grey600_18dp, //left
                    0, //top
                    0, //right
                    0);
            vDistination = (TextView)  v.findViewById(R.id.txtDistination);
            vDistination.setTypeface(tl);
            vDistination.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_map_marker_circle_grey600_18dp, //left
                    0, //top
                    0, //right
                    0);
            vTitle = (TextView) v.findViewById(R.id.title);
            vTitle.setTypeface(tf);
            vTitle.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_airplane_white_18dp, //left
                    0, //top
                    0, //right
                    0);
        }
    }
}
