package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.PointListResponseItem;

import java.util.ArrayList;


public class SeriesPointTableAdaptor extends RecyclerView.Adapter<SeriesPointTableAdaptor.ViewHolder> {
    private ArrayList<PointListResponseItem> modelList;
    private Context context;
    //private AdapterView.OnItemClickListener listener;
    int pos = -1;
//    boolean SameDateAct = false;
//    public static final int GroupDateNo = 0;
//    public static final int GroupDateYes = 1;

    //constructor define list and context
    public SeriesPointTableAdaptor(ArrayList<PointListResponseItem> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    public void updateList(ArrayList<PointListResponseItem> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SeriesPointTableAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == GroupDateYes){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match3_upcoming,parent,false);
//            return  new seriesPointTblAdaptor.ViewHolder(v);
//        }else {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match,parent,false);
//            return  new seriesPointTblAdaptor.ViewHolder(v);
//        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_points_table, parent, false);
        //liveScoreFragment = new TeamSqud();
        return new SeriesPointTableAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesPointTableAdaptor.ViewHolder holder, int position) {
        final PointListResponseItem fixturedata = modelList.get(position);

        //String matchId = fixturedata.getMatch_id();
        holder.textViewTeamName.setText(fixturedata.getTeams());
        holder.textViewNotResult.setText(fixturedata.getNR());
        holder.textViewNetRunRate.setText(fixturedata.getNRR());
        holder.textViewPlayMatches.setText(fixturedata.getP());
        holder.textViewPoints.setText(fixturedata.getPts());
        holder.textViewTeamWin.setText(fixturedata.getW());
        holder.textViewTeamLose.setText(fixturedata.getL());

        //Picasso.get().load(fixturedata.getTeam_a_img()).fit().placeholder(R.drawable.cricchamp_blacknwhite).into(holder.imageView);
        //Picasso.get().load(fixturedata.getTeam_b_img()).fit().placeholder(R.drawable.cricchamp_blacknwhite).into(holder.imageView2);

        pos = position;

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (SameDateAct == true){
//            return GroupDateYes;
//        }else {
//            return GroupDateNo;
//        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //final CardView cardView;
        public TextView textViewTeamName, textViewPlayMatches, textViewTeamWin, textViewTeamLose,
                textViewNotResult, textViewPoints, textViewNetRunRate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTeamName = itemView.findViewById(R.id.textview_team_name1);
            textViewPlayMatches = itemView.findViewById(R.id.textview_series_team_play_match);
            textViewTeamWin = itemView.findViewById(R.id.textview_series_team_win);
            textViewTeamLose = itemView.findViewById(R.id.textview_series_team_lose);
            textViewNotResult = itemView.findViewById(R.id.textview_series_team_notresult);
            textViewPoints = itemView.findViewById(R.id.textview_series_team_point);
            textViewNetRunRate = itemView.findViewById(R.id.textview_series_team_nrr);


        }
    }
}

