package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemRecentTransactionBinding
import com.cricbuzzplus.liveline.livedata.response.newresponse.WithdrawListResponseItem

class RecentTransactionAdapter(
    val list: ArrayList<WithdrawListResponseItem>,
    val context: Context,
) : RecyclerView.Adapter<RecentTransactionAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemRecentTransactionBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRecentTransactionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.winType.setText(model.title.toString())
        holder.binding.transactionId.setText(model.transactionId.toString())

        holder.binding.amount.setText("\u20B9 " + model.amount.toString())
        holder.binding.status.setText(model.status.toString())

        if (model.status?.equals("Success")!!) {
            //holder.binding.amount.setText("\u20B9 " + model.amount.toString())
            //holder.binding.status.setText(model.status.toString())
            holder.binding.amount.setTextColor(context.getColor(R.color.verified))
            holder.binding.status.setTextColor(context.getColor(R.color.verified))
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }



}