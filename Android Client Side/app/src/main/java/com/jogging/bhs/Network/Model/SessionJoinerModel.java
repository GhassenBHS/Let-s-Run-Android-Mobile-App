package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

public class SessionJoinerModel {

    @SerializedName("username")
    private String Participater;
    @SerializedName("sessionId")
    private String sessionId;

    public SessionJoinerModel(String Participater, String sessionId)
    {
        this.Participater=Participater ;
        this.sessionId=sessionId ;
    }
}
