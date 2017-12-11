package com.example.zhang.picfun;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhang on 2017/11/22.
 */

public class PictureAdapter extends ArrayAdapter<Picture> {

    private int resourceID;

    public PictureAdapter(Context context, int textViewResourceId, List<Picture> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        resourceID = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Picture p = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        ImageView pictureImage = (ImageView) view.findViewById(R.id.picture_image);
        TextView pictureTitle = (TextView) view.findViewById(R.id.picture_title);
        TextView pictureDescription = (TextView) view.findViewById(R.id.picture_description);
        TextView pictureTime = (TextView) view.findViewById(R.id.picture_time);

        pictureImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        pictureImage.setImageBitmap(p.getBitmap());
        pictureTitle.setText(p.getTitle());
        pictureDescription.setText(p.getDescription());
        return view;
    }

}
