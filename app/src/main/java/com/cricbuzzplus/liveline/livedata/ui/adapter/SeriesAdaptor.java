package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.livedata.response.SeriesListResponseItem;

import java.util.ArrayList;

public class SeriesAdaptor extends RecyclerView.Adapter<SeriesAdaptor.ViewHolder> {
    private ArrayList<SeriesListResponseItem> modelList;
    private Context context;
    Context appContext;
    //private AdapterView.OnItemClickListener listener;
    int pos = -1;

    public interface MyClickListener {
        void onItemClick(SeriesListResponseItem seriesListResponseItem);
    }

    private MyClickListener mMyClickListener;

    /*public SeriesAdaptor() {

    }*/

    //constructor define list and context
    public SeriesAdaptor(ArrayList<SeriesListResponseItem> modelList, Context context,MyClickListener myClickListener) {
        this.modelList = modelList;
        this.context = context;
        this.mMyClickListener = myClickListener;
    }

    public void updateList(ArrayList<SeriesListResponseItem> horizontalList){
        this.modelList = horizontalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeriesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == GroupDateYes){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match3_upcoming,parent,false);
//            return  new upcomingAdaptorApi.ViewHolder(v);
//        }else {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_live_match,parent,false);
//            return  new upcomingAdaptorApi.ViewHolder(v);
//        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_series_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        
        //liveScoreFragment = new TeamSqud();
        return new SeriesAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesAdaptor.ViewHolder holder, int position) {
        final SeriesListResponseItem fixturedata = modelList.get(position);

        RequestOptions requestOptions= new  RequestOptions();
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);

        String seriesids = fixturedata.getSeriesId().toString();
        String image = fixturedata.getImage();
        Glide.with(context).load(image).apply(requestOptions).circleCrop().into(holder.imageView);
        String end_date = fixturedata.getEndDate();
        String start_date = fixturedata.getStartDate();
        String series_date = fixturedata.getSeriesDate();
        holder.textViewSeriesDate.setText(series_date);

        String series = fixturedata.getSeries();
        holder.textViewSeriesName.setText(series);

        String total_matches = fixturedata.getTotalMatches().toString();
        holder.textViewTotalMatches.setText(total_matches+" Matches");

        String series_id = fixturedata.getSeriesId().toString();
        String series_name = fixturedata.getSeries();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the position of this Vh
                // int position = viewHolder.getAdapterPosition();
                if (mMyClickListener != null) mMyClickListener.onItemClick(modelList.get(position));
            }
        });
        //click on cardView Activity
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SeriesTabActivity.class);
                intent.putExtra("seriesId", series_id);
                intent.putExtra("seriesName", series_name);
                v.getContext().startActivity(intent);

            }
        });*/

        pos = position;

    }

//    private void dateTodayTomoro(String matchDate, ViewHolder holder) throws ParseException {
////        Calendar calendar1 = Calendar.getInstance();
////        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM");
////        String TodayDateStr = format1.format(calendar1.getTime());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
//        Date date1 = dateFormat.parse(matchDate);
//
//        Date datefromapi = null;
//        datefromapi = new SimpleDateFormat("dd-MM-yyyy").parse(matchDate);
//        if (DateUtils.isToday(datefromapi.getTime())){
//            String status1 = "Today";
//            holder.textViewMatchDate.setText(status1);
//        }else {
//
//        }
//    }

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
        public ImageView imageView;
        public TextView textViewSeriesName, textViewSeriesDate, textViewTotalMatches;
        public  CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_squad_player);
            cardView = itemView.findViewById(R.id.button_option_a);

            textViewSeriesName = itemView.findViewById(R.id.tv_series_name);
            textViewSeriesDate = itemView.findViewById(R.id.tv_series_date);
            textViewTotalMatches = itemView.findViewById(R.id.tv_total_matches);

        }
    }


}

