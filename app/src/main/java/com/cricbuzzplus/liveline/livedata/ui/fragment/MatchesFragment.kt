package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentMatchesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel
import java.util.ArrayList

class MatchesFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentMatchesBinding

    lateinit var adapter: ViewPagerAdaptorNew
    var fragmentList = ArrayList<Fragment>()

    val tabArray = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentMatchesBinding.inflate(inflater, container, false)

        tabArray.add(requireActivity().getString(R.string.title_live))
        tabArray.add(requireActivity().getString(R.string.title_upcoming))
        tabArray.add(requireActivity().getString(R.string.title_recent))


        if (fragmentList.isEmpty()) {
            fragmentList.add(LiveHomeFragment())
            fragmentList.add(UpcomingFragment())
            fragmentList.add(RecentMatchesFragment())
        }

        adapter = ViewPagerAdaptorNew(activity.supportFragmentManager, lifecycle,fragmentList)

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),
            resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

        return binding.root
    }


}