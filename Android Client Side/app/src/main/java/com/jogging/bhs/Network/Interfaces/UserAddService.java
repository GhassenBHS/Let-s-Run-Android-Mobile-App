package com.jogging.bhs.Network.Interfaces;
import com.jogging.bhs.Network.Model.UserModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SPEED on 8/11/2016.
 */
public interface UserAddService {

    @POST("settings/update")
    Call<Void> postUser(@Body UserModel body);

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
