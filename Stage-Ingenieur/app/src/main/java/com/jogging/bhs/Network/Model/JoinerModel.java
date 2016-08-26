package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/10/2016.
 */
public class JoinerModel {

    @SerializedName("username")
    private String username;

    public JoinerModel (String username)
    {
        this.username=username ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
