package com.example.zhang.picfun;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class HomeFragment extends ListFragment {

    private String TAG = HomeFragment.class.getName();
    private List<Picture> picList = new ArrayList<Picture>();

    public static boolean refreshFlag = true;

    private void createPicture() {
        Picture testImage = new Picture("Funny Picture", "test", 123, R.drawable.test);
        picList.add(testImage);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(refreshFlag) {
            createPicture();
            PictureAdapter adapter = new PictureAdapter(getActivity(), R.layout.picture_item, picList);
            setListAdapter(adapter);
            refreshFlag = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
