package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricBinding
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricMainBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*

class SeriesPointTableCricBuzzMainNewAdapter(
    val list: ArrayList<PointsTableItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesPointTableCricBuzzMainNewAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPointTableCricMainBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPointTableCricMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)



        if (list.size > 1) {
            holder.binding.group.visibility = View.VISIBLE

            holder.binding.group.setText(model.groupName.toString())

            if (!model.pointsTableInfo.isNullOrEmpty()) {
                holder.binding.recyclerPointTable.adapter =
                    SeriesPointTableCricBuzzNewAdapter(
                        model.pointsTableInfo as ArrayList<PointsTableInfoItem>,
                        context
                    )

            }

        } else {
            holder.binding.group.visibility = View.GONE

            if (!model.pointsTableInfo.isNullOrEmpty()) {
                holder.binding.recyclerPointTable.adapter =
                    SeriesPointTableCricBuzzNewAdapter(
                        model.pointsTableInfo as ArrayList<PointsTableInfoItem>,
                        context
                    )
            }


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}