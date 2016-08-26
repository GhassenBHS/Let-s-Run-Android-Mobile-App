package com.jogging.bhs.fragments;
        import android.animation.ValueAnimator;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.Snackbar;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.AccelerateDecelerateInterpolator;
        import android.widget.Button;
        import android.widget.ImageView;

        import com.dd.CircularProgressButton;
        import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
        import com.jogging.bhs.Network.Interfaces.UserAddService;
        import com.jogging.bhs.Network.Model.UserModel;
        import com.jogging.bhs.R;
        import com.jogging.bhs.managers.SharedPrefsMgr;
        import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

        import com.google.firebase.iid.FirebaseInstanceId;

        import java.io.IOException;
        import dmax.dialog.SpotsDialog;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


public class ParametersFragment extends BaseFragment {
    private Button btn1;
    private Button btn2;
    private ImageView modifyImage,addImage;

    private int seekBarProgressRadius = 0,seekBarProgressRythm=0;
    private SharedPrefsMgr spm;
    private AlertDialog dialog ;
    private MaterialStyledDialog materiel_style_dialog ;

    public static ParametersFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ParametersFragment fragment = new ParametersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
        spm=new SharedPrefsMgr();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parameters, container, false);


        modifyImage = (ImageView) view.findViewById(R.id.modify);
        addImage = (ImageView) view.findViewById(R.id.add);



        final DiscreteSeekBar rythmSeekBar = (DiscreteSeekBar) view.findViewById(R.id.rythm);
        DiscreteSeekBar radiusSeekBar = (DiscreteSeekBar) view.findViewById(R.id.radius);



        rythmSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                seekBarProgressRythm = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                spm.setIntValue(getContext(), "rythm", seekBarProgressRythm);


            }
        });

        radiusSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                seekBarProgressRadius = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                spm.setIntValue(getContext(), "radious", seekBarProgressRadius);


            }
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog=new SpotsDialog(getContext(),R.style.Custom);
        materiel_style_dialog=new MaterialStyledDialog(getContext());

        if ( spm.exists("latitude_interest_center",getContext()) && spm.exists("longitude_interest_center",getContext())) {

            addImage.setVisibility(View.INVISIBLE);
            modifyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start map here


                    Intent intent = new Intent(getActivity(), MapFragmnt.class);
                    intent.putExtra("launcher", "ParametersFragment");
                    getActivity().startActivity(intent);


                }
            });
        }

        if ( !spm.exists("latitude_interest_center",getContext()) && !spm.exists("longitude_interest_center",getContext()))
        {
            modifyImage.setVisibility(View.INVISIBLE);
            addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start map here for the first time

                     Intent intent = new Intent(getActivity(), MapFragmnt.class);
                     intent.putExtra("launcher", "ParametersFragment");
                     getActivity().startActivity(intent);

                 }




        });
    }



        final CircularProgressButton circularButton1 = (CircularProgressButton) view.findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setIndeterminateProgressMode(true);



                    String token = FirebaseInstanceId.getInstance().getToken();
                    String fb_id = spm.getStringValue(getContext(), "facebook_id");
                    int rad = spm.getIntValue(getContext(), "radious");
                    double longitude = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "longitude_interest_center"));
                    double latitude = Double.longBitsToDouble(spm.getLongValue(getContext(),
                            "latitude_interest_center"));
                    String Firstname = spm.getStringValue(getContext(), "first_name");
                    String Lastname = spm.getStringValue(getContext(), "last_name");

                    //Connect to server


                    UserModel userModel = new UserModel(Firstname+" "+Lastname,fb_id,longitude,latitude,
                            rad,token) ;
                    UserAddService userAddService = UserAddService.retrofit.create(UserAddService.class) ;
                    Call<Void> call = userAddService.postUser(userModel) ;
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
                                Snackbar.make(view, "Error occurred", Snackbar.LENGTH_LONG).show();


                                return;
                            }
                            Snackbar snackbar = Snackbar.make(view, "Successfully updated data !", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            circularButton1.setProgress(0);
                            simulateSuccessProgress(circularButton1);

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println(t);
                            circularButton1.setProgress(0);
                            simulateErrorProgress(circularButton1);

                        }
                    });





                } else {
                    circularButton1.setProgress(0);
                }
            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }





}


