package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemTeamComLastBinding
import com.cricbuzzplus.liveline.livedata.response.newresponse.TeamAMatchlistItem

class TeamMatchCompareListAdapter(
    var list: ArrayList<TeamAMatchlistItem>,
    val context: Context,
    var team :String
) : RecyclerView.Adapter<TeamMatchCompareListAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<TeamAMatchlistItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemTeamComLastBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTeamComLastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.teamAName.setText(""+model.teamAShort)
        holder.binding.teamAScore.setText(""+model.teamAScores)
        holder.binding.match.setText(""+model.matchs+", ${model.matchDate}")
        holder.binding.teamAOver.setText("(" + model.teamAOver + ")")

        Glide.with(context).load(model.teamAImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamAImg)


        holder.binding.teamBName.setText(""+model.teamBShort)
        holder.binding.teamBScore.setText(""+model.teamBScores)
        holder.binding.teamBOver.setText("(" + model.teamBOver + ")")

        Glide.with(context).load(model.teamBImg).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.teamBImage)

        holder.binding.result.setText(""+model.result)

        holder.binding.textviewMatchType.setText(""+model.matchType)
        holder.binding.textviewMatchSeries.setText(""+model.series)


        if (team.equals("one")) {

            if (model.finalresultteamA.equals("won", true)) {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.limeGreen))
                holder.binding.teamForm.text = "W"

            } else if (model.finalresultteamA.equals("loss", true)) {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                holder.binding.teamForm.text = "L"

            } else {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yelow_mild))
                holder.binding.teamForm.text = "D"

            }
        }else if (team.equals("two")) {

            if (model.finalresultteamB.equals("won", true)) {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.limeGreen))
                holder.binding.teamForm.text = "W"

            } else if (model.finalresultteamB.equals("loss", true)) {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                holder.binding.teamForm.text = "L"

            } else {

                holder.binding.teamForm.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yelow_mild))
                holder.binding.teamForm.text = "D"

            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}