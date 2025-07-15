package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSessionLiveLayoutBinding
import com.cricbuzzplus.liveline.livedata.response.TestSessions

class MatchOddsLiveAdapter(
    var list: ArrayList<TestSessions>,
    val context: Context
) : RecyclerView.Adapter<MatchOddsLiveAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<TestSessions>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemSessionLiveLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSessionLiveLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.sessionOvr.setText("Over "+model.overs.toString())
        holder.binding.sessionMax.setText(""+model.max)
        holder.binding.sessionMin.setText(""+model.min)
        holder.binding.sessionOpen.setText(""+model.open)
       // holder.binding.sessionPass.setText(""+model.pass+"/"+model.wkt+"Wk")
        holder.binding.sessionPass.setText(""+model.pass)
       // holder.binding.sessionWkt.setText(""+model.wkt+"Wk")
        holder.binding.sessionRate.setText(""+model.rate+" (${model.fav})")


        if (model.pass?.toInt()!! >= model.open?.toInt()!!) {
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
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}