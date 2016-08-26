package com.jogging.bhs.RecyclerViews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jogging.bhs.Network.Interfaces.SessionDeleteService;
import com.jogging.bhs.Network.Model.SessionIdModel;
import com.jogging.bhs.R;
import com.jogging.bhs.fragments.BaseFragment;
import com.jogging.bhs.fragments.SessionDetailsFragment;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SPEED on 8/18/2016.
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {



    public ArrayList<String> listAdder;
    public ArrayList<String> listTimeStart;
    public ArrayList<String> listIdSessions;
    public BaseFragment.FragmentNavigation fragmentNavigation ;
    public String firstName,lastname,TAG,fb_id ;



    Context mContext;




    public SessionAdapter(Context context,BaseFragment.FragmentNavigation fragmentNavigation
            ,ArrayList<String> listAdder, ArrayList<String> listTimeStart,ArrayList<String> listIdSessions
            ,String firstName,String lastName,String TAG,String fb_id)
    {
        mContext = context;
        this.listAdder = listAdder;
        this.listTimeStart = listTimeStart;
        this.listIdSessions=listIdSessions ;
        this.fragmentNavigation=fragmentNavigation ;
        this.firstName=firstName ;
        this.lastname=lastName ;
        this.TAG=TAG;
        this.fb_id=fb_id;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView statictext, by, starts;
        public View btDelete;




        public MyViewHolder(View view) {
            super(view);
            statictext = (TextView) view.findViewById(R.id.textview);
            by = (TextView) view.findViewById(R.id.textview2);
            starts = (TextView) view.findViewById(R.id.textview3);
            btDelete = view.findViewById(R.id.btDelete);
            btDelete.setVisibility(View.INVISIBLE);


        }
    }




    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



        final SwipeHorizontalMenuLayout item = (SwipeHorizontalMenuLayout) holder.itemView;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(item, "Successfully got session details", Snackbar.LENGTH_LONG).show();
                Bundle args = new Bundle();
                args.putString("id_session", listIdSessions.get(position));

                SessionDetailsFragment fragment = new SessionDetailsFragment();
                fragment.setArguments(args);

                if (fragmentNavigation != null) {
                    fragmentNavigation.pushFragment(fragment);
                }

            }
        });
        if (listAdder.get(position).equals(firstName+" "+lastname))
        {
            holder.btDelete.setVisibility(View.VISIBLE);


        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog;
                dialog = new SpotsDialog(mContext, R.style.Custom);


                dialog.setMessage("Connecting to server, please wait...");
                dialog.show();

                SessionDeleteService sessionDeleteService = SessionDeleteService.retrofit.create(SessionDeleteService.class);
                Call<Void> call = sessionDeleteService.deleteSession(fb_id, new SessionIdModel(listIdSessions.get(position)));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        System.out.println("Response status code: " + response.code());

                        // isSuccess is true if response code => 200 and <= 300
                        if (!response.isSuccessful()) {
                            // print response body if unsuccessful
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Snackbar.make(item, "Error occurred", Snackbar.LENGTH_LONG).show();

                            dialog.dismiss();
                            return;
                        }
                        int id = holder.getAdapterPosition();
                        item.smoothCloseMenu();
                        listAdder.remove(id);
                        listTimeStart.remove(id);
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Snackbar.make(item, "Successfully deleted session details", Snackbar.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        dialog.dismiss();

                    }
                });


            }

        });
    }





        holder.statictext.setText("Jogging session number " + position);
        holder.by.setText(listAdder.get(position));
        holder.starts.setText(listTimeStart.get(position));


    }




    @Override
    public int getItemCount() {
        return listAdder.size();
    }





}
