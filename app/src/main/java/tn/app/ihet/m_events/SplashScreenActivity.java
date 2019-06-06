package tn.app.ihet.m_events;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private SharedPreferences mPrefs;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //  mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("smartinterphone.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        // Use the config
        Realm.getInstance(config);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                if (mPrefs.contains("login")) {
                    String login = mPrefs.getString("login", "");
                    assert login != null;
                    if (!login.equals("")) {

                        startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();

                }


            }
        }, 3000);
    }



}
