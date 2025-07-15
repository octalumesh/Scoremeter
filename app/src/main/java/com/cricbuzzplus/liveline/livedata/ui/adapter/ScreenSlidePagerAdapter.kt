package com.cricbuzzplus.liveline.livedata.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ScreenSlidePagerAdapter(fa: FragmentActivity,val fragmentList: ArrayList<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList.get(position)

}