package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentScheduleInternationalBinding
import com.cricbuzzplus.liveline.databinding.FragmentScheduleLeagueBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchScheduleMapItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.ScheduleMainCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class ScheduleLeagueFragment : BaseFragment() {


    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentScheduleLeagueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleLeagueBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()


        callSchedule()

        return binding.root
    }


    fun callSchedule(){
        if (isInternetConnection()) {
            viewModel.getScheduleLeague()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSchedule()

    }


    private fun observeSchedule(){

        viewModel.scheduleLeagueLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.matchScheduleMap.isNullOrEmpty()) {

                    try {

                        binding.recyclerSeries.adapter = ScheduleMainCricBuzzAdapter(
                            it.matchScheduleMap as ArrayList<MatchScheduleMapItem>,
                            activity
                        )
                    }catch (e:Exception){
                        e.printStackTrace()


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