package com.mba2dna.wechkhassek.fragment;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.constants.Constants;
import com.rey.material.widget.FloatingActionButton;


public class HomeFragment extends Fragment {
    private TextView bienvenuelbl, descritpionlbl;
    private FloatingActionButton addArtisant;

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
                Constants.NexaLight);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                Constants.NexaBold);
        bienvenuelbl = (TextView) rootView.findViewById(R.id.bienvenuelbl);
        bienvenuelbl.setTypeface(tf);
        descritpionlbl = (TextView) rootView.findViewById(R.id.descriptionlbl);
        descritpionlbl.setTypeface(tl);
        addArtisant = (FloatingActionButton) rootView.findViewById(R.id.fab_image);
        addArtisant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RechercheArtisantFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
            }
        });
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
