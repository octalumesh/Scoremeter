package com.cricbuzzplus.liveline.livedata.ui.fragment.stadium

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentStadiumMatchesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.StadiumMatchesAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.StadiumViewModel


class StadiumMatchesFragment : BaseFragment() {

    lateinit var binding: FragmentStadiumMatchesBinding
    private lateinit var viewModel: StadiumViewModel

    var stadiumId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStadiumMatchesBinding.inflate(inflater, container, false)
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
                viewModel.getStadiumMatches(stadiumId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }
    var list = arrayListOf<MatchItem>()
    private fun observePlayerInfo() {

        viewModel.stadiumMatchesLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {
                    binding.recyclerStadiumMatches.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE



                    if (!list.isNullOrEmpty()){
                        list.clear()
                    }

                    if (!it.matchDetails.isNullOrEmpty()){

                        for (items in it.matchDetails){

                            binding.series.setText(items?.matchDetailsMap?.key)

                            if (!items?.matchDetailsMap?.match.isNullOrEmpty()){

                                list.addAll(items?.matchDetailsMap?.match as ArrayList<MatchItem>)

                            }

                        }

                        if (!list.isNullOrEmpty()){

                            binding.recyclerStadiumMatches.adapter = StadiumMatchesAdapter(list,activity)

                        }


                    }



                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else {
                 binding.recyclerStadiumMatches.visibility = View.GONE
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
                binding.recyclerStadiumMatches.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }
          //  handleError(s.toString())
        })
    }


}