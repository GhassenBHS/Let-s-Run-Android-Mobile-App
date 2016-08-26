package com.jogging.bhs.RecyclerViews;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jogging.bhs.R;
import java.util.ArrayList;


/**
 * Created by SPEED on 8/20/2016.
 */
public class SessionRecentAdapter extends RecyclerView.Adapter<SessionRecentAdapter.MyViewHolder> {

    public ArrayList<String> listAdder;
    public ArrayList<String> listTimeStart;
    private Context mContext;
    private int size =0 ;


    public SessionRecentAdapter(Context context, int size,ArrayList<String> listAdder, ArrayList<String> listTimeStart) {
        mContext = context;
        this.size=size ;
        this.listAdder = listAdder;
        this.listTimeStart = listTimeStart;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView statictext, by, starts;




        public MyViewHolder(View view) {
            super(view);
            statictext = (TextView) view.findViewById(R.id.textview);
            by = (TextView) view.findViewById(R.id.textview2);
            starts = (TextView) view.findViewById(R.id.textview3);


        }
    }




    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.statictext.setText("Jogging session number " + position);
        holder.by.setText(listAdder.get(position));
        holder.starts.setText(listTimeStart.get(position));


    }




    @Override
    public int getItemCount() {
        return listAdder.size();
    }




}
