package com.mba2dna.wechkhassek.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.johnpersano.supertoasts.SuperToast;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.util.DatabaseHandler;
import com.mba2dna.wechkhassek.util.UserFunctions;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import net.steamcrafted.loadtoast.LoadToast;

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

   private LoadToast lt ;
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
       /* pDialog = new ProgressDialog(this);
        pDialog.setMessage("Connexion en cours...");
        pDialog.setCancelable(false);*/
        lt = new LoadToast(this);
        lt.setText("Connexion en cours...");
        lt.setTranslationY(200);
        lt.setProgressColor(getResources().getColor(R.color.colorAccent));
        String fontBold = Constants.NexaBold;
        String fontLight = Constants.NexaLight;
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

                    CharSequence text = "Probleme de connexion au serveur";

                    SuperToast superToast = new SuperToast(getApplicationContext());
                    superToast.setDuration(SuperToast.Duration.LONG);
                    superToast.setText(text);
                    superToast.setBackground(R.color.color_error);
                    superToast.show();
                   /* Toast toast = Toast.makeText(context, text, duration);
                    toast.show();*/
                } else {
                    UsernameTxt = (EditText) findViewById(R.id.UsernameField);
                    PasswordTxt = (EditText) findViewById(R.id.PasswordField);
                    //if (isValidEmail(UsernameTxt.getText().toString())) {
                  //  pDialog.show();
                    lt.show();
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
                                        //pDialog.hide();

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
                                                    lt.success();
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
                                                    lt.error();
                                                    if (response
                                                            .getString(KEY_ERROR_MSG) != null) {
                                                        CharSequence text = response
                                                                .getString(KEY_ERROR_MSG);

                                                        SuperToast superToast = new SuperToast(getApplicationContext());
                                                        superToast.setDuration(SuperToast.Duration.LONG);
                                                        superToast.setText(text);
                                                        superToast.setBackground(R.color.color_error);
                                                        superToast.show();
                                                    } else {
                                                        CharSequence text = "Probleme de connexion au serveur";
                                                        SuperToast superToast = new SuperToast(getApplicationContext());
                                                        superToast.setDuration(SuperToast.Duration.LONG);
                                                        superToast.setText(text);
                                                        superToast.setBackground(R.color.color_error);
                                                        superToast.show();
                                                    }
                                                }
                                            } else {
                                                CharSequence text = "Probleme de connexion au serveur";
                                                SuperToast superToast = new SuperToast(getApplicationContext());
                                                superToast.setDuration(SuperToast.Duration.LONG);
                                                superToast.setText(text);
                                                superToast.setBackground(R.color.color_error);
                                                superToast.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG,"Error: "+ error.getMessage());
                                //pDialog.hide();
                                lt.error();
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



    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
