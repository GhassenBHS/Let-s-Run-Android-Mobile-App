package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/9/2016.
 */

public class SessionIdModel {
    @SerializedName("sessionId")
    private String sessionId;


    public SessionIdModel(String sessionId)
    {
        this.sessionId=sessionId;

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
