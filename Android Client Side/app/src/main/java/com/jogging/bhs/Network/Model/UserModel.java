package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SPEED on 8/11/2016.
 */
public class UserModel {

    @SerializedName("username")
    private String username;
    @SerializedName("facebook_id")
    private String FacebookID;
    @SerializedName("longitude_center")
    private double longitudeCenter;
    @SerializedName("latitude_center")
    private double latitudeCenter;
    @SerializedName("radius")
    private int radius;
    @SerializedName("token")
    private String token;

    public UserModel (String username,String FacebookID, double longitudeCenter, double latitudeCenter,
                      int radius,String token)
    {
        this.username=username ;
        this.FacebookID=FacebookID ;
        this.longitudeCenter=longitudeCenter ;
        this.latitudeCenter=latitudeCenter ;
        this.radius=radius ;
        this.token=token ;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLatitudeCenter() {
        return latitudeCenter;
    }

    public void setLatitudeCenter(double latitudeCenter) {
        this.latitudeCenter = latitudeCenter;
    }

    public double getLongitudeCenter() {
        return longitudeCenter;
    }

    public void setLongitudeCenter(double longitudeCenter) {
        this.longitudeCenter = longitudeCenter;
    }

    public String getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(String facebookID) {
        FacebookID = facebookID;
    }
}
