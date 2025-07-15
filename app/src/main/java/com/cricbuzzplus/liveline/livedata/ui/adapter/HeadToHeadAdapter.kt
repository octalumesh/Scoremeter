package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemHeadToHeadBinding
import com.cricbuzzplus.liveline.livedata.response.MatchesItem
import com.cricbuzzplus.liveline.livedata.response.RecentMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.newresponse.HeadToHeadTeam

class HeadToHeadAdapter(
    var list: ArrayList<MatchesItem>,
    val context: Context,
    val teamAShort: String,
    val teamAImg: String,
    val teamBShort: String,
    val teamBImg: String,
) : RecyclerView.Adapter<HeadToHeadAdapter.MyViewHolder>() {



    class MyViewHolder(val binding: ItemHeadToHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemHeadToHeadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.teamAName.setText(""+teamAShort)

        if (!model.teamAScore.isNullOrEmpty()) {
            holder.binding.teamAScore.setText("" + model.teamAScore)
            holder.binding.teamAOver.setText("(" + model.teamAOver + ")")
        }

        Glide.with(context).load(teamAImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamAImg)


        holder.binding.teamBName.setText(""+teamBShort)
        if (!model.teamBScore.isNullOrEmpty()) {
            holder.binding.teamBScore.setText("" + model.teamBScore)
            holder.binding.teamBOver.setText("(" + model.teamBOver + ")")
        }

        Glide.with(context).load(teamBImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamBImage)

        holder.binding.result.setText(""+model.result)

        holder.binding.textviewMatchSeries.setText(""+model.matchs)


    }

    override fun getItemCount(): Int {
        return list.size
    }


}