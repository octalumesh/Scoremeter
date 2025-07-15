package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem;

import java.util.ArrayList;

public class MatchOddsInsideAdapter extends RecyclerView.Adapter<MatchOddsInsideAdapter.MyViewHolder> {

    private ArrayList<MatchOddsResponseItem> modelList;
    private Context context;


    public MatchOddsInsideAdapter(ArrayList<MatchOddsResponseItem> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    public void updateList(ArrayList<MatchOddsResponseItem> modelList){
        this.modelList=modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_odds_inside, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MatchOddsResponseItem data = modelList.get(position);

        holder.oddsOver.setText(data.getOvers());
        holder.oddsTime.setText(data.getTime());
        holder.run_at_this_overball.setText(data.getScore());
        holder.sMin.setText(data.getSMin());
        holder.sMax.setText(data.getSMax());
        holder.oddsTeam.setText(data.getFavTeam());
        holder.oddsMinRate.setText(data.getMinRate());
        holder.oddsMaxRate.setText(data.getMaxRate());


        if (data.getRuns().equalsIgnoreCase("0")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("0");
            }
        }else if (data.getRuns().equalsIgnoreCase("1")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("1");
            }
        }else if (data.getRuns().equalsIgnoreCase("2")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("2");
            }
        }else if (data.getRuns().equalsIgnoreCase("3")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("3");
            }
        }else if (data.getRuns().equalsIgnoreCase("4")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.blue)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setText("4");
            }
        }else if (data.getRuns().equalsIgnoreCase("6")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.limeGreen)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setText("6");
            }
        }else if (data.getRuns().equalsIgnoreCase("C/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("B/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("St.")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("R/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("LBW")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("WK")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("W")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("W");
            }
        }else if (data.getRuns().equalsIgnoreCase("WB")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("WB");
            }
        }else if (data.getRuns().equalsIgnoreCase("Wd")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("Wd");
            }
        }else if (data.getRuns().equalsIgnoreCase("NB") || data.getRuns().equalsIgnoreCase("FREE HIT")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("NB");
            }
        }else if (data.getRuns().equalsIgnoreCase("N4")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.limeGreen)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setText("N4");
            }
        }else if (data.getRuns().equalsIgnoreCase("LB")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("LB");
            }
        }else if (data.getRuns().equalsIgnoreCase("Bye")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                holder.textViewLastBall.setText("Bye");
            }
        }
        else if (data.getRuns().equals("WK") || data.getRuns().toString().toLowerCase()
                .contains("new batter") || data.getRuns().toString().toLowerCase()
                .contains("new bat") || data.getRuns().toString().toLowerCase().contains("bowled")
                || data.getRuns().toString().toLowerCase().contains("bat")
        ){
            holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
            holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
            holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            holder.textViewLastBall.setText("W");
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView oddsOver, oddsTime, sMin,sMax, oddsTeam,oddsMinRate,oddsMaxRate, run_at_this_overball,textViewLastBall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            run_at_this_overball = itemView.findViewById(R.id.run_at_this_overball);
            oddsOver = itemView.findViewById(R.id.oddsOver);
            oddsTime = itemView.findViewById(R.id.oddsTime);
            sMin = itemView.findViewById(R.id.oddsSMin);
            sMax = itemView.findViewById(R.id.oddsSMax);
            oddsTeam = itemView.findViewById(R.id.oddsTeam);
            oddsMinRate = itemView.findViewById(R.id.oddsMinRate);
            oddsMaxRate = itemView.findViewById(R.id.oddsMaxRate);
            textViewLastBall = itemView.findViewById(R.id.tv_lastball_cycle);

        }
    }
}
