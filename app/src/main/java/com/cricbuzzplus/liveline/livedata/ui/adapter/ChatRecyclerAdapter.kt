package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemChatLayoutBinding
import com.cricbuzzplus.liveline.livedata.model.ChatResponse

class ChatRecyclerAdapter(context: Context, private var mList: ArrayList<ChatResponse>, val senderid: String) :
    RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder>() {

    fun updateList(mList: ArrayList<ChatResponse>){
        this.mList = mList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemChatLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {

        return MyViewHolder(ItemChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentuser = mList[position]

        if(currentuser.senderid == senderid){

            holder.binding.tvSenderMsg.text = currentuser.msg
            holder.binding.tvSenderName.text = currentuser.name

            holder.binding.receiverLinear.visibility=View.GONE
            holder.binding.senderLinear.visibility=View.VISIBLE
        }
        else{
            holder.binding.tvReceiverMsg.text = currentuser.msg
            holder.binding.tvReceiverName.text = currentuser.name

            holder.binding.receiverLinear.visibility=View.VISIBLE
            holder.binding.senderLinear.visibility=View.GONE


        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }


}
