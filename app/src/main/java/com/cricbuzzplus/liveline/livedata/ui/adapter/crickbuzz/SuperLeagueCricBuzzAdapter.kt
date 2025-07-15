package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricBinding
import com.cricbuzzplus.liveline.databinding.ItemPointTableCricMainBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesCricbuzzMatchesBinding
import com.cricbuzzplus.liveline.databinding.ItemSuperLeagueCricBinding
import com.cricbuzzplus.liveline.databinding.ItemWTCTableCricBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.utils.Constants

class SuperLeagueCricBuzzAdapter(
    val list: ArrayList<SuperLeagueValuesItem>,
    val context: Context,
) : RecyclerView.Adapter<SuperLeagueCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSuperLeagueCricBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSuperLeagueCricBinding.inflate(
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

        Glide.with(context).load(Constants.cricbuzzImgFirst+model.value?.get(1)+ Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.teamImg)

        holder.binding.position.setText(""+model.value?.get(0))
        holder.binding.teamName.setText(""+model.value?.get(2))
        holder.binding.point.setText(""+model.value?.get(3))


    }

    override fun getItemCount(): Int {
        return list.size
    }


}