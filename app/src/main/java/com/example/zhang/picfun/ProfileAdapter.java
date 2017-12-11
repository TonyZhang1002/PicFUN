package com.example.zhang.picfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhang on 2017/12/8.
 */

public class ProfileAdapter extends ArrayAdapter<Profile> {

    private int resourceID;

    public ProfileAdapter(Context context, int textViewResourceId, List<Profile> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        resourceID = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Profile p = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image);
        TextView profileTitle = (TextView) view.findViewById(R.id.profile_text);

        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        profileImage.setImageResource(p.getProfileImageID());
        profileTitle.setText(p.getUserName());
        return view;
    }
}
