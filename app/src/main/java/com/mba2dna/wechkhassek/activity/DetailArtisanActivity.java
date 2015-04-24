package com.mba2dna.wechkhassek.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailArtisanActivity extends DialogFragment {
	private ListView listview;
	private String tele = "";
	MapView mapView;
    GoogleMap map;
    Double lat,lang;
	public static DetailArtisanActivity newInstance() {
		DetailArtisanActivity f = new DetailArtisanActivity();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getDialog() != null) {
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			getDialog().getWindow().setBackgroundDrawableResource(
					android.R.color.transparent);
		}

		View root = inflater.inflate(R.layout.detail_artisan, container, false);
		String fontBold = Constants.NexaBold;
		String fontLight = Constants.NexaLight;
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				fontBold);
		Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
				fontLight);
		TextView titre = (TextView) root.findViewById(R.id.informationrapide);
		titre.setTypeface(tf);
		TextView nomprenom = (TextView) root.findViewById(R.id.nomprenom);
		nomprenom.setTypeface(tf);
		TextView spe = (TextView) root.findViewById(R.id.spe);
		spe.setTypeface(tl);
		TextView adr = (TextView) root.findViewById(R.id.adr);
		adr.setTypeface(tl);
		TextView caltxt = (TextView) root.findViewById(R.id.caltxt);
		ImageView imagestar = (ImageView) root.findViewById(R.id.star1);
		caltxt.setTypeface(tl);
		Bundle mArgs = getArguments();
		nomprenom.setText( mArgs.getString("nom"));
		spe.setText( mArgs.getString("spe"));
		adr.setText( mArgs.getString("adr"));

        listview = (ListView) root.findViewById(R.id.listDisp);
		caltxt.setText("Nombre d'appels : "+ mArgs.getString("Calls") + " Appelle(s)");
		if(Integer.valueOf(mArgs.getString("Calls"))>5){imagestar.setVisibility(View.VISIBLE);}else{imagestar.setVisibility(View.GONE);}
		tele= mArgs.getString("tele");

		final String lien = Constants.URL
				+ "?ListDsp=true&t="+tele;
        Log.d("google", lien);
		JsonObjectRequest movieReq = new JsonObjectRequest(
				Request.Method.GET, lien, (String)null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("google", lien);
						Log.d("google", response.toString());
						try {
							//adrTxt.setText(response.getString("adress"));
							JSONArray Places;
							Places = response
									.getJSONArray("ListDsp");
							ArrayList<String> mylist = new ArrayList<String>();
							// Parsing json
							for (int i = 0; i < Places.length(); i++) {
								try {
									JSONObject obj = Places
											.getJSONObject(i);
									mylist.add(obj
											.getString("disp_date"));
									Log.v("Displ", obj
											.getString("disp_date"));
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mylist);
							
							listview.setAdapter(adapter);

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		// Adding request to request queue
        RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);
		
		
		
		lat= Double.valueOf(mArgs.getString("lat"));
		lang=Double.valueOf( mArgs.getString("lang"));
		//Toast.makeText(getActivity(), lat + "phone" +lang, Toast.LENGTH_LONG).show();
		Button CallBtn = (Button) root.findViewById(R.id.callBtn);
		CallBtn.setTypeface(tf);
		CallBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isNetworkAvailable()){
				String lien = Constants.URL + "?addCalls=true&t=" + tele;
				Log.d("addCalls",lien);
				JsonObjectRequest movieReq = new JsonObjectRequest(
						Request.Method.GET, lien,(String) null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								
								
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								// log.d(TAG, "Error: " + error.getMessage());
								

							}
						});

				// Adding request to request queue
                    RequesteVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq);
				}
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + tele));
				startActivity(callIntent);
				
			}
		});

	MapsInitializer.initialize(getActivity());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
        {
            case ConnectionResult.SUCCESS:
               // Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                mapView = (MapView) root.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mapView!=null)
                {
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    map.setMyLocationEnabled(true);
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), 15);
                    map.animateCamera(cameraUpdate);
                    Marker hamburg = map.addMarker(new MarkerOptions().position(new LatLng(lat, lang))
                            .title("ICI"));
                }
                break;
            case ConnectionResult.SERVICE_MISSING: 
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: 
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
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
	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {
		super.onStart();

		// change dialog width
		if (getDialog() != null) {

			int fullWidth = getDialog().getWindow().getAttributes().width;

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				fullWidth = size.x;
			} else {
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();
				fullWidth = display.getWidth();
			}

			final int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
							.getDisplayMetrics());

			int w = fullWidth - padding;
			int h = getDialog().getWindow().getAttributes().height;

			getDialog().getWindow().setLayout(w, h);
		}
	}
	// Private class isNetworkAvailable
	private boolean isNetworkAvailable() {
		// Using ConnectivityManager to check for Network Connection
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}




}
