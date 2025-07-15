package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSquadBinding
import com.cricbuzzplus.liveline.databinding.ItemSquadBindingTeamB
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SupportStaffItem
import com.cricbuzzplus.liveline.utils.Constants

class SupportStaffCricBuzzTeamBAdapter(
    val list: ArrayList<SupportStaffItem>,
    val context: Context,
) : RecyclerView.Adapter<SupportStaffCricBuzzTeamBAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSquadBindingTeamB) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSquadBindingTeamB.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.tvPlayerName.setText(model.name.toString())
        holder.binding.tvPlayerRole.setText(""+model.role)

        Glide.with(context).load(""+Constants.cricbuzzImgFirst+model.faceImageId+Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.imgSquadPlayer)


        holder.binding.parentSquad.setOnClickListener {

           // onClickInterface.onClick(model)

            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            context.startActivity(intent)*/
        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}