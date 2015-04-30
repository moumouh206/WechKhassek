package com.mba2dna.wechkhassek.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.AppController;
import com.mba2dna.wechkhassek.constants.Constants;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.rey.material.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment {
    MaterialCalendarView calendar;
    Button ajoutdates;
    private ProgressDialog pDialog;
    private Drawable oldBackground = null;
    private int currentColor = 0xFFf1b35e;
    private List<String> DateList = new ArrayList<String>();
    String uid ="";

    public AgendaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_agenda, container, false);
        String fontLight = "fonts/NexaLight.ttf";
        Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                fontLight);
        TextView titre = (TextView) root.findViewById(R.id.textView1);
        titre.setTypeface(tl);
        if (savedInstanceState == null) {

            if(getActivity().getIntent().getExtras() == null) {
                uid= "";
            } else {
                uid= getActivity().getIntent().getExtras().getString("uid");
            }
        } else {
            uid= (String) savedInstanceState.getSerializable("uid");
        }
        //Toast.makeText(this,"La uid :"+ uid +"", Toast.LENGTH_SHORT).show();
        ajoutdates = (Button) root.findViewById(R.id.ajoutdates);
        ajoutdates.setEnabled(false);
        ajoutdates.setTypeface(tl);
        ajoutdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Fermer la Session")
                        .setMessage("Les dates sont enregistré avec succés , voullez vous fermer la session?")
                        .setPositiveButton("Oui",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
								/*UserFunctions userFunction = new UserFunctions();
								userFunction
										.logoutUser(getApplicationContext());
								Intent login = new Intent(
										getApplicationContext(),
										LoginActivity.class);
								login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(login);*/

                                    }

                                }).setNegativeButton("Non", null).show();


            }
        });
        calendar = (MaterialCalendarView) root.findViewById(R.id.calendar);
        calendar.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {

                        final String d =calendar.getSelectedDate().toString();
                        new AlertDialog.Builder(getActivity())
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Ajouter la date")
                                .setMessage("Etes vous sure de vouloir ajouter cette date?")
                                .setPositiveButton("Oui",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                DateList.add(d);
                                                pDialog = new ProgressDialog(getActivity());
                                                pDialog.setMessage("Ajout en cours...");
                                                pDialog.setCancelable(false);
                                                pDialog.show();
                                                String lien = Constants.URL + "?addDay=true&d="+d
                                                        + "&i=" + uid;
                                                Log.d("Add Day", lien);
                                                JsonObjectRequest movieReq = new JsonObjectRequest(
                                                        Request.Method.GET, lien,(String) null,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                pDialog.hide();
                                                                Toast.makeText(getActivity().getApplicationContext(), "La date :" + d + " est ajouté", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // log.d(TAG, "Error: " + error.getMessage());
                                                        pDialog.hide();

                                                    }
                                                });

                                                // Adding request to request queue
                                                AppController.getInstance().addToRequestQueue(movieReq);

                                                ajoutdates.setEnabled(true);
                                            }

                                        }).setNegativeButton("Non", null).show();



            }
        });
        return root;
    }


}
