package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemTeamRankingCricBuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.RankItem
import com.cricbuzzplus.liveline.utils.Constants

class TeamRankingCricBuzzAdapter(
    val list: ArrayList<RankItem>,
    val context: Context
) : RecyclerView.Adapter<TeamRankingCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemTeamRankingCricBuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTeamRankingCricBuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.teamName.setText(model.name.toString())
        holder.binding.teamMatch.setText("Matches: "+model.matches)
        holder.binding.rank.setText(""+model.rank)
        holder.binding.points.setText(""+model.points)
        if (model.rating.isNullOrEmpty()){
            holder.binding.rating.setText("0")
        }else {
            holder.binding.rating.setText("" + model.rating)
        }

        Glide.with(context).load(""+Constants.cricbuzzImgFirst+model.imageId+Constants.cricbuzzImghigh).apply(requestOptions).into(holder.binding.teamImg)



    }

    override fun getItemCount(): Int {
       return list.size
    }



}