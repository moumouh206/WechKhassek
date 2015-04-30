package com.mba2dna.wechkhassek.activity;

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


public class SignupActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button SignupBtn;
    private EditText UserTxt,PassTxt,Pass2Txt,Email;
    private LoadToast lt;
    private TextView lbl,LoginTxt;
    // JSON Response node names
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
        setContentView(R.layout.activity_signup);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        lt = new LoadToast(this);
        lt.setText("Connexion en cours...");
        lt.setTranslationY(200);
        lt.setProgressColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(mToolbar);
        String fontBold = Constants.NexaBold;
        String fontLight = Constants.NexaLight;
        Typeface tf = Typeface.createFromAsset(getAssets(), fontBold);
        Typeface tl = Typeface.createFromAsset(getAssets(), fontLight);
        UserTxt = (EditText) findViewById(R.id.usernamefield);
        PassTxt = (EditText) findViewById(R.id.passTxt1);
        Pass2Txt = (EditText) findViewById(R.id.passTxt2);
        Email = (EditText) findViewById(R.id.EmailTxt);
        lbl = (TextView) findViewById(R.id.Dinaninielabel);

        UserTxt.setTypeface(tl);
        PassTxt.setTypeface(tl);
        Pass2Txt.setTypeface(tl);
        Email.setTypeface(tl);
        lbl.setTypeface(tl);
        LoginTxt = (TextView) findViewById(R.id.Login);
        LoginTxt.setTypeface(tf);
        LoginTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent signup = new Intent(getApplicationContext(),
                        LoginActivity.class);
                signup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signup);

            }
        });
        SignupBtn = (Button) findViewById(R.id.SignUpBtn);
        SignupBtn.setTypeface(tf);
        SignupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isNetworkAvailable()) {
                    // Create an Alert Dialog
                    CharSequence text = "Probleme de connexion au serveur";
                    SuperToast superToast = new SuperToast(getApplicationContext());
                    superToast.setDuration(SuperToast.Duration.LONG);
                    superToast.setText(text);
                    superToast.setBackground(R.color.color_error);
                    superToast.show();
                } else {
                    UserTxt = (EditText) findViewById(R.id.usernamefield);
                    PassTxt = (EditText) findViewById(R.id.passTxt1);
                    Pass2Txt = (EditText) findViewById(R.id.passTxt2);
                    Email = (EditText) findViewById(R.id.EmailTxt);
                    lt.show();
                    try{
                        String LoginUrl =  Constants.URL + "?register=true&u="+UserTxt.getText()+"&p1="+PassTxt.getText()+"&p2="+Pass2Txt.getText()+"&e="+Email.getText();
                        Log.d(TAG, LoginUrl);
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                LoginUrl, (String) null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());
                                        try {
                                            if (response.getString(KEY_SUCCESS) != null) {
                                                String res = response.getString(KEY_SUCCESS);
                                                if(Integer.parseInt(res) == 1){
                                                    lt.success();
                                                    // user successfully Sign up
                                                    // Store user details in SQLite Database
                                                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                                    JSONObject json_user = response.getJSONObject("user");
                                                    UserFunctions userFunction = new UserFunctions();
                                                    // Clear all previous data in database
                                                    userFunction.logoutUser(getApplicationContext());
                                                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), response.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                                                    // Launch Dashboard Screen
                                                    Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);

                                                    // Close all views before launching Dashboard
                                                    dashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(dashboard);

                                                    // Close Login Screen
                                                    finish();

                                                }else{
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
                                            }else{
                                                lt.error();
                                                if (response
                                                        .getString(KEY_ERROR_MSG) != null) {
                                                    CharSequence text = response.getString(KEY_ERROR_MSG);
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            lt.error();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error: " + error.getMessage());
                                lt.error();
                            }
                        }){


                        };
                        // Adding request to request queue
                       // AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                        RequesteVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
                    }catch (Exception e) {
                        Log.d(TAG, "Error: " + e.getMessage());
                        lt.error();
                    }

                }}
        });
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
