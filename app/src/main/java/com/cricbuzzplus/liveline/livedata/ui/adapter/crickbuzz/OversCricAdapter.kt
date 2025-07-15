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
import com.cricbuzzplus.liveline.databinding.ItemOversCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class OversCricAdapter(
    var list: ArrayList<OverSepItem>,
    val context: Context
) : RecyclerView.Adapter<OversCricAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<OverSepItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemOversCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemOversCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        try {
            val convertedOverNumber = Math.ceil(model.overNum.toString().toDouble()).toInt()

            holder.binding.over.setText("Ov ${convertedOverNumber}")
        }catch (e:Exception){
            e.printStackTrace()
            holder.binding.over.setText("Ov ${model.overNum}")
        }

        if ( model.runs != null) {

            holder.binding.overRuns.setText("${model.runs} runs")
        }else{
            holder.binding.overRuns.setText("0 runs")
        }

        if (!model.overSummary.isNullOrEmpty()) {
            val items = model.overSummary?.trim()?.split(" ")

            holder.binding.recyclerBalls.adapter = OversBallsCricAdapter(items!!,context)

        }

        var batsman =""

        if (!model.ovrBatNames.isNullOrEmpty()){

            batsman = model.ovrBatNames?.joinToString(" & ")!!
        }


       /* if (!model.batNonStrikerNames.isNullOrEmpty()){

            batsman = batsman +" & "+ model.batNonStrikerNames?.joinToString(" & ")
        }*/

        var bowler =""

        if (!model.ovrBowlNames.isNullOrEmpty()){
            bowler = model.ovrBowlNames?.joinToString(" & ")!!
        }

        holder.binding.overComm.setText("$bowler to $batsman")


    }

    override fun getItemCount(): Int {
       return list.size
    }



}