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
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.RequesteVolley;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.util.DatabaseHandler;
import com.mba2dna.wechkhassek.util.UserFunctions;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;


public class SignupActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button SignupBtn;
    private EditText UserTxt,PassTxt,Pass2Txt,Email;
    private ProgressDialog pDialog;
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
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Connexion en cours...");
        pDialog.setCancelable(false);
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
                    Context context = getApplicationContext();
                    CharSequence text = "Probleme de connexion au serveur";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    UserTxt = (EditText) findViewById(R.id.usernamefield);
                    PassTxt = (EditText) findViewById(R.id.passTxt1);
                    Pass2Txt = (EditText) findViewById(R.id.passTxt2);
                    Email = (EditText) findViewById(R.id.EmailTxt);
                    pDialog.show();
                    try{
                        String LoginUrl =  Constants.URL + "?register=true&u="+UserTxt.getText()+"&p1="+PassTxt.getText()+"&p2="+Pass2Txt.getText()+"&e="+Email.getText();
                        Log.d(TAG, LoginUrl);
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                LoginUrl, (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());
                                        pDialog.hide();
                                        try {
                                            if (response.getString(KEY_SUCCESS) != null) {
                                                String res = response.getString(KEY_SUCCESS);
                                                if(Integer.parseInt(res) == 1){
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
                                            }else{
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error: " + error.getMessage());
                                pDialog.hide();
                            }
                        }){


                        };
                        // Adding request to request queue
                       // AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                        RequesteVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
                    }catch (Exception e) {
                        Log.d(TAG, "Error: " + e.getMessage());

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
