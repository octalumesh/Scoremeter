package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSeriesTableBinding
import com.cricbuzzplus.liveline.databinding.FragmentSeriesVenuesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesVenueItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TypesItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesStatsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesVenuesCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesVenuesFragment : BaseFragment() {

    lateinit var binding: FragmentSeriesVenuesBinding
    private lateinit var viewModel: CricbuzzViewModel
    var seriesId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesVenuesBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        seriesId = arguments?.getInt("seriesId")!!

        callSeries()

        return binding.root
    }

    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesVenues(seriesId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    private fun observeSeries(){

        viewModel.seriesVenuesLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.seriesVenue.isNullOrEmpty() ) {

                    binding.recyclerSeries.adapter = SeriesVenuesCricBuzzAdapter(it.seriesVenue as ArrayList<SeriesVenueItem>,activity)

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