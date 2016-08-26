package com.jogging.bhs.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jogging.bhs.Network.Interfaces.NotificationService;
import com.jogging.bhs.Network.Model.NotificationModel;
import com.jogging.bhs.R;
import com.jogging.bhs.WearableListView.ListViewAdapter;
import com.jogging.bhs.WearableListView.ListViewItem;
import com.jogging.bhs.managers.SharedPrefsMgr;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StartJoggingFragment extends BaseFragment implements
        WearableListView.ClickListener,WearableListView.OnCenterProximityListener{
    private List<ListViewItem> viewItemList;
    private WearableListView wearableListView;
    boolean make_scroll_time=false;
    private SharedPrefsMgr spm;
    private int dmonthOfYear=0,dyear=0,ddayOfMonth=0,mselectedHour=0,mselectedMinute=0;
    private long startTime=0,finishTime=0;
    private AlertDialog dialog ;
    private MaterialStyledDialog materiel_style_dialog ;



    public static StartJoggingFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        StartJoggingFragment fragment = new StartJoggingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_list_activity, container, false);

        wearableListView = (WearableListView) view.findViewById(R.id.wearable_list_view);
        viewItemList = new ArrayList<>();



        viewItemList.add(new ListViewItem(R.drawable.ic_running, "Where are you running ?"));
        viewItemList.add(new ListViewItem(R.drawable.my_watch, "Date of jogging session ?"));
        viewItemList.add(new ListViewItem(R.drawable.my_watch, "Time of jogging session ?"));
        viewItemList.add(new ListViewItem(R.drawable.ic_action_accept, "Notify my friends"));
        viewItemList.add(new ListViewItem(R.drawable.ic_running, "Need Help ?"));

        wearableListView.setAdapter(new ListViewAdapter(getContext(), viewItemList));
        wearableListView.setClickListener(this);
        spm=new SharedPrefsMgr();
        dialog=new SpotsDialog(getContext(),R.style.Custom);
        materiel_style_dialog=new MaterialStyledDialog(getContext());


        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        make_scroll_time=spm.getBoolValue(getContext(),"make_scroll_time");

        if (make_scroll_time) {
            wearableListView.smoothScrollToPosition(1);
        }
        else {
            wearableListView.smoothScrollToPosition(0);

        }

    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        if (spm.exists("radious",getContext()) && spm.exists("rythm",getContext()) &&
                spm.exists("longitude_interest_center",getContext()) && spm.exists("latitude_interest_center",getContext()))

        {

            if (viewItemList.get(viewHolder.getLayoutPosition()).text.equals("Where are you running ?")) {
                Intent intent = new Intent(getActivity(), MapFragmnt.class);
                intent.putExtra("launcher", "StartJoggingFragment");
                getActivity().startActivity(intent);


                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();


            } else if (viewItemList.get(viewHolder.getLayoutPosition()).text.equals("Date of jogging session ?")) {


                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        wearableListView.smoothScrollToPosition(2);
                        dyear = year;
                        ddayOfMonth = dayOfMonth;
                        dmonthOfYear = monthOfYear;


                    }
                }, mYear, mMonth, mDay);

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();


            } else if (viewItemList.get(viewHolder.getLayoutPosition()).text.equals("Time of jogging session ?")) {


                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wearableListView.smoothScrollToPosition(3);
                        mselectedHour = selectedHour;
                        mselectedMinute = selectedMinute;

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(dyear, dmonthOfYear, ddayOfMonth,
                                mselectedHour, mselectedMinute, 0);
                        startTime = calendar.getTimeInMillis();
                        Log.e("wa9t", String.valueOf(startTime));


                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            } else if (viewItemList.get(viewHolder.getLayoutPosition()).text.equals("Notify my friends")) {

                // check if a data exists in prefs
                Calendar myCal = Calendar.getInstance();

                if (spm.exists("longitude_start_point", getContext()) && spm.exists("latitude_start_point", getContext()) &&
                        spm.exists("longitude_end_point", getContext()) && spm.exists("latitude_end_point", getContext())
                        && startTime!=0 &&   spm.exists("running_time", getContext()) && ddayOfMonth>=myCal.get(Calendar.DAY_OF_MONTH)
                        && dmonthOfYear>=myCal.get(Calendar.MONTH) && dyear>=myCal.get(Calendar.YEAR))

                {


                    dialog.setMessage("Posting data to server...");
                    dialog.show();
                    // Here make FCM call by sending a post request containing circuit details
                    String fb_id = spm.getStringValue(getContext(), "facebook_id");
                    double longitude_start_point = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "longitude_start_point"));
                    double latitude_start_point = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "latitude_start_point"));
                    double longitude_end_point = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "longitude_end_point"));
                    double latitude_end_point = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "latitude_end_point"));
                    String Firstname = spm.getStringValue(getContext(), "first_name");
                    String Lastname = spm.getStringValue(getContext(), "last_name");
                    double running_time = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "running_time"));
                    long in_millis = Math.round(TimeUnit.MINUTES.toMillis(Math.round(running_time)));
                    finishTime = startTime + in_millis;


                    NotificationModel notificationModel = new NotificationModel(Firstname + " " + Lastname, longitude_start_point,
                            latitude_start_point, longitude_end_point, latitude_end_point, startTime, finishTime);

                    // Connect to server to notify users

                    NotificationService notificationService = NotificationService.retrofit.create(NotificationService.class);
                    Call<Void> call = notificationService.notifyMyFriends(fb_id, notificationModel);
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
                                Snackbar.make(getView(), "Error occurred", Snackbar.LENGTH_SHORT).show();

                                return;
                            }
                            Snackbar.make(getView(), "Successfully Notified your friends !", Snackbar.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });


                }
                else {
                    Snackbar.make( getView(),"Please specify a jogging circuit first or verify your entries", Snackbar.LENGTH_LONG).show();


                }
            }


        }
        else {
            Snackbar.make( getView(),"Please setup your settings preferences  first.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }


    @Override
    public void onCenterPosition(boolean b) {

    }

    @Override
    public void onNonCenterPosition(boolean b) {

}


}
