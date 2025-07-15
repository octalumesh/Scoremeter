package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemTeamFormLastFiveBinding

class TeamTossCompareAdapter(
    var list: ArrayList<String>,
    val context: Context,
) : RecyclerView.Adapter<TeamTossCompareAdapter.MyViewHolder>() {



    class MyViewHolder(val binding: ItemTeamFormLastFiveBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTeamFormLastFiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        if (model.equals("W",true)) {

            holder.binding.teamForm.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.limeGreen))
            holder.binding.teamForm.text = "W"

        } else if (model.equals("L",true)) {

            holder.binding.teamForm.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            holder.binding.teamForm.text = "L"

        } else {

            holder.binding.teamForm.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yelow_mild))
            holder.binding.teamForm.text = "D"

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}