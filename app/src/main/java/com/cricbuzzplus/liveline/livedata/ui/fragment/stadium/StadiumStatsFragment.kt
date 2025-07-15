package com.cricbuzzplus.liveline.livedata.ui.fragment.stadium

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentStadiumStatsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.VenueStatsItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.StadiumStatsAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.StadiumViewModel


class StadiumStatsFragment : BaseFragment() {

    lateinit var binding: FragmentStadiumStatsBinding
    private lateinit var viewModel: StadiumViewModel

    var stadiumId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStadiumStatsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(StadiumViewModel::class.java)

        hideKeyBoard()
        setObserver()

        val bundle = arguments
        if (bundle != null) {

            stadiumId = bundle.getInt("stadiumId", 0)
        } else {
            Log.d("TAG", "bundle is null")
        }



        callPlayerInfo()

        return binding.root
    }

    fun callPlayerInfo() {

        if (stadiumId != 0) {
            if (isInternetConnection()) {
                viewModel.getStadiumStats(stadiumId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo() {

        viewModel.stadiumStatsLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {

                    binding.parent.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                    if (!it.venueStats.isNullOrEmpty()) {
                        binding.recyclerStats.adapter =
                            StadiumStatsAdapter(it.venueStats as List<VenueStatsItem>, activity)
                    }else {
                        binding.parent.visibility = View.GONE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else {
                 binding.parent.visibility = View.GONE
                 binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })

    }


    private fun observeExtras() {
        viewModel.loaderLiveData.observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(viewLifecycleOwner, Observer { s ->
            Log.e(TAG, "onChanged: $s")
            if (!s.toString().isNullOrEmpty()) {
                showToast(s.toString())
                binding.parent.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }
           // handleError(s.toString())
        })
    }

}