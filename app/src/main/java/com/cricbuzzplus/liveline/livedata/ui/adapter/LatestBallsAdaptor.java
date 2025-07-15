package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.annotation.SuppressLint;
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

import java.util.List;

public class LatestBallsAdaptor extends RecyclerView.Adapter<LatestBallsAdaptor.ViewHolder> {
    private List<String> modelList;
    private Context context;
    Context appContext;
    int pos = -1;

    //constructor define list and context
    public LatestBallsAdaptor(List<String> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }


    @NonNull
    @Override
    public LatestBallsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itam_latest_balls, parent, false);
        //liveScoreFragment = new TeamSqud();
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        // set the Context here
        context = parent.getContext();
        return vh;
        //return new lastBallAdaptor.ViewHolder(vh);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final String score = modelList.get(position);
//
        holder.textViewLastBall.setText(String.valueOf(score));

//        int overComplete = liveScoreFragment.OverDecimal_b;
//        if (overComplete != 0) {
//            holder.lastball_divider.setVisibility(View.VISIBLE);
//            Toast.makeText(getApplicationContext(), "over complete: "+overComplete, Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getApplicationContext(), "over not complete: "+overComplete, Toast.LENGTH_SHORT).show();
//            holder.lastball_divider.setVisibility(View.GONE);
//        }
        if (score.equalsIgnoreCase("0")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("1")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("2")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("3")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tab_gray_lgt)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_black)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("4")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.blue)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
            }
        }else if (score.equalsIgnoreCase("6")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.limeGreen)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
            }
        }else if (score.equalsIgnoreCase("C/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
            }
        }else if (score.equalsIgnoreCase("B/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
            }
        }else if (score.equalsIgnoreCase("St.")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("R/O")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
            }
        }else if (score.equalsIgnoreCase("LBW")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
            }
        }else if (score.equalsIgnoreCase("WK")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("W")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("WB")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("Wd")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("NB")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("N4")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("LB")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }else if (score.equalsIgnoreCase("Bye")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.textViewLastBall.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_kabutar)));
                holder.textViewLastBall.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.text_white)));
                holder.textViewLastBall.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            }
        }
        else {
        }

        pos = position;

    }
    // Constructor for adapter class
    // which takes a list of String type
    public LatestBallsAdaptor(List<String> horizontalList)
    {
        this.modelList = horizontalList;
    }

    public void updateList(List<String> horizontalList){
        this.modelList = horizontalList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
//        if(modelList.size() > limit){
//            return limit;
//        }
//        else
//        {
//            return modelList.size();
//        }
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewLastBall;
        public View lastball_divider;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLastBall = itemView.findViewById(R.id.tv_lastball_cycle);
            lastball_divider = itemView.findViewById(R.id.lastball_divider);
        }
    }


}
