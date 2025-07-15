package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSeriesMatchesBinding
import com.cricbuzzplus.liveline.databinding.FragmentSeriesTableBinding
import com.cricbuzzplus.liveline.databinding.ItemNewsTopicsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PointTableCricResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PointsTableItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SeriesScheduleMatchDetailsItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamsItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesMatchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesPointTableCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesPointTableCricBuzzMainAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesPointTableCricBuzzMainNewAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class SeriesTableFragment : BaseFragment() {


    lateinit var binding: FragmentSeriesTableBinding
    private lateinit var viewModel: CricbuzzViewModel
    var seriesId = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesTableBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        seriesId = arguments?.getInt("seriesId")!!

        callSeries()


        return binding.root
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesPointTable(seriesId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    /*private fun observeSeries(){

        viewModel.seriesPointTableLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.order.isNullOrEmpty() ) {

                    var list = arrayListOf<HashMap<String,ArrayList<TeamsItem>>>()


                        for (group in it.order) {

                            var hashListPoint  = HashMap<String,ArrayList<TeamsItem>>()

                            val groupList = it.group?.get(group.toString())

                            hashListPoint.put(group.toString(), groupList as ArrayList<TeamsItem>)

                            list.add(hashListPoint)
                            //Log.e("TAGvsv", "observeSeries: $group ==>   "+groupList )
                        }


                        if (!list.isNullOrEmpty()){
                            binding.recyclerPointTable.adapter = SeriesPointTableCricBuzzMainAdapter(list,activity)
                        }



                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.parent.visibility = View.GONE
            }

        })

    }*/


    private fun observeSeries(){

        viewModel.seriesPointTableLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.pointsTable.isNullOrEmpty() ) {

                        binding.recyclerPointTable.adapter = SeriesPointTableCricBuzzMainNewAdapter(
                            it?.pointsTable as ArrayList<PointsTableItem>,activity)

                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.parent.visibility = View.GONE
            }

        })

    }



    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }

}