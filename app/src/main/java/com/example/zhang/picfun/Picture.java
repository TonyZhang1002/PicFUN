package com.example.zhang.picfun;

import android.graphics.Bitmap;

/**
 * Created by zhang on 2017/11/22.
 */

public class Picture {

    private String title;
    private String description;
    private int userID;
    private Bitmap bitmap;

    public Picture(String title, String description, int userID, Bitmap bitmap) {
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getUserID() {
        return userID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
