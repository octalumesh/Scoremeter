package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.PointTableFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.PointListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.SeriesPointTableAdaptor
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PointTableViewModel

class PointTableFragment : BaseFragment() {

    lateinit var binding: PointTableFragmentBinding
    private lateinit var viewModel: PointTableViewModel

    var seriesId = 0
     var adapter : SeriesPointTableAdaptor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(PointTableViewModel::class.java)
        binding = PointTableFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        val bundle = arguments


        if (bundle != null) {
            seriesId = bundle.getInt("seriesId")
            // matchStatus = bundle.getString("matchStatus").toString()
        } else {
            Log.e("TAG11", "bundle is null")
        }

        Log.e("TAG11", "bundle " +seriesId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callPointTable(seriesId)
    }

    fun callPointTable(seriesId : Int){
        viewModel.getPointTable(seriesId)
    }


    private fun setObservers(){
        observeExtras()
        observeSeries()
    }

    private fun observeSeries() {
        viewModel.getPointTableLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null){

                if (adapter == null) {
                    adapter = SeriesPointTableAdaptor(it as ArrayList<PointListResponseItem>?, context)
                    binding.recyclerviewSeriesPointtable.setAdapter(adapter)
                } else {
                    adapter?.updateList(it as ArrayList<PointListResponseItem>?)
                }
            }

        })
    }



    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        /*viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }



}