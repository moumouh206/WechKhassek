package com.mba2dna.wechkhassek.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.adapter.CustomListAdapter;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.Artisan;
import com.mba2dna.wechkhassek.service.GPSTracker;
import com.mba2dna.wechkhassek.util.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechercheArtisantFragment extends Fragment {
    String ArtisanUrl = Constants.URL + "?searchArtisan=true&n=";
    TextView saerchTxt;
    private Typeface myFont;
    // Log tag
    private static final String TAG = "Message";

    // Movies json url

    private ProgressDialog pDialog;
    private List<Artisan> ArtisanList = new ArrayList<Artisan>();
    private ListView listView;
    private CustomListAdapter adapter;
    private String[] Specialites;
    private Spinner mySpinner;
    GPSTracker gps;

    public RechercheArtisantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root =inflater.inflate(R.layout.fragment_recherche_artisant, container, false);
        String fontLight = "fonts/NexaLight.ttf";

        Specialites = getResources().getStringArray(R.array.specialiter_array);
        DatabaseHandler db = new DatabaseHandler(
                getActivity().getApplicationContext());
        ArtisanList.clear();
        ArtisanList.addAll(db.getAllArtisans());
        myFont = Typeface.createFromAsset(getActivity().getAssets(), fontLight);
        Button rechercBtn = (Button) Root.findViewById(R.id.recherchBtn1);
        rechercBtn.setTypeface(myFont);
        rechercBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ArtisanList.clear();
                pDialog = new ProgressDialog(getActivity());
                // Showing progress dialog before making http request
                pDialog.setMessage("Chargement...");
                pDialog.show();
                gps = new GPSTracker(getActivity().getApplicationContext());
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    ArtisanUrl = Constants.URL + "?searchArtisan=true&l="
                            + latitude + "&g=" + longitude + "&s=" + mySpinner.getSelectedItemPosition();
                } else {
                    ArtisanUrl = Constants.URL + "?searchArtisan=true&s="
                            + mySpinner.getSelectedItemPosition();
                }

                Log.d(TAG, ArtisanUrl);
                JsonObjectRequest movieReq = new JsonObjectRequest(
                        Request.Method.GET, ArtisanUrl,(String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                hidePDialog();
                                JSONArray Annonces;
                                try {
                                    Annonces = response
                                            .getJSONArray("Artisans");
                                    // Parsing json
                                    for (int i = 0; i < Annonces.length(); i++) {
                                        try {
                                            JSONObject obj = Annonces
                                                    .getJSONObject(i);
                                            Artisan artisan = new Artisan();
                                            artisan.setName(obj
                                                    .getString("Nom"));
                                            artisan.setThumbnailUrl(obj
                                                    .getString("image"));
                                            artisan.setTele(obj
                                                    .getString("tele"));
                                            artisan.setspecialite(Specialites[obj
                                                    .getInt("specialite")]);
                                            artisan.setAdress(obj
                                                    .getString("address"));
                                            artisan.setLat(obj
                                                    .getString("lat"));
                                            artisan.setLang(obj
                                                    .getString("lang"));
                                            artisan.setCalls(obj
                                                    .getString("calls"));
                                            ArtisanList.add(artisan);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (JSONException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                    hidePDialog();
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated
                                // data
                                adapter.notifyDataSetChanged();
                                hidePDialog();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // log.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();

                    }
                });

                // Adding request to request queue
               // AppController.getInstance().addToRequestQueue(movieReq);
                RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);

            }
        });
        mySpinner = (Spinner) Root.findViewById(R.id.sprinnerSpecialiterFilter);
        MyArrayAdapter ma = new MyArrayAdapter(getActivity().getApplicationContext());
        mySpinner.setAdapter(ma);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        listView = (ListView) Root.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), ArtisanList);
        listView.setAdapter(adapter);
        //listView.setEmptyView(Root.findViewById(R.id.emptyElement));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                TextView tele = (TextView) arg1.findViewById(R.id.releaseYear);
                TextView nom = (TextView) arg1.findViewById(R.id.title);
                TextView spe = (TextView) arg1.findViewById(R.id.rating);
                TextView adr = (TextView) arg1.findViewById(R.id.genre);
                TextView lat = (TextView) arg1.findViewById(R.id.lat);
                TextView lang = (TextView) arg1.findViewById(R.id.lang);
                TextView Calltxt = (TextView) arg1.findViewById(R.id.Calltxt);
                DatabaseHandler db = new DatabaseHandler(
                        getActivity().getApplicationContext());
                db.deleteTitle("'"+nom.getText().toString()+"'");
                db.addArtisan(
                        nom.getText().toString(),
                        spe.getText().toString(),
                        adr.getText().toString(),
                        tele.getText().toString(),
                        lat.getText().toString(),
                        lang.getText().toString(),
                        Calltxt.getText().toString());
                // Toast.makeText(ArtisanListTab.this, lat.getText().toString()+" SUCCESS "+lang.getText().toString(), Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();
                args.putString("nom", nom.getText().toString());
                args.putString("spe", spe.getText().toString());
                args.putString("adr", adr.getText().toString());
                args.putString("tele", tele.getText().toString());
                args.putString("lat", lat.getText().toString());
                args.putString("lang", lang.getText().toString());
                args.putString("Calls", Calltxt.getText().toString());

                DetailArtisanActivity dialo = new DetailArtisanActivity();
                dialo.setArguments(args);
                dialo.show(getActivity().getSupportFragmentManager(), "DetailArtisanActivity");
            }

        });
        return Root;
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(Context inscriptionTab) {
            mInflater = LayoutInflater.from(inscriptionTab);
        }

        @Override
        public int getCount() {
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

            holder.name.setTypeface(myFont);
            holder.name.setText("" + Specialites[position]);

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }
}
