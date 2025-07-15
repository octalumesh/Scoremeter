package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeMatchesBindingNewCric
import com.cricbuzzplus.liveline.databinding.HomeNewsBinding
import com.cricbuzzplus.liveline.databinding.ItemHomeCommentaryCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCricHomeBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.CommentaryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomepageItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class LiveCommentaryCricAdapter(
    var list: ArrayList<CommentaryListItem>,
    val context: Context
) : RecyclerView.Adapter<LiveCommentaryCricAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<CommentaryListItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemHomeCommentaryCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemHomeCommentaryCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model.overNumber != null){

            holder.binding.oversCommentry.visibility = View.VISIBLE
            holder.binding.commentryText.visibility = View.GONE

            if (model.event?.contains("over-break",true)!!){
                holder.binding.relativeOverComplete.visibility = View.VISIBLE
            }else{
                holder.binding.relativeOverComplete.visibility = View.GONE
            }

            holder.binding.commentaryOver.setText("${model.overNumber}")
            holder.binding.overDescription.setText("${model.commText}")


            if (model.overSeparator != null){

                holder.binding.overTitleTeam.setText("${model.overSeparator.batTeamName}")
                holder.binding.overTitleScore.setText("${model.overSeparator.score}-${model.overSeparator.wickets}")
                holder.binding.overTitleRun.setText("${model.overSeparator.oSummary} (${model.overSeparator.runs} RUNS)")

                try {
                    val convertedOverNumber = Math.ceil(model.overNumber.toString().toDouble()).toInt()

                    holder.binding.overTitleMain.setText("OVER ${convertedOverNumber}")
                }catch (e:Exception){
                    e.printStackTrace()
                    holder.binding.overTitleMain.setText("OVER ${model.overNumber}")
                }

                if (!model.overSeparator.batStrikerNames.isNullOrEmpty()){

                    holder.binding.overPlayer1Name.setText("${model.overSeparator.batStrikerNames[0]}")
                    holder.binding.overPlayer1Score.setText("${model.overSeparator.batStrikerRuns}(${model.overSeparator.batStrikerBalls})")

                }

                if (!model.overSeparator.batNonStrikerNames.isNullOrEmpty()){

                    holder.binding.overPlayer2Name.setText("${model.overSeparator.batNonStrikerNames[0]}")
                    holder.binding.overPlayer2Score.setText("${model.overSeparator.batNonStrikerRuns}(${model.overSeparator.batNonStrikerBalls})")

                }

                if (!model.overSeparator.bowlNames.isNullOrEmpty()){

                    holder.binding.overBolwerName.setText("${model.overSeparator.bowlNames[0]}")
                    holder.binding.overBolwerState.setText("${model.overSeparator.bowlOvers}-${model.overSeparator.bowlMaidens}-${model.overSeparator.bowlRuns}-${model.overSeparator.bowlWickets}")

                }


            }

            if (model.event.toString().contains("four",true)){

                holder.binding.tvOverRunCommentry.setText("4")
                holder.binding.tvOverRunCommentry.visibility= View.VISIBLE
                holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.green)))

            }else if (model.event.toString().contains("six",true)){

                holder.binding.tvOverRunCommentry.setText("6")
                holder.binding.tvOverRunCommentry.visibility= View.VISIBLE
                holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.pink_lgt)))

            }else if (model.event.toString().contains("wicket",true)){

                holder.binding.tvOverRunCommentry.setText("W")
                holder.binding.tvOverRunCommentry.visibility= View.VISIBLE
                holder.binding.tvOverRunCommentry.setTextColor(context.getResources().getColor(R.color.white))
                holder.binding.tvOverRunCommentry.setBackgroundTintList(ColorStateList.valueOf(context.resources.getColor(R.color.red)))

            }else{
                holder.binding.tvOverRunCommentry.visibility= View.GONE
            }


            var commText = model.commText
            val commentaryFormats = model.commentaryFormats

            if (commentaryFormats != null) {

                val boldFormats = commentaryFormats.bold?.formatId
                val italicFormats = commentaryFormats.italic?.formatId
                val boldValues = commentaryFormats.bold?.formatValue
                val italicValues = commentaryFormats.italic?.formatValue

                // Apply bold formats
                if (boldFormats != null && boldValues != null) {
                    for (i in boldFormats.indices) {
                        val formatId = boldFormats[i]
                        val formatValue = boldValues[i]
                        commText = commText?.replace(formatId.toString(), "<b>" + formatValue.toString() + "</b>")
                    }
                }

                // Apply italic formats
                if (italicFormats != null && italicValues != null) {
                    for (i in italicFormats.indices) {
                        val formatId = italicFormats[i]
                        val formatValue = italicValues[i]

                        commText = commText?.replace(formatId.toString(), "<i>"+formatValue.toString()+"</i>")
                    }
                }



                if (commText!!.contains("\\n")) {
                    commText = commText?.replace("\\n", "<br>")

                }else if (commText!!.contains("\n")) {
                    commText = commText?.replace("\n", "<br>")

                }
                // Set the formatted text in your TextView
                holder.binding.overDescription.setText(Html.fromHtml(commText));

            }


        }else{
            holder.binding.oversCommentry.visibility = View.GONE
            holder.binding.commentryText.visibility = View.VISIBLE


            var commText = model.commText
            val commentaryFormats = model.commentaryFormats

            if (commentaryFormats != null) {

                val boldFormats = commentaryFormats.bold?.formatId
                val italicFormats = commentaryFormats.italic?.formatId
                val boldValues = commentaryFormats.bold?.formatValue
                val italicValues = commentaryFormats.italic?.formatValue

                // Apply bold formats
                if (boldFormats != null && boldValues != null) {
                    for (i in boldFormats.indices) {
                        val formatId = boldFormats[i]
                        val formatValue = boldValues[i]
                        commText = commText?.replace(formatId.toString(), "<b>" + formatValue.toString() + "</b>")
                    }
                }

                // Apply italic formats
                if (italicFormats != null && italicValues != null) {
                    for (i in italicFormats.indices) {
                        val formatId = italicFormats[i]
                        val formatValue = italicValues[i]

                        commText = commText?.replace(formatId.toString(), "<i>"+formatValue.toString()+"</i>")
                    }
                }

                if (commText!!.contains("\\n")) {
                    commText = commText?.replace("\\n", "<br>")

                }else if (commText!!.contains("\n")) {
                    commText = commText?.replace("\n", "<br>")

                }
                // Set the formatted text in your TextView
                holder.binding.commentryText.setText(Html.fromHtml(commText));

            }

        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}