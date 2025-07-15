package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentTeamsLeaguesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ListItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamsBrowsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class TeamsLeaguesFragment : BaseFragment() {


    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentTeamsLeaguesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsLeaguesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        callTeams()

        return binding.root
    }


    fun callTeams(){
        if (isInternetConnection()) {
            viewModel.getTeamsLeague()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeTeams()

    }


    private fun observeTeams(){

        viewModel.teamsT20LeagueLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                binding.recyclerTeams.adapter = TeamsBrowsCricBuzzAdapter(it.list as ArrayList<ListItem>,activity)

            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }
}