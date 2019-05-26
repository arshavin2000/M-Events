package tn.app.ihet.m_events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import tn.app.ihet.m_events.service.FacebookService;
import tn.app.ihet.m_events.service.GoogleService;

public class LoginActivity extends AppCompatActivity {


    private CallbackManager callbackManager;
    public GoogleSignInClient mGoogleSignInClient;
    private static final int RC_GOOGLE = 2;

    private Activity activity;
    private SharedPreferences mPrefs;
    public static final String MY_PREFS_NAME = "MyPrefsFile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button facebook = findViewById(R.id.facebook);
        Button google = findViewById(R.id.google);
        activity = this;
        callbackManager = CallbackManager.Factory.create();
        mPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);





        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("user_photos", "email",
                                "public_profile"));


                // Callback registration
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        FacebookTask facebookTask = new FacebookTask();
                        facebookTask.execute();

                    }

                    @Override
                    public void onCancel() {
                        // App code

                        Toast.makeText(getApplicationContext(), "Facebook Cancel", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), "Facebook" + exception.toString(), Toast.LENGTH_LONG).show();


                    }
                });

            }


        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                signIn();




            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleService googleService = new GoogleService(getApplicationContext(),activity);
            googleService.handleSignInResult(task);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("login", "logged");
            mEditor.apply();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE);
    }

    @SuppressLint("StaticFieldLeak")
    public class FacebookTask extends  AsyncTask<Boolean,Boolean,Boolean>
    {


        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("login", "logged");
            mEditor.apply();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finishAffinity();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            FacebookService facebookService = new FacebookService(getApplicationContext());
            facebookService.getFbInfo();
            return true;
        }
    }
}
