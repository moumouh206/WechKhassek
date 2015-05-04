package com.mba2dna.wechkhassek.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.mba2dna.wechkhassek.util.AndroidMultiPartEntity;
import com.mba2dna.wechkhassek.util.DatabaseHandler;
import com.mba2dna.wechkhassek.util.UserFunctions;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import net.steamcrafted.loadtoast.LoadToast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignupActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    long totalSize = 0;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button SignupBtn;
    private EditText UserTxt,PassTxt,Pass2Txt,Email;
    private LoadToast lt;
    private TextView lbl,LoginTxt;
    private CircleImageView img;
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "entry_date";
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

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
        img=(CircleImageView) findViewById(R.id.dirlogo);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking camera availability
                if (!isDeviceSupportCamera()) {
                    CharSequence text = "Cette fonction n'est pas disponible sur votre telephone";
                    SuperToast superToast = new SuperToast(getApplicationContext());
                    superToast.setDuration(SuperToast.Duration.LONG);
                    superToast.setText(text);
                    superToast.setBackground(R.color.color_error);
                    superToast.show();
                }else{
                    captureImage();
                }

            }
        });
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                //launchUploadActivity(true);
                img.setImageURI(fileUri);
                new UploadFileToServer().execute();


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
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
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    private void launchUploadActivity(boolean isImage){
        /*Intent i = new Intent(SignupActivity.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);*/
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Constants.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
          //  progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
           // progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
          //  progressBar.setProgress(progress[0]);

            // updating percentage value
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(fileUri.toString());
                Log.d(TAG, "file : "
                        + fileUri.toString());
              //  entity.setRequestProperty("Content-Type", "multipart/form-data; boundary=" );
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("website",new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
