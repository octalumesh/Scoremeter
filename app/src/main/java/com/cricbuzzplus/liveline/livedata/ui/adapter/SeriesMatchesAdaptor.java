package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.SeriesMatchResponseItem;
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity;

import java.util.ArrayList;

public class SeriesMatchesAdaptor extends RecyclerView.Adapter<SeriesMatchesAdaptor.ViewHolder> {
    private ArrayList<SeriesMatchResponseItem> modelList;
    private Context context;
    String seriesName;
    //private AdapterView.OnItemClickListener listener;
    int pos = -1;
//    boolean SameDateAct = false;
//    public static final int GroupDateNo = 0;
//    public static final int GroupDateYes = 1;

    //constructor define list and context
    public SeriesMatchesAdaptor(ArrayList<SeriesMatchResponseItem> modelList, Context context,String seriesName) {
        this.modelList = modelList;
        this.context = context;
        this.seriesName = seriesName;
    }

    public void updateList(ArrayList<SeriesMatchResponseItem> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SeriesMatchesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_series_matches, parent, false);
        //liveScoreFragment = new TeamSqud();
        return new SeriesMatchesAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesMatchesAdaptor.ViewHolder holder, int position) {
        final SeriesMatchResponseItem fixturedata = modelList.get(position);

        RequestOptions requestOptions= new  RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);


        int matchId = fixturedata.getMatchId();
        //holder.textViewId.setText(fixturedata.getMatch_id());

        if (!fixturedata.getDateWise().equals("")) {
            holder.textViewMatchDate.setVisibility(View.VISIBLE);
        } else {
            holder.textViewMatchDate.setVisibility(View.GONE);
        }

        holder.textViewMatchDate.setText(fixturedata.getDateWise());
        holder.textViewTeam1.setText(fixturedata.getTeamA());
        holder.textViewTeam2.setText(fixturedata.getTeamB());
        holder.textViewMatchType.setText(fixturedata.getMatchType());
        holder.textViewMatchSeries.setText(fixturedata.getMatchs());
        holder.textviewTiming.setText(fixturedata.getMatchTime());
        holder.textview_venue_upcoming.setText(fixturedata.getVenue());

        Glide.with(context).load(fixturedata.getTeamAImg()).apply(requestOptions).into(holder.imageView);
        Glide.with(context).load(fixturedata.getTeamBImg()).apply(requestOptions).into(holder.imageView2);

        String teamShortName = fixturedata.getTeamAShort();
        String teamShortNameB = fixturedata.getTeamBShort();
        checkTeamNameTextLength(holder, teamShortName);
        checkTeamNameTextLengthTeamB(holder, teamShortNameB);

        String matchStatus = "Upcoming";
        String match_time = fixturedata.getMatchTime();
        String matchDate = fixturedata.getDateWise();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), LiveScoreHome.class);
                Log.e("newactivity", "onCreate: "+"sereismatch" );
                Intent intent = new Intent(v.getContext(), LiveHomeActivity.class);
                intent.putExtra("matchId", matchId);
                intent.putExtra("matchStatus", matchStatus);
                intent.putExtra("match_time", match_time);
                intent.putExtra("matchDate", matchDate);
                intent.putExtra("teamA", fixturedata.getTeamA());
                intent.putExtra("teamB", fixturedata.getTeamB());
                intent.putExtra("teamAShort", fixturedata.getTeamAShort());
                intent.putExtra("teamBShort", fixturedata.getTeamBShort());
                intent.putExtra("matchType", fixturedata.getMatchType());
                intent.putExtra("result", "");
                intent.putExtra("matchNo", fixturedata.getMatchs());
                intent.putExtra("series", seriesName);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(), "clicked" + matchId, Toast.LENGTH_SHORT).show();
            }
        });

        pos = position;

    }

    private void checkTeamNameTextLengthTeamB(SeriesMatchesAdaptor.ViewHolder holder, String teamShortNameB) {
        if (holder.textViewTeam2.length() >= 10){
            //**holder.textViewTeam1.setText(teamShortName);
            holder.textViewTeam2.setText(teamShortNameB);
        }else {

        }
    }

    private void checkTeamNameTextLength(SeriesMatchesAdaptor.ViewHolder holder, String teamShortName) {

        if (holder.textViewTeam1.length() >= 10){
            holder.textViewTeam1.setText(teamShortName);
            //holder.textViewTeam2.setText(teamShortNameB);
        }else {

        }
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
        public ImageView imageView, imageView2;
        public TextView textViewId, textViewMatchDate, textViewTeam1, textViewTeam2, textViewMatchType, textViewMatchSeries,
                textViewSquad, textViewStatus, textViewVenue, textviewTiming, textview_venue_upcoming;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.team_image_a_live);
            imageView2 = itemView.findViewById(R.id.team_image_b_live);

            //cardView = itemView.findViewById(R.id.cardview_recycler);
            textViewId = itemView.findViewById(R.id.textview_count_predictions);
            textViewMatchDate = itemView.findViewById(R.id.textview_count_time);
            textViewTeam1 = itemView.findViewById(R.id.textview_team_a_live);
            textViewTeam2 = itemView.findViewById(R.id.textview_team_b_live);
            textViewMatchType = itemView.findViewById(R.id.textview_match_type);
            textViewMatchSeries = itemView.findViewById(R.id.textview_match_series);
            //textViewStatus = itemView.findViewById(R.id.textview_match_status);
            //textViewVenue = itemView.findViewById(R.id.textview_match_time);
            textviewTiming = itemView.findViewById(R.id.textview_count_predictions);
            //textViewMatchStarted = itemView.findViewById(R.id.textview_matchstarted);
            textview_venue_upcoming = itemView.findViewById(R.id.textview_venue_upcoming);


//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                        Intent intent = new Intent(context, TeamSqud.class);
////                    intent.putExtra("id", matchId);
////                    intent.putExtra("date", date);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                        //Log.e("LOG Intent Onclick : ", String.valueOf(matchId));
//                    }
//
//            });
        }
    }
}
