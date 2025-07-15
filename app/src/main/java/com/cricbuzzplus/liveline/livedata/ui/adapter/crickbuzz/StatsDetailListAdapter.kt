package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.ItemPlayerBattingBinding
import com.cricbuzzplus.liveline.databinding.ItemStatsDetailsBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItemStats
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity

class StatsDetailListAdapter(
    var list: ArrayList<ValuesItemStats>,
    val context: Context
) : RecyclerView.Adapter<StatsDetailListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemStatsDetailsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemStatsDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        if (!model.values.isNullOrEmpty()) {

                try {

                    for (i in 0 until model.values.size!!) {
                        when(i){
                            0->{
                               // holder.binding.title.setText("${model.values?.get(i)}")
                            }
                            1->{
                                holder.binding.title.setText("${model.values?.get(i)}")
                            }
                            2->{
                                holder.binding.firstValue.visibility= View.VISIBLE
                                holder.binding.firstValue.setText("${model.values?.get(i)}")
                            }
                            3->{
                                holder.binding.secondValue.visibility= View.VISIBLE
                                holder.binding.secondValue.setText("${model.values?.get(i)}")
                            }
                            4->{
                                holder.binding.thirdValue.visibility= View.VISIBLE
                                holder.binding.thirdValue.setText("${model.values.get(i)}")
                            }
                            5->{
                                holder.binding.fourthValue.visibility= View.VISIBLE
                                holder.binding.fourthValue.setText("${model.values.get(i)}")
                            }
                        }
                    }


                }catch (e:Exception){
                    Log.e("TAGplayer", "onBindViewHolder: error "+e.message )
                }
        }

        holder.binding.linearParent.setOnClickListener(View.OnClickListener {
            if (!model.values.isNullOrEmpty()) {

                try {
                    val intent = Intent(context, PlayerProfileActivity::class.java)
                    intent.putExtra("playerName", model.values.get(1).toString())
                    intent.putExtra("playerId", model.values.get(0)?.toInt())
                    context.startActivity(intent)
                }catch (e:Exception){
                    Log.e("TAGplayer", "onBindViewHolder: error "+e.message )
                }
            }
        })

    }

    override fun getItemCount(): Int {
       return list.size
    }



}