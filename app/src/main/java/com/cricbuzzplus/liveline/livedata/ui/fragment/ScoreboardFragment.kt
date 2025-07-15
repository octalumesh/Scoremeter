package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ScoreboardFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ScoreCardItem
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.ScoreboardViewModel
import com.cricbuzzplus.liveline.livedata.ui.adapter.ScorecardBatsmanAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.ScorecardBowlerAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.ScorecardFallWicketAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.ScorecardMainCricBuzzAdapter
import java.util.*
import kotlin.collections.ArrayList

class ScoreboardFragment : BaseFragment() {

    private lateinit var viewModel: ScoreboardViewModel
    lateinit var binding: ScoreboardFragmentBinding

    var matchId = 0
    var matchDate = ""
    var matchStatus = ""

    var matchNo = ""
    var series = ""
    var matchIdCricbuzz = 0

    var adapter :ScorecardMainCricBuzzAdapter ?= null


    private var timer: Timer? = null

    var isUISet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ScoreboardViewModel::class.java)
        binding = ScoreboardFragmentBinding.inflate(inflater, container, false)

        val bundle = arguments

        hideKeyBoard()
        setObservers()

        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            matchDate = bundle.getString("matchDate").toString()
            matchNo = arguments?.getString("matchNo", "").toString()
            series = arguments?.getString("series", "").toString()
        } else {
            Log.d("TAG", "bundle is null")
        }


        if (matchStatus.equals("Upcoming")) {
            binding.progressBar.visibility = View.GONE
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        } else {
            if (matchStatus.equals("Upcoming")) {
                getMatchIdCricBuzzUpcoming(series, matchNo)
            } else if (matchStatus.equals("Finished")) {
                getMatchIdCricBuzzFinished(series, matchNo)
            } else if (matchStatus.equals("Live")) {
                getMatchIdCricBuzzLive(series, matchNo)
            }
            // callScorecard()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // binding.relativeFirst.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.white))


    }




    fun callScorecard() {

        if (matchStatus.equals("Upcoming")) {
            binding.progressBar.visibility = View.GONE
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        } else {
             if (matchStatus.equals("Finished")) {
                 if (isInternetConnection()) {
                     viewModel.getScorecardCricBuzz(matchIdCricbuzz)
                 }
            } else if (matchStatus.equals("Live")) {
                 if (isInternetConnection()) {
                     viewModel.getScorecardCricBuzz(matchIdCricbuzz)
                 }
                 if (timer == null) {
                     timer = Timer()
                 }

                 timer!!.scheduleAtFixedRate(object : TimerTask() {
                     override fun run() {
                         // recyclerViewMaincls();
                         if (isUISet) {
                             if (isInternetConnection()) {
                                 viewModel.getScorecardCricBuzz(matchIdCricbuzz)
                             }
                         }
                     }
                 }, 3000, 3000)
            }
            // callScorecard()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
            isUISet = false
        }

        adapter = null
    }


    private fun setObservers() {
        observeExtras()
        observeScorecard()
        observeMatchId()
    }

    fun observeMatchId() {

        matchIdCricLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                matchIdCricbuzz = it
                Log.e(TAG, "onViewCreated:matchid crci score ${matchIdCricbuzz}")
                callScorecard()
            }
        })

    }

    private fun observeScorecard() {
        viewModel.scoreCardCricbuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {


                    binding.progressBar.visibility = View.GONE
                    binding.parent.visibility = View.VISIBLE

                    isUISet = true

                    if (it.matchHeader != null) {


                        if (it.matchHeader?.state.equals("In Progress", true)) {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                            binding.resultToss.setText("${it?.status}")
                        }else if (it.matchHeader?.state.equals("Innings Break", true)) {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                            binding.resultToss.setText("${it.matchHeader?.state}")
                        }else if (it.matchHeader?.state.equals("Stumps", true)) {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                            binding.resultToss.setText("${it?.status}")
                        } else if (it.matchHeader?.state.equals("Preview", true)) {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.orange))
                            binding.resultToss.setText("${it?.status}")
                        } else if (it.matchHeader.state.equals("Complete", true)) {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.bluene))
                            binding.resultToss.setText("${it?.status}")
                        } else {
                            binding.resultToss.setTextColor(activity.resources.getColor(R.color.txt_color))
                            binding.resultToss.setText("${it?.status}")
                        }

                    }




                    if (!it.scoreCard.isNullOrEmpty()){

                        if (adapter == null){
                            adapter = ScorecardMainCricBuzzAdapter(it.scoreCard as ArrayList<ScoreCardItem>,activity,matchStatus)

                            binding.recyclerScorecard.adapter = adapter

                        }else{
                            adapter?.updateList(it.scoreCard as ArrayList<ScoreCardItem>)
                        }


                    }



                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "observeScorecard: ${e.message}", )
                }


            }

        })
    }


    private fun observeExtras() {
        /*viewModel!!.getLoaderLiveData().observe(activity,
            { isLoading -> handleProgressLoader(isLoading!!) })
            viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

}