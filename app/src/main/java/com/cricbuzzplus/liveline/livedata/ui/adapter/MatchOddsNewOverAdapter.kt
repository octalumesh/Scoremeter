package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemOddsOverLayoutBinding
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface

class MatchOddsNewOverAdapter(
    var list: ArrayList<ArrayList<MatchOddsResponseItem>>,
    val context: Context,
    val onclick: OnClickInterface
) : RecyclerView.Adapter<MatchOddsNewOverAdapter.MyViewHolder>() {

    var selectedPos = -1

    var dropdownImageStates = BooleanArray(list.size)

    fun updateList(list: ArrayList<ArrayList<MatchOddsResponseItem>>) {
        val oldDropdownImageStates = dropdownImageStates.copyOf()
        this.list = list
        dropdownImageStates = BooleanArray(list.size)
        oldDropdownImageStates.copyInto(dropdownImageStates)
        notifyDataSetChanged()
    }


    class MyViewHolder(val binding: ItemOddsOverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemOddsOverLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelList = list[position]

        holder.binding.overData.setText("" + (position + 1) + " Over")


        val adapter2 = MatchOddsInsideAdapter(modelList, context)
        holder.binding.recyclerOverOdds.adapter = adapter2
        holder.binding.recyclerOverOdds.scrollToPosition(modelList.size - 1)

        try {

            if (dropdownImageStates[position]) {
                holder.binding.recyclerOverOdds.visibility = View.VISIBLE
                holder.binding.dropdown.visibility = View.GONE
                holder.binding.dropdownUp.visibility = View.VISIBLE
            } else {
                holder.binding.recyclerOverOdds.visibility = View.GONE
                holder.binding.dropdown.visibility = View.VISIBLE
                holder.binding.dropdownUp.visibility = View.GONE
            }
        }catch (e:Exception){
            Log.e("TAG", "onBindViewHolder: "+e.message )
        }

        holder.binding.relativeOddsOver.setOnClickListener {
            if (dropdownImageStates[position]) {
                dropdownImageStates[position] = false
            }else{
                dropdownImageStates[position] = true
            }
            onclick.onClick(position)
            notifyItemChanged(position)
        }



    }

    override fun getItemCount(): Int {

        return list.size
    }


}