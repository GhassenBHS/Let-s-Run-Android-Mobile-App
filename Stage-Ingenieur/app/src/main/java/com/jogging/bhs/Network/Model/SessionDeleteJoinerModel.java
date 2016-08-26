package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/17/2016.
 */
public class SessionDeleteJoinerModel {


    @SerializedName("sessionId")
    private String sessionId;

    public SessionDeleteJoinerModel(String sessionId)
    {
        this.sessionId=sessionId ;
    }


}
