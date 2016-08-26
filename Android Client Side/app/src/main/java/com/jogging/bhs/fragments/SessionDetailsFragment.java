package com.jogging.bhs.fragments;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jogging.bhs.Network.Interfaces.SessionDetailsService;
import com.jogging.bhs.Network.Interfaces.SessionJoinedService;
import com.jogging.bhs.Network.Interfaces.SessionJoinerService;
import com.jogging.bhs.Network.Model.JoinerModel;
import com.jogging.bhs.Network.Model.JoinersResponseModel;
import com.jogging.bhs.Network.Model.SessionDeleteJoinerModel;
import com.jogging.bhs.Network.Model.SessionDetailsModel;
import com.jogging.bhs.Network.Model.SessionIdModel;
import com.jogging.bhs.Network.Model.SessionJoinerModel;
import com.jogging.bhs.R;
import com.jogging.bhs.managers.SharedPrefsMgr;
import com.jogging.bhs.managers.ConvertDatesMilliseconds;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SessionDetailsFragment extends Fragment {
    private Button btn_map,btn_join,btn_joined;
    private TextView starter,start_time,finish_time,quit_text,join_text ;
    private AlertDialog dialog ;
    private String fb_id,id_session,first_name,last_name,session_starter,listText="";
    private double longitude_start_point,latitude_start_point,longitude_end_point,latitude_end_point;
    private MaterialStyledDialog materiel_style_dialog;
    private ImageView locationImage,runnersImage,joinImage,quitImage ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_session_details, container, false);




    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btn_join= (Button) view.findViewById(R.id.btn_join);
