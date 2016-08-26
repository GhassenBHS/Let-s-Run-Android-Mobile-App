package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/10/2016.
 */
public class SessionStarterModel {
    @SerializedName("id_session")
    private String IdSession;
    @SerializedName("username")
    private String username;
    @SerializedName("startAt")
    private long startAt;
    @SerializedName("finishAt")
    private long finishAt;

    public String getIdSession() {
        return IdSession;
    }

    public long getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(long finishAt) {
        this.finishAt = finishAt;
    }

    public void setIdSession(String idSession) {
        IdSession = idSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public SessionStarterModel(String IdSession, String username, long startAt,long finishAt)
    {
        this.IdSession=IdSession ;
        this.username=username;
        this.startAt=startAt;
        this.finishAt=finishAt ;
    }

}
