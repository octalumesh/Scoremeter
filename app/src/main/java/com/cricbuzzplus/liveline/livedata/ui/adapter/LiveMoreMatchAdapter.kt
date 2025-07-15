package com.cricbuzzplus.liveline.livedata.ui.adapter

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
import com.cricbuzzplus.liveline.databinding.ItemLiveMoreMatchBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCricHomeBinding
import com.cricbuzzplus.liveline.databinding.ItemOversCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.CommentaryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomepageItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.OverSummaryListItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnMatchClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class LiveMoreMatchAdapter(
    var list: ArrayList<HomeMatchResponseItem>,
    val context: Context,
    var clickinterface: OnMatchClickInterface,
) : RecyclerView.Adapter<LiveMoreMatchAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemLiveMoreMatchBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLiveMoreMatchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]

        holder.binding.teams.setText("${model.teamAShort} vs ${model.teamBShort}")
        holder.binding.matchStatus.setText("${model.matchStatus}")


        holder.binding.parent.setOnClickListener {
            clickinterface.onClick(model)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}