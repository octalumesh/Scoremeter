package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPlayerRankingCricBuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerRankingRankItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnRankingClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class PlayerRankingCricBuzzAdapter(
    val list: ArrayList<PlayerRankingRankItem>,
    val context: Context,
    val onClickInterface : OnRankingClickInterface
) : RecyclerView.Adapter<PlayerRankingCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPlayerRankingCricBuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPlayerRankingCricBuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.playerName.setText(model.name.toString())
        holder.binding.playerCountry.setText(""+model.country)
        holder.binding.rank.setText(""+model.rank)
        holder.binding.points.setText(""+model.rating)

        Glide.with(context).load(""+Constants.cricbuzzImgFirst+model.faceImageId+Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.playerImg)

        if (!model.trend.isNullOrEmpty()){
            holder.binding.trend.visibility = View.VISIBLE
            if (model.trend.equals("Flat",true)){
                holder.binding.trend.setImageResource(R.drawable.ic_baseline_stable)
            }else if (model.trend.equals("Up",true)){
                holder.binding.trend.setImageResource(R.drawable.rank_up)
            }else if (model.trend.equals("Down",true)){
                holder.binding.trend.setImageResource(R.drawable.rank_down)
            }

        }

        holder.binding.parent.setOnClickListener {

            onClickInterface.onClick(model)

            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            context.startActivity(intent)*/
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}