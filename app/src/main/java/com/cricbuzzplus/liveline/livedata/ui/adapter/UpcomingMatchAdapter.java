package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.cricbuzzplus.liveline.livedata.model.MatchListModel;
import com.cricbuzzplus.liveline.livedata.response.UpcomingResponseItem;
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity;
import com.cricbuzzplus.liveline.livedata.ui.activity.SeriesTabActivity;
import com.cricbuzzplus.liveline.livedata.ui.interfaces.HomeMatchClickInterface;
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterface;
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterfaceUpcoming;
import com.cricbuzzplus.liveline.livedata.ui.services.PinTopInterface;

import java.util.ArrayList;

public class UpcomingMatchAdapter extends RecyclerView.Adapter<UpcomingMatchAdapter.ViewHolder> {
    private ArrayList<UpcomingResponseItem> modelList;
    private Context context;
    private ArrayList<String> commonDateList = new ArrayList<>();

    PinTopInterface pinTopInterface;
    NotificationInterfaceUpcoming onNotifyInterface;

    MatchListModel matchPinModel;
    //private AdapterView.OnItemClickListener listener;
    int pos = -1;
//    boolean SameDateAct = false;
//    public static final int GroupDateNo = 0;
//    public static final int GroupDateYes = 1;

    //constructor define list and context
    public UpcomingMatchAdapter(ArrayList<UpcomingResponseItem> modelList, Context context, PinTopInterface pinTopInterface, MatchListModel matchPinModel, NotificationInterfaceUpcoming onNotifyInterface) {
        this.modelList = modelList;
        this.context = context;
        this.pinTopInterface = pinTopInterface;
        this.matchPinModel = matchPinModel;
        this.onNotifyInterface = onNotifyInterface;
    }

