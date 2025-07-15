package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemTvGuideBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BroadcasterItem

class TvGuideAdapter(
    var list: List<BroadcasterItem>,
    val context: Context
) : RecyclerView.Adapter<TvGuideAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemTvGuideBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTvGuideBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]
        holder.binding.key.setText(""+model.broadcastType)
        holder.binding.value.setText(""+model.value)
    }

    override fun getItemCount(): Int {
       return list.size
    }



}