package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentPlayerRankingTypeBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel

class PlayerRankingTypeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentPlayerRankingTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentPlayerRankingTypeBinding.inflate(inflater, container, false)

        hideKeyBoard()

        binding.testBatsman.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 1)
            )
        })

        binding.testBowler.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 2)
            )
        })

        binding.testAllrounder.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 3)
            )
        })

        binding.odiBatsman.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 4)
            )
        })
        binding.odiBowler.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 5)
            )
        })
        binding.odiAllrounder.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 6)
            )
        })
        binding.t20Batsman.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 7)
            )
        })
        binding.t20Bowler.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 8)
            )
        })
        binding.t20Allrounder.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 9)
            )
        })
        binding.womenOdiBatsman.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 10)
            )
        })
        binding.womenOdiBowler.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 11)
            )
        })

        binding.womenOdiAllrounder.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 12)
            )
        })
        binding.womenT20Batsman.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 13)
            )
        })

        binding.womenT20Bowler.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 14)
            )
        })
        binding.womenT20Allrounder.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_player_ranking,
                bundleOf("type" to 15)
            )
        })



        return binding.root
    }



}