package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesMapProtoItem

class SeriesCricBuzzAdapter(
    val list: ArrayList<SeriesMapProtoItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSeriesCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSeriesCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.dateSeries.setText(model.date.toString())

        holder.binding.recyclerSeries.adapter = SeriesListCricBuzzAdapter(model.series as ArrayList<SeriesItem>,context)

    }

    override fun getItemCount(): Int {
       return list.size
    }



}