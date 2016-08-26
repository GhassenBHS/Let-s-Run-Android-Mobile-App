package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/11/2016.
 */
public class NotificationModel {

    @SerializedName("username")
    private String username;
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


    public NotificationModel (String username,double LongStartPt,
                                double LatStartPt, double LongFinishPt,double LatFinishPt,
                                long startAt,long finishAt)
    {
        this.username=username;
        this.LongStartPt=LongStartPt ;
        this.LatStartPt=LatStartPt ;
        this.LongFinishPt=LongFinishPt ;
        this.LatFinishPt=LatFinishPt ;
        this.startAt=startAt;
        this.finishAt=finishAt;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(long finishAt) {
        this.finishAt = finishAt;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public double getLatFinishPt() {
        return LatFinishPt;
    }

    public void setLatFinishPt(double latFinishPt) {
        LatFinishPt = latFinishPt;
    }

    public double getLongFinishPt() {
        return LongFinishPt;
    }

    public void setLongFinishPt(double longFinishPt) {
        LongFinishPt = longFinishPt;
    }

    public double getLatStartPt() {
        return LatStartPt;
    }

    public void setLatStartPt(double latStartPt) {
        LatStartPt = latStartPt;
    }

    public double getLongStartPt() {
        return LongStartPt;
    }

    public void setLongStartPt(double longStartPt) {
        LongStartPt = longStartPt;
    }
}
