package com.mba2dna.wechkhassek.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.adapter.FavorieAdapter;
import com.mba2dna.wechkhassek.model.RenderVous;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavorieFragment extends Fragment {
    private Typeface myFont;
    static RecyclerView recList;
    // Log tag
    private static final String TAG = "Message";

    public FavorieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root= inflater.inflate(R.layout.fragment_favorie, container, false);



        recList = (RecyclerView) root.findViewById(R.id.volsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        FavorieAdapter ca = new FavorieAdapter(createList(12));
        recList.setAdapter(ca);
        return root;
    }
    private List<RenderVous> createList(int size) {
        String[] places = getResources().getStringArray(R.array.willaya_array);
        String[] etat = getResources().getStringArray(R.array.specialiter_array);
        String[] names = {"","BIDA Mohamed Amine" ,"Amriou Farid" , "Salhi Abdelkrim", "Moukhtari Amer" , "Rabhi Mohamed" , "Tahelkhir Said ", "Wadah Fakri" , "Mazari Rabah" , "Serdina Smina" , "Bouhan Sifdine" ,"Motar Tah" , "Daraji Samir", "Mouhoubi Adel"};
        List<RenderVous> result = new ArrayList<RenderVous>();
        for (int i=1; i <= size; i++) {
            RenderVous ci = new RenderVous();
            ci.name = "";
            ci.nom_prenom = etat[i];
            ci.times = places[i];
            ci.distination = "N°: " +i;
            ci.Etat = names[i];
            result.add(ci);
        }
        return result;
    }
    }



