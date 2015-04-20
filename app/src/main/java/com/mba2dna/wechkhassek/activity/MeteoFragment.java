package com.mba2dna.wechkhassek.activity;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mba2dna.wechkhassek.R;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeteoFragment extends Fragment {

    public static MeteoFragment newInstance(){
        MeteoFragment fragment = new MeteoFragment();
        return fragment;
    }
    public MeteoFragment() {
        // Required empty public constructor
    }
    private Drawable[] mDrawables = new Drawable[2];
    private int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View root =inflater.inflate(R.layout.fragment_meteo, container, false);
        Spinner spn_label = (Spinner)root.findViewById(R.id.spinner_label);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, new String[]{"Guelma", "New York", "London","Canada","Qatar", "Lyon", "montreal","Oran","Serif", "Constantine", "Tunisia","Hassi mesoud","Paris", "Morocco", "Chicago","Germany"});
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spn_label.setAdapter(adapter);
        spn_label.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                TextView citie, temp,dy,wtxt;
                ImageView wheather;
                citie = (TextView) root.findViewById(R.id.city_field);
                wheather = (ImageView) root.findViewById(R.id.weathericon);
                temp = (TextView) root.findViewById(R.id.current_temperature_field);
                dy = (TextView) root.findViewById(R.id.updated_field);
                wtxt = (TextView) root.findViewById(R.id.details_field);

                String[] cities = {"Guelma", "New York", "London","Canada","Qatar", "Lyon", "montreal","Oran","Serif", "Constantine", "Tunisia","Hassi mesoud","Paris", "Morocco", "Chicago","Germany"};
                int[] weathers = { R.drawable.ic_clear,R.drawable.ic_cloudly,R.drawable.ic_blowing_snow, R.drawable.ic_drizzle,R.drawable.ic_isolated_thundershowers,R.drawable.ic_showers,R.drawable.ic_clear,R.drawable.ic_cloudly,R.drawable.ic_blowing_snow, R.drawable.ic_drizzle,R.drawable.ic_isolated_thundershowers,R.drawable.ic_showers,R.drawable.ic_isolated_thundershowers,R.drawable.ic_showers,R.drawable.ic_showers,R.drawable.ic_isolated_thundershowers};
                String[] xtxtw = { "clear","cloudy","blowing snow","drizzle","isolated thundershowers","showers","clear","cloudy","blowing snow","drizzle","isolated thundershowers","showers","clear","cloudy","blowing snow","drizzle","isolated thundershowers","showers","clear","cloudy","blowing snow","drizzle","isolated thundershowers","showers","clear","cloudy","blowing snow","drizzle","isolated thundershowers","showers"};
                String[] temps = { "20° C","18° C","02° C","12° C","05° C","08° C","20° C","18° C","02° C","12° C","05° C","08° C","20° C","18° C","02° C","12° C","12° C","12° C"};
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                citie.setText(cities[i]);
                wheather.setImageResource(weathers[i]);
                temp.setText(temps[i]);
                dy.setText(sdf.format(new Date()));
                wtxt.setText(xtxtw[i]);
            }
        });
        return root;
    }


}
