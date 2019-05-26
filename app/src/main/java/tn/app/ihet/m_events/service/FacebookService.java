package tn.app.ihet.m_events.service;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import tn.app.ihet.m_events.db.UserManager;
import tn.app.ihet.m_events.model.User;


public class FacebookService {

    private Context context;
    private String LOG_TAG = "FB";


    public FacebookService(Context context) {

        this.context = context;

    }


    public void getFbInfo() {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {
                            Log.d(LOG_TAG, "fb json object: " + object);
                            Log.d(LOG_TAG, "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
//                            String gender = object.getString("gender");
                            // String birthday = object.getString("birthday");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=large";


                            String email = null;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }

                            // add user to sqlite

                            User user = new User();
                            user.setUrlImage(image_url);
                            user.setName(first_name + " " + last_name);
                            //  d.setGender(gender);
                            user.setUsername(first_name + "_" + last_name);
                            if (email != null)
                                user.setEmail(email);
                            else
                                user.setEmail("");

                            UserManager userManager = new UserManager(context);
                            userManager.addUser(user);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAndWait();
    }
}

