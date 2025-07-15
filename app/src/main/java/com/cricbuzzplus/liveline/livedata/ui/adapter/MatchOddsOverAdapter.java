package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem;
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnOverClick;

import java.util.ArrayList;

public class MatchOddsOverAdapter extends RecyclerView.Adapter<MatchOddsOverAdapter.MyViewHolder> {

    private ArrayList<ArrayList<MatchOddsResponseItem>> modelList;
    private Context context;


    int selectedPos;

    OnOverClick onOverClick;

    public MatchOddsOverAdapter(ArrayList<ArrayList<MatchOddsResponseItem>> modelList, Context context, int size, OnOverClick onOverClick) {
        this.modelList = modelList;
        this.context = context;
        this.selectedPos = size-1;
        this.onOverClick = onOverClick;
    }

    public void updateList(ArrayList<ArrayList<MatchOddsResponseItem>> modelList,OnOverClick onOverClick){
        this.modelList=modelList;
        this.onOverClick = onOverClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_over_data, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ArrayList<MatchOddsResponseItem> data = modelList.get(position);

        holder.overData.setText(""+ (position+1));

        Log.e("TAG", "onBindViewHolder: "+modelList.size() );


        holder.relative_odds_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPos = position;
                onOverClick.onOverClick(data);
            }
        });

        if (selectedPos == position){
            //holder.overData.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green1_lgt_odds2));
            holder.relative_odds_over.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.odds_left_back)));
//            holder.overData.setBackgroundColor(context.getColor(R.color.limeGreen));
            holder.overData.setTextColor(context.getColor(R.color.text_white));
            holder.overData1.setTextColor(context.getColor(R.color.text_white));
            // onOverClick.onOverClick(data);
        }
        else {
           // holder.overData.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.card_view_white));
            holder.relative_odds_over.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.card_view_white)));
//            holder.overData.setBackgroundColor(context.getColor(R.color.card_view_white));
            holder.overData.setTextColor(ColorStateList.valueOf(context.getColor(R.color.green_lgt_odds2)));
            holder.overData1.setTextColor(ColorStateList.valueOf(context.getColor(R.color.green_lgt_odds2)));
        }

       /* if (adapter == null){

            adapter = new MatchOddsInsideAdapter(data,context);
            holder.recyclerOddsInside.setAdapter(adapter);

        }
        else {
            adapter.updateList(data);
        }*/

        /*for(DataItem item : modelList){
            Double over = Double.valueOf(item.getOvers());
            int newover = (int) Math.ceil(over);
            item.setNewover(newover);
        }



        int lastover1=modelList.get(modelList.size() - 1).getNewover();

        ArrayList<ArrayList<DataItem>> overlist1=new ArrayList<>();

        for (int i = 1; i <= lastover1; i++) {
            ArrayList<DataItem> templist =new ArrayList<>();
            for(DataItem item : modelList) {
                if (i==item.getNewover()){
                    templist.add(item);

                }
            }
            overlist1.add(templist);
        }

        Log.e("TAG", "onBindViewHolder: "+overlist1.size() );

        holder.oddsOverNew.setText(""+overlist1.get(position));

       *//* for (int i = 0 ; i < overlist1.size();i++){

           holder.oddsOverNew.setText( ""+overlist1.get(i).get(i).getOvers());

        }*//*

        holder.oddsOver.setText(data.getOvers());
        holder.oddsTime.setText(data.getTime());
        holder.sMin.setText(data.getSMin());
        holder.sMax.setText(data.getSMax());
        holder.oddsTeam.setText(data.getTeam());
        holder.oddsMinRate.setText(data.getMinRate());
        holder.oddsMaxRate.setText(data.getMaxRate());*/

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView oddsOver, oddsTime, sMin,sMax, oddsTeam,oddsMinRate,oddsMaxRate,overData, overData1;
        RecyclerView recyclerOddsInside;
        RelativeLayout relative_odds_over;

       public boolean recyclerVisible = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            overData1 = itemView.findViewById(R.id.overData1);
            oddsOver = itemView.findViewById(R.id.oddsOver);
            oddsTime = itemView.findViewById(R.id.oddsTime);
            sMin = itemView.findViewById(R.id.oddsSMin);
            sMax = itemView.findViewById(R.id.oddsSMax);
            oddsTeam = itemView.findViewById(R.id.oddsTeam);
            oddsMinRate = itemView.findViewById(R.id.oddsMinRate);
            oddsMaxRate = itemView.findViewById(R.id.oddsMaxRate);
            overData = itemView.findViewById(R.id.overData);
            relative_odds_over = itemView.findViewById(R.id.relative_odds_over);
            //recyclerOddsInside = itemView.findViewById(R.id.recyclerOddsInside);

        }
    }
}
