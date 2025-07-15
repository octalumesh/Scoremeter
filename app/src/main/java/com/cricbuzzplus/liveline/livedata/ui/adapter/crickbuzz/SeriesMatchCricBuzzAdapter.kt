package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*

class SeriesMatchCricBuzzAdapter(
    val list: ArrayList<SeriesScheduleMatchDetailsItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesMatchCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSeriesCricbuzzMatchesBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSeriesCricbuzzMatchesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.dateSeries.setText(model.matchDetailsMap?.key.toString())

        holder.binding.recyclerSeries.adapter = SeriesMatchListCricBuzzAdapter(model.matchDetailsMap?.match as ArrayList<SeriesScheduleMatchItem>,context)

    }

    override fun getItemCount(): Int {
       return list.size
    }



}