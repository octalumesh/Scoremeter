package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentArchivesDomesticBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ArchivesMapProtoItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.ArchivesCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class ArchivesDomesticFragment : BaseFragment() {


    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentArchivesDomesticBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchivesDomesticBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        callSeries()

        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getArchivesDomestic()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }


    private fun observeSeries(){

        viewModel.archivesDomesticLiveData.observe(viewLifecycleOwner, Observer{

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