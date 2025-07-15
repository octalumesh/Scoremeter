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
import com.cricbuzzplus.liveline.databinding.ItemPlayerNewsBinding
import com.cricbuzzplus.liveline.databinding.ItemSquadMemberCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerItemSquad
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlayerSquadCricAdapter(
    val list: ArrayList<PlayerItemSquad>,
    val context: Context
) : RecyclerView.Adapter<PlayerSquadCricAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSquadMemberCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSquadMemberCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)




        if (model.isHeader != null && model.isHeader) {

            holder.binding.type.setText(model.name)
            holder.binding.type.visibility = View.VISIBLE
            holder.binding.linearParent.visibility = View.GONE

        }else{
            val image = "https://api2.cricbuzz.com/a/img/v1/i1/c${model?.imageId}/i.jpg?p=det&d=high"
            Glide.with(context).load(image).placeholder(R.drawable.custom_progress)
                .apply(requestOptions).into(holder.binding.playerImg)

            if (model.captain != null && model.captain) {
                holder.binding.playerName.setText(model.name+" (c)")
            }else{
                holder.binding.playerName.setText(model.name)
            }

            holder.binding.type.visibility = View.GONE
            holder.binding.linearParent.visibility = View.VISIBLE

        }



        holder.binding.linearParent.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            intent.putExtra("playerId",model.id?.toInt())
            context.startActivity(intent)
        })
    }

    fun convertTimestampToRelativeDate(timestamp: Long): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timestampMillis = timestamp.toLong()

        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val differenceMillis = currentTimeMillis - timestampMillis
        val daysDifference = differenceMillis / (1000 * 60 * 60 * 24)

        return if (daysDifference <= 7) {
            if (daysDifference <= 1) {
                "Today"
            } else {
                "${daysDifference.toInt()} days ago"
            }
        } else {
            dateFormat.format(Date(timestampMillis))
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }



}