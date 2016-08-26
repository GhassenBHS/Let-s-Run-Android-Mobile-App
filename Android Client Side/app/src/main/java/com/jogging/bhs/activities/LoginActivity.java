package com.jogging.bhs.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jogging.bhs.R;
import com.jogging.bhs.managers.SharedPrefsMgr;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MY_PREFS_NAME = "UserData";
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private ProfileTracker mProfileTracker;
    Button myFacebookButton;
    SharedPrefsMgr spm;

    //Facebook login button
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile=Profile.getCurrentProfile();

            if(profile == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        // profile2 is the new profile
                        storeUserData(profile2);
                        nextActivity(profile2);
                        mProfileTracker.stopTracking();
                    }
                };
            }
            else {
                storeUserData(profile);
                nextActivity(profile);
            }


        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        myFacebookButton = (Button) findViewById(R.id.myFacebookButton);
        myFacebookButton.setOnClickListener(this);




        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
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

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        storeUserData(profile);
        nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    private void nextActivity(Profile profile) {
        if (profile != null) {
            startActivity(new Intent(LoginActivity.this, NavDrawerActivity.class));
            finish();
        }
    }

    private void storeUserData(Profile profile) {
        if (profile != null) {
            spm=new SharedPrefsMgr();

            spm.setStringValue(LoginActivity.this, "facebook_id", profile.getId());
            spm.setStringValue(LoginActivity.this, "first_name", profile.getFirstName());
            spm.setStringValue(LoginActivity.this, "last_name", profile.getLastName());
            spm.setStringValue(LoginActivity.this,"image_url",profile.getProfilePictureUri(90, 90).toString());
            spm.setBoolValue(LoginActivity.this,"isLoggedIn",true);

        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myFacebookButton:

                LoginButton loginButton = new LoginButton(LoginActivity.this);
                loginButton.setReadPermissions("user_friends");
                loginButton.registerCallback(callbackManager, callback);
                loginButton.performClick();
                break;
        }
    }
}
