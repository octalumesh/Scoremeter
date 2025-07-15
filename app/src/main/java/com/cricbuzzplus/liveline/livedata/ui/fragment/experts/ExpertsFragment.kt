package com.cricbuzzplus.liveline.livedata.ui.fragment.experts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentExpertsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel


class ExpertsFragment : BaseFragment() {


    private lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentExpertsBinding

    lateinit var allExpertsFragment: AllExpertsFragment
    lateinit var topExpertsFragment: TopExpertsFragment

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = FragmentExpertsBinding.inflate(inflater, container, false)


        tabArray.add(getString(R.string.experts_top))
        tabArray.add(getString(R.string.experts_all))

        topExpertsFragment = TopExpertsFragment()
        allExpertsFragment = AllExpertsFragment()


        fragmentList.add(topExpertsFragment)
        fragmentList.add(allExpertsFragment)


        val adapter = ViewPagerAdaptorNew(activity.supportFragmentManager, lifecycle,fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lite));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

        return binding.root
    }


}