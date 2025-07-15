package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.databinding.FragmentTeamTypeBinding
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class TeamTypeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentTeamTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentTeamTypeBinding.inflate(inflater, container, false)

        hideKeyBoard()

        MobileAds.initialize(activity) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView1.loadAd(adRequest)

        binding.odi.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_team_ranking,
                bundleOf("type" to 1))
        })

        binding.test.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_team_ranking,
                bundleOf("type" to 2))
        })

        binding.t20.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_team_ranking,
                bundleOf("type" to 3))
        })

        return binding.root
    }



}