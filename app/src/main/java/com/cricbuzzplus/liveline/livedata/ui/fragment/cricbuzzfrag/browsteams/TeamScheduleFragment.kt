package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.browsteams

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSeriesMatchesBinding
import com.cricbuzzplus.liveline.databinding.FragmentTeamScheduleBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesScheduleMatchDetailsItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamMatchesDataItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesMatchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamsMatchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class TeamScheduleFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentTeamScheduleBinding

    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamScheduleBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        teamId = arguments?.getInt("teamId")!!

        callTeam()

        return binding.root
    }


    fun callTeam(){
        if (isInternetConnection()) {
            viewModel.getTeamSchedule(teamId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeTeam()

    }


    private fun observeTeam(){

        viewModel.teamScheduleLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.teamMatchesData.isNullOrEmpty()) {
                    var firstPosition = -1
                    try {

                        binding.recyclerSeries.adapter = TeamsMatchCricBuzzAdapter(
                            it.teamMatchesData as ArrayList<TeamMatchesDataItem>,
                            activity
                        )
                    }catch (e:Exception){
                        e.printStackTrace()

                        Log.e(TAG, "observeSeries: ${e.printStackTrace()}", )
                    }

                }
            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}