package com.example.zhang.picfun;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

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
        ask for write permission if the version is up to 23
        @author zhang
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.CAMERA , Manifest.permission.READ_EXTERNAL_STORAGE};
            //check if get the permission
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //ask for permission
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);

        setDefaultFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(!SendFragment.iniFTPflag) {
            SendFragment.InitFTPServerSetting();
        }
    }

    public static void writeTxtToFile(String s, String tmpPath, String imgMessageFileName) {
        makeFilePath(tmpPath, imgMessageFileName);
        String strFilePath = tmpPath+"/"+imgMessageFileName;
        String strContent = s + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("Tony", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("Tony", "Error on write File:" + e);
        }
    }

    public static List<String> readTxtFromFile(String tmpPath, String imgMessageFileName) {
        List<String> mesgList = new ArrayList<String>();
        makeFilePath(tmpPath, imgMessageFileName);
        String strFilePath = tmpPath+"/"+imgMessageFileName;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("Tony", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(0);
            while(raf.getFilePointer() != file.length()) {
                mesgList.add(raf.readLine());
            }
            raf.close();
            return mesgList;
        } catch (Exception e) {
            Log.e("Tony", "Error on read File:" + e);
            return null;
        }
    }

    public static void clearFile(String filePath, String fileName) {
        try {
            File file = new File(filePath +"/"+ fileName);
            if (file.exists()) {
                file.delete();
                Log.i("Tony","Clear file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        try {
            File tmpDir = new File(filePath);
            if (!tmpDir.exists()){
                tmpDir.mkdirs();
            }
            file = new File(filePath + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
