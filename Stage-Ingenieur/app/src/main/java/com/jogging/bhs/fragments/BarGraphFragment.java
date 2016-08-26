package com.jogging.bhs.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jogging.bhs.Network.Interfaces.SessionsResponseService;
import com.jogging.bhs.Network.Model.SessionStarterModel;
import com.jogging.bhs.Network.Model.SessionsResponseModel;
import com.jogging.bhs.R;
import com.jogging.bhs.managers.ConvertDatesMilliseconds;
import com.jogging.bhs.managers.GraphDatesManager;
import com.jogging.bhs.managers.SharedPrefsMgr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarGraphFragment extends BaseFragment {


    private AlertDialog dialog;
    private String fb_id="",picked="Last 4 Weeks";
    private long Week_1Start,Week_1Finish,Week_2Start,Week_2Finish,Week_3Start,Week_3Finish,Week_4Start,Week_4Finish,
            Week_5Start,Week_5Finish,Week_6Start,Week_6Finish,Week_7Start,Week_7Finish,Week_8Start,Week_8Finish,
            Week_9Start,Week_9Finish,Week_10Start,Week_10Finish,Week_11Start,Week_11Finish,Week_12Start,Week_12Finish;






    public static BarGraphFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        BarGraphFragment fragment = new BarGraphFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_graph, container, false);


    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new SpotsDialog(getContext(), R.style.Custom);
        dialog.setMessage("Connecting to server...");
        dialog.show();
        if (getArguments().getString("item")!= null)
        {
            picked = getArguments().getString("item");

        }

        final BarChart chart= (BarChart) view.findViewById(R.id.chart);


        SharedPrefsMgr spm = new SharedPrefsMgr();
        fb_id = spm.getStringValue(getContext(), "facebook_id");
        final ArrayList<Long> listTimeStart = new ArrayList<>();
        final ArrayList<Long> listTimeFinish = new ArrayList<>();

        // Making Time intervals:
        Week_1Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber() - 1, 2016));
        Week_1Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-1, 2016));
        Week_2Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-2, 2016));
        Week_2Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-2, 2016));
        Week_3Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-3, 2016));
        Week_3Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-3, 2016));
        Week_4Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-4, 2016));
        Week_4Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-4, 2016));
        Week_5Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-5, 2016));
        Week_5Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-5, 2016));
        Week_6Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-6, 2016));
        Week_6Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-6, 2016));
        Week_7Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-7, 2016));
        Week_7Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-7, 2016));
        Week_8Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-8, 2016));
        Week_8Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-8, 2016));
        Week_9Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-9, 2016));
        Week_9Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-9, 2016));
        Week_10Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-10, 2016));
        Week_10Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-10, 2016));
        Week_11Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-11, 2016));
        Week_11Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-11, 2016));
        Week_12Start= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-12, 2016));
        Week_12Finish= ConvertDatesMilliseconds.ConvertDateToMillis(
                GraphDatesManager.getEndOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-12, 2016));


        SessionsResponseService sessionResponseService = SessionsResponseService.retrofit.create(SessionsResponseService.class);
        Call<SessionsResponseModel> call = sessionResponseService.GetUserSessionsList(fb_id);
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



                Log.e("Okk", String.valueOf(GraphDatesManager.getLastDayOfPreviousMonth()));
                Log.e("Okk", String.valueOf(GraphDatesManager.getFirstDayOfPreviousMonth()));
                Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.ConvertDateToMillis(
                        GraphDatesManager.getStartOFWeek(32, 2016))));
                Log.e("Okk", String.valueOf(GraphDatesManager.GetCurrentYearWeekNumber()));
                Log.e("Okk", String.valueOf(GraphDatesManager.getStartOFWeek(32, 2016)));

                List<SessionStarterModel> myList = response.body().getIdSessionsArray();
                for (int i = 0; i < myList.size(); i++) {
                    listTimeStart.add(myList.get(i).getStartAt());
                    listTimeFinish.add(myList.get(i).getFinishAt());

                }
                if (picked.equals("Last 2 Weeks"))
                {
                    float week_1NumberMinutes=0,week_2NumberMinutes=0 ;

                    for (int sessionIndex=0;sessionIndex<listTimeStart.size();sessionIndex++)
                    {
                        if (listTimeStart.get(sessionIndex)>Week_1Start && listTimeStart.get(sessionIndex)<Week_1Finish )
                        {
                            week_1NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));




                        }
                        else if (listTimeStart.get(sessionIndex)>Week_2Start && listTimeStart.get(sessionIndex)<Week_2Finish )
                        {
                            week_2NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }


                    }
                    dialog.dismiss();


                    BarData data = new BarData(getXAxisValues2weeks(), getDataSet2weeks(week_1NumberMinutes,week_2NumberMinutes));

                    chart.setData(data);
                    chart.setDescription("Bar graph describing user activity");
                    chart.animateXY(2000, 2000);

                    chart.invalidate();



                }
                else if (picked.equals("Last 4 Weeks"))
                {
                    float week_1NumberMinutes=0,week_2NumberMinutes=0,week_3NumberMinutes=0,week_4NumberMinutes=0;
                    for (int sessionIndex=0;sessionIndex<listTimeStart.size();sessionIndex++)
                    {
                        if (listTimeStart.get(sessionIndex)>Week_1Start && listTimeStart.get(sessionIndex)<Week_1Finish )
                        {
                            week_1NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));




                        }
                        else if (listTimeStart.get(sessionIndex)>Week_2Start && listTimeStart.get(sessionIndex)<Week_2Finish )
                        {
                            week_2NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_3Start && listTimeStart.get(sessionIndex)<Week_3Finish )
                        {
                            week_3NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_4Start && listTimeStart.get(sessionIndex)<Week_4Finish )
                        {
                            week_4NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }

                    }
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_1Start, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(Week_1Start)) ;
                    Log.e("Okk", String.valueOf(GraphDatesManager.getStartOFWeek(GraphDatesManager.GetCurrentYearWeekNumber()-1, 2016))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_1Finish, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_2Start, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_2Finish, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_3Start, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_3Finish, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_4Start, "dd/MM/yyyy  hh:mm"))) ;
                    Log.e("Okk", String.valueOf(ConvertDatesMilliseconds.getdate(Week_4Finish, "dd/MM/yyyy  hh:mm"))) ;

                    Log.e("Okk", String.valueOf(week_1NumberMinutes)) ;
                    Log.e("Okk", String.valueOf(week_2NumberMinutes)) ;
                    Log.e("Okk", String.valueOf(week_3NumberMinutes)) ;
                    Log.e("Okk", String.valueOf(week_4NumberMinutes)) ;


                    BarData data = new BarData(getXAxisValues4weeks(), getDataSet4weeks(week_1NumberMinutes,week_2NumberMinutes,week_3NumberMinutes,week_4NumberMinutes));
                    chart.setData(data);
                    chart.setDescription("My Chart");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();

                    dialog.dismiss();





                }
                else if (picked.equals("Last 8 Weeks"))
                {

                    float week_1NumberMinutes=0,week_2NumberMinutes=0,week_3NumberMinutes=0,week_4NumberMinutes=0,
                            week_5NumberMinutes=0,week_6NumberMinutes=0,week_7NumberMinutes=0,week_8NumberMinutes=0;
                    for (int sessionIndex=0;sessionIndex<listTimeStart.size();sessionIndex++)
                    {
                        if (listTimeStart.get(sessionIndex)>Week_1Start && listTimeStart.get(sessionIndex)<Week_1Finish )
                        {
                            week_1NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));




                        }
                        else if (listTimeStart.get(sessionIndex)>Week_2Start && listTimeStart.get(sessionIndex)<Week_2Finish )
                        {
                            week_2NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_3Start && listTimeStart.get(sessionIndex)<Week_3Finish )
                        {
                            week_3NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_4Start && listTimeStart.get(sessionIndex)<Week_4Finish )
                        {
                            week_4NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_5Start && listTimeStart.get(sessionIndex)<Week_5Finish )
                        {
                            week_5NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_6Start && listTimeStart.get(sessionIndex)<Week_6Finish )
                        {
                            week_6NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_7Start && listTimeStart.get(sessionIndex)<Week_7Finish )
                        {
                            week_7NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_8Start && listTimeStart.get(sessionIndex)<Week_8Finish )
                        {
                            week_8NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }

                    }



                    BarData data = new BarData(getXAxisValues8weeks(), getDataSet8weeks(week_1NumberMinutes, week_2NumberMinutes, week_3NumberMinutes, week_4NumberMinutes,
                            week_5NumberMinutes,week_6NumberMinutes,week_7NumberMinutes,week_8NumberMinutes));
                    chart.setData(data);
                    chart.setDescription("My Chart");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();

                    dialog.dismiss();


                }
                else
                {
                    float week_1NumberMinutes=0,week_2NumberMinutes=0,week_3NumberMinutes=0,week_4NumberMinutes=0,
                            week_5NumberMinutes=0,week_6NumberMinutes=0,week_7NumberMinutes=0,week_8NumberMinutes=0,
                            week_9NumberMinutes=0,week_10NumberMinutes=0,week_11NumberMinutes=0,week_12NumberMinutes=0;
                    for (int sessionIndex=0;sessionIndex<listTimeStart.size();sessionIndex++)
                    {
                        if (listTimeStart.get(sessionIndex)>Week_1Start && listTimeStart.get(sessionIndex)<Week_1Finish )
                        {
                            week_1NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));




                        }
                        else if (listTimeStart.get(sessionIndex)>Week_2Start && listTimeStart.get(sessionIndex)<Week_2Finish )
                        {
                            week_2NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_3Start && listTimeStart.get(sessionIndex)<Week_3Finish )
                        {
                            week_3NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_4Start && listTimeStart.get(sessionIndex)<Week_4Finish )
                        {
                            week_4NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_5Start && listTimeStart.get(sessionIndex)<Week_5Finish )
                        {
                            week_5NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_6Start && listTimeStart.get(sessionIndex)<Week_6Finish )
                        {
                            week_6NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_7Start && listTimeStart.get(sessionIndex)<Week_7Finish )
                        {
                            week_7NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_8Start && listTimeStart.get(sessionIndex)<Week_8Finish )
                        {
                            week_8NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }


                        else if (listTimeStart.get(sessionIndex)>Week_9Start && listTimeStart.get(sessionIndex)<Week_9Finish )
                        {
                            week_9NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_10Start && listTimeStart.get(sessionIndex)<Week_10Finish )
                        {
                            week_10NumberMinutes+= TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex));

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_11Start && listTimeStart.get(sessionIndex)<Week_11Finish )
                        {
                            week_11NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }
                        else if (listTimeStart.get(sessionIndex)>Week_12Start && listTimeStart.get(sessionIndex)<Week_12Finish )
                        {
                            week_12NumberMinutes+=TimeUnit.MILLISECONDS.toMinutes(listTimeFinish.get(sessionIndex) -listTimeStart.get(sessionIndex) );

                        }

                    }



                    BarData data = new BarData(getXAxisValues12weeks(), getDataSet12weeks(week_1NumberMinutes, week_2NumberMinutes, week_3NumberMinutes, week_4NumberMinutes,
                            week_5NumberMinutes,week_6NumberMinutes,week_7NumberMinutes,week_8NumberMinutes,
                            week_9NumberMinutes,week_10NumberMinutes,week_11NumberMinutes,week_12NumberMinutes));
                    chart.setData(data);
                    chart.setDescription("My Chart");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();

                    dialog.dismiss();

                }




            }

            @Override
            public void onFailure(Call<SessionsResponseModel> call, Throwable t) {
                Log.e("Erreur", String.valueOf(t));
                dialog.dismiss();

            }


        });
    }

    private ArrayList<BarDataSet> getDataSet2weeks(float val1,float val2) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(val1, 0); // Week 1
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(val2, 1); // Week 2
        valueSet1.add(v1e2);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Number of minutes");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }
    private ArrayList<String> getXAxisValues2weeks() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Week1");
        xAxis.add("Week2");

        return xAxis;
    }


    private ArrayList<BarDataSet> getDataSet4weeks(float val1,float val2,float val3,float val4 ) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(val1, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(val2, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(val3, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(val4, 3); // Apr
        valueSet1.add(v1e4);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Number of minutes");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }
    private ArrayList<String> getXAxisValues4weeks() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Week1");
        xAxis.add("Week2");
        xAxis.add("Week3");
        xAxis.add("Week4");
        return xAxis;
    }

    private ArrayList<BarDataSet> getDataSet8weeks(float val1,float val2,float val3,float val4,float val5,float val6,float val7,float val8 ) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(val1, 0); // Week 1
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(val2, 1); // week 2
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(val3, 2); // week 3
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(val4, 3); // week 4
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(val5, 4); // week 5
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(val6, 5); // week 6
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(val7, 6); // week 7
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(val8, 7); // week 8
        valueSet1.add(v1e8);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Number of minutes");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }
    private ArrayList<String> getXAxisValues8weeks() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Week1");
        xAxis.add("Week2");
        xAxis.add("Week3");
        xAxis.add("Week4");
        xAxis.add("Week5");
        xAxis.add("Week6");
        xAxis.add("Week7");
        xAxis.add("Week8");
        return xAxis;
    }

    private ArrayList<BarDataSet> getDataSet12weeks(float val1,float val2,float val3,float val4,float val5,float val6,float val7,float val8,
                                                    float val9,float val10,float val11,float val12) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(val1, 0); // Week 1
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(val2, 1); // week 2
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(val3, 2); // week 3
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(val4, 3); // week 4
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(val5, 4); // week 5
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(val6, 5); // week 6
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(val7, 6); // week 7
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(val8, 7); // week 8
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry(val9, 8); // week 9
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry(val10, 9); // week 10
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry(val11, 10); // week 11
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry(val12, 11); // week 12
        valueSet1.add(v1e12);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Number of minutes");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }
    private ArrayList<String> getXAxisValues12weeks() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Week1");
        xAxis.add("Week2");
        xAxis.add("Week3");
        xAxis.add("Week4");
        xAxis.add("Week5");
        xAxis.add("Week6");
        xAxis.add("Week7");
        xAxis.add("Week8");
        xAxis.add("Week9");
        xAxis.add("Week10");
        xAxis.add("Week11");
        xAxis.add("Week12");
        return xAxis;
    }








}
