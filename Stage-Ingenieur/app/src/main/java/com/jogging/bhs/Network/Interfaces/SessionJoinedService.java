package com.jogging.bhs.Network.Interfaces;

import com.jogging.bhs.Network.Model.SessionIdModel;
import com.jogging.bhs.Network.Model.JoinersResponseModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SPEED on 8/10/2016.
 */
public interface SessionJoinedService {

    @POST("get_joiners_list/{facebook_id}")
    Call<JoinersResponseModel> seeJoiners(
            @Path("facebook_id") String facebook_id,
            @Body SessionIdModel body);

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
