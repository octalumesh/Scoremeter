package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemScoreBowlerBinding
import com.cricbuzzplus.liveline.livedata.response.BolwerItem

class ScorecardBowlerAdapter(
    var list: ArrayList<BolwerItem>,
    val context: Context
) : RecyclerView.Adapter<ScorecardBowlerAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<BolwerItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemScoreBowlerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemScoreBowlerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.bowlerName.setText(model.name.toString())
        holder.binding.over.setText(""+model.over)
        holder.binding.maiden.setText(""+model.maiden)
        holder.binding.runs.setText(""+model.run)
        holder.binding.wicket.setText(""+model.wicket)
        holder.binding.economy.setText(""+model.economy)

        holder.binding.bowlerName.setOnClickListener {
            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name)
            context.startActivity(intent)*/
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}