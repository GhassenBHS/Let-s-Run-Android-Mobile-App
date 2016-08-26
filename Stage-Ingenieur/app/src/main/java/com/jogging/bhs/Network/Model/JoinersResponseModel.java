package com.jogging.bhs.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SPEED on 8/10/2016.
 */
public class JoinersResponseModel {

    @SerializedName("joiners_array")
    private List<JoinerModel> JoinersArray;

    public List<JoinerModel> getJoinersArray() {
        return JoinersArray;
    }

    public void setJoinersArray(List<JoinerModel> joinersArray) {
        JoinersArray = joinersArray;
    }

    public JoinersResponseModel (List<JoinerModel> JoinersArray)
    {

        this.JoinersArray=JoinersArray ;

    }

}
