package com.jogging.bhs.Network.Interfaces;


import com.jogging.bhs.Network.Model.SessionsResponseModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SessionsResponseService {
    @GET("get_sessions_list/{facebook_id}")
    Call<SessionsResponseModel> GetsessionsList(
            @Path("facebook_id") String facebook_id);

    @GET("get_user_sessions_list/{facebook_id}")
    Call<SessionsResponseModel> GetUserSessionsList(
            @Path("facebook_id") String facebook_id);

    @GET("get_recent_sessions_list/{facebook_id}")
    Call<SessionsResponseModel> GetRecentSessionsList(
            @Path("facebook_id") String facebook_id);

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}

