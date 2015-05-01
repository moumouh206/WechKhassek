package com.mba2dna.wechkhassek.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.service.GPSTracker;
import com.rey.material.widget.Button;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddArtisantFragment extends Fragment {
    private TextView WillayaTxt, titre, explicationtxt, nom, prenom, phone,
            SpecialiteTxt;
    Double lt, lg;
    private String[] Specialites, willaya;
    private Spinner mySpinner, willayaSpinner;
    private LoadToast lt1;
    private MultiAutoCompleteTextView adrTxt;
    MapView mapView;
    GoogleMap map;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "entry_date";
    Typeface tf;
    String uid = "";
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    // GPSTracker class
    GPSTracker gps;
    LinearLayout l;

    public AddArtisantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_add_artisant, container, false);
        Specialites = getResources().getStringArray(R.array.specialiter_array);
        willaya = getResources().getStringArray(R.array.willaya_array);
        lt1 = new LoadToast(getActivity());
        lt1.setText("Enregistrement en cours...");
        lt1.setTranslationY(300);
        lt1.setProgressColor(getResources().getColor(R.color.colorAccent));
        String fontBold = Constants.NexaBold;
        String fontLight =  Constants.NexaLight;
         tf = Typeface.createFromAsset(getActivity().getAssets(), fontBold);
        Typeface tl = Typeface.createFromAsset(getActivity().getAssets(), fontLight);
        TextView titre = (TextView) root.findViewById(R.id.titre1);
        titre.setTypeface(tf);
        TextView explicationtxt = (TextView) root.findViewById(R.id.explicationtxt);
        explicationtxt.setTypeface(tl);
        l = (LinearLayout) root.findViewById(R.id.mapAdrcontener);
        l.setVisibility(View.GONE);
        nom = (TextView) root.findViewById(R.id.nom);
        nom.setTypeface(tl);
        prenom = (TextView) root.findViewById(R.id.prenom);
        prenom.setTypeface(tl);
        phone = (TextView) root.findViewById(R.id.phone);
        phone.setTypeface(tl);
        String[] words = new String[] { "word1", "word2", "word3", "word4",
                "word5" };
        adrTxt = (MultiAutoCompleteTextView) root.findViewById(R.id.AdrTxt);
        ArrayAdapter<String> aaStr = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, words);
        adrTxt.setAdapter(aaStr);
        adrTxt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        adrTxt.setTypeface(tl);
        adrTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                final String lien = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                        + adrTxt.getText().toString().replace(" ", "%20")
                        + "&types=geocode&language=fr&key="
                        + Constants.API_KEY;

                JsonObjectRequest jsobject = new JsonObjectRequest(
                        Request.Method.GET, lien,(String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("google", lien);
                                Log.d("google", response.toString());
                                try {
                                    //adrTxt.setText(response.getString("adress"));
                                    JSONArray Places;
                                    Places = response
                                            .getJSONArray("predictions");
                                    ArrayList<String> mylist = new ArrayList<String>();
                                    // Parsing json
                                    for (int i = 0; i < Places.length(); i++) {
                                        try {
                                            JSONObject obj = Places
                                                    .getJSONObject(i);
                                            mylist.add(obj
                                                    .getString("description"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    ArrayAdapter<String> aaStr = new ArrayAdapter<String>(
                                            getActivity(),
                                            android.R.layout.simple_list_item_1,
                                            mylist);
                                    adrTxt.setAdapter(aaStr);
                                    adrTxt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                                } catch (JSONException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();

                                    l.setVisibility(View.GONE);
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated
                                // data

                                l.setVisibility(View.GONE);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // log.d(TAG, "Error: " + error.getMessage());


                    }
                });

                // Adding request to request queue
                RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsobject);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

        SpecialiteTxt = (TextView) root.findViewById(R.id.SpecialiteTxt);
        SpecialiteTxt.setTypeface(tf);
        ImageView showMap = (ImageView) root.findViewById(R.id.SearchBtn);
        showMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                l.setVisibility(View.VISIBLE);

            }
        });
        mySpinner = (Spinner) root.findViewById(R.id.sprinnerSpecialiter);

        MyArrayAdapter ma = new MyArrayAdapter(getActivity());
        mySpinner.setAdapter(ma);
        Button inscriptionBtn = (Button) root.findViewById(R.id.inscriptionBtn);
        inscriptionBtn.setTypeface(tl);
        inscriptionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                gps = new GPSTracker(getActivity(). getApplicationContext());

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    // lt = gps.getLatitude();
                    // lg = gps.getLongitude();
                    if (!isNetworkAvailable()) {
                        // Create an Alert Dialog
                        CharSequence text = "Probleme de connexion au serveur";
                        SuperToast superToast = new SuperToast(getActivity());
                        superToast.setDuration(SuperToast.Duration.LONG);
                        superToast.setText(text);
                        superToast.setBackground(R.color.color_error);
                        superToast.show();
                    } else {

                       /* pDialog = new ProgressDialog(getActivity(). getApplicationContext());
                        pDialog.setMessage("Enregistrement en cours...");

                        pDialog.setCancelable(false);
                        pDialog.show();*/
                        lt1.setText("Enregistrement en cours...");
                        lt1.show();
                        try {
                            String LoginUrl = Constants.URL
                                    + "?addArtisan=true&n="
                                    + nom.getText().toString()
                                    .replace(" ", "_")
                                    + "&p="
                                    + prenom.getText().toString().toString()
                                    .replace(" ", "_") + "&t="
                                    + phone.getText().toString() + "&s="
                                    + mySpinner.getSelectedItem().toString()
                                    + "&l=" + lt + "&g=" + lg + "&a="
                                    + adrTxt.getText().toString();
                            Log.d("Inscription", LoginUrl);
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.GET, LoginUrl, (String) null,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(
                                                JSONObject response) {
                                            Log.d("Inscription",
                                                    response.toString());
                                          //  pDialog.hide();
                                            try {
                                                if (response
                                                        .getString(KEY_SUCCESS) != null) {
                                                    String res = response
                                                            .getString(KEY_SUCCESS);
                                                    if (Integer.parseInt(res) == 1) {

                                                        lt1.success();
                                                        uid = response
                                                                .getString("uid");
                                                        // Toast.makeText(getApplicationContext(),"La uid :"+
                                                        // uid +" ",
                                                        // Toast.LENGTH_SHORT).show();
                                                        nom.setText("");
                                                        prenom.setText("");
                                                        phone.setText("");
                                                        mySpinner
                                                                .setSelection(0);
                                                        l.setVisibility(View.GONE);

                                                        new AlertDialog.Builder(
                                                                getActivity())
                                                                .setIcon(
                                                                        R.mipmap.ic_launcher)
                                                                .setTitle(
                                                                        "Inscription réussi")
                                                                .setMessage(
                                                                        "Vous avez bien inscris l'artisan, Voullez vous ajouter des dates de desponibilités  ?")
                                                                .setPositiveButton(
                                                                        "Oui",
                                                                        new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(
                                                                                    DialogInterface dialog,
                                                                                    int which) {
                                                                               Fragment fragment = new AgendaFragment();
                                                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                                                                fragmentTransaction.replace(R.id.container_body, fragment);
                                                                                fragmentTransaction.commit();
                                                                            }

                                                                        })
                                                                .setNegativeButton(
                                                                        "Non",
                                                                        new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(
                                                                                    DialogInterface dialog,
                                                                                    int which) {

                                                                            }

                                                                        })
                                                                .show();

                                                    } else {
                                                        if (response
                                                                .getString(KEY_ERROR_MSG) != null) {
                                                            CharSequence text = response
                                                                    .getString(KEY_ERROR_MSG);
                                                            SuperToast superToast = new SuperToast(getActivity().getApplicationContext());
                                                            superToast.setDuration(SuperToast.Duration.LONG);
                                                            superToast.setText(text);
                                                            superToast.setBackground(R.color.color_error);
                                                            superToast.show();
                                                        } else {
                                                            Context context = getActivity(). getApplicationContext();
                                                            CharSequence text = "Probleme de connexion au serveur";
                                                            SuperToast superToast = new SuperToast(getActivity().getApplicationContext());
                                                            superToast.setDuration(SuperToast.Duration.LONG);
                                                            superToast.setText(text);
                                                            superToast.setBackground(R.color.color_error);
                                                            superToast.show();
                                                        }
                                                    }
                                                } else {
                                                    if (response
                                                            .getString(KEY_ERROR_MSG) != null) {
                                                        lt1.error();

                                                        CharSequence text = response
                                                                .getString(KEY_ERROR_MSG);
                                                        SuperToast superToast = new SuperToast(getActivity().getApplicationContext());
                                                        superToast.setDuration(SuperToast.Duration.LONG);
                                                        superToast.setText(text);
                                                        superToast.setBackground(R.color.color_error);
                                                        superToast.show();
                                                    } else {
                                                        Context context = getActivity(). getApplicationContext();
                                                        CharSequence text = "Probleme de connexion au serveur";

                                                        SuperToast superToast = new SuperToast(getActivity().getApplicationContext());
                                                        superToast.setDuration(SuperToast.Duration.LONG);
                                                        superToast.setText(text);
                                                        superToast.setBackground(R.color.color_error);
                                                        superToast.show();
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                lt1.error();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(
                                        VolleyError error) {
                                    Log.d("Inscription", "Error: "
                                            + error.getMessage());
                                    lt1.error();
                                }
                            }) {

                            };
                            // Adding request to request queue
                            lt1.success();
                            RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjReq);
                        } catch (Exception e) {
                            Log.d("Inscription", "Error: " + e.getMessage());
                            lt1.error();
                        }

                    }
                } else {

                }

            }
        });
        MapsInitializer.initialize(getActivity());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                // Toast.makeText(getActivity(), "SUCCESS",
                // Toast.LENGTH_SHORT).show();
                mapView = (MapView) root.findViewById(R.id.mapAdr);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    map.setMyLocationEnabled(true);
                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng coordinate) {
                            /*pDialog = new ProgressDialog(getActivity());
                            pDialog.setMessage("Recherche d'addresse...");

                            pDialog.setCancelable(false);
                            pDialog.show();*/
                            lt = coordinate.latitude;
                            lg = coordinate.longitude;
                            String lien = Constants.URL + "?getAddress=true&l="
                                    + lt + "&g=" + lg;
						/*
						 * Toast.makeText(InscriptionActivity.this, lt + "phone"
						 * + lg, Toast.LENGTH_LONG).show();
						 */
                            JsonObjectRequest movieReq = new JsonObjectRequest(
                                    Request.Method.GET, lien,(String) null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("google", response.toString());
                                            lt1.success();
                                           l.setVisibility(View.GONE);
                                            try {
                                                adrTxt.setText(response
                                                        .getString("adress"));
                                                // Parsing json

                                            } catch (JSONException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                                lt1.error();
                                                l.setVisibility(View.GONE);
                                            }

                                            // notifying list adapter about data
                                            // changes
                                            // so that it renders the list view with
                                            // updated
                                            // data
                                            lt1.success();
                                            l.setVisibility(View.GONE);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(
                                        VolleyError error) {
                                    // log.d(TAG, "Error: " +
                                    // error.getMessage());
                                    lt1.error();

                                }
                            });

                            // Adding request to request queue
                            RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);

                        }
                    });
                    gps = new GPSTracker(getActivity().getApplicationContext());

                    // check if GPS enabled
                    if (gps.canGetLocation()) {

                        lt = gps.getLatitude();
                        lg = gps.getLongitude();
                        CameraUpdate cameraUpdate = CameraUpdateFactory
                                .newLatLngZoom(new LatLng(lt, lg), 14);
                        map.animateCamera(cameraUpdate);
					/*
					 * Marker hamburg = map.addMarker(new MarkerOptions()
					 * .position(new LatLng(lt, lg)).title( "Vous ete ici"));
					 */


                        lt1.setText("Collection d'information...");
                        lt1.show();
                        String lien = Constants.URL + "?getAddress=true&l=" + lt
                                + "&g=" + lg;
                        JsonObjectRequest movieReq = new JsonObjectRequest(
                                Request.Method.GET, lien,(String) null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("google", response.toString());
                                        lt1.success();
                                        try {
                                            adrTxt.setText(response
                                                    .getString("adress"));
                                            // Parsing json

                                        } catch (JSONException e1) {
                                            // TODO Auto-generated catch block
                                            e1.printStackTrace();
                                            lt1.error();
                                        }

                                        // notifying list adapter about data changes
                                        // so that it renders the list view with
                                        // updated
                                        // data
                                        lt1.success();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // log.d(TAG, "Error: " +
                                // error.getMessage());
                                lt1.success();

                            }
                        });

                        // Adding request to request queue
                        RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);

                    }
				/*
				 * //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG,
				 * 15)); CameraUpdate cameraUpdate =
				 * CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), 15);
				 * map.animateCamera(cameraUpdate); Marker hamburg =
				 * map.addMarker(new MarkerOptions().position(new LatLng(lat,
				 * lang)) .title("ICI"));
				 */
                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(),
                        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()),
                        Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(Context inscriptionTab) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(inscriptionTab);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Specialites.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.my_spinner_style, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.SpinnerItem);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }

            holder.name.setTypeface(tf);
            holder.name.setText("" + Specialites[position]);

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }
    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
