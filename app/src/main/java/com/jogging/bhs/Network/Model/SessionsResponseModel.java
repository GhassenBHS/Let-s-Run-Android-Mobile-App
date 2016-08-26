package com.jogging.bhs.Network.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionsResponseModel {

    @SerializedName("ids_session_array")
    private List<SessionStarterModel> IdSessionsArray;

    public SessionsResponseModel (List<SessionStarterModel> IdSessionsArray)
    {
        this.IdSessionsArray=IdSessionsArray ;

    }

    public List<SessionStarterModel> getIdSessionsArray() {
        return IdSessionsArray;
    }

    public void setIdSessionsArray(List<SessionStarterModel> idSessionsArray) {
        IdSessionsArray = idSessionsArray;
    }
}
