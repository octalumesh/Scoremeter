package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemExpertsListBinding
import com.cricbuzzplus.liveline.livedata.response.newresponse.AllExpertsResponseItem
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnExpertsClick
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants

class AllExpertsAdapter(
    var list: ArrayList<AllExpertsResponseItem>,
    val context: Context,
    val onExpertsClick: OnExpertsClick
) : RecyclerView.Adapter<AllExpertsAdapter.MyViewHolder>() {

    fun updateList(list: ArrayList<AllExpertsResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemExpertsListBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemExpertsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        if (mPrefs.prefUserDetails != null) {

            if (model.id == mPrefs?.prefUserDetails!!.id) {
                holder.binding.topParent.setBackgroundTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.green_light)
                    )
                )
            } else {
                holder.binding.topParent.setBackgroundTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.darkstatusbar1)
                    )
                )
            }

        }

        holder.binding.expertName.setText(""+model.firstName)
        holder.binding.points.setText(""+model.points)
        holder.binding.progress.setText(""+model.predicationPrecent+"%")

        Glide.with(context).load(Constants.ImgURl+model.profilepicture).placeholder(R.mipmap.ic_launcher_round).into(holder.binding.expertProfile)

        holder.binding.parentExperts.setOnClickListener {
            onExpertsClick.onClick(model?.id!!)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}