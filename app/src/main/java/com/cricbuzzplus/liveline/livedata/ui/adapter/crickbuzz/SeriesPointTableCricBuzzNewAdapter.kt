package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.utils.Constants

class SeriesPointTableCricBuzzNewAdapter(
    val list: ArrayList<PointsTableInfoItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesPointTableCricBuzzNewAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPointTableCricBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPointTableCricBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        Glide.with(context).load(Constants.cricbuzzImgFirst+model.teamImageId+Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.teamImg)

        holder.binding.team.setText(""+model.teamName)
        holder.binding.matchPlayed.setText(""+model.matchesPlayed)
        holder.binding.matchLost.setText(""+model.matchesLost)
        holder.binding.matchWin.setText(""+model.matchesWon)
        holder.binding.matchNoResult.setText(""+model.noRes)
        holder.binding.matchPoints.setText(""+model.points)
        holder.binding.matchNetRR.setText(""+model.nrr)



    }

    override fun getItemCount(): Int {
       return list.size
    }



}