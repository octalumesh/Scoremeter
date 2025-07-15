package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSCorecardCricbuzzMainBinding
import com.cricbuzzplus.liveline.databinding.ItemScoreBatsmanBinding
import com.cricbuzzplus.liveline.databinding.ItemScoreBowlerBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesListCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BatsmenDataItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BowlersDataItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ScoreCardItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScorecardBowlerCricBuzzAdapter(
    val list: ArrayList<BowlersDataItem>,
    val context: Context,
) : RecyclerView.Adapter<ScorecardBowlerCricBuzzAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemScoreBowlerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemScoreBowlerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

      //  holder.binding.seriesName.setText(model.name.toString())


        try {



            holder.binding.bowlerName.setText("${model.bowlName}")
            holder.binding.runs.setText("${model?.runs}")
            holder.binding.over.setText("${model?.overs}")
            holder.binding.maiden.setText("${model?.maidens}")
            holder.binding.wicket.setText("${model?.wickets}")
            holder.binding.economy.setText("${model?.economy}")


            holder.binding.bowlerName.setOnClickListener {
                val intent = Intent(context, PlayerProfileActivity::class.java)
                intent.putExtra("playerName", model?.bowlName.toString())
                intent.putExtra("playerId", model?.bowlerId)
                context.startActivity(intent)
            }


        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}