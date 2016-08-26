package com.jogging.bhs.Network.Interfaces;

import com.jogging.bhs.Network.Model.SessionDeleteJoinerModel;
import com.jogging.bhs.Network.Model.SessionJoinerModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;




public interface SessionJoinerService {
    @POST("join_session/{facebook_id}")
    Call<Void> postJoiner(
            @Path("facebook_id") String facebook_id,
            @Body SessionJoinerModel body);

    @POST("quit_session/{facebook_id}")
    Call<Void> deleteJoiner(
            @Path("facebook_id") String facebook_id,
            @Body SessionDeleteJoinerModel body);

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
