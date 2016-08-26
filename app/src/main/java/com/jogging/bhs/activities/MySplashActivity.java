package com.jogging.bhs.activities;

import android.content.Intent;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jogging.bhs.R;
import com.jogging.bhs.fragments.MapSessionDetailsFragment;
import com.jogging.bhs.managers.CheckNetwork;
import com.jogging.bhs.managers.SharedPrefsMgr;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class MySplashActivity extends AwesomeSplash {
    private boolean isLoggedIn = false;
    CheckNetwork checkNetwork;
    boolean status=false;
    MaterialStyledDialog materiel_style_dialog ;
    private  double latStart,longStart,latEnd,longEnd;
    private boolean launcherIsNotif=false;

    @Override
    public void initSplash(ConfigSplash configSplash) {

            /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.green); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.my_logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques
        // (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Title
        configSplash.setTitleSplash("Make Jogging More Fun");
        configSplash.setTitleTextColor(R.color.black);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(5000);
        configSplash.setAnimTitleTechnique(Techniques.Pulse);
        configSplash.setTitleFont("fonts/bruch.ttf"); //provide string to your font located in assets/fonts/

        SharedPrefsMgr spm = new SharedPrefsMgr();
        isLoggedIn = spm.getBoolValue(MySplashActivity.this, "isLoggedIn");

//        Set data to receive notification
        if (getIntent().getExtras() != null) {


            launcherIsNotif=true;



        }
        spm.setBoolValue(getApplicationContext(), "launcherIsNotif",launcherIsNotif);




    }

    @Override
    public void animationsFinished() {
        checkNetwork=new CheckNetwork();
        status = checkNetwork.isConnected(this);
        if (!status)
        {
            materiel_style_dialog=new MaterialStyledDialog(MySplashActivity.this);
            materiel_style_dialog.setTitle("Internet Connection Problem !")
                    .setHeaderColor(R.color.green)
                    .setIcon(R.drawable.ic_running)
                    .setDescription("There have been a problem connecting to the internet. Please check your internet connection and relaunch the app")
                    .withIconAnimation(true)
                    .withDialogAnimation(true)
                    .setPositive("Got it", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            dialog.dismiss();
                            finish();

                        }
                    })
                    .show();


        } else {
            if (!isLoggedIn) {

                Intent loginIntent = new Intent(MySplashActivity.this, LoginActivity.class);
                MySplashActivity.this.startActivity(loginIntent);
                MySplashActivity.this.finish();
            }



            else{

                Intent mainIntent = new Intent(MySplashActivity.this, NavDrawerActivity.class);
                MySplashActivity.this.startActivity(mainIntent);
                MySplashActivity.this.finish();


            }

        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}


