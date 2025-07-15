package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemBatsmanLiveBinding
import com.cricbuzzplus.liveline.databinding.ItemBatsmanLiveCricCommentaryBinding
import com.cricbuzzplus.liveline.livedata.response.BatsmanItem

class BatsmanLiveCricAdapter(
    var list: ArrayList<BatsmanItem>,
    val context: Context
) : RecyclerView.Adapter<BatsmanLiveCricAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<BatsmanItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemBatsmanLiveCricCommentaryBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBatsmanLiveCricCommentaryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.batsmanName.setText(model.name.toString())
        if (!model.run.toString().isNullOrEmpty()) {
            holder.binding.batsmanRuns.setText(
                "" + model.run.toString().toDouble().toInt()
            )
        }
        if (!model.ball.toString().isNullOrEmpty()) {
            holder.binding.batsmanBalls.setText(
                "" + model.ball.toString().toDouble().toInt()
            )
        }

        if (!model.fours.toString().isNullOrEmpty()) {
            holder.binding.batsmanFours.setText(
                "" + model.fours.toString().toDouble().toInt()
            )
        }

        if (!model.sixes.toString().isNullOrEmpty()) {
            holder.binding.batsmanSix.setText(
                "" + model.sixes.toString().toDouble().toInt()
            )
        }

        if (!model.strikeRate.toString().isNullOrEmpty()) {
            holder.binding.batsmanStrikeRate.setText("" + model.strikeRate)
        }


        holder.binding.batsmanName.setOnClickListener {
            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            context.startActivity(intent)*/
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}