package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemBatsmanLiveBinding
import com.cricbuzzplus.liveline.livedata.response.BatsmanItem

class BatsmanLiveAdapter(
    var list: ArrayList<BatsmanItem>,
    val context: Context
) : RecyclerView.Adapter<BatsmanLiveAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<BatsmanItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemBatsmanLiveBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBatsmanLiveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.textviewBatsmanName1.setText(model.name.toString())
        if (!model.run.toString().isNullOrEmpty()) {
            holder.binding.textviewBatsmanDetails1ScoreboardRunLive.setText(
                "" + model.run.toString().toDouble().toInt()
            )
        }
        if (!model.ball.toString().isNullOrEmpty()) {
            holder.binding.textviewBatsmanDetails1ScoreboardBallLive.setText(
                "" + model.ball.toString().toDouble().toInt()
            )
        }

        if (!model.fours.toString().isNullOrEmpty()) {
            holder.binding.textviewBatsmanDetails1Scoreboar4sLive.setText(
                "" + model.fours.toString().toDouble().toInt()
            )
        }

        if (!model.sixes.toString().isNullOrEmpty()) {
            holder.binding.textviewBatsmanDetails1Scoreboard6sLive.setText(
                "" + model.sixes.toString().toDouble().toInt()
            )
        }

        if (!model.strikeRate.toString().isNullOrEmpty()) {
            holder.binding.textviewBatsmanDetails1ScoreboardStrateLive.setText("" + model.strikeRate)
        }


        holder.binding.textviewBatsmanName1.setOnClickListener {
            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            context.startActivity(intent)*/
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}