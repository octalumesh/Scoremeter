package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentArchivesLeaguesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ArchivesMapProtoItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.ArchivesCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class ArchivesLeaguesFragment : BaseFragment() {


    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentArchivesLeaguesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchivesLeaguesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        callSeries()

        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getArchivesLeagues()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }


    private fun observeSeries(){

        viewModel.archivesT20LeagueLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                binding.recyclerSeries.adapter = ArchivesCricBuzzAdapter(it.seriesMapProto as ArrayList<ArchivesMapProtoItem>,activity)

            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }
}