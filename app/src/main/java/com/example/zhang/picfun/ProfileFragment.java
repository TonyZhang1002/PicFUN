package com.example.zhang.picfun;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends ListFragment {

    private List<Profile> proList = new ArrayList<Profile>();

    public static boolean profileRefreshFlag = true;

    Button clear;
    private View view;
    Context applicationContext = MainActivity.getContextOfApplication();
    SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("myImgNameList", MODE_PRIVATE);

    private void createProfile() {
        Profile defaultProfile = new Profile(R.drawable.default_avater, "Default user");
        proList.add(defaultProfile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_profile, null);
        clear = (Button) view.findViewById(R.id.clear_button);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(profileRefreshFlag) {
            createProfile();
            ProfileAdapter adapter = new ProfileAdapter(getActivity(), R.layout.profile_item, proList);
            setListAdapter(adapter);
            profileRefreshFlag = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImg();
            }
        });
    }

    private void clearImg() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int fileSize = sharedPreferences.getInt("FileSize",0);
        for(int i=0;i<fileSize;i++) {
            editor.putString("p"+Integer.toString(i),null);
            editor.putString("t"+Integer.toString(i),null);
            editor.putString("d"+Integer.toString(i),null);
        }
        editor.putInt("FileSize",0);
        editor.commit();
        Log.i("Tony","All clear");
        Log.i("Tony",Integer.toString(sharedPreferences.getInt("FileSize",0)));
        HomeFragment.refreshFlag = true;
    }
}
