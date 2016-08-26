package com.jogging.bhs.Network.Interfaces;

import com.jogging.bhs.Network.Model.SessionDetailsModel;
import com.jogging.bhs.Network.Model.SessionIdModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SessionDetailsService {

    @POST("get_session_details/{facebook_id}")
    Call<SessionDetailsModel> sessionsDetails(
            @Path("facebook_id") String repo,
            @Body SessionIdModel body);

     static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
