package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemTeamRankingBinding
import com.cricbuzzplus.liveline.livedata.response.TeamRankingResponseItem

class TeamRankingRecyclerAdapter(context: Context, private var mList: ArrayList<TeamRankingResponseItem>) :
    RecyclerView.Adapter<TeamRankingRecyclerAdapter.MyViewHolder>() {

    fun updateList(mList: ArrayList<TeamRankingResponseItem>){
        this.mList = mList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemTeamRankingBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {

        return MyViewHolder(ItemTeamRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = mList[position]

        holder.binding.listItem = model
    }


    override fun getItemCount(): Int {
        return mList.size
    }


}
