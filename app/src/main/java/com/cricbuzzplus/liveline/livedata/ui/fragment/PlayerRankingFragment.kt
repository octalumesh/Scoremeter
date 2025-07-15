package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.cricbuzzplus.liveline.databinding.FragmentPlayerRankingBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.PlayerRankingResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerRankingRecyclerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel

class PlayerRankingFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentPlayerRankingBinding

    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentPlayerRankingBinding.inflate(inflater, container, false)

        type = arguments?.getInt("type")!!

        hideKeyBoard()
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == 1){
            binding.rankingType.setText("Player Ranking (Test Batsman)")
        }else if (type == 2){
            binding.rankingType.setText("Player Ranking (Test Bowlers)")
        }else if (type == 3){
            binding.rankingType.setText("Player Ranking (Test Allrounders)")
        }
        else if (type == 4){
            binding.rankingType.setText("Player Ranking (ODI Batsman)")
        }else if (type == 5){
            binding.rankingType.setText("Player Ranking (ODI Bowlers)")
        }else if (type == 6){
            binding.rankingType.setText("Player Ranking (ODI Allrounders)")
        }
        else if (type == 7){
            binding.rankingType.setText("Player Ranking (T20 Batsman)")
        }else if (type == 8){
            binding.rankingType.setText("Player Ranking (T20 Bowlers)")
        }else if (type == 9){
            binding.rankingType.setText("Player Ranking (T20 Allrounders)")
        }
        else if (type == 10){
            binding.rankingType.setText("Player Ranking (Women's ODI Batting)")
        }else if (type == 11){
            binding.rankingType.setText("Player Ranking (Women's ODI Bowling)")
        }else if (type == 12){
            binding.rankingType.setText("Player Ranking (Women's ODI Allrounders)")
        }
        else if (type == 13){
            binding.rankingType.setText("Player Ranking (Women's T20 Batting)")
        }else if (type == 14){
            binding.rankingType.setText("Player Ranking (Women's T20 Bowling)")
        }else if (type == 15){
            binding.rankingType.setText("Player Ranking (Women's T20 Allrounders)")
        }

        if (type != 0) {
            callPlayerRanking()
        }
    }

    fun callPlayerRanking(){

        if (isInternetConnection()) {
            viewModel.getPlayerRanking(type)
        }

    }

    private fun setObservers(){
        observeLoader()
        observePlayerRanking()
    }

    private fun observePlayerRanking() {
        viewModel.getPlayerRankingLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                binding.recyclerRanking.adapter = PlayerRankingRecyclerAdapter(activity, it as ArrayList<PlayerRankingResponseItem>)

            }
        })
    }

    private fun observeLoader() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner, Observer {

            if (it) {

                binding.progressBar.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE

            }else{
                if (!binding.parentRanking.isVisible) {
                    binding.progressBar.visibility = View.GONE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE
                    binding.parentRanking.visibility = View.VISIBLE
                }
            }
        })
    }


}