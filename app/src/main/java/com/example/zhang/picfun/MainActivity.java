package com.example.zhang.picfun;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/*
    Here is the main activity class
    @author zhang
 */

public class MainActivity extends AppCompatActivity {

    private HomeFragment mHome;
    private SendFragment mSend;
    private ProfileFragment mProfile;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    /*
    We set the default fragment to the HomeFragment
    @author zhang
    */
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHome = new HomeFragment();
        transaction.replace(R.id.content_fragment, mHome);
        transaction.commit();
    }

    /*
    Change the fragment when user click another navigation button
    @author zhang
    */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mHome == null) {
                        mHome = new HomeFragment();
                    }
                    transaction.replace(R.id.content_fragment, mHome);
                    transaction.commit();
                    return true;
                case R.id.navigation_send:
                    if (mSend == null) {
                        mSend = new SendFragment();
                    }
                    transaction.replace(R.id.content_fragment, mSend);
                    transaction.commit();
                    return true;
                case R.id.navigation_profile:
                    if (mProfile == null) {
                        mProfile = new ProfileFragment();
                    }
                    transaction.replace(R.id.content_fragment, mProfile);
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    /*
    In OnCreate method, set the default fragment and set the listener
    @author zhang
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        ask for permission if the version is up to 23
        @author zhang
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //check if get the permission
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //ask for permission
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);

        setDefaultFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
