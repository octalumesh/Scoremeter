package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.databinding.SliderBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.SliderImage
import com.huanhailiuxin.coolviewpager.CoolViewPager

import android.content.Intent
import android.net.Uri
import com.cricbuzzplus.liveline.livedata.response.newresponse.BannerListItem
import com.cricbuzzplus.liveline.livedata.response.newresponse.DocsItem
import com.cricbuzzplus.liveline.utils.Constants


class HomeSliderViewPagerAdapter(val activity: BaseActivity, val list: List<BannerListItem?>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ImageView
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: SliderBinding =
            SliderBinding.inflate(LayoutInflater.from(activity), container, false)
        Glide.with(activity).load(Constants.ImgURl+list[position]?.image).into(binding.imageView)
        //binding.setListitem(queList.get(i));

        //binding.setListitem(queList.get(i));

        binding.imageView.setOnClickListener {
            try {
                val url = list[position]?.link.toString()
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                activity.startActivity(i)
            }catch (e:Exception){
              // Toast.makeText(activity,e.message.toString(),Toast.LENGTH_SHORT).show()
            }
          /*  val bundel= bundleOf("FeaturedProductItem" to queList[position])
            activity.findNavController(R.id.nav_host_fragment).navigate(R.id.redirect_feature_product,bundel)*/
        }


        (container as CoolViewPager).addView(binding.getRoot(), 0)
        return binding.getRoot()
    }


}