package com.example.zhang.picfun;

/**
 * Created by zhang on 2017/11/22.
 */

public class Picture {

    private String title;
    private String description;
    private int userID;
    private int imageID;

    public Picture(String title, String description, int userID, int imageID) {
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.imageID = imageID;
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

    public int getImageID() {
        return imageID;
    }
}
