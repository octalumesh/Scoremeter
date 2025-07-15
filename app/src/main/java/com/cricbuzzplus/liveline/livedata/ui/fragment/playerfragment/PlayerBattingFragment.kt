package com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentPlayerBattingBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerBattingStatAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel


class PlayerBattingFragment : BaseFragment() {

    lateinit var binding : FragmentPlayerBattingBinding
    private lateinit var viewModel: PlayersViewModel

    var playerName =""
    var playerId =0

    var battingList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBattingBinding.inflate(inflater, container, false)
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

        /*battingList.add("Matches")
        battingList.add("Innings")
        battingList.add("Runs")
        battingList.add("Balls")
        battingList.add("Highest")
        battingList.add("Average")
        battingList.add("SR")
        battingList.add("Not Out")
        battingList.add("Fours")
        battingList.add("Sixes")
        battingList.add("Ducks")
        battingList.add("50s")
        battingList.add("100s")*/



        callPlayerBatting()


        return binding.root
    }


    fun callPlayerBatting() {

        if (playerId != 0) {
            if (isInternetConnection()) {
                viewModel.getPlayerBatting(playerId)
            }
        }
    }


    private fun setObserver(){

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo(){

        viewModel.playerBattingLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null){

                binding.parent.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE


                if (!it.values.isNullOrEmpty()) {

                    binding.recyclerPlayerBatting.adapter =
                        PlayerBattingStatAdapter(it.values as ArrayList<ValuesItem>, activity)
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