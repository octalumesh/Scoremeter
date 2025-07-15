package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemPlayerBowlingBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItemBowling

class PlayerBowlingStatAdapter(
    var list: ArrayList<ValuesItemBowling>,
    val context: Context
) : RecyclerView.Adapter<PlayerBowlingStatAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPlayerBowlingBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPlayerBowlingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]
        if (!model.values.isNullOrEmpty()) {

            if (model.values?.size!! >= 5) {

                try {

                    val type = model.values.get(0)
                    val test = model.values.get(1)
                    val odi = model.values.get(2)
                    val t20 = model.values.get(3)
                    val ipl = model.values.get(4)


                    holder.binding.playerType.setText("" + type)
                    holder.binding.playerTest.setText("" + test)
                    holder.binding.playerOdi.setText("" + odi)
                    holder.binding.playerT20.setText("" + t20)
                    holder.binding.playerIpl.setText("" + ipl)


                }catch (e:Exception){
                    Log.e("TAGplayer", "onBindViewHolder: error "+e.message )
                }
            }

        }
    }

    override fun getItemCount(): Int {
       return list.size
    }



}