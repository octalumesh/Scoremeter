package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSCorecardCricbuzzMainBinding
import com.cricbuzzplus.liveline.databinding.ItemSeriesListCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScorecardMainCricBuzzAdapter(
    var list: ArrayList<ScoreCardItem>,
    val context: Context,
    val status: String,
) : RecyclerView.Adapter<ScorecardMainCricBuzzAdapter.MyViewHolder>() {


    fun updateList(list: ArrayList<ScoreCardItem>) {
        this.list = list
        uiSet = true
        notifyDataSetChanged()
    }

    var uiSet = false

    var selectedPosition = (list.size - 1)

    class MyViewHolder(val binding: ItemSCorecardCricbuzzMainBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSCorecardCricbuzzMainBinding.inflate(
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

        //  holder.binding.seriesName.setText(model.name.toString())


        try {


            holder.binding.teamNameFirstInning.setText("" + model.batTeamDetails?.batTeamName)
            holder.binding.firstInningScore.setText("${model.scoreDetails?.runs}-${model.scoreDetails?.wickets} (${model.scoreDetails?.overs})")
            holder.binding.scoreTotal1St.setText("${model.scoreDetails?.runs}-${model.scoreDetails?.wickets} (${model.scoreDetails?.overs})")
            holder.binding.currRate.setText("CRR ${model.scoreDetails?.runRate}")

            if (model.extrasData != null) {

                holder.binding.extrasTotal.setText("" + model.extrasData.total)
                holder.binding.extras1St.setText("b ${model.extrasData.byes}, lb ${model.extrasData.legByes}, w ${model.extrasData.wides}, nb ${model.extrasData.noBalls}, p ${model.extrasData.penalty}")

            }


            if (model.batTeamDetails != null && !model.batTeamDetails.batsmenData.isNullOrEmpty()) {

                var notBat = ""

                var list = arrayListOf<BatsmenDataItem>()

                for (item in model.batTeamDetails.batsmenData) {

                    if (!item?.outDesc.isNullOrEmpty()) {
                        list.add(item!!)
                    } else {
                        notBat = notBat + item?.batName + ", "
                    }

                }

                if (!notBat.isNullOrEmpty()) {

                    holder.binding.linearDidNotBat.visibility = View.VISIBLE

                    if (status.equals("Finished")) {
                        holder.binding.didNotBatText.setText("Did not bat")
                        holder.binding.didNotBatPlayer.setText(notBat)
                    } else if (status.equals("Live")) {
                        holder.binding.didNotBatText.setText("Yet to bat")
                        holder.binding.didNotBatPlayer.setText(notBat)
                    }

                }


                if (!list.isNullOrEmpty()) {
                    holder.binding.recyclerBatsmanFirst.adapter =
                        ScorecardBatsmanCricBuzzAdapter(list, context)
                }

            }

            if (model.bowlTeamDetails != null && !model.bowlTeamDetails.bowlersData.isNullOrEmpty()) {
                holder.binding.recyclerBowlerFirst.adapter = ScorecardBowlerCricBuzzAdapter(
                    model.bowlTeamDetails.bowlersData as ArrayList<BowlersDataItem>,
                    context
                )
            }

            if (!model.wicketsData.isNullOrEmpty()) {
                holder.binding.linearFallWkt.visibility = View.VISIBLE
                holder.binding.recyclerFallWicketFirst.adapter = ScorecardFallWktCricBuzzAdapter(
                    model.wicketsData as ArrayList<WicketsDataItem>, context
                )
            } else {
                holder.binding.linearFallWkt.visibility = View.GONE
            }

            if (model.ppData != null && model.ppData.pp1 != null) {
                holder.binding.powerplayLinear.visibility = View.VISIBLE
                holder.binding.powerplay.setText("" + model.ppData.pp1?.ppType)
                holder.binding.powerplayRuns.setText("" + model.ppData.pp1?.runsScored)
                holder.binding.powerPlayOver.setText("" + model.ppData.pp1?.ppOversFrom + " - " + model.ppData.pp1?.ppOversTo)
            }

            /*holder.binding.firstInningScoreCard.setOnClickListener {
                uiSet = false
                if (model.expand!!) {
                    model.expand = false
                }else{
                    model.expand = true
                }
                selectedPosition = position
                notifyDataSetChanged()
            }*/

            holder.binding.firstInningScoreCard.setOnClickListener {
               // uiSet = false
                if (holder.binding.firstInningLinear.visibility == View.GONE) {
                    holder.binding.firstInningScoreCard.setBackgroundTintList(
                        ColorStateList.valueOf(
                            ContextCompat.getColor(context, R.color.colorAccent)
                        )
                    )

                    holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.white))
                    holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.white))

                    holder.binding.inning1Down.visibility = View.GONE
                    holder.binding.inning1Up.visibility = View.VISIBLE
                    holder.binding.firstInningLinear.visibility = View.VISIBLE
                }else{
                    holder.binding.firstInningScoreCard.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(context, R.color.card_view_white)
                        )

                    holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.txt_color))
                    holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.txt_color))

                    holder.binding.inning1Down.visibility = View.VISIBLE
                    holder.binding.inning1Up.visibility = View.GONE
                    holder.binding.firstInningLinear.visibility = View.GONE
                }
            }

            if (!uiSet) {
                if (position == (list.size - 1)) {
                    holder.binding.firstInningScoreCard.setBackgroundTintList(
                        ColorStateList.valueOf(
                            ContextCompat.getColor(context, R.color.colorAccent)
                        )
                    )

                    holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.white))
                    holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.white))

                    holder.binding.inning1Down.visibility = View.GONE
                    holder.binding.inning1Up.visibility = View.VISIBLE
                    holder.binding.firstInningLinear.visibility = View.VISIBLE
                } else {
                    holder.binding.firstInningScoreCard.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(context, R.color.card_view_white)
                        )

                    holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.txt_color))
                    holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.txt_color))

                    holder.binding.inning1Down.visibility = View.VISIBLE
                    holder.binding.inning1Up.visibility = View.GONE
                    holder.binding.firstInningLinear.visibility = View.GONE
                }
            }

           /* if (!uiSet) {

                if (position == selectedPosition) {

                    if (model.expand!!) {

                        holder.binding.firstInningScoreCard.setBackgroundTintList(
                            ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.colorAccent)
                            )
                        )

                        holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.white))
                        holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.white))

                        holder.binding.inning1Down.visibility = View.GONE
                        holder.binding.inning1Up.visibility = View.VISIBLE
                        holder.binding.firstInningLinear.visibility = View.VISIBLE
                    } else {
                        holder.binding.firstInningScoreCard.backgroundTintList =
                            ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.card_view_white)
                            )

                        holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.txt_color))
                        holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.txt_color))

                        holder.binding.inning1Down.visibility = View.VISIBLE
                        holder.binding.inning1Up.visibility = View.GONE
                        holder.binding.firstInningLinear.visibility = View.GONE
                    }

                } else {
                    model.expand = false
                    holder.binding.firstInningScoreCard.setBackgroundTintList(
                        ColorStateList.valueOf(
                            ContextCompat.getColor(context, R.color.card_view_white)
                        )
                    )

                    holder.binding.teamNameFirstInning.setTextColor(context.resources.getColor(R.color.txt_color))
                    holder.binding.firstInningScore.setTextColor(context.resources.getColor(R.color.txt_color))

                    holder.binding.inning1Down.visibility = View.VISIBLE
                    holder.binding.inning1Up.visibility = View.GONE
                    holder.binding.firstInningLinear.visibility = View.GONE
                }
            }*/

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "onBindViewHolder: " + e.message)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}