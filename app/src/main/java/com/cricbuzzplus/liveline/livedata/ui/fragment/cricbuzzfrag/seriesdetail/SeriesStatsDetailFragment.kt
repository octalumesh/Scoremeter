package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSeriesStatesBinding
import com.cricbuzzplus.liveline.databinding.FragmentSeriesStatsDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesStatsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class SeriesStatsDetailFragment : BaseFragment() {

    lateinit var binding: FragmentSeriesStatsDetailBinding
    private lateinit var viewModel: CricbuzzViewModel
    var seriesId = 0
    var seriesType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesStatsDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        seriesId = arguments?.getInt("seriesId")!!
        seriesType = arguments?.getString("seriesType")!!


        if (seriesType.equals("Test")) {

            binding.tesTe.setText("this is Test   ?dcdsc")
        } else if (seriesType.equals("Odi")) {
            binding.tesTe.setText("this is ODI   ?----")
        } else if (seriesType.equals("T20")) {
            binding.tesTe.setText("this is T20   ?,,,,,,,")
        }

        // callSeries()

        return binding.root
    }

    fun setObservers() {



    }

}