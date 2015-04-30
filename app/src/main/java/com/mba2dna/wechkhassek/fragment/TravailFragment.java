package com.mba2dna.wechkhassek.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravailFragment extends Fragment {

    TextView wl;
    // GPSTracker class
    GPSTracker gps;
    private String[] Specialites;
    private Spinner mySpinner;
    private Typeface myFont;
    String ArtisanUrl = Constants.URL + "?searchArtisan=true&n=";
    TextView saerchTxt;
    // Log tag
    private static final String TAG = "Message";

    // Movies json url
    private LoadToast lt;
    private List<Artisan> ArtisanList = new ArrayList<Artisan>();
    private ListView listView;
    private CustomListAdapter adapter;
    public TravailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_travail, container, false);
        String fontBold = Constants.NexaBold;
        String fontLight = Constants.NexaLight;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontBold);
        Typeface tl = Typeface.createFromAsset(getActivity().getAssets(), fontLight);
        myFont = Typeface.createFromAsset(getActivity().getAssets(), fontLight);
        Specialites = getResources().getStringArray(R.array.specialiter_array);
        TextView Recherch1 = (TextView) root.findViewById(R.id.titre1);
        Recherch1.setTypeface(tf);
        TextView SpecialiteTxt = (TextView) root.findViewById(R.id.SpecialiteTxt1);
        SpecialiteTxt.setTypeface(tf);
        TextView Recherch2 = (TextView) root.findViewById(R.id.explicationtxt);
        Recherch2.setTypeface(tl);
        lt = new LoadToast(getActivity());
        lt.setText("Recherche en cours...");
        lt.setTranslationY(450);
        lt.setProgressColor(getResources().getColor(R.color.colorAccent));
        mySpinner = (Spinner) root.findViewById(R.id.sprinnerCom1);
        MyArrayAdapter ma = new MyArrayAdapter(getActivity());
        mySpinner.setAdapter(ma);

        Button rechercBtn = (Button) root.findViewById(R.id.callBtn);
        rechercBtn.setTypeface(tl);
        rechercBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ArtisanList.clear();
                lt.show();
                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    ArtisanUrl = Constants.URL + "?searchOffre=true&l="
                            + latitude + "&g=" + longitude + "&s="
                            + mySpinner.getSelectedItemPosition();
                } else {
                    ArtisanUrl = Constants.URL + "?searchOffre=true&s="
                            + mySpinner.getSelectedItemPosition();
                }
                Log.d(TAG, ArtisanUrl);
                JsonObjectRequest movieReq = new JsonObjectRequest(
                        Request.Method.GET, ArtisanUrl, (String)null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                lt.success();
                                JSONArray Annonces;
                                try {
                                    Annonces = response.getJSONArray("Offres");
                                    // Parsing json
                                    for (int i = 0; i < Annonces.length(); i++) {
                                        try {
                                            JSONObject obj = Annonces
                                                    .getJSONObject(i);
                                            Artisan artisan = new Artisan();
                                            artisan.setName(obj
                                                    .getString("titre"));
                                            artisan.setThumbnailUrl(obj
                                                    .getString("image"));
                                            artisan.setTele(obj
                                                    .getString("tele"));
                                            artisan.setspecialite(Specialites[obj
                                                    .getInt("specialite")]);
                                            artisan.setAdress(obj
                                                    .getString("address"));

                                            ArtisanList.add(artisan);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            lt.error();
                                        }

                                    }
                                } catch (JSONException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                    lt.error();
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated
                                // data
                                adapter.notifyDataSetChanged();
                                lt.success();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // log.d(TAG, "Error: " + error.getMessage());
                        lt.error();

                    }
                });

                // Adding request to request queue
                RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);

            }
        });
        listView = (ListView) root.findViewById(R.id.listOffre);
        listView.setEmptyView(root.findViewById(R.id.emptyElement));
        adapter = new CustomListAdapter(getActivity(), ArtisanList);
        listView.setAdapter(adapter);
        return root;
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

            holder.name.setTypeface(myFont);
            holder.name.setText("" + Specialites[position]);

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }
}
