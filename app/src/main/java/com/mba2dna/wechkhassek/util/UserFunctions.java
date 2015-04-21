package com.mba2dna.depannini.util;

import android.content.Context;

import com.mba2dna.depannini.LoginActivity;
 
public class UserFunctions {
	private static final String TAG = LoginActivity.class.getSimpleName();
   // private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://10.0.2.2/ah_login_api/";
    private static String registerURL = "http://10.0.2.2/ah_login_api/";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
     
    // constructor
    public UserFunctions(){
       // jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    /*public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        final JSONObject json ;//= jsonParser.getJSONFromUrl(loginURL, params);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				loginURL, null,
                new Response.Listener<JSONObject>() {
 
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        json = response;
                      
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                       
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");
 
                return params;
            }
 
        };
 
// Adding request to request queue
//AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }*/
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    /*  public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
         
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }*/
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}