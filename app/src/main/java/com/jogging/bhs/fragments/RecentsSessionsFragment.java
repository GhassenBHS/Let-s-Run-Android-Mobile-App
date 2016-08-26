package com.jogging.bhs.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jogging.bhs.Network.Interfaces.SessionsResponseService;
import com.jogging.bhs.Network.Model.SessionStarterModel;
import com.jogging.bhs.Network.Model.SessionsResponseModel;
import com.jogging.bhs.R;
import com.jogging.bhs.RecyclerViews.DividerItemDecoration;
import com.jogging.bhs.RecyclerViews.SessionRecentAdapter;
import com.jogging.bhs.managers.SharedPrefsMgr;
import com.jogging.bhs.managers.ConvertDatesMilliseconds;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecentsSessionsFragment extends BaseFragment {

    private AlertDialog dialog;
    private String fb_id="";
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    public static RecentsSessionsFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        RecentsSessionsFragment fragment = new RecentsSessionsFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recent_session, container, false);


    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        dialog=new SpotsDialog(getContext(),R.style.Custom);
        dialog.setMessage("Connecting to server...");
        dialog.show();
        SharedPrefsMgr spm = new SharedPrefsMgr();
        fb_id = spm.getStringValue(getContext(), "facebook_id");
        final ArrayList<String> listAdder = new ArrayList<>();
        final ArrayList<String> listTimeStart = new ArrayList<>();
        final ArrayList<String> listIdSessions = new ArrayList<>();



        SessionsResponseService sessionResponseService= SessionsResponseService.retrofit.create(SessionsResponseService.class);
        Call<SessionsResponseModel> call=sessionResponseService.GetRecentSessionsList(fb_id);
        call.enqueue(new Callback<SessionsResponseModel>() {
            @Override
            public void onResponse(Call<SessionsResponseModel> call, Response<SessionsResponseModel> response) {
                System.out.println("Response status code: " + response.code());

                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(view, "Error occurred", Snackbar.LENGTH_LONG).show();

                    dialog.dismiss();
                    return;
                }
                Snackbar.make(view, "Successfully retrieved recent sessions list", Snackbar.LENGTH_LONG).show();

                List<SessionStarterModel> myList = response.body().getIdSessionsArray();
                for (int i = 0; i < myList.size(); i++) {
                    listAdder.add(myList.get(i).getUsername());
                    listTimeStart.add(ConvertDatesMilliseconds.getdate(myList.get(i).getStartAt(), "dd/MM/yyyy  hh:mm aa"));
                    listIdSessions.add(myList.get(i).getIdSession());

                }

                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                final SessionRecentAdapter adapter = new SessionRecentAdapter(getContext(), listAdder.size(),listAdder,listTimeStart);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

                recyclerView.setAdapter(adapter);

                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<SessionsResponseModel> call, Throwable t) {
                dialog.dismiss();

            }


        });

    }
}
