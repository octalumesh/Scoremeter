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
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.news.NewsCategoryActivity

class CategoriesNewsAdapter(
    val list: ArrayList<StoryTypeItem>,
    val context: Context,
) : RecyclerView.Adapter<CategoriesNewsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNewsCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemNewsCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.title.setText(model.name.toString())
        holder.binding.description.setText(model.description.toString())


        holder.binding.parent.setOnClickListener {

            val intent = Intent(context, NewsCategoryActivity::class.java)
            intent.putExtra("catId", model.id)
            intent.putExtra("title", model.name)
            context.startActivity(intent)

        }
        //  holder.binding.recyclerSeries.adapter = ArchivesListCricBuzzAdapter(model.series as ArrayList<ArchivesItem>,context)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}