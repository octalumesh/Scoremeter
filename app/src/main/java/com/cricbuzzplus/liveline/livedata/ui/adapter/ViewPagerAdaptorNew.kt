package com.cricbuzzplus.liveline.livedata.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 3

class ViewPagerAdaptorNew(fragmentManager: FragmentManager, lifecycle: Lifecycle,val list : List<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle)  {



    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {

        return list.get(position)
    }


}