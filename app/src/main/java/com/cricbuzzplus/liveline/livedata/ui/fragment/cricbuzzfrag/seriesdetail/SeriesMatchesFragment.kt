package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentSeriesMatchesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesScheduleMatchDetailsItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesMatchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesMatchesFragment : BaseFragment() {


    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentSeriesMatchesBinding

    var seriesId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesMatchesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        seriesId = arguments?.getInt("seriesId")!!

        callSeries()

        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesMatches(seriesId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }


    private fun observeSeries(){

        viewModel.seriesMatchesLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.matchDetails.isNullOrEmpty()) {
                    var firstPosition = -1
                    try {

                        for (index in 0 until it.matchDetails.size) {
                            val matchInfo =
                                it.matchDetails[index]?.matchDetailsMap!!?.match!![0]?.matchInfo
                            if (matchInfo?.state != "Complete") {
                                firstPosition = index
                                break
                            }
                        }
                    }catch (e:Exception){
                        e.printStackTrace()

                        binding.recyclerSeries.adapter = SeriesMatchCricBuzzAdapter(
                            it.matchDetails as ArrayList<SeriesScheduleMatchDetailsItem>,
                            activity
                        )
                    }

                    binding.recyclerSeries.adapter = SeriesMatchCricBuzzAdapter(
                        it.matchDetails as ArrayList<SeriesScheduleMatchDetailsItem>,
                        activity
                    )

                    if (firstPosition != -1){
                        binding.recyclerSeries.scrollToPosition(firstPosition)
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