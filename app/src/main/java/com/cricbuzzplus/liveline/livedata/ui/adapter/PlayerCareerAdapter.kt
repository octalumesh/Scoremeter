package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemPlayerCareerBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerCareerValuesItem

class PlayerCareerAdapter(
    var list: List<PlayerCareerValuesItem>,
    val context: Context
) : RecyclerView.Adapter<PlayerCareerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPlayerCareerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPlayerCareerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]
        holder.binding.playerType.setText(""+model.name?.uppercase())
        holder.binding.playerDebut.setText(""+model.debut)
        holder.binding.playerLast.setText(""+model.lastPlayed)
    }

    override fun getItemCount(): Int {
       return list.size
    }



}