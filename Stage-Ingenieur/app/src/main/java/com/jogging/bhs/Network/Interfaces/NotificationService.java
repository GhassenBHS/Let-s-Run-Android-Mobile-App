package com.jogging.bhs.Network.Interfaces;


import com.jogging.bhs.Network.Model.NotificationModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SPEED on 8/11/2016.
 */
public interface NotificationService {

    @POST("notify/{facebook_id}")
    Call<Void> notifyMyFriends (@Path("facebook_id") String facebook_id,
                                @Body NotificationModel body);

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sheltered-forest-63363.herokuapp.com/joggingApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();






}
