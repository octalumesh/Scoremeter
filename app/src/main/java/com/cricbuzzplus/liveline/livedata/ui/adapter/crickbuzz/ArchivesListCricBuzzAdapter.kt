package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemArchivesListCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ArchivesItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArchivesListCricBuzzAdapter(
    val list: ArrayList<ArchivesItem>,
    val context: Context,
) : RecyclerView.Adapter<ArchivesListCricBuzzAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemArchivesListCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemArchivesListCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.seriesName.setText(model.name.toString())


        try {

            val datestart = Date(model.startDt?.toLong()!!)
            val outputFormatStart = SimpleDateFormat("MMM dd", Locale.getDefault())

            var startDate = outputFormatStart.format(datestart)

            val dateEnd = Date(model.endDt?.toLong()!!)
            val outputFormatEnd = SimpleDateFormat("MMM dd", Locale.getDefault())

            var endDate = outputFormatEnd.format(dateEnd)

            holder.binding.seriesDate.setText("$startDate - $endDate")

        }catch (e:Exception){
            e.printStackTrace()
        }

        holder.binding.parent.setOnClickListener {

            val intent = Intent(context, SeriesDetailsActivity::class.java)
            intent.putExtra("series",model.name)
            intent.putExtra("seriesId",model.id)
            context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}