package com.mba2dna.wechkhassek.activity;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.constants.Constants;


public class HomeFragment extends Fragment {
    public TextView bienvenuelbl, descritpionlbl;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                Constants.fontLight);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                Constants.fontBold);
        bienvenuelbl = (TextView) rootView.findViewById(R.id.bienvenuelbl);
        bienvenuelbl.setTypeface(tf);
        descritpionlbl = (TextView) rootView.findViewById(R.id.descriptionlbl);
        descritpionlbl.setTypeface(tl);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
