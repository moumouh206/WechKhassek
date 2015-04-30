package com.mba2dna.wechkhassek.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.util.Patterns;
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
import com.mba2dna.wechkhassek.service.GooglePlusLoginUtils;
import com.mba2dna.wechkhassek.util.DatabaseHandler;
import com.mba2dna.wechkhassek.util.UserFunctions;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class LoginActivity extends ActionBarActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button LoginBtn;
    private EditText UsernameTxt, PasswordTxt;
    private TextView SignUpBtn;

    private Toolbar mToolbar;

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "entry_date";

    private LoadToast lt;


    private GooglePlusLoginUtils gLogin;
    /* Request code used to invoke sign in user interactions. */
   // private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
   // private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
   // private boolean mIntentInProgress;
    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */
   // private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    // Tag used to cancel the request
    //String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
       /* gLogin = new GooglePlusLoginUtils(this, R.id.btn_sign_in);
        gLogin.setLoginStatus(this);*/

       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        SignInButton gplusBtn = (SignInButton)findViewById(R.id.sign_in_button);
                gplusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
                        mSignInClicked = true;
                        mGoogleApiClient.connect();
                    }

            }
        });*/
       /* loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setReadPermissions("user_friends");
        CallbackManager callbackManager =
        // If using in a fragment
       // loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/
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
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                UsernameTxt.setText(possibleEmail);
            }
        }
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
                                Request.Method.GET, LoginUrl, (String) null,
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
                                Log.d(TAG, "Error: " + error.getMessage());
                                //pDialog.hide();
                                lt.error();
                            }
                        });

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

   /* @Override
    protected void onStart() {
        super.onStart();
        gLogin.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        gLogin.disconnect();
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        gLogin.onActivityResult(requestCode, responseCode, intent);

    }

    @Override
    public void OnSuccessGPlusLogin(Bundle profile) {
        Log.i(TAG,profile.getString(GooglePlusLoginUtils.NAME));
        Log.i(TAG,profile.getString(GooglePlusLoginUtils.EMAIL));
        Log.i(TAG,profile.getString(GooglePlusLoginUtils.PHOTO));
        Log.i(TAG,profile.getString(GooglePlusLoginUtils.PROFILE));
    }*/
/*
    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
            if (mSignInClicked && result.hasResolution()) {
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    mGoogleApiClient.connect();
                }
            }
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }*/
  /*  protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
    }*/
}
