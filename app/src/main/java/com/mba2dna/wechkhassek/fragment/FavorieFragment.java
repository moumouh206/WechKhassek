package com.mba2dna.wechkhassek.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mba2dna.wechkhassek.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavorieFragment extends Fragment {


    public FavorieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorie, container, false);
    }


}
