package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.graphics.Paint
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R

class DataBindingAdapter {

    companion object {



        @JvmStatic
        @BindingAdapter("bind:loadimage")
        fun loadimage(view: ImageView, url: String?) {
            val requestOptions:RequestOptions = RequestOptions()
            requestOptions.placeholder(R.mipmap.ic_launcher)
            requestOptions.error(R.mipmap.ic_launcher)
            requestOptions.dontTransform()
            Glide.with(view.context.applicationContext).load("" + url)
                .apply(requestOptions).into(view)
        }


        @JvmStatic
        @BindingAdapter("bind:setStrikeText")
        fun setStrikeText(textView: TextView, data: String) {
            textView.setText(data)
            textView.setPaintFlags(textView.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        }



    }

}
