package com.mba2dna.wechkhassek.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.AppController;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.util.DatabaseHandler;
import com.mba2dna.wechkhassek.util.UserFunctions;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button LoginBtn;
    private EditText UsernameTxt, PasswordTxt;
    private ProgressDialog pDialog;
    private TextView SignUpBtn;

    private Toolbar mToolbar;

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "entry_date";

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Connexion en cours...");
        pDialog.setCancelable(false);
        String fontBold = "fonts/NexaBold.ttf";
        String fontLight = "fonts/NexaLight.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontBold);
        Typeface tl = Typeface.createFromAsset(getAssets(), fontLight);

        UsernameTxt = (EditText) findViewById(R.id.UsernameField);
        UsernameTxt.setTypeface(tl);
        PasswordTxt = (EditText) findViewById(R.id.PasswordField);
        PasswordTxt.setTypeface(tl);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        LoginBtn.setTypeface(tf);
        LoginBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                if (!isNetworkAvailable()) {
                    // Create an Alert Dialog
                    Context context = getApplicationContext();
                    CharSequence text = "Probleme de connexion au serveur";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    UsernameTxt = (EditText) findViewById(R.id.UsernameField);
                    PasswordTxt = (EditText) findViewById(R.id.PasswordField);
                    //if (isValidEmail(UsernameTxt.getText().toString())) {
                    pDialog.show();

                    try {
                        String LoginUrl = Constants.URL + "?login=true&u="
                                + UsernameTxt.getText() + "&p="
                                + PasswordTxt.getText();
                        Log.d(TAG, LoginUrl);
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                Request.Method.GET, LoginUrl,(String) null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());
                                        pDialog.hide();
                                        // check for login response
                                        try {
                                            if (response
                                                    .getString(KEY_SUCCESS) != null) {
                                                // loginErrorMsg.setText("");
                                                String res = response
                                                        .getString(KEY_SUCCESS);
                                                if (Integer.parseInt(res) == 1) {
                                                    // user successfully
                                                    // logged
                                                    // in
                                                    // Store user details in
                                                    // SQLite Database
                                                    DatabaseHandler db = new DatabaseHandler(
                                                            getApplicationContext());
                                                    JSONObject json_user = response
                                                            .getJSONObject("user");
                                                    UserFunctions userFunction = new UserFunctions();
                                                    // Clear all previous
                                                    // data
                                                    // in database
                                                    userFunction
                                                            .logoutUser(getApplicationContext());
                                                    db.addUser(
                                                            json_user
                                                                    .getString(KEY_NAME),
                                                            json_user
                                                                    .getString(KEY_EMAIL),
                                                            response.getString(KEY_UID),
                                                            json_user
                                                                    .getString(KEY_CREATED_AT));

                                                    // Launch Dashboard
                                                    // Screen
                                                    Intent dashboard = new Intent(
                                                            getApplicationContext(),
                                                            MainActivity.class);

                                                    // Close all views
                                                    // before
                                                    // launching Dashboard
                                                    dashboard
                                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(dashboard);

                                                    // Close Login Screen
                                                    finish();
                                                } else {
                                                    if (response
                                                            .getString(KEY_ERROR_MSG) != null) {
                                                        Context context = getApplicationContext();
                                                        CharSequence text = response
                                                                .getString(KEY_ERROR_MSG);
                                                        int duration = Toast.LENGTH_LONG;

                                                        Toast toast = Toast
                                                                .makeText(
                                                                        context,
                                                                        text,
                                                                        duration);
                                                        toast.show();
                                                    } else {
                                                        Context context = getApplicationContext();
                                                        CharSequence text = "Probleme de connexion au serveur";
                                                        int duration = Toast.LENGTH_LONG;

                                                        Toast toast = Toast
                                                                .makeText(
                                                                        context,
                                                                        text,
                                                                        duration);
                                                        toast.show();
                                                    }
                                                }
                                            } else {
                                                Context context = getApplicationContext();
                                                CharSequence text = "Probleme de connexion au serveur";
                                                int duration = Toast.LENGTH_LONG;

                                                Toast toast = Toast
                                                        .makeText(context,
                                                                text,
                                                                duration);
                                                toast.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG,"Error: "+ error.getMessage());
                                pDialog.hide();
                            }
                        }) ;
                        // Adding request to request queue
                      /*  RequestQueue mRequestQueue;

// Instantiate the cache
                        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
                        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
                        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
                        mRequestQueue.start();
                        mRequestQueue.add(jsonObjReq);*/
                       // AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                        RequesteVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
                    } catch (Exception e) {
                        Log.d(TAG, "Error: " + e.getMessage());

                    }

					/*}else{
						Context context = getApplicationContext();
						CharSequence text = "Votre email est invalide";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast
								.makeText(context,
										text,
										duration);
						toast.show();
					}*/
                }
            }
        });
        SignUpBtn = (TextView) findViewById(R.id.ou);
        SignUpBtn.setTypeface(tl);
        SignUpBtn = (TextView) findViewById(R.id.Dinaninielabel);
        SignUpBtn.setTypeface(tl);
        SignUpBtn = (TextView) findViewById(R.id.Signup);
        SignUpBtn.setTypeface(tf);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent signup = new Intent(getApplicationContext(),
                        SignupActivity.class);
                startActivity(signup);

            }
        });
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
