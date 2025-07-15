package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSessionLiveLayoutBinding
import com.cricbuzzplus.liveline.livedata.response.SHistoryItem
import com.cricbuzzplus.liveline.livedata.response.TestSessions

class MatchSessionLiveAdapter(
    var list: ArrayList<SHistoryItem>,
    val context: Context
) : RecyclerView.Adapter<MatchSessionLiveAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<SHistoryItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemSessionLiveLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSessionLiveLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        if (model.over != null && !model.over.toString().isNullOrEmpty()) {
            holder.binding.sessionOvr.setText("Over " + model.over.toString())
        } else {
            holder.binding.sessionOvr.setText("Over -")
        }


        if ((model.sOMin != null && !model.sOMin.toString()
                .isNullOrEmpty()) && (model.sOMax != null && !model.sOMax.toString()
                .isNullOrEmpty())
        ) {
            holder.binding.sessionMin.setText(
                "" + model.sOMin.toString().toDouble().toInt() + "-" + model.sOMax.toString()
                    .toDouble().toInt()
            )
        } else {
            holder.binding.sessionMin.setText("-")
        }

        if ((model.sMin != null && !model.sMin.toString()
                .isNullOrEmpty()) && (model.sMax != null && !model.sMax.toString().isNullOrEmpty())
        ) {
            holder.binding.sessionMax.setText(
                "" + model.sMin.toString().toDouble().toInt() + "-" + model.sMax.toString()
                    .toDouble().toInt()
            )
        } else {
            holder.binding.sessionMax.setText("-")
        }


        if ((model.score != null && !model.score.toString()
                .isNullOrEmpty()) && (model.wicket != null && !model.wicket.toString()
                .isNullOrEmpty())
        ) {

            if (model.sOMin != null && !model.sOMin.toString().isNullOrEmpty()) {
                if (model.score.toString().toDouble().toInt() == model.sOMin.toString().toDouble()
                        .toInt() || model.score.toString().toDouble()
                        .toInt() > model.sOMin.toString().toDouble().toInt()
                ) {
                    holder.binding.sessionPass.setCompoundDrawablesWithIntrinsicBounds(null,null,context.resources.getDrawable(R.drawable.ic_baseline_check_24),null)
                }else{
                    holder.binding.sessionPass.setCompoundDrawablesWithIntrinsicBounds(null,null,context.resources.getDrawable(R.drawable.close_ic_red),null)
                }
            }

            holder.binding.sessionPass.setText(
                "" + model.score.toString().toDouble().toInt() + "/" + model.wicket.toString()
                    .toDouble().toInt()
            )
        } else {
            holder.binding.sessionPass.setText("-/-")
        }

        // holder.binding.sessionPass.setText(""+model.pass+"/"+model.wkt+"Wk")
        // holder.binding.sessionWkt.setText(""+model.wkt+"Wk")

        if ((model.minRate != null && !model.minRate.toString()
                .isNullOrEmpty()) && (model.maxRate != null && !model.maxRate.toString()
                .isNullOrEmpty())
        ) {

            if ((model.favTeam != null && !model.favTeam.toString().isNullOrEmpty())) {
                holder.binding.sessionRate.setText("(${model.favTeam.toString()}) " + model.minRate.toString() + "-" + model.maxRate.toString())
            } else {
                holder.binding.sessionRate.setText("" + model.minRate.toString() + "-" + model.maxRate.toString())
            }

        } else {
            holder.binding.sessionRate.setText("-")
        }


        /*if (model.pass?.toInt()!! >= model.open?.toInt()!!) {
            holder.binding.sessionPass.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context!!, R.color.limeGreen
                    )
                )
            )
        } else {

            holder.binding.sessionPass.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context!!, R.color.red
                    )
                )
            )
        }*/


    }

    override fun getItemCount(): Int {
        return list.size
    }


}