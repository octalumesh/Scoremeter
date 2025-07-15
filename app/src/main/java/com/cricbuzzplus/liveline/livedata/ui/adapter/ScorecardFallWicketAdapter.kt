package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemScoreFallWicketBinding
import com.cricbuzzplus.liveline.livedata.response.FallwicketItem

class ScorecardFallWicketAdapter(
    var list: ArrayList<FallwicketItem>,
    val context: Context
) : RecyclerView.Adapter<ScorecardFallWicketAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<FallwicketItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemScoreFallWicketBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemScoreFallWicketBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]
        holder.binding.playerName.setText(model.player.toString())
        holder.binding.score.setText(""+model.score+"-"+model.wicket)
        holder.binding.over.setText(""+model.over)

    }

    override fun getItemCount(): Int {
       return list.size
    }



}