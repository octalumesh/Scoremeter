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
import com.cricbuzzplus.liveline.databinding.ItemSeriesListCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BatsmenDataItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ScoreCardItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScorecardBatsmanCricBuzzAdapter(
    val list: ArrayList<BatsmenDataItem>,
    val context: Context,
) : RecyclerView.Adapter<ScorecardBatsmanCricBuzzAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemScoreBatsmanBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemScoreBatsmanBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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


            if (model.isCaptain == true && model.isKeeper == true) {
                holder.binding.playerName.setText("${model.batName} (c & wk)")
            }else if (model.isCaptain == true) {
                holder.binding.playerName.setText("${model.batName} (c)")
            }else if (model.isKeeper == true) {
                holder.binding.playerName.setText("${model.batName} (wk)")
            }else{
                holder.binding.playerName.setText("${model.batName}")
            }


            holder.binding.runs.setText("${model?.runs}")
            holder.binding.ballFaced.setText("${model?.balls}")
            holder.binding.fours.setText("${model?.fours}")
            holder.binding.sixes.setText("${model?.sixes}")
            holder.binding.strikeRate.setText("${model?.strikeRate}")
            holder.binding.outBy.setText("${model?.outDesc}")


            holder.binding.playerName.setOnClickListener {
                val intent = Intent(context, PlayerProfileActivity::class.java)
                intent.putExtra("playerName", model?.batName.toString())
                intent.putExtra("playerId", model?.batId)
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