package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemArchivesCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsStoriesBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AllStoriesNewsAdapter(
    var list: ArrayList<StoryListItem>,
    val context: Context,val listener: OnClickInterface
) : RecyclerView.Adapter<AllStoriesNewsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNewsStoriesBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemNewsStoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        holder.binding.title.setText(""+model.story?.hline)
        holder.binding.description.setText(""+model.story?.intro)

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)


        val image = "https://api2.cricbuzz.com/a/img/v1/i1/c${model.story?.imageId}/i.jpg?p=det&d=high"


        Glide.with(context).load(image).placeholder(R.drawable.custom_progress).apply(requestOptions).into(holder.binding.image)

        if (!model.story?.pubTime.isNullOrEmpty()) {
            try {
                var time = convertTimestampToRelativeDate(model.story?.pubTime.toString().toLong())

                holder.binding.time.setText(time)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        holder.binding.parent.setOnClickListener(View.OnClickListener {
            listener.onClick(model?.story?.id!!)
        })
    }

    fun convertTimestampToRelativeDate(timestamp: Long): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timestampMillis = timestamp.toLong()

        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val differenceMillis = currentTimeMillis - timestampMillis
        val daysDifference = differenceMillis / (1000 * 60 * 60 * 24)

        return if (daysDifference <= 7) {
            if (daysDifference <= 1) {
                "Today"
            } else {
                "${daysDifference.toInt()} days ago"
            }
        } else {
            dateFormat.format(Date(timestampMillis))
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }



}