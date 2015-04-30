package com.mba2dna.wechkhassek.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.adapter.VolsAdapter;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.VolInfo;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment {

    static RecyclerView recList;
    public CompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root= inflater.inflate(R.layout.fragment_cities, container, false);
        Spinner spn_label = (Spinner)root.findViewById(R.id.spinner_label);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, new String[]{"AirAlgerie","Asure","Aigle","khalifa airline"});
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spn_label.setAdapter(adapter);
        spn_label.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                String[] Companies = { "AirAlgerie","Asure","Aigle","khalifa airline"};
                recList = (RecyclerView) root.findViewById(R.id.volsList);
                recList.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recList.setLayoutManager(llm);
                VolsAdapter ca = new VolsAdapter(createList(20,Companies[i]));
                recList.setAdapter(ca);
            }
        });

        recList = (RecyclerView) root.findViewById(R.id.volsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        VolsAdapter ca = new VolsAdapter(createList(20,"Air Algerie"));
        recList.setAdapter(ca);
        return root;
    }
    private List<VolInfo> createList(int size,String Companie) {
        String[] places = { "Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago"};

        String[] etat = { "Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure",};
        List<VolInfo> result = new ArrayList<VolInfo>();
        for (int i=1; i <= size; i++) {
            VolInfo ci = new VolInfo();
            ci.name = VolInfo.NAME_PREFIX + (i* 126);
            ci.company = Companie;
            ci.times = Constants.RandomDateTime();
            ci.distination = places[i];
            ci.Etat = etat[i];

            result.add(ci);

        }

        return result;
    }

}
