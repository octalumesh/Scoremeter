package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemArchivesCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCategoriesBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsStoriesBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsTopicsBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.news.NewsCategoryActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.news.StoriesActivity

class TopicsNewsAdapter(
    val list: ArrayList<TopicsItem>,
    val context: Context,
) : RecyclerView.Adapter<TopicsNewsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNewsTopicsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemNewsTopicsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)
        holder.binding.title.setText(model.headline.toString())
        holder.binding.description.setText(model.description.toString())

        holder.binding.parent.setOnClickListener {

            val intent = Intent(context, StoriesActivity::class.java)
            intent.putExtra("catId", model.id)
            intent.putExtra("title", model.headline)
            context.startActivity(intent)

        }

      //  holder.binding.dateSeries.setText(model.date.toString())
//
      //  holder.binding.recyclerSeries.adapter = ArchivesListCricBuzzAdapter(model.series as ArrayList<ArchivesItem>,context)

    }

    override fun getItemCount(): Int {
       return list.size
    }



}