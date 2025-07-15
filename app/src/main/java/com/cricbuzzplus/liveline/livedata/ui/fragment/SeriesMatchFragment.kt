package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.SeriesMatchFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.SeriesMatchResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.SeriesMatchesAdaptor
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.SeriesMatchViewModel

class SeriesMatchFragment : BaseFragment() {

    lateinit var binding : SeriesMatchFragmentBinding
    private lateinit var viewModel: SeriesMatchViewModel

     var adapter : SeriesMatchesAdaptor? = null

    var seriesId = 0 ;
    var seriesName = "" ;
    var teamAId = 0
    var teamBId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SeriesMatchViewModel::class.java)
        binding = SeriesMatchFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        val bundle = arguments


        if (bundle != null) {
            seriesId = bundle.getInt("seriesId")
            teamAId = bundle.getInt("teamAId")
            teamBId = bundle.getInt("teamBId")
            seriesName = bundle.getString("seriesName","").toString()
           // matchStatus = bundle.getString("matchStatus").toString()
        } else {
            Log.e("TAG11", "bundle is null")
        }

        Log.e("TAG11", "bundle " +seriesId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callSeriesMatches(seriesId)

    }

    fun callSeriesMatches(seriesId : Int){
        viewModel.getSeriesMatch(seriesId)
    }

    private fun setObservers(){
        observeExtras()
        observeSeries()
    }

    private fun observeSeries() {
        viewModel.getSeriesMatchesLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null){

                if (teamAId == 0) {

                    if (adapter == null) {
                        adapter = SeriesMatchesAdaptor(
                            it as ArrayList<SeriesMatchResponseItem>?,
                            context,
                            seriesName
                        )
                        binding.recyclerMatchList.setAdapter(adapter)
                    } else {
                        adapter?.updateList(it as ArrayList<SeriesMatchResponseItem>?)
                    }
                }else{

                    var matchList = arrayListOf<SeriesMatchResponseItem>()

                    for (item in it){

                        if (item.teamAId == teamAId  || item.teamAId == teamBId || item.teamBId == teamBId  || item.teamBId == teamAId ){

                            matchList.add(item)

                        }

                    }

                    if (!matchList.isNullOrEmpty()){

                        if (adapter == null) {
                            adapter = SeriesMatchesAdaptor(
                                matchList,
                                context,
                                seriesName
                            )
                            binding.recyclerMatchList.setAdapter(adapter)
                        } else {
                            adapter?.updateList(matchList)
                        }

                    }

                }
            }

        })
    }



    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel.getDataLoadErrorLiveData().observe(viewLifecycleOwner,Observer { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })
    }

}