package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.*

class OversBallsCricAdapter(
    var list: List<String>,
    val context: Context
) : RecyclerView.Adapter<OversBallsCricAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemOverBallsBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemOverBallsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.tvLastballCycle.setText(model)

        if (model.equals("6")) {
            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.six
                    )
                )
            )
        } else if (model.equals("4")) {

            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.four
                    )
                )
            )

        } else if (model.contains("Wd", true) || model.contains("L", true) || model.contains(
                "B",
                true
            ) || model.contains("N", true)
        ) {

            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.extra
                    )
                )
            )

        } else if (model.contains("W", true)) {

            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.wicket
                    )
                )
            )

        }
        else if (model.equals("1") || model.equals("2") || model.equals("3")) {

            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.runs
                    )
                )
            )

        }else{
            holder.binding.tvLastballCycle.setBackgroundTintList(
                ColorStateList.valueOf(
                    context.resources.getColor(
                        R.color.gray_ball
                    )
                )
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}