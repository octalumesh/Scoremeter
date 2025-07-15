package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPredictionListLayoutBinding
import com.cricbuzzplus.liveline.livedata.response.newresponse.PredictionListResponseItem

class UserPredictionListAdapter(
    var list: ArrayList<PredictionListResponseItem>,
    val context: Context,
    val type: String
) : RecyclerView.Adapter<UserPredictionListAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<PredictionListResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemPredictionListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPredictionListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.textviewMatchSeries.setText(""+model.seriesName)
        holder.binding.textviewTeamA.setText(""+model.teamAShort)
        holder.binding.textviewTeamB.setText(""+model.teamBShort)
        holder.binding.textviewMatchType.setText(""+model.matchType)
        holder.binding.textviewDateTime.setText(""+model.matchDate)

        if (!model.predictPassFail.isNullOrEmpty()) {
            holder.binding.textviewPassFail.setText("" + model.predictPassFail.uppercase())

            if (model.predictPassFail.equals("pass")){
                holder.binding.relativeRecyclerviewPredictOdds.setBackgroundTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.green_predict)
                    )
                )
            } else if (model.predictPassFail.equals("fail")){
                holder.binding.relativeRecyclerviewPredictOdds.setBackgroundTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.red_predct)
                    )
                )
            }
        }else{
            holder.binding.relativeRecyclerviewPredictOdds.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.colorAccent)
                )
            )
          //  holder.binding.relativeRecyclerviewPredictOdds.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
        }

        if (type.equals("match",true)) {
            holder.binding.myTeam.setText("" + model.matchPredict)
        }else if (type.equals("toss",true)) {
            holder.binding.myTeam.setText("" + model.tossPredict)
        }


        Glide.with(context).load(model.teamAImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamImageA)
        Glide.with(context).load(model.teamBImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamImageB)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}