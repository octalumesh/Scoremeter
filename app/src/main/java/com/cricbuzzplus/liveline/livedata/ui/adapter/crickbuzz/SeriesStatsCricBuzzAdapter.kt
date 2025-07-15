package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.databinding.ItemSquadCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesSquadActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesStatsDetailActivity

class SeriesStatsCricBuzzAdapter(
    val list: ArrayList<TypesItem>,
    val context: Context,
    val seriesId: Int
) : RecyclerView.Adapter<SeriesStatsCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSquadCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSquadCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.squadType.setText(""+model.header)
        holder.binding.teamName.setText(""+model.header)

        if (model.category != null ){
            holder.binding.squadType.visibility = View.GONE
            holder.binding.teamName.visibility = View.VISIBLE

        }else{
            holder.binding.squadType.visibility = View.VISIBLE
            holder.binding.teamName.visibility = View.GONE
        }

        holder.binding.teamName.setOnClickListener {
            if (!model.value.isNullOrEmpty()) {
                val intent = Intent(context, SeriesStatsDetailActivity::class.java)
                intent.putExtra("seriesId", seriesId)
                intent.putExtra("selectedItem", model.value)
                intent.putExtra("header", model.header)
                context.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}