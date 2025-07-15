package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSeriesSquadBinding
import com.cricbuzzplus.liveline.databinding.FragmentSeriesTableBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SquadsItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamsItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesSquadActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesPointTableCricBuzzMainAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesSquadCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesSquadFragment : BaseFragment() {

    lateinit var binding: FragmentSeriesSquadBinding
    private lateinit var viewModel: CricbuzzViewModel
    var seriesId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesSquadBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        seriesId = arguments?.getInt("seriesId")!!

        callSeries()

        return binding.root
    }

    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesSquad(seriesId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    private fun observeSeries(){

        viewModel.seriesSquadLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.squads.isNullOrEmpty() ) {

                        binding.recyclerSeries.adapter = SeriesSquadCricBuzzAdapter(it.squads as ArrayList<SquadsItem>,activity,seriesId)

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