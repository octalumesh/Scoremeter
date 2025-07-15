package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.*
import com.cricbuzzplus.liveline.livedata.response.ProjectedScoreItem

class ProjectedScoreAdapter(
    var list: List<ProjectedScoreItem>,
    val context: Context
) : RecyclerView.Adapter<ProjectedScoreAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemProjectedScoreBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemProjectedScoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.getBinding().model=list[position]
        val model = list[position]


        holder.binding.over.setText(""+model?.over.toString().toDouble().toInt()+" Over")
        holder.binding.textviewProjectedRateA1.setText(""+model?.curRateScore)
        holder.binding.textviewProjectedRateA2.setText(""+model?.curRate1Score)
        holder.binding.textviewProjectedRateA3.setText(""+model?.curRate2Score)
        holder.binding.textviewProjectedRateA4.setText(""+model?.curRate3Score)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}