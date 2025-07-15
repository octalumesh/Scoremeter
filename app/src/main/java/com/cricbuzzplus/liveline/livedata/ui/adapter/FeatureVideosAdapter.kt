package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemFeatureVideoBinding
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface

class FeatureVideosAdapter(
    val list: ArrayList<String>,
    val context: Context,val listener: OnClickInterface
) : RecyclerView.Adapter<FeatureVideosAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemFeatureVideoBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemFeatureVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]



       /* val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)



        Glide.with(context).load(model.image).placeholder(R.drawable.custom_progress).apply(requestOptions).into(holder.binding.image)

        holder.binding.parent.setOnClickListener(View.OnClickListener {
            listener.onClick(model?.newsId!!)
        })*/
    }

    override fun getItemCount(): Int {
       return list.size
    }



}