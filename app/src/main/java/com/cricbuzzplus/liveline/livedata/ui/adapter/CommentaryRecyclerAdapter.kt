package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemCommentaryBinding
import com.cricbuzzplus.liveline.livedata.response.OverResponseItem

class CommentaryRecyclerAdapter(var context: Context,  var mList: ArrayList<OverResponseItem>) :
    RecyclerView.Adapter<CommentaryRecyclerAdapter.MyViewHolder>() {

    fun updateList(mList: ArrayList<OverResponseItem>){
        this.mList = mList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemCommentaryBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {

        return MyViewHolder(ItemCommentaryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data  = mList[position]



        if (!data.data?.team.isNullOrEmpty()) {
            holder.binding.relativeOverComplete.setVisibility(View.VISIBLE)
            holder.binding.overTitleMain.setText(data.data?.title.toString())
            holder.binding.overTitleRun.setText(data.data?.runs.toString() + " Runs")
            holder.binding.overTitleScore.setText( " " + data.data?.teamScore + "/" + data.data?.teamWicket)
            holder.binding.overTitleTeam.setText( "" + data.data?.team.toString())

            if (data.data?.teamWicket.equals("10")) {
                holder.binding.overPlayer1.setText("")
                holder.binding.overPlayer2.setText("")
            } else {
                holder.binding.overPlayer1.setText(data.data?.batsman1Name.toString() + "                " + data.data?.batsman1Runs + "( " + data.data?.batsman1Balls + " )")
                holder.binding.overPlayer2.setText(data.data?.batsman2Name.toString() + "                " + data.data?.batsman2Runs + "( " + data.data?.batsman2Balls + " )")
            }
            holder.binding.overBolwer.setText(
                data.data?.bolwerName.toString() + " " + data.data?.bolwerOvers + "-" + data.data?.bolwerMaidens + "-" + data.data?.bolwerRuns + "-" + data.data?.bolwerWickets
            )
            holder.binding.overDetail.setVisibility(View.GONE)
            holder.binding.viewCommen.setVisibility(View.GONE)
        } else {
            holder.binding.relativeOverComplete.setVisibility(View.GONE)
            holder.binding.overDetail.setVisibility(View.VISIBLE)
            holder.binding.viewCommen.setVisibility(View.VISIBLE)
            holder.binding.commentaryOver.setText(data.data?.overs)
            if (data.data?.wicket.equals("1")) {
                holder.binding.tvOverRunCommentry.setText("W")
                holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.red)))
                if (data.data?.runs.equals("0")) {
                    holder.binding.overRuns.setText("Out ")
                    holder.binding.overRuns.setTextColor(context.getResources().getColor(R.color.red))
                } else {
                    holder.binding.overRuns.setText("Out " + data.data?.runs.toString() + " Runs")
                    holder.binding.overRuns.setTextColor(context.getResources().getColor(R.color.red))
                }
            } else {
                if (data.data?.runs.equals("4")) {
                    holder.binding.overRuns.setText(data.data?.runs.toString() + " Runs")
                    holder.binding.overRuns.setTextColor(context.getResources().getColor(R.color.blue))

                    holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                    holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.blue)))

                } else if (data.data?.runs.equals("6")) {
                    holder.binding.overRuns.setText(data.data?.runs.toString() + " Runs")
                    holder.binding.overRuns.setTextColor(context.getResources().getColor(R.color.limeGreen))

                    holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                    holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.limeGreen)))

                } else {
                    holder.binding.overRuns.setTextColor(context.getResources().getColor(R.color.black))
                    holder.binding.overRuns.setText(data.data?.runs.toString() + " Runs")

                    holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.black))
                    holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.gray_main)))
                }
                holder.binding.tvOverRunCommentry.setText(data.data?.runs.toString())
            }
            holder.binding.overTitle.setText(", " + data.data?.title)
            holder.binding.overDescription.setText("       " + data.data?.description)

        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }


}
