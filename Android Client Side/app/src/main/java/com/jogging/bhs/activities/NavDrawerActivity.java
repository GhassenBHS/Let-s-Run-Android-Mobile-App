package com.jogging.bhs.activities;



import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.jogging.bhs.R;
import com.jogging.bhs.fragments.BaseFragment;
import com.jogging.bhs.fragments.GraphChoiceFragment;
import com.jogging.bhs.fragments.ParametersFragment;
import com.jogging.bhs.fragments.StartJoggingFragment;
import com.jogging.bhs.fragments.ComingSessionsFragment;
import com.jogging.bhs.fragments.RecentsSessionsFragment;

import com.jogging.bhs.managers.DownloadImage;
import com.jogging.bhs.managers.SharedPrefsMgr;
import com.ncapdevi.fragnav.FragNavController;


public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.FragmentNavigation {
    private FragNavController mNavController;
    private ImageView iv ;
    private TextView tv;
    private String first_name,last_name,image_url;
    private SharedPrefsMgr spm;
    private boolean launcherIsNotif=false ;
    private View header;
    private final int INDEX_PARAMETERS = FragNavController.TAB1;
    private final int INDEX_STARTJOGGING = FragNavController.TAB2;
    private final int INDEX_RECENTS = FragNavController.TAB3;
    private final int INDEX_GRAPH = FragNavController.TAB4;
    private final int INDEX_JOGGINGSESSIONS = FragNavController.TAB5;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        FacebookSdk.sdkInitialize(getApplicationContext());

        spm=new SharedPrefsMgr();
        if (spm.exists("launcherIsNotif",getApplicationContext()))
        {
            launcherIsNotif= spm.getBoolValue(getApplicationContext(), "launcherIsNotif");


        }
        first_name=spm.getStringValue(NavDrawerActivity.this, "first_name");
        last_name=spm.getStringValue(NavDrawerActivity.this, "last_name");
        image_url=spm.getStringValue(NavDrawerActivity.this,"image_url");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        List<Fragment> fragments = new ArrayList<>(5);
        fragments.add(ParametersFragment.newInstance(0));
        fragments.add(StartJoggingFragment.newInstance(0));
        fragments.add(RecentsSessionsFragment.newInstance(0));
        fragments.add(GraphChoiceFragment.newInstance(0));
        fragments.add(ComingSessionsFragment.newInstance(0));




        mNavController = new FragNavController(getSupportFragmentManager(),R.id.container,fragments);

        if (launcherIsNotif)
        {
            mNavController.switchTab(INDEX_JOGGINGSESSIONS);

        }
        else {
            mNavController.switchTab(INDEX_PARAMETERS);
        }
        header=navigationView.getHeaderView(0);
        tv= (TextView) header.findViewById(R.id.username);
        tv.setText("Hello "+first_name);
        iv= (ImageView) header.findViewById(R.id.profile_pic);
        new DownloadImage(iv, NavDrawerActivity.this).execute(image_url);








    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (mNavController.getCurrentStack().size() > 1) {
            mNavController.pop();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mNavController.onSaveInstanceState(outState);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bb_menu_recents:
                mNavController.switchTab(INDEX_RECENTS);
                break;
            case R.id.menu_graph:
                mNavController.switchTab(INDEX_GRAPH);
                break;
            case R.id.coming_sessions:
                mNavController.switchTab(INDEX_JOGGINGSESSIONS);
                break;
            case R.id.start_jogging:
                mNavController.switchTab(INDEX_STARTJOGGING);
                break;
            case R.id.menu_settings:
                mNavController.switchTab(INDEX_PARAMETERS);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.push(fragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }


    public void logout(){
        LoginManager.getInstance().logOut();
        spm.setBoolValue(NavDrawerActivity.this,"isLoggedIn",false);

        Intent login = new Intent(NavDrawerActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}

