package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FixturesFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import java.util.ArrayList

class FixturesFragment : BaseFragment() {

    lateinit var binding: FixturesFragmentBinding
    private lateinit var viewModel: UsersViewModel
   // lateinit var pagerAdapter: ViewPagerAdapter
   var fragmentList = ArrayList<Fragment>()
    lateinit var adapter :ViewPagerAdaptorNew

    val tabArray = arrayOf(
        "Upcoming",
        "Recent"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = FixturesFragmentBinding.inflate(inflater, container, false)

        /*pagerAdapter= ViewPagerAdapter(activity?.supportFragmentManager)

        pagerAdapter.addFragment(UpcomingFragment(),"Upcoming")
        pagerAdapter.addFragment(RecentMatchesFragment(),"Recent")
        binding.pager.adapter=pagerAdapter

        binding.tabLayout.setupWithViewPager(binding.pager)*/
        if (fragmentList.isEmpty()) {
            fragmentList.add(UpcomingFragment())
            fragmentList.add(RecentMatchesFragment())
        }

        adapter = ViewPagerAdaptorNew(activity.supportFragmentManager, lifecycle,fragmentList)
       
        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lite));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

       /* binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Upcoming"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Recent"));
        binding.pager.setAdapter( ScreenSlidePagerAdapter(activity!!,fragmentList));

         binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
               // binding.pager.setCurrentItem(tab.position)
            }
        })



        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
              //  super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lite));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.black),resources.getColor(R.color.white))*/

        return binding.root
    }



}