//        btn_joined= (Button)view.findViewById(R.id.btn_joined);
//        btn_map=(Button)view.findViewById(R.id.btn_map);

        locationImage = (ImageView) view.findViewById(R.id.location_image);
        runnersImage = (ImageView) view.findViewById(R.id.runners);
        joinImage = (ImageView) view.findViewById(R.id.add);
        quitImage = (ImageView) view.findViewById(R.id.minus);

        quit_text= (TextView) view.findViewById(R.id.quit);
        join_text= (TextView) view.findViewById(R.id.join);
        quit_text.setVisibility(View.INVISIBLE);
        join_text.setVisibility(View.INVISIBLE);
        quitImage.setVisibility(View.INVISIBLE);
        joinImage.setVisibility(View.INVISIBLE);

        dialog=new SpotsDialog(getContext(),R.style.Custom);
        SharedPrefsMgr spm = new SharedPrefsMgr();
        materiel_style_dialog=new MaterialStyledDialog(getContext());
        fb_id = spm.getStringValue(getContext(), "facebook_id");
        first_name=spm.getStringValue(getContext(), "first_name");
        last_name=spm.getStringValue(getContext(), "last_name");
        id_session = getArguments().getString("id_session");



        starter = (TextView) view.findViewById(R.id.starter);
        start_time = (TextView) view.findViewById(R.id.start);
        finish_time = (TextView) view.findViewById(R.id.finish);

        dialog.setMessage("Connecting to server,Please wait...");
        dialog.show();

        final SessionIdModel srm= new SessionIdModel(id_session);
        SessionDetailsService session_details_service= SessionDetailsService.retrofit.create(SessionDetailsService.class);
        Call<SessionDetailsModel> call= session_details_service.sessionsDetails(fb_id,srm);

        call.enqueue(new Callback<SessionDetailsModel>() {
            @Override
            public void onResponse(Call<SessionDetailsModel> call, Response<SessionDetailsModel> response) {
                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccessful()) {
                    // print response body if unsuccessful
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Sorry ! Error occurred", Toast.LENGTH_LONG).show();
                    return;
                }
                SessionDetailsModel myModel = response.body();
                longitude_start_point = myModel.getLongStartPt();
                latitude_start_point = myModel.getLatStartPt();
                longitude_end_point = myModel.getLongFinishPt();
                latitude_end_point = myModel.getLatFinishPt();
                session_starter=myModel.getSessionStarter() ;
                starter.setText(session_starter);
                start_time.setText(ConvertDatesMilliseconds.getTime(myModel.getStartAt()));
                finish_time.setText(ConvertDatesMilliseconds.getTime(myModel.getFinishAt()));

                SessionIdModel sim= new SessionIdModel(id_session) ;

                // Call to webservice to get the list of joiners

                SessionJoinedService sessionJoinedService= SessionJoinedService.retrofit.create(SessionJoinedService.class);
                Call<JoinersResponseModel> callJoiners =sessionJoinedService.seeJoiners(fb_id, sim) ;

                callJoiners.enqueue(new Callback<JoinersResponseModel>() {
                    @Override
                    public void onResponse(Call<JoinersResponseModel> call, Response<JoinersResponseModel> response) {
                        if (!response.isSuccessful()) {
                            // print response body if unsuccessful
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            return;
                        }

                        List<JoinerModel> myList = response.body().getJoinersArray();
                        listText = session_starter;
                        for (int i = 0; i < myList.size(); i++) {
                            listText = listText + "\n" + myList.get(i).getUsername();



                        }
                        if (!session_starter.equals(first_name+" "+last_name)) {

                            if (listText.contains(first_name + " " + last_name)) {

                                quitImage.setVisibility(View.VISIBLE);
                                quit_text.setVisibility(View.VISIBLE);
                            } else {
                                joinImage.setVisibility(View.VISIBLE);
                                join_text.setVisibility(View.VISIBLE);
                            }
                        }
                        dialog.dismiss();


                    }

                    @Override
                    public void onFailure(Call<JoinersResponseModel> call, Throwable t) {
                        Log.e("error response joiners", String.valueOf(t));
                        dialog.dismiss();

                    }
                });







            }

            @Override
            public void onFailure(Call<SessionDetailsModel> call, Throwable t) {
                Log.e("Erreur", String.valueOf(t));
                dialog.dismiss();


            }
        });


        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapSessionDetailsFragment.class);
                intent.putExtra("latStart", latitude_start_point);
                intent.putExtra("longStart", longitude_start_point);
                intent.putExtra("latEnd", latitude_end_point);
                intent.putExtra("longEnd", longitude_end_point);
                getActivity().startActivity(intent);



            }
        });
        runnersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setMessage("Connecting to server,Please wait...");
                dialog.show();

                // Call to webservice to get the list of joiners


                SessionIdModel sim= new SessionIdModel(id_session) ;
                SessionJoinedService sessionJoinedService= SessionJoinedService.retrofit.create(SessionJoinedService.class);
                Call<JoinersResponseModel> callJoiners =sessionJoinedService.seeJoiners(fb_id, sim) ;

                callJoiners.enqueue(new Callback<JoinersResponseModel>() {
                    @Override
                    public void onResponse(Call<JoinersResponseModel> call, Response<JoinersResponseModel> response) {
                        if (!response.isSuccessful()) {
                            // print response body if unsuccessful
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            return;
                        }

                        List<JoinerModel> myList = response.body().getJoinersArray();
                        final ArrayAdapter<String> arrayAdapter
                                = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1);
                        for (int i = 0; i < myList.size(); i++) {

                            arrayAdapter.add(myList.get(i).getUsername());

                             }
                        dialog.dismiss();

                        ListView listView = new ListView(getContext());
                        listView.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        float scale = getResources().getDisplayMetrics().density;
                        int dpAsPixels = (int) (8 * scale + 0.5f);
                        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
                        listView.setDividerHeight(0);
                        listView.setAdapter(arrayAdapter);

                        final me.drakeet.materialdialog.MaterialDialog alert =
                                new me.drakeet.materialdialog.MaterialDialog(getContext())
                                        .setTitle("Friends who joined this session")
                                        .setContentView(listView);

                        alert.setPositiveButton("OK", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                alert.dismiss();
                            }
                        });

                        alert.show();




                    }

                    @Override
                    public void onFailure(Call<JoinersResponseModel> call, Throwable t) {
                        Log.e("error response joiners", String.valueOf(t));
                        dialog.dismiss();

                    }
                });



            }
        });


            joinImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setMessage("Connecting to server,Please wait...");
                    dialog.show();

                    SessionJoinerModel sjm = new SessionJoinerModel(first_name + " " + last_name, id_session);
                    SessionJoinerService sessionJoinerService = SessionJoinerService.retrofit.create(SessionJoinerService.class);
                    Call<Void> call = sessionJoinerService.postJoiner(fb_id, sjm);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            if (!response.isSuccessful()) {
                                // print response body if unsuccessful
                                try {
                                    System.out.println(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Snackbar.make(view, "Error Occurred !", Snackbar.LENGTH_LONG).show();
                                return;
                            }

                            if (!session_starter.equals(first_name + " " + last_name)) {
                                quitImage.setVisibility(View.VISIBLE);
                                quit_text.setVisibility(View.VISIBLE);
                                joinImage.setVisibility(View.INVISIBLE);
                                join_text.setVisibility(View.INVISIBLE);
                            }
                            Snackbar.make(view, "Successfully joined session !", Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            materiel_style_dialog.setTitle("Server Problem !")
                                    .setHeaderColor(R.color.green)
                                    .setIcon(R.drawable.alert)
                                    .setCancelable(false)
                                    .setDescription("There have been a problem connecting to a server")
                                    .withIconAnimation(true)
                                    .withDialogAnimation(true)
                                    .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            dialog.dismiss();


                                        }
                                    })
                                    .show();

                        }
                    });

                }
            });



            quitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.setMessage("Connecting to server,Please wait...");
                    dialog.show();

                    SessionDeleteJoinerModel sdjm = new SessionDeleteJoinerModel(id_session);
                    SessionJoinerService sessionJoinerService = SessionJoinerService.retrofit.create(SessionJoinerService.class);
                    Call<Void> call = sessionJoinerService.deleteJoiner(fb_id, sdjm);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            if (!response.isSuccessful()) {
                                // print response body if unsuccessful
                                try {
                                    System.out.println(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Snackbar.make(view, "Error Occurred !", Snackbar.LENGTH_LONG).show();
                                return;
                            }

                            if (!session_starter.equals(first_name + " " + last_name)) {



                                    quitImage.setVisibility(View.INVISIBLE);
                                    quit_text.setVisibility(View.INVISIBLE);

                                    joinImage.setVisibility(View.VISIBLE);
                                    join_text.setVisibility(View.VISIBLE);

                            }

                            Snackbar.make(view, "Successfully quited session !", Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            materiel_style_dialog.setTitle("Server Problem !")
                                    .setHeaderColor(R.color.green)
                                    .setIcon(R.drawable.alert)
                                    .setCancelable(false)
                                    .setDescription("There have been a problem connecting to a server")
                                    .withIconAnimation(true)
                                    .withDialogAnimation(true)
                                    .setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            dialog.dismiss();


                                        }
                                    })
                                    .show();

                        }
                    });



                }
            });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onResume() {
        super.onResume();

    }





}


