package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentSeriesWomenBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesMapProtoItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesWomenFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentSeriesWomenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesWomenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        callSeries()

        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesWomen()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }


    private fun observeSeries(){

        viewModel.seriesWomenLiveData.observe(viewLifecycleOwner, Observer{

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