package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemBrowsTeamsCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.BrowsTeamDetailActivity
import com.cricbuzzplus.liveline.utils.Constants

class TeamsBrowsCricBuzzAdapter(
    val list: ArrayList<ListItem>,
    val context: Context,
) : RecyclerView.Adapter<TeamsBrowsCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemBrowsTeamsCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBrowsTeamsCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model.teamId == null){
            holder.binding.category.visibility = View.VISIBLE
            holder.binding.linearTeam.visibility = View.GONE

            holder.binding.category.setText(""+model.teamName)
        }else{

            holder.binding.category.visibility = View.GONE
            holder.binding.linearTeam.visibility = View.VISIBLE

            holder.binding.teamName.setText(""+model.teamName)

            Glide.with(context).load(""+Constants.cricbuzzImgFirst+model.imageId+Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.teamImage)

        }




        holder.binding.linearTeam.setOnClickListener {

           // onClickInterface.onClick(model)

            val intent = Intent(context, BrowsTeamDetailActivity::class.java)
            intent.putExtra("teamName",model.teamName.toString())
            intent.putExtra("teamId",model.teamId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}