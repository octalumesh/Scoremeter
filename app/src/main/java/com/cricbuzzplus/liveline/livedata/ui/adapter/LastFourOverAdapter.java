package com.cricbuzzplus.liveline.livedata.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.databinding.ItemLast4OverBinding;
import com.cricbuzzplus.liveline.livedata.response.Last4OverItem;

import java.util.List;


public class LastFourOverAdapter extends RecyclerView.Adapter<LastFourOverAdapter.ViewHolder> {

    Context context;
    List<Last4OverItem>  mList;
    LatestBallsAdaptor adapter2;

    public LastFourOverAdapter(Context context, List<Last4OverItem>  mList) {
        this.context = context;
        this.mList = mList;
    }

    public void updateList(List<Last4OverItem>  mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLast4OverBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_last4_over, parent, false);

      //  View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_runs, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Last4OverItem dataItem = mList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);


        holder.binding.over.setText("Over "+dataItem.getOver());
        holder.binding.overRun.setText(" = "+dataItem.getRuns()+" Runs ");

        if (dataItem.getBalls() != null) {
            List<String> jsonArrayLastBall = dataItem.getBalls();

            //if (adapter2 == null) {
                adapter2 = new LatestBallsAdaptor(jsonArrayLastBall);
                holder.binding.recyclerOverRuns.setAdapter(adapter2);
                holder.binding.recyclerOverRuns.scrollToPosition(adapter2.getItemCount() - 1);
           /* } else {
                adapter2.updateList(jsonArrayLastBall);
                //binding.recyclerviewOverLastball.scrollToPosition(adapter2?.itemCount!! - 1)
            }*/
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemLast4OverBinding binding;

        public ViewHolder(ItemLast4OverBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
