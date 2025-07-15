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
import com.cricbuzzplus.liveline.databinding.FragmentTeamPlayersBinding
import com.cricbuzzplus.liveline.databinding.FragmentTeamResultBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamMatchesDataItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamPlayerItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamPlayerCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamsMatchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class TeamPlayersFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentTeamPlayersBinding

    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamPlayersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        teamId = arguments?.getInt("teamId")!!

        callTeam()

        return binding.root
    }


    fun callTeam(){
        if (isInternetConnection()) {
            viewModel.getTeamPlayers(teamId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeTeam()

    }


    private fun observeTeam(){

        viewModel.teamPlayersLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.player.isNullOrEmpty()) {

                    try {

                        binding.recyclerSeries.adapter = TeamPlayerCricAdapter(
                            it.player as ArrayList<TeamPlayerItem>,
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