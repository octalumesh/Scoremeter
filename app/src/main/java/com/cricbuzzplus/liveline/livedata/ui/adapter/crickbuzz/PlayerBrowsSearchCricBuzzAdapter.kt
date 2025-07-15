package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPlayerSearchCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity

class PlayerBrowsSearchCricBuzzAdapter(
    val list: ArrayList<PlayerItem>,
    val context: Context,
) : RecyclerView.Adapter<PlayerBrowsSearchCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPlayerSearchCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPlayerSearchCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.playerName.setText(model.name.toString())


        holder.binding.parent.setOnClickListener {

           // onClickInterface.onClick(model)

            val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            intent.putExtra("playerId",model.id?.toInt())
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}