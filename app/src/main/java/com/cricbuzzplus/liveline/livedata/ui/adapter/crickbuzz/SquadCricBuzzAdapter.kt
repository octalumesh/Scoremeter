package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemSquadBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayingXIItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnSquadClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class SquadCricBuzzAdapter(
    val list: ArrayList<PlayingXIItem>,
    val context: Context,
    val onClickInterface: OnSquadClickInterface
) : RecyclerView.Adapter<SquadCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemSquadBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSquadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model.captain == true && model.keeper == true) {
            holder.binding.tvPlayerName.setText(""+model.name.toString()+"(c & wk)")
        }else if (model.captain == true ) {
            holder.binding.tvPlayerName.setText(""+model.name.toString()+"(c)")
        }else if (model.keeper == true) {
            holder.binding.tvPlayerName.setText(""+model.name.toString()+"(wk)")
        }
        else {
            holder.binding.tvPlayerName.setText(""+model.name.toString())
        }
        holder.binding.tvPlayerRole.setText("" + model.role)

        Glide.with(context)
            .load("" + Constants.cricbuzzImgFirst + model.faceImageId + Constants.cricbuzzImgSecond)
            .apply(requestOptions).into(holder.binding.imgSquadPlayer)

        if (!model.playingXIChange.isNullOrEmpty()) {

            if (model.playingXIChange.equals("IN", true)) {
              //  holder.binding.imgInout.visibility = View.VISIBLE

              //  holder.binding.imgInout.setImageResource(R.drawable.rank_up)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.binding.parentSquad.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.green_tablenew
                            )
                        )
                }

            } else if (model.playingXIChange.equals("OUT", true)) {
               // holder.binding.imgInout.visibility = View.VISIBLE

              //  holder.binding.imgInout.setImageResource(R.drawable.rank_down)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.binding.parentSquad.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.red_table
                            )
                        )
                }

            } else {
                holder.binding.imgInout.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.binding.parentSquad.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.card_view_white
                            )
                        )
                }
            }


        } else {
            holder.binding.imgInout.visibility = View.GONE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.binding.parentSquad.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            R.color.card_view_white
                        )
                    )
            }

        }


        holder.binding.parentSquad.setOnClickListener {

            onClickInterface.onClick(model)

            /*val intent = Intent(context, PlayerProfileActivity::class.java)
            intent.putExtra("playerName",model.name.toString())
            context.startActivity(intent)*/
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}