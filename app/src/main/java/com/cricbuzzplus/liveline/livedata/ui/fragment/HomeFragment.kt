package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentHomeBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.livedata.ui.fragment.news.NewsFragment
import java.util.ArrayList

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding

    // lateinit var pagerAdapter: ViewPagerAdapter
    //  lateinit var adapter: ViewPagerAdaptorNew
    var fragmentList = ArrayList<Fragment>()

    val tabArray = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*tabArray.add(requireActivity().getString(R.string.title_home))
        tabArray.add(requireActivity().getString(R.string.title_live))
        tabArray.add(requireActivity().getString(R.string.title_upcoming))
        tabArray.add(requireActivity().getString(R.string.title_finished))
        tabArray.add(requireActivity().getString(R.string.title_news))*/

        tabArray.add("FEATURED")
        tabArray.add("CRICBUZZ PLUS")


        /*if (fragmentList.isEmpty()) {

            fragmentList.add(HomeMainFragment())
            fragmentList.add(LiveHomeFragment())
            //fragmentList.add(UpcomingFragment())
            //fragmentList.add(RecentMatchesFragment())
            //fragmentList.add(NewsFragment())

        }
        val adapter = ViewPagerAdaptorNew(activity.supportFragmentManager, lifecycle, fragmentList)

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white_lgt),
            resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        binding.pager.offscreenPageLimit = fragmentList.size - 1

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()*/


        val viewpagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewpagerAdapter.addFragment(HomeMainFragment(), "FEATURED")
        ///////////////////////////////////////////////////////////
        viewpagerAdapter.addFragment(LiveHomeFragment(), "CRICBUZZ PLUS")

        binding.pager.adapter = viewpagerAdapter


        //swipe page left to right than fragment change next show
        binding.tabLayout.setupWithViewPager( binding.pager)

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),
            resources.getColor(R.color.white))


        return binding.root
    }


}