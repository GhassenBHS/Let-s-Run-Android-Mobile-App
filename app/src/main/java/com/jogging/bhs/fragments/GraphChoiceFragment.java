package com.jogging.bhs.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jogging.bhs.R;


public class GraphChoiceFragment extends BaseFragment  {

    private AlertDialog dialog;
    MaterialSpinner spinner ;
    private String fb_id="",picked="4 last weeks";







    public static GraphChoiceFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        GraphChoiceFragment fragment = new GraphChoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {





        return inflater.inflate(R.layout.fragment_choice_graph, container, false);


    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);

        spinner.setItems(" ","Last 2 Weeks","Last 4 Weeks", "Last 8 Weeks", "Last 12 Weeks");
        final Bundle args = new Bundle();
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (position == 1) {

                    args.putString("item","Last 2 Weeks");

                    BarGraphFragment fragment = new BarGraphFragment();
                    fragment.setArguments(args);

                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(fragment);
                    }

                } else if (position == 2) {
                    args.putString("item","Last 4 Weeks");

                    BarGraphFragment fragment = new BarGraphFragment();
                    fragment.setArguments(args);

                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(fragment);
                    }

                } else if (position == 3) {
                    args.putString("item","Last 8 Weeks");

                    BarGraphFragment fragment = new BarGraphFragment();
                    fragment.setArguments(args);

                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(fragment);
                    }

                } else if (position==4)  {
                    args.putString("item","Last 12 Weeks");

                    BarGraphFragment fragment = new BarGraphFragment();
                    fragment.setArguments(args);

                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(fragment);
                    }

                }
            }
        });

        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


    }








}
