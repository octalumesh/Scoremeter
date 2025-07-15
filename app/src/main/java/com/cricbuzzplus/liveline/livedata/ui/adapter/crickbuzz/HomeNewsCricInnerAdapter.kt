package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeMatchesBindingNewCric
import com.cricbuzzplus.liveline.databinding.HomeNewsBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCricHomeBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsCricHomeInnerBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomepageItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class HomeNewsCricInnerAdapter(
    val list: List<HomepageItem>,
    val context: Context
) : RecyclerView.Adapter<HomeNewsCricInnerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNewsCricHomeInnerBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemNewsCricHomeInnerBinding.inflate(
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
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (model.stories?.cardType.equals("NewsBig")) {
            holder.binding.linearBig.visibility = View.VISIBLE
            holder.binding.linearRsSmall.visibility = View.GONE
            holder.binding.linearSmall.visibility = View.GONE
            holder.binding.linearRsVideo.visibility = View.GONE
            holder.binding.linearVideo.visibility = View.GONE

            holder.binding.titleHead.setText("" + model?.stories?.context)
            holder.binding.titleBigNews.setText("" + model?.stories?.headline)
            holder.binding.descriptionBig.setText("" + model?.stories?.intro)
            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.stories?.imageId + Constants.cricbuzzImgSecond)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.binding.imageBigNews)

        } else if (model.stories?.cardType.equals("RSNewsBig")) {
            holder.binding.linearRsSmall.visibility = View.VISIBLE
            holder.binding.linearBig.visibility = View.GONE
            holder.binding.linearSmall.visibility = View.GONE
            holder.binding.linearRsVideo.visibility = View.GONE
            holder.binding.linearVideo.visibility = View.GONE

            holder.binding.title.setText("" + model?.stories?.headline)
            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.stories?.imageId + Constants.cricbuzzImghigh)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.binding.imageNews)


        } else if (model.stories?.cardType.equals("Video")) {
            holder.binding.linearRsSmall.visibility = View.GONE
            holder.binding.linearBig.visibility = View.GONE
            holder.binding.linearSmall.visibility = View.GONE
            holder.binding.linearRsVideo.visibility = View.GONE
            holder.binding.linearVideo.visibility = View.VISIBLE

            holder.binding.headVideoSmall.setText("" + model?.stories?.context)
            holder.binding.titleVideoSmall.setText("" + model?.stories?.headline)
            holder.binding.descriptionVideoSmall.setText("" + model?.stories?.intro)
            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.stories?.imageId + Constants.cricbuzzImghigh)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.binding.imageVideo)


        } else if (model.stories?.cardType.equals("RSVideo")) {
            holder.binding.linearRsSmall.visibility = View.GONE
            holder.binding.linearBig.visibility = View.GONE
            holder.binding.linearSmall.visibility = View.GONE
            holder.binding.linearRsVideo.visibility = View.VISIBLE
            holder.binding.linearVideo.visibility = View.GONE

            holder.binding.titleRsVideo.setText("" + model?.stories?.headline)
            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.stories?.imageId + Constants.cricbuzzImghigh)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.binding.imageNewsRsVideo)


        } else {

            holder.binding.linearBig.visibility = View.GONE
            holder.binding.linearRsSmall.visibility = View.GONE
            holder.binding.linearSmall.visibility = View.VISIBLE
            holder.binding.linearRsVideo.visibility = View.GONE
            holder.binding.linearVideo.visibility = View.GONE

            holder.binding.titleHeadSmall.setText("" + model?.stories?.context)
            holder.binding.titleSmall.setText("" + model?.stories?.headline)
            if (!model?.stories?.intro.isNullOrEmpty()) {
                holder.binding.descriptionSmall.setText("" + model?.stories?.intro)
            }
            Glide.with(context)
                .load("" + Constants.cricbuzzImgFirst + model.stories?.imageId + Constants.cricbuzzImghigh)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.binding.imageSmall)

        }


        holder.binding.linearBig.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("newsId", model.stories?.itemId)
            context.startActivity(intent)
        }

        holder.binding.linearSmall.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("newsId", model.stories?.itemId)
            context.startActivity(intent)
        }

        holder.binding.linearRsSmall.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("newsId", model.stories?.itemId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}