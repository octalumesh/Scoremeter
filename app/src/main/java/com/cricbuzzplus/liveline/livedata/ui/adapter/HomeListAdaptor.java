package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem;
import com.cricbuzzplus.liveline.livedata.model.MatchListModel;
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity;
import com.cricbuzzplus.liveline.livedata.ui.activity.SeriesTabActivity;
import com.cricbuzzplus.liveline.livedata.ui.interfaces.HomeMatchClickInterface;
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterface;
import com.cricbuzzplus.liveline.livedata.ui.services.PinTopInterface;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class HomeListAdaptor extends RecyclerView.Adapter<HomeListAdaptor.ViewHolder> {
    private List<HomeMatchResponseItem> modelList;
    Context context;
    Context appContext;
    //private AdapterView.OnItemClickListener listener;
    int pos = -1;
    boolean matchStatus1 = false;
    boolean matchFinished = false;
    PinTopInterface pinTopInterface;

    MatchListModel matchPinModel;

    NotificationInterface onNotifyInterface;
    HomeMatchClickInterface onHomeInterface;

    //  private ArrayList<String> commonDateList = new ArrayList<>();

    private int limitItems = 10;
//    boolean SameDateAct = false;
//    public static final int GroupDateNo = 0;
//    public static final int GroupDateYes = 1;

    //constructor define list and context
    public HomeListAdaptor(List<HomeMatchResponseItem> modelList, Context context, PinTopInterface pinTopInterface, MatchListModel matchPinModel, NotificationInterface onNotifyInterface, HomeMatchClickInterface onHomeInterface) {

        this.modelList = modelList;
        this.context = context;
        this.pinTopInterface = pinTopInterface;
        this.matchPinModel = matchPinModel;
        this.onNotifyInterface = onNotifyInterface;
        this.onHomeInterface = onHomeInterface;
    }

    public void updateDta(List<HomeMatchResponseItem> modelList, MatchListModel matchPinModel) {
        this.modelList.clear();
        this.modelList = modelList;
        this.matchPinModel = matchPinModel;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public HomeListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);

        return new HomeListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListAdaptor.ViewHolder holder, int position) {
        final HomeMatchResponseItem fixturedata = modelList.get(position);

        String matchId = fixturedata.getMatchId().toString();
        String match_time = fixturedata.getMatchTime();
        String resultss = fixturedata.getResult();

        RequestOptions requestOptions = new RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        RequestOptions requestOptions1 = new RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions1.placeholder(R.mipmap.ic_launcher_round);
        requestOptions1.error(R.mipmap.ic_launcher_round);


        String matchStatus = fixturedata.getMatchStatus();


        holder.pinScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pinTopInterface.onPinClick(fixturedata.getMatchId(), true);
                //  fixturedata.setPinScore(true);

            }
        });

        holder.pinScoreAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pinTopInterface.onPinClick(fixturedata.getMatchId(), false);
                //  fixturedata.setPinScore(true);

            }
        });

        if (matchPinModel != null) {
            if (matchPinModel.getMatchList() != null && !matchPinModel.getMatchList().isEmpty()) {

                if (matchPinModel.getMatchList().contains(fixturedata.getMatchId())) {
                    // holder.pinScoreAlready.setVisibility(View.VISIBLE);
                    holder.pinScore.setVisibility(View.GONE);
                } else {
                    // holder.pinScoreAlready.setVisibility(View.GONE);
                    //  holder.pinScore.setVisibility(View.VISIBLE);
                }

            } else {
                holder.pinScoreAlready.setVisibility(View.GONE);
                //  holder.pinScore.setVisibility(View.VISIBLE);
            }
        } else {
            holder.pinScoreAlready.setVisibility(View.GONE);
            //  holder.pinScore.setVisibility(View.VISIBLE);
        }


        if (matchStatus.equals("Live")) {
            // holder.lottieAnim_live.setVisibility(View.VISIBLE);
            // holder.textViewStatus.setVisibility(View.VISIBLE);
            holder.textview_date_time.setVisibility(View.VISIBLE);
            // holder.textview_status.setVisibility(View.GONE);
            // holder.lottieAnim_live.setAnimation(R.raw.live_red_anim);
            holder.textview_result_recent.setVisibility(View.GONE);
            holder.relative_recyclerview_predict_odds.setVisibility(View.VISIBLE);
            holder.textViewFavTeam.setVisibility(View.VISIBLE);
            holder.linearMatchHighlight.setVisibility(View.GONE);
            holder.textViewOddsMin.setVisibility(View.VISIBLE);
            holder.textViewOddsMax.setVisibility(View.VISIBLE);
            holder.bellIcon.setVisibility(View.VISIBLE);
            //holder.textviewTiming.setText("• " + matchStatus);
            // holder.textview_date_time.setText(matchStatus);
            //   holder.textview_status.setText(matchStatus);

            if (fixturedata.getToss() != null) {
                // holder.resultVanue.setText(fixturedata.getToss());
                holder.resultVanue.setText("");
                // holder.resultVanue.setTextColor(context.getResources().getColor(R.color.red));
            }

            holder.textview_date_time.setText(fixturedata.getVenue());

        } else if (matchStatus.equals("Finished")) {     //Upcoming   Finished
            //holder.textviewTiming.setText(resultss);
            // holder.lottieAnim_live.setVisibility(View.GONE);
            //  holder.textViewStatus.setVisibility(View.GONE);
            holder.textview_date_time.setVisibility(View.VISIBLE);
            // holder.textview_status.setVisibility(View.VISIBLE);
            // holder.relative_recyclerview_predict_odds.setVisibility(View.GONE);
            // holder.textViewFavTeam.setVisibility(View.GONE);
            holder.linearMatchHighlight.setVisibility(View.GONE);
            holder.bellIcon.setVisibility(View.GONE);
            //holder.textViewOddsMin.setVisibility(View.GONE);
            //holder.textViewOddsMax.setVisibility(View.GONE);
            holder.textview_result_recent.setVisibility(View.GONE);
            holder.textview_result_recent.setText(resultss);
            holder.textview_date_time.setText(fixturedata.getVenue());

            if (fixturedata.getResult() != null) {
                holder.resultVanue.setText(fixturedata.getResult());
                holder.resultVanue.setTextColor(context.getResources().getColor(R.color.bluene1));
            }

        } else if (matchStatus.equals("Upcoming")) {
            //  holder.lottieAnim_live.setVisibility(View.GONE);
            //   holder.textViewStatus.setVisibility(View.GONE);
            holder.textview_date_time.setVisibility(View.VISIBLE);
            holder.linearMatchHighlight.setVisibility(View.GONE);
            // holder.textview_status.setVisibility(View.VISIBLE);
            holder.textview_result_recent.setVisibility(View.GONE);
            holder.textViewFavTeam.setVisibility(View.VISIBLE);
            holder.textViewOddsMin.setVisibility(View.VISIBLE);
            holder.textViewOddsMax.setVisibility(View.VISIBLE);
            holder.bellIcon.setVisibility(View.VISIBLE);
            holder.relative_recyclerview_predict_odds.setVisibility(View.VISIBLE);

            holder.textview_date_time.setText(fixturedata.getMatchDate() + "\n" + fixturedata.getMatchTime());

            holder.resultVanue.setText(fixturedata.getMatchDate() + ", " + fixturedata.getMatchTime());
            holder.resultVanue.setTextColor(context.getResources().getColor(R.color.orange));

        }
        //String tosss = fixturedata.get
        //holder.textViewId.setText(fixturedata.getMatch_id());
        String matchDate = fixturedata.getMatchDate();
        //dateTodayTomoro(matchDate, holder);
        holder.textViewMatchDate.setText(fixturedata.getMatchDate());
        // holder.textview_status.setText(fixturedata.getMatchStatus());

        if (fixturedata.getSMax() != null) {
            holder.tv_home_session_max.setText("" + fixturedata.getSMax());
        }

        if (fixturedata.getSMin() != null) {
            holder.tv_home_session_min.setText("" + fixturedata.getSMin());
        }


        if (fixturedata.getFavTeam() != null && !fixturedata.getFavTeam().isEmpty()) {
            if (fixturedata.getResult() != null && !fixturedata.getResult().isEmpty()) {
                holder.linearOdds.setVisibility(View.GONE);
            } else {
                holder.linearOdds.setVisibility(View.VISIBLE);
            }
        } else {
            holder.linearOdds.setVisibility(View.GONE);
        }


        holder.textViewTeam1.setText(fixturedata.getTeamAShort());
        holder.textViewTeam2.setText(fixturedata.getTeamBShort());
        holder.textViewMatchType.setText(fixturedata.getMatchType());
        holder.textViewMatchSeries.setText(fixturedata.getMatchs() + ", " + fixturedata.getSeries());
        //holder.textviewTiming.setText(fixturedata.getMatchTime());

        holder.textViewFavTeam.setText(fixturedata.getFavTeam());
        String min_rate1 = fixturedata.getMinRate();
        String max_rate1 = fixturedata.getMaxRate();
        // holder.textViewStatus.setText(fixturedata.getMatchStatus());
        Glide.with(context).load(fixturedata.getTeamAImg()).apply(requestOptions).into(holder.imageView);
        Glide.with(context).load(fixturedata.getTeamBImg()).apply(requestOptions1).into(holder.imageView2);


        if (fixturedata.getMatchStatus().equals("Upcoming")) {
            holder.teamAOvers.setVisibility(View.GONE);
            holder.teamBOvers.setVisibility(View.GONE);
            holder.teamAScores.setVisibility(View.GONE);
            holder.teamBScores.setVisibility(View.GONE);
        } else {
            holder.teamAOvers.setVisibility(View.VISIBLE);
            holder.teamBOvers.setVisibility(View.VISIBLE);
            holder.teamAScores.setVisibility(View.VISIBLE);
            holder.teamBScores.setVisibility(View.VISIBLE);

           /* String firstInning = "0";
            String secondInning = "0";
            String thirdInning = "0";
            String fourthInning = "0";

            String firstInningWicket = "0";
            String secondInningWicket = "0";
            String thirdInningWicket = "0";
            String fourthInningWicket = "0";

            String firstInningOver = "0";
            String secondInningOver = "0";
            String thirdInningOver = "0";
            String fourthInningOver = "0";

            boolean teamA = false;
            boolean teamB = false;

            if (fixturedata.getTeamAScore() != null) {

                if (fixturedata.getTeamAScore().getJsonMember1() != null) {
                    teamA = true;
                    firstInning = "" + fixturedata.getTeamAScore().getJsonMember1().getScore();
                    firstInningWicket = "" + fixturedata.getTeamAScore().getJsonMember1().getWicket();
                    firstInningOver = "" + fixturedata.getTeamAScore().getJsonMember1().getOver();

                }

                if (fixturedata.getTeamAScore().getJsonMember2() != null) {
                    secondInning = "" + fixturedata.getTeamAScore().getJsonMember2().getScore();
                    secondInningWicket = "" + fixturedata.getTeamAScore().getJsonMember2().getWicket();
                    secondInningOver = "" + fixturedata.getTeamAScore().getJsonMember2().getOver();
                }
            }
            if (fixturedata.getTeamBScore() != null) {

                if (fixturedata.getTeamBScore().getJsonMember1() != null) {
                    teamB = true;
                    firstInning = "" + fixturedata.getTeamBScore().getJsonMember1().getScore();
                    firstInningWicket = "" + fixturedata.getTeamBScore().getJsonMember1().getWicket();
                    firstInningOver = "" + fixturedata.getTeamBScore().getJsonMember1().getOver();

                }

                if (fixturedata.getTeamBScore().getJsonMember2() != null) {
                    secondInning = "" + fixturedata.getTeamBScore().getJsonMember2().getScore();
                    secondInningWicket = "" + fixturedata.getTeamBScore().getJsonMember2().getWicket();
                    secondInningOver = "" + fixturedata.getTeamBScore().getJsonMember2().getOver();
                }
            }

            if (fixturedata.getMatchType().equals("Test")) {

                if (fixturedata.getTeamAScore() != null) {
                    if (fixturedata.getTeamAScore().getJsonMember3() != null) {
                        thirdInning = "" + fixturedata.getTeamAScore().getJsonMember3().getScore();
                        thirdInningWicket = "" + fixturedata.getTeamAScore().getJsonMember3().getWicket();
                        thirdInningOver = "" + fixturedata.getTeamAScore().getJsonMember3().getOver();
                    }

                    if (fixturedata.getTeamAScore().getJsonMember4() != null) {
                        fourthInning = "" + fixturedata.getTeamAScore().getJsonMember4().getScore();
                        fourthInningWicket = "" + fixturedata.getTeamAScore().getJsonMember4().getWicket();
                        fourthInningOver = "" + fixturedata.getTeamAScore().getJsonMember4().getOver();
                    }
                }

                if (fixturedata.getTeamBScore() != null) {

                    if (fixturedata.getTeamBScore().getJsonMember3() != null) {
                        thirdInning = "" + fixturedata.getTeamBScore().getJsonMember3().getScore();
                        thirdInningWicket = "" + fixturedata.getTeamBScore().getJsonMember3().getWicket();
                        thirdInningOver = "" + fixturedata.getTeamBScore().getJsonMember3().getOver();
                    }

                    if (fixturedata.getTeamBScore().getJsonMember4() != null) {
                        fourthInning = "" + fixturedata.getTeamBScore().getJsonMember4().getScore();
                        fourthInningWicket = "" + fixturedata.getTeamBScore().getJsonMember4().getWicket();
                        fourthInningOver = "" + fixturedata.getTeamBScore().getJsonMember4().getOver();
                    }
                }
            }


            if (fixturedata.getMatchType().equals("Test")) {
                if (teamA == true) {
                    if (thirdInning.equals("0")) {
                        holder.teamAScores.setText(firstInning + "/" + firstInningWicket);
                        holder.teamAOvers.setText(firstInningOver + " Over");
                    } else {
                        holder.teamAScores.setText(firstInning + "/" + firstInningWicket + " & \n" + thirdInning + "/" + thirdInningWicket);
                        holder.teamAOvers.setText(firstInningOver + " & " + thirdInningOver + " Over");
                    }
                    if (secondInning.equals("0")) {
                        holder.teamBScores.setText("");
                        holder.teamBOvers.setVisibility(View.GONE);
                    } else if (fourthInning.equals("0")) {
                        holder.teamBOvers.setVisibility(View.VISIBLE);
                        holder.teamBScores.setText(secondInning + "/" + secondInningWicket);
                        holder.teamBOvers.setText(secondInningOver + " Over");
                    } else {
                        holder.teamBOvers.setVisibility(View.VISIBLE);
                        holder.teamBScores.setText(secondInning + "/" + secondInningWicket + " & \n" + fourthInning + "/" + fourthInningWicket);
                        holder.teamBOvers.setText(secondInningOver + " & " + fourthInningOver + " Over");
                    }
                }

                if (teamB == true) {
                    if (thirdInning.equals("0")) {
                        holder.teamBScores.setText(firstInning + "/" + firstInningWicket);
                        holder.teamBOvers.setText(firstInningOver + " Over");
                    } else {
                        holder.teamBScores.setText(firstInning + "/" + firstInningWicket + " & \n" + thirdInning + "/" + thirdInningWicket);
                        holder.teamBOvers.setText(firstInningOver + " & " + thirdInningOver + " Over");
                    }
                    if (secondInning.equals("0")) {
                        holder.teamAScores.setText("");
                        holder.teamAOvers.setVisibility(View.GONE);
                    } else if (fourthInning.equals("0")) {
                        holder.teamAOvers.setVisibility(View.VISIBLE);
                        holder.teamAScores.setText(secondInning + "/" + secondInningWicket);
                        holder.teamAOvers.setText(secondInningOver + " Over");
                    } else {
                        holder.teamAOvers.setVisibility(View.VISIBLE);
                        holder.teamAScores.setText(secondInning + "/" + secondInningWicket + " & \n" + fourthInning + "/" + fourthInningWicket);
                        holder.teamAOvers.setText(secondInningOver + " & " + fourthInningOver + " Over");
                    }
                }

            } else {

                if (teamA == true) {
                    holder.teamAScores.setText(firstInning + "/" + firstInningWicket);
                    holder.teamAOvers.setText(firstInningOver + " Over");
                    if (secondInning.equals("0")) {
                        holder.teamBScores.setText("");
                        holder.teamBOvers.setVisibility(View.GONE);
                    } else {
                        holder.teamBOvers.setVisibility(View.VISIBLE);
                        holder.teamBScores.setText(secondInning + "/" + secondInningWicket);
                        holder.teamBOvers.setText(secondInningOver + " Over");
                    }
                }

                if (teamB == true) {
                    holder.teamBScores.setText(firstInning + "/" + firstInningWicket);
                    holder.teamBOvers.setText(firstInningOver + " Over");
                    if (secondInning.equals("0")) {
                        holder.teamAScores.setText("");
                        holder.teamAOvers.setVisibility(View.GONE);
                    } else {
                        holder.teamAOvers.setVisibility(View.VISIBLE);
                        holder.teamAScores.setText(secondInning + "/" + secondInningWicket);
                        holder.teamAOvers.setText(secondInningOver + " Over");
                    }
                }


            }*/


            if (!fixturedata.getTeamAScores().isEmpty()) {
                holder.teamAScores.setText(fixturedata.getTeamAScores());
                holder.teamAOvers.setText(fixturedata.getTeamAOver());
            } else {
                if (fixturedata.getMatchStatus().equalsIgnoreCase("Finished")) {
                    holder.teamAScores.setText("");
                    holder.teamAOvers.setText("");
                } else {
                    holder.teamAScores.setText("Yet to Bat");
                    holder.teamAOvers.setText("");
                }
            }

            if (!fixturedata.getTeamBScores().isEmpty()) {
                holder.teamBScores.setText(fixturedata.getTeamBScores());
                holder.teamBOvers.setText(fixturedata.getTeamBOver());
            } else {
                if (fixturedata.getMatchStatus().equalsIgnoreCase("Finished")) {
                    holder.teamBScores.setText("");
                    holder.teamBOvers.setText("");
                } else {
                    holder.teamBOvers.setText("");
                    holder.teamBScores.setText("Yet to Bat");
                }

            }
        }


        ////odds 1.52 remove1. and show only 52

        //String TestMin_rate = String.valueOf(1.9);
        try {
            // get this trick from url - https://stackoverflow.com/questions/3732790/android-split-string/3732820
            StringTokenizer tokens = new StringTokenizer(min_rate1, ".");
            String first = tokens.nextToken();// this will contain "Fruit"
            String second = tokens.nextToken();// this will contain " they taste good"
            int secondInt = second.length();
            if (secondInt == 1) {
                holder.textViewOddsMin.setText(second + "0");
            } else {
                //Toast.makeText(mContext, "not 1", Toast.LENGTH_SHORT).show();
                holder.textViewOddsMin.setText(second);
            }
        } catch (Exception e) {
            holder.textViewOddsMin.setText(fixturedata.getMinRate());
        }

        try {
            // get this trick from url - https://stackoverflow.com/questions/3732790/android-split-string/3732820
            StringTokenizer tokensMax = new StringTokenizer(max_rate1, ".");
            String first = tokensMax.nextToken();// this will contain "Fruit"
            String secondMax = tokensMax.nextToken();// this will contain " they taste good"
            int secondInt = secondMax.length();
            if (secondInt == 1) {
                holder.textViewOddsMax.setText(secondMax + "0");
            } else {
                //Toast.makeText(mContext, "not 1", Toast.LENGTH_SHORT).show();
                holder.textViewOddsMax.setText(secondMax);
            }
        } catch (Exception e) {
            holder.textViewOddsMax.setText(fixturedata.getMaxRate());
        }
        ///////////////////////////////////////////////////////////

        holder.bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotifyInterface.onClickNotify(fixturedata);
            }
        });

        //click on cardView Activity
        holder.matchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                intent.putExtra("result", fixturedata.getResult());
                intent.putExtra("matchNo", fixturedata.getMatchs());
                intent.putExtra("series", fixturedata.getSeries());
                v.getContext().startActivity(intent);


            }
        });


        holder.pointTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onHomeInterface.onClick(fixturedata, 1);

                /*Intent intent = new Intent(context, SeriesTabActivity.class);
                intent.putExtra("seriesId", fixturedata.getSeriesId());
                intent.putExtra("seriesName", fixturedata.getSeries());
                intent.putExtra("teamAId", 0);
                intent.putExtra("teamBId", 0);
                intent.putExtra("index", 1);
                context.startActivity(intent);*/
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeInterface.onClick(fixturedata, 0);
                /*Intent intent = new Intent(context, SeriesTabActivity.class);
                intent.putExtra("seriesId", fixturedata.getSeriesId());
                intent.putExtra("seriesName", fixturedata.getSeries());
                intent.putExtra("teamAId", fixturedata.getTeamAId());
                intent.putExtra("teamBId", fixturedata.getTeamBId());
                intent.putExtra("index", 0);
                context.startActivity(intent);*/
            }
        });

        pos = position;

    }

    @SuppressLint("NewApi")
    private String changeDate(String match_date) {

        return getFormattedDate(match_date);
    }

    public String getFormattedDate(String date) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(getTimeStamp(date));

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "dd-mmm";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("dd mmm", smsTime).toString();
        }
    }

    private long getTimeStamp(String getDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mmm");
        try {
            Date date = simpleDateFormat.parse(getDate);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // final image cardView  ,arshad;
        public ImageView imageView, imageView2, bellIcon, pinScore, pinScoreAlready;
        public TextView textViewMatchDate, textViewTeam1, textViewTeam2, textViewMatchType, textViewMatchSeries,
                textViewSquad, textViewStatus, textViewOddsMin, textViewOddsMax, textViewFavTeam,
                textview_date_time, teamAScores, teamBScores, teamAOvers, teamBOvers, textview_status, tv_home_session_max, tv_home_session_min, pointTable, schedule, resultVanue;
        public RelativeLayout relativeHomelistPrediction, relative_recyclerview_predict_odds;
        public TextView textview_result_recent;
        // public LottieAnimationView lottieAnim_live;
        public LinearLayout matchParent, linearMatchHighlight, linearOdds;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //   lottieAnim_live = (LottieAnimationView) itemView.findViewById(R.id.lottieanimationview_live);  arshad

            resultVanue = itemView.findViewById(R.id.resultVanue);
            imageView = itemView.findViewById(R.id.team_image_a_live);
            bellIcon = itemView.findViewById(R.id.bell_icon);
            imageView2 = itemView.findViewById(R.id.team_image_b_live);
            pinScore = itemView.findViewById(R.id.pin_icon);
            pinScoreAlready = itemView.findViewById(R.id.pin_icon_already);

            matchParent = itemView.findViewById(R.id.matchParent);
            teamAScores = itemView.findViewById(R.id.tv_team_a_score);
            teamBScores = itemView.findViewById(R.id.tv_team_b_score);
            teamAOvers = itemView.findViewById(R.id.teamAOvers);
            teamBOvers = itemView.findViewById(R.id.teamBOvers);

            textview_date_time = itemView.findViewById(R.id.textview_date_time);
            linearMatchHighlight = itemView.findViewById(R.id.linearMatchHighlight);
            // textview_status = itemView.findViewById(R.id.textview_status);
            tv_home_session_max = itemView.findViewById(R.id.tv_home_session_max);
            tv_home_session_min = itemView.findViewById(R.id.tv_home_session_min);
            //cardView = itemView.findViewById(R.id.cardview_recycler);
            //textViewId = itemView.findViewById(R.id.textview_count_predictions);
            textViewMatchDate = itemView.findViewById(R.id.textview_count_time);
            textViewTeam1 = itemView.findViewById(R.id.textview_team_a_live);
            textViewTeam2 = itemView.findViewById(R.id.textview_team_b_live);
            textViewMatchType = itemView.findViewById(R.id.textview_match_type);
            textViewMatchSeries = itemView.findViewById(R.id.textview_match_series);
            // textViewStatus = itemView.findViewById(R.id.textview_count_predictions);
            pointTable = itemView.findViewById(R.id.pointTable);
            schedule = itemView.findViewById(R.id.schedule);
            //textViewVenue = itemView.findViewById(R.id.tv_home_pred);
            // textviewTiming = itemView.findViewById(R.id.textview_count_predictions);
            //textViewMatchStarted = itemView.findViewById(R.id.textview_matchstarted);
            textViewOddsMin = itemView.findViewById(R.id.tv_home_odds1);
            textViewOddsMax = itemView.findViewById(R.id.tv_home_odds2);
            textViewFavTeam = itemView.findViewById(R.id.tv_home_favteam);


            linearOdds = itemView.findViewById(R.id.linearOdds);

            relativeHomelistPrediction = itemView.findViewById(R.id.relative_prediction_homelist);
            relative_recyclerview_predict_odds = itemView.findViewById(R.id.relative_prediction_homelist);

            textview_result_recent = itemView.findViewById(R.id.textview_result_recent);

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

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }
}
