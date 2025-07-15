package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemStadiumMatchesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchItem
import com.cricbuzzplus.liveline.utils.Constants
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class StadiumMatchesAdapter(
    var list: List<MatchItem>,
    val context: Context
) : RecyclerView.Adapter<StadiumMatchesAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemStadiumMatchesBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemStadiumMatchesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)


        val image1 = "https://api2.cricbuzz.com/a/img/v1/i1/c${model.matchInfo?.team1?.imageId}/i.jpg?p=det&d=high"
        val image2 = "https://api2.cricbuzz.com/a/img/v1/i1/c${model.matchInfo?.team2?.imageId}/i.jpg?p=det&d=high"


        holder.binding.textviewMatchSeries.setText(model.matchInfo?.matchDesc.toString()+", "+model.matchInfo?.venueInfo?.city)


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
                        holder.binding.resultVanue.setText(formattedTime)

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
                    holder.binding.resultVanue.setText(formattedTime)

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



        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}