package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSquadBinding
import com.cricbuzzplus.liveline.livedata.response.PlayerItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnPlayerClickInterface

class SquadAdapter(
    val list: ArrayList<PlayerItem>,
    val context: Context,
    val onClickInterface : OnPlayerClickInterface
) : RecyclerView.Adapter<SquadAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSquadBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSquadBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.tvPlayerName.setText(model.name.toString())
        holder.binding.tvPlayerRole.setText(""+model.playRole)

        Glide.with(context).load(model.image).apply(requestOptions).into(holder.binding.imgSquadPlayer)


        holder.binding.parentSquad.setOnClickListener {

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