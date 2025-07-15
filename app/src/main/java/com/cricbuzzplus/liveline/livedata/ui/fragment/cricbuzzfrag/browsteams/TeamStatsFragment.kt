package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.browsteams

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentTeamNewsBinding
import com.cricbuzzplus.liveline.databinding.FragmentTeamScheduleBinding
import com.cricbuzzplus.liveline.databinding.FragmentTeamStatsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TypesItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesStatsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamStatsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class TeamStatsFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentTeamStatsBinding

    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamStatsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        teamId = arguments?.getInt("teamId")!!

        callTeam()

        return binding.root
    }


    fun callTeam(){
        if (isInternetConnection()) {
            viewModel.getTeamStats(teamId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeTeam()

    }


    private fun observeTeam(){

        viewModel.teamStatsLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.types.isNullOrEmpty() ) {

                    binding.recyclerSeries.adapter = TeamStatsCricBuzzAdapter(it.types as ArrayList<TypesItem>,activity,teamId)

                }else{
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    binding.recyclerSeries.visibility = View.GONE
                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.recyclerSeries.visibility = View.GONE
            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}