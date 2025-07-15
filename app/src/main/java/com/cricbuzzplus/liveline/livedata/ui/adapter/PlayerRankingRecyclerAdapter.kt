package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemPlayerRankingBinding
import com.cricbuzzplus.liveline.livedata.response.PlayerRankingResponseItem

class PlayerRankingRecyclerAdapter(context: Context, private var mList: ArrayList<PlayerRankingResponseItem>) :
    RecyclerView.Adapter<PlayerRankingRecyclerAdapter.MyViewHolder>() {

    fun updateList(mList: ArrayList<PlayerRankingResponseItem>){
        this.mList = mList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemPlayerRankingBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {

        return MyViewHolder(ItemPlayerRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = mList[position]

        holder.binding.listItem = model
    }


    override fun getItemCount(): Int {
        return mList.size
    }


}
