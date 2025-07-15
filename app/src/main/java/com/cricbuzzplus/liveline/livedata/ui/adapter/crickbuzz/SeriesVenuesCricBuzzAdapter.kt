package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesVenuesCricBinding
import com.cricbuzzplus.liveline.databinding.ItemSquadCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.StadiumActivity
import com.cricbuzzplus.liveline.utils.Constants

class SeriesVenuesCricBuzzAdapter(
    val list: ArrayList<SeriesVenueItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesVenuesCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSeriesVenuesCricBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSeriesVenuesCricBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.drawable.stadium_venue)
        requestOptions.error(R.drawable.stadium_venue)

        holder.binding.venueName.setText(""+model.ground)
        holder.binding.venueCity.setText(""+model.city)


        Glide.with(context)
            .load("" + Constants.cricbuzzImgFirst + model.imageId + Constants.cricbuzzImghigh)
            .apply(requestOptions).into(holder.binding.venueImg)


        holder.binding.parent.setOnClickListener {
            val intent = Intent(context, StadiumActivity::class.java)
            intent.putExtra("stadiumName",model?.ground.toString())
            intent.putExtra("stadiumId",model?.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}