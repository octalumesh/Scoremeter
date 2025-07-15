package com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentPlayerCareerBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerCareerValuesItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerCareerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel


class PlayerCareerFragment : BaseFragment() {


    lateinit var binding : FragmentPlayerCareerBinding
    private lateinit var viewModel: PlayersViewModel

    var playerName =""
    var playerId =0

    var bowlingList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerCareerBinding.inflate(inflater, container, false)
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

        /*bowlingList.add("T20")
        bowlingList.add("TEST")
        bowlingList.add("ODI")
        bowlingList.add("IPL")
        bowlingList.add("CL")*/

       // binding.recyclerCareer.adapter = PlayerCareerAdapter(bowlingList,activity)

        callPlayerBatting()


        return binding.root
    }


    fun callPlayerBatting() {

        if (playerId != 0) {
            if (isInternetConnection()) {
                viewModel.getPlayerCareer(playerId)
            }
        }
    }


    private fun setObserver(){

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo(){

        viewModel.playerCareerLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null){

                binding.recyclerCareer.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE


                if (!it.values.isNullOrEmpty()) {

                    binding.recyclerCareer.adapter = PlayerCareerAdapter(it.values as List<PlayerCareerValuesItem>, activity)
                }


            }else{
                binding.recyclerCareer.visibility = View.GONE
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