package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemScheduleMatchListCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesMatchListCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.utils.Constants
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ScheduleMatchListCricBuzzAdapter(
    val list: ArrayList<MatchInfoItem>,
    val context: Context,
) : RecyclerView.Adapter<ScheduleMatchListCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemScheduleMatchListCricbuzzBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemScheduleMatchListCricbuzzBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model.venueInfo != null) {
            holder.binding.textviewMatchSeries.setText(model.matchDesc.toString() + ", ${model?.venueInfo!!.city}")
        } else {

            holder.binding.textviewMatchSeries.setText(model?.matchDesc.toString())
        }


        /* try {

             val datestart = Date(model.matchInfo?.startDate?.toLong()!!)
             val outputFormatStart = SimpleDateFormat("MMM dd", Locale.getDefault())

             var startDate = outputFormatStart.format(datestart)

             holder.binding.textviewDateTime.setText("$startDate")


         } catch (e: Exception) {
             e.printStackTrace()
         }*/

        Glide.with(context)
            .load("" + Constants.cricbuzzImgFirst + model?.team1?.imageId + Constants.cricbuzzImghigh)
            .apply(requestOptions).into(holder.binding.teamAImage)
        Glide.with(context)
            .load("" + Constants.cricbuzzImgFirst + model?.team2?.imageId + Constants.cricbuzzImghigh)
            .apply(requestOptions).into(holder.binding.teamBImage)



        holder.binding.teamAName.setText("" + model?.team1?.teamName)
        holder.binding.teamBName.setText("" + model?.team2?.teamName)


        // holder.binding.teamAName.setText("" + model?.team1?.teamSName)
        // holder.binding.teamBName.setText("" + model?.team2?.teamSName)


        if (!model?.startDate.isNullOrEmpty()) {

            val timestamp = model?.startDate?.toLong()

            val instant = Instant.ofEpochMilli(timestamp!!)
            val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

            val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM")
            val formattedDate = localDateTime.format(dateFormatter)

            val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
            val formattedTime = localDateTime.format(timeFormatter)

            val formattedDateTime = "$formattedDate, $formattedTime"
            holder.binding.time.setText(formattedTime.toString().uppercase())

        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}