package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPhotoCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemPhotoGalleryCricbuzzBinding
import com.cricbuzzplus.liveline.databinding.ItemPlayerCricbuzzBinding
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PhotoDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PhotoGalleryActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhotosGalleryCricBuzzAdapter(
    var list: ArrayList<PhotoGalleryDetailsItem>,
    val context: Context,
) : RecyclerView.Adapter<PhotosGalleryCricBuzzAdapter.MyViewHolder>() {

    fun updateList( list: ArrayList<PhotoGalleryDetailsItem>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemPhotoGalleryCricbuzzBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPhotoGalleryCricbuzzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=list[position]

        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model != null) {



            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.imageId + Constants.cricbuzzImgSecond)
                .apply(requestOptions).into(holder.binding.image)



            holder.binding.image.setOnClickListener {

                // onClickInterface.onClick(model)

                val intent = Intent(context, PhotoDetailActivity::class.java)
                intent.putExtra("url", "" + Constants.cricbuzzImgFirst + model.imageId + Constants.cricbuzzImgSecond)
                intent.putExtra("caption", model.caption)
                context.startActivity(intent)
            }

        }


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