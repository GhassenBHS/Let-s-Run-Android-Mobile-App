package com.jogging.bhs.Network.Model;

import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPEED on 8/9/2016.
 */
public class SessionDetailsModel {
    @SerializedName("SessionStarter")
    private String SessionStarter;
    @SerializedName("facebook_id")
    private String FacebookID;
    @SerializedName("id_session")
    private String SessionID;
    @SerializedName("longitude_start_point")
    private double LongStartPt;
    @SerializedName("latitude_start_point")
    private double LatStartPt;
    @SerializedName("longitude_end_point")
    private double LongFinishPt;
    @SerializedName("latitude_end_point")
    private double LatFinishPt;

    @SerializedName("startAt")
    private long startAt;
    @SerializedName("finishAt")
    private long finishAt;





    public SessionDetailsModel (String SessionStarter,String FacebookID,String SessionID,long LongStartPt,
                                long LatStartPt, long LongFinishPt,long LatFinishPt,
                                long startAt,long finishAt)
    {
        this.SessionStarter=SessionStarter;
        this.SessionID=SessionID ;
        this.FacebookID=FacebookID;
        this.LongStartPt=LongStartPt ;

        this.LatStartPt=LatStartPt ;
        this.LongFinishPt=LongFinishPt ;
        this.LatFinishPt=LatFinishPt ;
        this.startAt=startAt;
        this.finishAt=finishAt;


    }

    public String getSessionStarter() {
        return SessionStarter;
    }

    public void setSessionStarter(String sessionStarter) {
        SessionStarter = sessionStarter;
    }

    public String getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(String facebookID) {
        FacebookID = facebookID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public double getLongStartPt() {
        return LongStartPt;
    }

    public void setLongStartPt(long longStartPt) {
        LongStartPt = longStartPt;
    }

    public double getLatStartPt() {
        return LatStartPt;
    }

    public void setLatStartPt(long latStartPt) {
        LatStartPt = latStartPt;
    }

    public double getLongFinishPt() {
        return LongFinishPt;
    }

    public void setLongFinishPt(long longFinishPt) {
        LongFinishPt = longFinishPt;
    }

    public double getLatFinishPt() {
        return LatFinishPt;
    }

    public void setLatFinishPt(long latFinishPt) {
        LatFinishPt = latFinishPt;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(long finishAt) {
        this.finishAt = finishAt;
    }





}
