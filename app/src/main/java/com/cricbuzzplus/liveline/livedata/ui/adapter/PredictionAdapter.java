package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.PredictionResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.MyViewHolder> {

    private ArrayList<PredictionResponse> modelList;
    private Context context;

    //constructor define list and context
    public PredictionAdapter(ArrayList<PredictionResponse> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    public void updateList(ArrayList<PredictionResponse> list){
        this.modelList = list;
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public PredictionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prediction, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PredictionResponse data = modelList.get(position);

        SimpleDateFormat sfd = new  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Log.e("TAG", "onBindViewHolder: "+data.getMessage() );

        holder.title.setText(data.getTitle());
        holder.message.setText(data.getMessage());
        holder.date.setText("Date: "+sfd.format(new Date(data.getDate().toDate().toString())));

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView date;
        public TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlePrediction);
            message = itemView.findViewById(R.id.titleMessage);
            date = itemView.findViewById(R.id.datePrediction);

        }
    }
}
