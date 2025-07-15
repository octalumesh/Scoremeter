package com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentPlayerBowlingBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItemBowling
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerBowlingStatAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel


class PlayerBowlingFragment : BaseFragment() {


    lateinit var binding : FragmentPlayerBowlingBinding
    private lateinit var viewModel: PlayersViewModel

    var playerName =""
    var playerId =0

    var bowlingList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBowlingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PlayersViewModel::class.java)

        hideKeyBoard()
        setObserver()

        val bundle = arguments
        if (bundle != null) {

            playerName = bundle.getString("playerName").toString()
            playerId = bundle.getInt("playerId",0)
        } else {
            Log.d("TAG", "bundle is null")
        }

        /*bowlingList.add("Matches")
        bowlingList.add("Innings")
        bowlingList.add("Runs")
        bowlingList.add("Balls")
        bowlingList.add("Maidens")
        bowlingList.add("Wickets")
        bowlingList.add("Avg")
        bowlingList.add("Eco")
        bowlingList.add("SR")
        bowlingList.add("BBI")
        bowlingList.add("BBM")
        bowlingList.add("4w")
        bowlingList.add("5w")*/



        callPlayerBatting()


        return binding.root
    }


    fun callPlayerBatting() {

        if (playerId != 0) {
            if (isInternetConnection()) {
                viewModel.getPlayerBowling(playerId)
            }
        }
    }


    private fun setObserver(){

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo(){

        viewModel.playerBowlingLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null){

                binding.parent.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE


                if (!it.values.isNullOrEmpty()) {

                    binding.recyclerPlayerBowling.adapter =
                        PlayerBowlingStatAdapter(it.values as ArrayList<ValuesItemBowling>, activity)
                }


            }else{
                binding.parent.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })

    }


    private fun observeExtras() {
        viewModel.loaderLiveData.observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(viewLifecycleOwner, Observer{ s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })
    }

}