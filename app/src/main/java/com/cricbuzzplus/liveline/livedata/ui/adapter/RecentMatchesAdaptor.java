package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.RecentMatchResponseItem;
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity;
import com.cricbuzzplus.liveline.livedata.ui.activity.SeriesTabActivity;

import java.util.ArrayList;

public class RecentMatchesAdaptor extends RecyclerView.Adapter<RecentMatchesAdaptor.ViewHolder>
{
    private ArrayList<RecentMatchResponseItem> modelList;
        private Context context;
    private ArrayList<String> commonDateList = new ArrayList<>();
        //private AdapterView.OnItemClickListener listener;
        int pos = -1;
    private final int limit = 8;
//    boolean SameDateAct = false;
//    public static final int GroupDateNo = 0;
//    public static final int GroupDateYes = 1;

        //constructor define list and context
        public RecentMatchesAdaptor(ArrayList<RecentMatchResponseItem> modelList, Context context) {
            this.modelList = modelList;
            this.context = context;
        }

    public void updateList(ArrayList<RecentMatchResponseItem> horizontalList){
        this.modelList = horizontalList;
        notifyDataSetChanged();
    }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == GroupDateYes){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match3_upcoming,parent,false);
//            return  new recentAdaptorApi.ViewHolder(v);
//        }else {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match,parent,false);
//            return  new recentAdaptorApi.ViewHolder(v);
//        }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_recent, parent, false);
            //liveScoreFragment = new TeamSqud();
            return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull RecentMatchesAdaptor.ViewHolder holder, int position) {
            final RecentMatchResponseItem fixturedata = modelList.get(position);

            String matchId = fixturedata.getMatchId().toString();
        String match_date = fixturedata.getMatchDate();
        String match_time = fixturedata.getMatchTime();
        String matchStatus = "Finished";

        if (commonDateList.contains(fixturedata.getDateWise())){
            commonDateList.add("");
        }else {
            commonDateList.add(fixturedata.getDateWise());
        }

        RequestOptions requestOptions= new  RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        RequestOptions requestOptions1= new  RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        if (commonDateList != null) {
            if (commonDateList.get(position) != "") {
                holder.textViewMatchDate.setVisibility(View.VISIBLE);
            } else {
                holder.textViewMatchDate.setVisibility(View.GONE);
            }
        }

            //holder.textViewId.setText(fixturedata.getMatch_id());


            holder.textViewMatchDate.setText(fixturedata.getDateWise());
            holder.textViewTime.setText(fixturedata.getMatchTime());
            holder.textViewTeam1.setText(fixturedata.getTeamAShort());
            holder.textViewTeam2.setText(fixturedata.getTeamBShort());
            holder.textViewMatchType.setText(fixturedata.getMatchType());
            holder.textViewMatchSeries.setText(fixturedata.getMatchs()+", "+fixturedata.getSeries());
            holder.textViewResult.setText(fixturedata.getResult());
            //holder.matchResult.setText(fixturedata.getResult());

           // holder.matchDetails.setText(fixturedata.getSeries());


            holder.textViewTeam1Over.setText("("+fixturedata.getTeamAOver()+")");
            holder.textViewTeam1Score.setText(fixturedata.getTeamAScores());
            holder.textViewTeam2Over.setText("("+fixturedata.getTeamBOver()+")");
            holder.textViewTeam2Score.setText(fixturedata.getTeamBScores());
            //holder.textViewStatus.setText(fixturedata.getDate_wise());
            //holder.textViewVenue.setText(fixturedata.getVenue());
            //holder.textViewOdds.setText(fixturedata.getFav_team()+" Min: "+fixturedata.min_rate+" Max: "+fixturedata.getMax_rate());
        Glide.with(context).load(fixturedata.getTeamAImg()).apply(requestOptions).circleCrop().into(holder.imageView);
        Glide.with(context).load(fixturedata.getTeamBImg()).apply(requestOptions1).circleCrop().into(holder.imageView2);

            String teamShortName = fixturedata.getTeamAShort();
            String teamShortNameB = fixturedata.getTeamBShort();
           // checkTeamNameTextLength(holder, teamShortName);
            //checkTeamNameTextLengthTeamB(holder, teamShortNameB);



        holder.parentRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String matchId = fixturedata.getId();
//                String date = fixturedata.getDateTimeGMT();
                //onClickInterFace.onClick(matchId,matchStatus,match_time,matchDate);

                //Intent intent = new Intent(v.getContext(), LiveScoreHome.class);
                Intent intent = new Intent(v.getContext(), LiveHomeActivity.class);
                intent.putExtra("matchId", fixturedata.getMatchId());
                intent.putExtra("matchStatus", matchStatus);
                intent.putExtra("matchTime", match_time);
                intent.putExtra("matchDate", match_date);
                intent.putExtra("teamA", fixturedata.getTeamA());
                intent.putExtra("teamB", fixturedata.getTeamB());
                intent.putExtra("teamAShort", fixturedata.getTeamAShort());
                intent.putExtra("teamBShort", fixturedata.getTeamBShort());
                intent.putExtra("matchType", fixturedata.getMatchType());
                intent.putExtra("result", fixturedata.getResult());
                intent.putExtra("matchNo", fixturedata.getMatchs());
                intent.putExtra("series", fixturedata.getSeries());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(), "clicked" + matchId, Toast.LENGTH_SHORT).show();
            }
        });

        holder.pointTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeriesTabActivity.class);
                intent.putExtra("seriesId", fixturedata.getSeriesId());
                intent.putExtra("seriesName", fixturedata.getSeries());
                intent.putExtra("index", 1);
                context.startActivity(intent);
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeriesTabActivity.class);
                intent.putExtra("seriesId", fixturedata.getSeriesId());
                intent.putExtra("seriesName", fixturedata.getSeries());
                intent.putExtra("index", 0);
                context.startActivity(intent);
            }
        });

            pos = position;

        }

        private void checkTeamNameTextLengthTeamB(RecentMatchesAdaptor.ViewHolder holder, String teamShortNameB) {
            if (holder.textViewTeam2.length() >= 10){
                //**holder.textViewTeam1.setText(teamShortName);
                holder.textViewTeam2.setText(teamShortNameB);
            }else {

            }
        }

        private void checkTeamNameTextLength(RecentMatchesAdaptor.ViewHolder holder, String teamShortName) {

            if (holder.textViewTeam1.length() >= 10){
                holder.textViewTeam1.setText(teamShortName);
                //holder.textViewTeam2.setText(teamShortNameB);
            }else {

            }
        }

        @Override
        public int getItemCount() {
//            if(modelList.size() > limit)
//            {
//            return limit;
//            }
//            else
//            {
//            return modelList.size();
//            }
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
                    textViewSquad, textViewStatus, textViewTime, textViewOdds, textViewTeam1Over, textViewTeam1Score,
            textViewTeam2Over, textViewTeam2Score, textViewResult,matchDetails,matchResult,pointTable,schedule;

            LinearLayout parentRecent;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.team_image_a_live);
                imageView2 = itemView.findViewById(R.id.team_image_b_live);

                parentRecent = itemView.findViewById(R.id.parentRecent);
                matchDetails = itemView.findViewById(R.id.textview_matchdetail);
                matchResult = itemView.findViewById(R.id.textview_result);

                //cardView = itemView.findViewById(R.id.cardview_recycler);
                textViewId = itemView.findViewById(R.id.textview_count_predictions);
                textViewMatchDate = itemView.findViewById(R.id.textview_count_time);
                textViewTeam1 = itemView.findViewById(R.id.textview_team_a_live);
                textViewTeam2 = itemView.findViewById(R.id.textview_team_b_live);
                textViewMatchType = itemView.findViewById(R.id.textview_match_type);
                textViewMatchSeries = itemView.findViewById(R.id.textview_match_series);
                //textViewStatus = itemView.findViewById(R.id.textview_match_status);
                //textViewVenue = itemView.findViewById(R.id.textview_match_time);
                textViewOdds = itemView.findViewById(R.id.textview_count_predictions);
                //textViewMatchStarted = itemView.findViewById(R.id.textview_matchstarted);
                textViewTeam1Over = itemView.findViewById(R.id.tv_team_a_over_recent);
                textViewTeam1Score = itemView.findViewById(R.id.tv_team_a_score_recent);
                textViewTeam2Over = itemView.findViewById(R.id.tv_team_b_over_recent);
                textViewTeam2Score = itemView.findViewById(R.id.tv_team_b_score_recent);
                textViewResult = itemView.findViewById(R.id.textview_result_recent);
                textViewTime = itemView.findViewById(R.id.textview_date_time);
                pointTable = itemView.findViewById(R.id.pointTable);
                schedule = itemView.findViewById(R.id.schedule);



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
