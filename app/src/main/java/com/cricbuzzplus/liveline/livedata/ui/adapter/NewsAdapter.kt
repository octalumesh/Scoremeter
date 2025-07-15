package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeNewsBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface

class NewsAdapter(
    val list: ArrayList<NewsListResponseItem>,
    val context: Context,val listener: OnClickInterface
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: HomeNewsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(HomeNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]

        holder.binding.title.setText(model.title)
        holder.binding.description.setText(model.description)
        holder.binding.time.setText(model.pubDate)

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)



        Glide.with(context).load(model.image).placeholder(R.drawable.custom_progress).apply(requestOptions).into(holder.binding.image)

        holder.binding.parent.setOnClickListener(View.OnClickListener {
            listener.onClick(model?.newsId!!)
        })
    }

    override fun getItemCount(): Int {
       return list.size
    }



}