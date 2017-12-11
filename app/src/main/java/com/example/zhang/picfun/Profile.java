package com.example.zhang.picfun;

/**
 * Created by zhang on 2017/12/8.
 */

public class Profile {

    private int profileImageID;
    private String userName;

    public Profile(int profileImageID, String userName) {
        this.profileImageID = profileImageID;
        this.userName = userName;
    }

    public int getProfileImageID() {
        return profileImageID;
    }

    public String getUserName() {
        return userName;
    }
}
