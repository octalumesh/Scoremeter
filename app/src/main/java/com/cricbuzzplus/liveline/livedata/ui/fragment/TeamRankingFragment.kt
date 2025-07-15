package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.databinding.FragmentTeamRankingBinding
import com.cricbuzzplus.liveline.livedata.response.TeamRankingResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.TeamRankingRecyclerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel

class TeamRankingFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentTeamRankingBinding

    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentTeamRankingBinding.inflate(inflater, container, false)

        type = arguments?.getInt("type")!!

        hideKeyBoard()
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == 1){
            binding.teamType.setText("Team Ranking (ODI)")
        }else if (type == 2){
            binding.teamType.setText("Team Ranking (Test)")
        }else if (type == 3){
            binding.teamType.setText("Team Ranking (T20)")
        }

        callTeamRanking()
    }

    fun callTeamRanking(){

        if (isInternetConnection()) {
            viewModel.getTeamRanking(type)
        }

    }

    private fun setObservers(){
        observeLoader()
        observeTeamRanking()
    }

    private fun observeTeamRanking() {
        viewModel.getTeamRankingLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                binding.recyclerRanking.adapter = TeamRankingRecyclerAdapter(activity, it as ArrayList<TeamRankingResponseItem>)

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