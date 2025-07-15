package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeMatchesBindingNewCric
import com.cricbuzzplus.liveline.databinding.HomeNewsBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCricHomeBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomepageItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class HomeNewsCricAdapter(
    val list: List<List<HomepageItem>>,
    val context: Context
) : RecyclerView.Adapter<HomeNewsCricAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNewsCricHomeBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemNewsCricHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.recyclerNews.adapter = HomeNewsCricInnerAdapter(model,context)


    }

    override fun getItemCount(): Int {
       return list.size
    }



}