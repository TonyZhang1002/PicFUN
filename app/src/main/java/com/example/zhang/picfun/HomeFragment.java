package com.example.zhang.picfun;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static android.content.Context.MODE_PRIVATE;

/*
    Here is the home fragment class
    @author zhang
 */
public class HomeFragment extends ListFragment {

    private String TAG = HomeFragment.class.getName();
    private List<Picture> picList = new ArrayList<Picture>();

    public static boolean refreshFlag = true;

    Context applicationContext = MainActivity.getContextOfApplication();
    SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("myImgNameList", MODE_PRIVATE);
    private int FileSize = 0;

    private void createPicture() {
        picList.clear();
        String nameList;
        FileSize = sharedPreferences.getInt("FileSize",0);
        Bitmap bitmap;
        Picture Image;
        String tmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "picFun";
        for(int i=0; i<FileSize; i++) {
            String title = sharedPreferences.getString("t"+Integer.toString(i),null);
            String des = sharedPreferences.getString("d"+Integer.toString(i),null);
            nameList = sharedPreferences.getString("p"+Integer.toString(i),null);
            bitmap = BitmapFactory.decodeFile(tmpPath +"/" + nameList + ".jpg");
            Image = new Picture(title, des, 123, bitmap);
            picList.add(Image);
        }

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
