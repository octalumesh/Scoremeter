package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentSeriesT20LeaguesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesMapProtoItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesT20LeaguesFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentSeriesT20LeaguesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesT20LeaguesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        callSeries()

        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesLeagues()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }


    private fun observeSeries(){

        viewModel.seriesT20LiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                binding.recyclerSeries.adapter = SeriesCricBuzzAdapter(it.seriesMapProto as ArrayList<SeriesMapProtoItem>,activity)

            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}