    public void updateList(ArrayList<UpcomingResponseItem> horizontalList, MatchListModel matchPinModel) {
        this.modelList = horizontalList;
        this.matchPinModel = matchPinModel;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == GroupDateYes){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match3_upcoming,parent,false);
//            return  new upcomingAdaptorApi.ViewHolder(v);
//        }else {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match,parent,false);
//            return  new upcomingAdaptorApi.ViewHolder(v);
//        }
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_list, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_main_upcoming, parent, false);
        //liveScoreFragment = new TeamSqud();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UpcomingResponseItem fixturedata = modelList.get(position);
        // commonDateList.add(fixturedata.getDateWise());

        if (commonDateList.contains(fixturedata.getDateWise())) {
            commonDateList.add("");
        } else {
            commonDateList.add(fixturedata.getDateWise());
        }


        RequestOptions requestOptions = new RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        RequestOptions requestOptions1 = new RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        String matchId = fixturedata.getMatchId().toString();
        //holder.textViewId.setText(fixturedata.getMatch_id());

        holder.pinScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pinTopInterface.onPinClick(fixturedata.getMatchId(),true);
                //  fixturedata.setPinScore(true);

            }
        });

        holder.pinScoreAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pinTopInterface.onPinClick(fixturedata.getMatchId(),false);
                //  fixturedata.setPinScore(true);

            }
        });

        if (matchPinModel != null){
            if (matchPinModel.getMatchList() != null && !matchPinModel.getMatchList().isEmpty()){

                if (matchPinModel.getMatchList().contains(fixturedata.getMatchId())){
                    holder.pinScoreAlready.setVisibility(View.VISIBLE);
                    holder.pinScore.setVisibility(View.GONE);
                }else {
                    holder.pinScoreAlready.setVisibility(View.GONE);
                    holder.pinScore.setVisibility(View.VISIBLE);
                }

            }else {
                holder.pinScoreAlready.setVisibility(View.GONE);
                holder.pinScore.setVisibility(View.VISIBLE);
            }
        }else {
            holder.pinScoreAlready.setVisibility(View.GONE);
            holder.pinScore.setVisibility(View.VISIBLE);
        }


        try {

            if (commonDateList != null) {
                if (commonDateList.get(position) != "") {
                    holder.textViewMatchDate.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewMatchDate.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "onBindViewHolder: " + e.getMessage());
        }

        if (fixturedata.getMatchType() == "T20") {

        }

        holder.textViewMatchDate.setText(fixturedata.getDateWise());
        holder.textViewTeam1.setText(fixturedata.getTeamA());
        holder.textViewTeam2.setText(fixturedata.getTeamB());
        holder.textViewMatchType.setText(fixturedata.getMatchType());
        // holder.matchType.setText(fixturedata.getMatchType());
        holder.textViewMatchSeries.setText(fixturedata.getMatchs() + ", " + fixturedata.getSeries());
        // holder.matchDetails.setText(fixturedata.getSeries());
        holder.textviewTiming.setText(fixturedata.getMatchTime());
        //holder.textViewStatus.setText(fixturedata.getDate_wise());
        holder.textview_venue_upcoming.setText(fixturedata.getVenue());
        //holder.textViewOdds.setText(fixturedata.getFav_team()+" Min: "+fixturedata.min_rate+" Max: "+fixturedata.getMax_rate());

        Glide.with(context).load(fixturedata.getTeamAImg()).apply(requestOptions).circleCrop().into(holder.imageView);
        Glide.with(context).load(fixturedata.getTeamBImg()).apply(requestOptions1).circleCrop().into(holder.imageView2);


        String teamShortName = fixturedata.getTeamAShort();
        String teamShortNameB = fixturedata.getTeamBShort();
        checkTeamNameTextLength(holder, teamShortName);
        checkTeamNameTextLengthTeamB(holder, teamShortNameB);
//            String matchDate = fixturedata.getDate_wise();
//            if (matchDate.equals(matchDate)){
//                SameDateAct = true;
//                //holder.tvCountTime.setVisibility(View.GONE);
//            }else {
//
//            }
        //click on cardView Activity

        String matchStatus = "Upcoming";
        String match_time = fixturedata.getMatchTime();
        String matchDate = fixturedata.getDateWise();

        holder.bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotifyInterface.onClickNotify(fixturedata);
            }
        });

        holder.parentUpcoming.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("matchDate", matchDate);
                intent.putExtra("teamA", fixturedata.getTeamA());
                intent.putExtra("teamB", fixturedata.getTeamB());
                intent.putExtra("teamAShort", fixturedata.getTeamAShort());
                intent.putExtra("teamBShort", fixturedata.getTeamBShort());
                intent.putExtra("matchType", fixturedata.getMatchType());
                intent.putExtra("result", "");
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

    private void checkTeamNameTextLengthTeamB(ViewHolder holder, String teamShortNameB) {
        if (holder.textViewTeam2.length() >= 10) {
            //**holder.textViewTeam1.setText(teamShortName);
            holder.textViewTeam2.setText(teamShortNameB);
        } else {

        }
    }

    private void checkTeamNameTextLength(ViewHolder holder, String teamShortName) {

        if (holder.textViewTeam1.length() >= 10) {
            holder.textViewTeam1.setText(teamShortName);
            //holder.textViewTeam2.setText(teamShortNameB);
        } else {

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
        public ImageView imageView, imageView2,bellIcon, pinScore, pinScoreAlready;
        public TextView textViewId, textViewMatchDate, textViewTeam1, textViewTeam2, textViewMatchType, textViewMatchSeries,
                textViewSquad, textViewStatus, textViewVenue, textviewTiming, textview_venue_upcoming, matchType, matchDetails, pointTable, schedule;

        LinearLayout parentUpcoming;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.team_image_a_live);
            imageView2 = itemView.findViewById(R.id.team_image_b_live);
            pinScore = itemView.findViewById(R.id.pin_icon);
            pinScoreAlready = itemView.findViewById(R.id.pin_icon_already);

            bellIcon = itemView.findViewById(R.id.bell_icon);

            parentUpcoming = itemView.findViewById(R.id.parentUpcoming);

            matchType = itemView.findViewById(R.id.upcomingMatchType);
            matchDetails = itemView.findViewById(R.id.textview_matchdetail);
            textViewId = itemView.findViewById(R.id.textview_count_predictions);
            textViewMatchDate = itemView.findViewById(R.id.textview_count_time);
            textViewTeam1 = itemView.findViewById(R.id.textview_team_a_live);
            textViewTeam2 = itemView.findViewById(R.id.textview_team_b_live);
            textViewMatchType = itemView.findViewById(R.id.textview_match_type);
            textViewMatchSeries = itemView.findViewById(R.id.textview_match_series);
            //textViewStatus = itemView.findViewById(R.id.textview_match_status);
            textview_venue_upcoming = itemView.findViewById(R.id.textview_venue_upcoming);
            textviewTiming = itemView.findViewById(R.id.textview_date_time);

            pointTable = itemView.findViewById(R.id.pointTable);
            schedule = itemView.findViewById(R.id.schedule);
            //textViewMatchStarted = itemView.findViewById(R.id.textview_matchstarted);


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
