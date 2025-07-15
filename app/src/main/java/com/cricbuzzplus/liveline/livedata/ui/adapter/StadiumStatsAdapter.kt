package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemStadiumStatsBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.VenueStatsItem

class StadiumStatsAdapter(
    var list: List<VenueStatsItem>,
    val context: Context
) : RecyclerView.Adapter<StadiumStatsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemStadiumStatsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemStadiumStatsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]
        holder.binding.key.setText(""+model.key)
        holder.binding.value.setText(""+model.value)

        if (position == (list.size- 1)){
            holder.binding.viewse.visibility = View.GONE
        }else{
            holder.binding.viewse.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }



}