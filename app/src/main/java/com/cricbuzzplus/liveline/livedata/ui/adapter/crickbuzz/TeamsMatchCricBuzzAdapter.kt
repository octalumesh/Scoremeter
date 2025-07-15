package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.databinding.ItemTeamsCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*

class TeamsMatchCricBuzzAdapter(
    val list: ArrayList<TeamMatchesDataItem>,
    val context: Context,
) : RecyclerView.Adapter<TeamsMatchCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemTeamsCricbuzzMatchesBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTeamsCricbuzzMatchesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.dateSeries.setText(model.matchDetailsMap?.key.toString())

        holder.binding.recyclerSeries.adapter = TeamsMatchListCricBuzzAdapter(model.matchDetailsMap?.match as ArrayList<ScheduleMatchItem>,context)

    }

    override fun getItemCount(): Int {
       return list.size
    }



}