package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemScoreBatsmanBinding
import com.cricbuzzplus.liveline.livedata.response.BatsmanItemBoard

class ScorecardBatsmanAdapter(
    var list: ArrayList<BatsmanItemBoard>,
    val context: Context
) : RecyclerView.Adapter<ScorecardBatsmanAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<BatsmanItemBoard>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemScoreBatsmanBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemScoreBatsmanBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.playerName.setText(model.name.toString())
        holder.binding.runs.setText(""+model.run)
        holder.binding.ballFaced.setText(""+model.ball)
        holder.binding.fours.setText(""+model.fours)
        holder.binding.sixes.setText(""+model.sixes)
        holder.binding.strikeRate.setText(""+model.strikeRate)

        if (model.outBy.isNullOrEmpty()){
            holder.binding.outBy.setText("not out")
        }else{
            holder.binding.outBy.setText(""+model.outBy)
        }

        holder.binding.playerName.setOnClickListener {
            /*val intent = Intent(context,PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name)
            context.startActivity(intent)*/
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}