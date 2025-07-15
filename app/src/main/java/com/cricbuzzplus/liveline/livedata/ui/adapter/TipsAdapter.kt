package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemTipsBinding
import com.cricbuzzplus.liveline.livedata.response.TipsResponse
import java.text.SimpleDateFormat
import java.util.*

class TipsAdapter(
    var list: ArrayList<TipsResponse>,
    val context: Context
) : RecyclerView.Adapter<TipsAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<TipsResponse>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemTipsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTipsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]

        holder.binding.titlePrediction.setText(model.title)
        holder.binding.titleMessage.setText(model.message)

        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        try {
            holder.binding.datePrediction.setText(
                "Date: " + sfd.format(
                    Date(
                        model.date?.toDate().toString()
                    )
                )
            )
        }catch (e:Exception){
            holder.binding.datePrediction.setText(model.date.toString())
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}