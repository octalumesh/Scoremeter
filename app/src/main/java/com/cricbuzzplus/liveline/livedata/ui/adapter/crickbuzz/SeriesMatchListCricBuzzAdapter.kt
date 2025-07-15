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

class SeriesMatchListCricBuzzAdapter(
    val list: ArrayList<SeriesScheduleMatchItem>,
    val context: Context,
) : RecyclerView.Adapter<SeriesMatchListCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSeriesMatchListCricbuzzBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSeriesMatchListCricbuzzBinding.inflate(
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

        if(model.matchInfo?.venueInfo != null){
            holder.binding.textviewMatchSeries.setText(model.matchInfo?.matchDesc.toString()+", ${model.matchInfo?.venueInfo!!.city}")
        }else {

            holder.binding.textviewMatchSeries.setText(model.matchInfo?.matchDesc.toString())
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
            .load("" + Constants.cricbuzzImgFirst + model.matchInfo?.team1?.imageId + Constants.cricbuzzImghigh)
            .apply(requestOptions).into(holder.binding.teamAImage)
        Glide.with(context)
            .load("" + Constants.cricbuzzImgFirst + model.matchInfo?.team2?.imageId + Constants.cricbuzzImghigh)
            .apply(requestOptions).into(holder.binding.teamBImage)

        holder.binding.teamAName.setText("" + model.matchInfo?.team1?.teamSName)
        holder.binding.teamBName.setText("" + model.matchInfo?.team2?.teamSName)

        if (!model?.matchInfo?.status.isNullOrEmpty()){
            if (model?.matchInfo?.state.equals("Preview",true) || model?.matchInfo?.state.equals("Upcoming",true)){
                try {

                    if (!model.matchInfo?.startDate.isNullOrEmpty()) {

                        val timestamp = model.matchInfo?.startDate?.toLong()

                        val instant = Instant.ofEpochMilli(timestamp!!)
                        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

                        val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM")
                        val formattedDate = localDateTime.format(dateFormatter)

                        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
                        val formattedTime = localDateTime.format(timeFormatter)

                        val formattedDateTime = "$formattedDate, $formattedTime"
                        holder.binding.resultVanue.setText(formattedTime.toString().uppercase())

                    }else{
                        holder.binding.resultVanue.setText("" + model.matchInfo?.venueInfo?.ground)
                    }

                }catch (ex:Exception){
                    ex.printStackTrace()

                    holder.binding.resultVanue.setText("" + model.matchInfo?.venueInfo?.ground)
                }
            }else {
                holder.binding.resultVanue.setText(model?.matchInfo?.status.toString())
            }
        }else{
            try {

                if (!model.matchInfo?.startDate.isNullOrEmpty()) {

                    val timestamp = model.matchInfo?.startDate?.toLong()

                    val instant = Instant.ofEpochMilli(timestamp!!)
                    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

                    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM")
                    val formattedDate = localDateTime.format(dateFormatter)

                    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
                    val formattedTime = localDateTime.format(timeFormatter)

                    val formattedDateTime = "$formattedDate, $formattedTime"
                    holder.binding.resultVanue.setText(formattedTime.toString().uppercase())

                }else{
                    holder.binding.resultVanue.setText("" + model.matchInfo?.venueInfo?.ground)
                }

            }catch (ex:Exception){
                ex.printStackTrace()

                holder.binding.resultVanue.setText("" + model.matchInfo?.venueInfo?.ground)
            }
        }



        if (model?.matchInfo?.state.equals("In Progress",true)){
            holder.binding.resultVanue.setTextColor(context.resources.getColor(R.color.red))
        }else if (model?.matchInfo?.state.equals("Preview",true) || model?.matchInfo?.state.equals("Upcoming",true)){
            holder.binding.resultVanue.setTextColor(context.resources.getColor(R.color.orange))
        }else if (model?.matchInfo?.state.equals("Toss",true) ){
            holder.binding.resultVanue.setTextColor(context.resources.getColor(R.color.orange))
        }else if (model?.matchInfo?.state.equals("Complete",true)){
            holder.binding.resultVanue.setTextColor(context.resources.getColor(R.color.bluene))
        }else{
            holder.binding.resultVanue.setTextColor(context.resources.getColor(R.color.txt_color))
        }

        if (model.matchInfo?.state.equals("Upcoming", true)) {

          //  holder.binding.resultVanue.setText("" + model.matchInfo?.venueInfo?.ground)

        } else if (model.matchInfo?.state.equals("Complete", true)) {

         //   holder.binding.resultVanue.setText("" + model.matchInfo?.status)

            if (model.matchScore != null) {

                if (model.matchScore.team1Score != null) {

                    if (model.matchInfo?.matchFormat.equals("TEST")) {

                        if (model.matchScore.team1Score.inngs1 != null) {

                            var inning = ""

                            if (model.matchScore.team1Score.inngs1.isDeclared == true) {

                                inning =
                                    "${model.matchScore.team1Score.inngs1.runs}-${model.matchScore.team1Score.inngs1.wickets} (${model.matchScore.team1Score.inngs1.overs}) d"

                            } else {
                                inning =
                                    "${model.matchScore.team1Score.inngs1.runs}-${model.matchScore.team1Score.inngs1.wickets} (${model.matchScore.team1Score.inngs1.overs})"
                            }

                            var inning2 = ""

                            if (model.matchScore.team1Score.inngs2 != null) {

                                if (model.matchScore.team1Score.inngs2.isDeclared == true) {

                                    inning2 =
                                        "${model.matchScore.team1Score.inngs2.runs}-${model.matchScore.team1Score.inngs2.wickets} (${model.matchScore.team1Score.inngs2.overs}) d"

                                } else {
                                    inning2 =
                                        "${model.matchScore.team1Score.inngs2.runs}-${model.matchScore.team1Score.inngs2.wickets} (${model.matchScore.team1Score.inngs2.overs})"
                                }

                            }

                            if (!inning2.isNullOrEmpty()) {
                                holder.binding.teamAScore.setText("$inning & $inning2")
                            } else {
                                holder.binding.teamAScore.setText("$inning")
                            }

                        }

                    } else {
                        if (model.matchScore.team1Score.inngs1 != null) {

                            var inning =
                                "${model.matchScore.team1Score.inngs1.runs}-${model.matchScore.team1Score.inngs1.wickets} (${model.matchScore.team1Score.inngs1.overs})"

                            holder.binding.teamAScore.setText("$inning")


                        }
                    }

                }


                if (model.matchScore.team2Score != null) {

                    if (model.matchInfo?.matchFormat.equals("TEST")) {

                        if (model.matchScore.team2Score.inngs1 != null) {

                            var inning = ""

                            if (model.matchScore.team2Score.inngs1.isDeclared == true) {
                                inning =
                                    "${model.matchScore.team2Score.inngs1.runs}-${model.matchScore.team2Score.inngs1.wickets} (${model.matchScore.team2Score.inngs1.overs}) d"

                            } else {
                                inning =
                                    "${model.matchScore.team2Score.inngs1.runs}-${model.matchScore.team2Score.inngs1.wickets} (${model.matchScore.team2Score.inngs1.overs})"
                            }

                            var inning2 = ""

                            if (model.matchScore.team2Score.inngs2 != null) {

                                if (model.matchScore.team2Score.inngs2.isDeclared == true) {

                                    inning2 =
                                        "${model.matchScore.team2Score.inngs2.runs}-${model.matchScore.team2Score.inngs2.wickets} (${model.matchScore.team2Score.inngs2.overs}) d"

                                } else {
                                    inning2 =
                                        "${model.matchScore.team2Score.inngs2.runs}-${model.matchScore.team2Score.inngs2.wickets} (${model.matchScore.team2Score.inngs2.overs})"
                                }

                            }

                            if (!inning2.isNullOrEmpty()) {
                                holder.binding.teamBScore.setText("$inning & $inning2")
                            } else {
                                holder.binding.teamBScore.setText("$inning")
                            }

                        }

                    } else {
                        if (model.matchScore.team2Score.inngs1 != null) {

                            if (model.matchScore.team2Score.inngs1.wickets == null) {

                                var inning =
                                    "${model.matchScore.team2Score.inngs1.runs}-${0} (${model.matchScore.team2Score.inngs1.overs})"

                                holder.binding.teamBScore.setText("$inning")
                            }else{
                                var inning =
                                    "${model.matchScore.team2Score.inngs1.runs}-${model.matchScore.team2Score.inngs1.wickets} (${model.matchScore.team2Score.inngs1.overs})"

                                holder.binding.teamBScore.setText("$inning")
                            }


                        }
                    }

                }

            }

        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}