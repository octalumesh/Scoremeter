package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.InfoFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.MatchesItem
import com.cricbuzzplus.liveline.livedata.response.RecentMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BroadcasterItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchByIdMatchInfo
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchByIdResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.Venue
import com.cricbuzzplus.liveline.livedata.response.newresponse.TeamAMatchlistItem
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.StadiumActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.*
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.InfoViewModel
import com.cricbuzzplus.liveline.mPrefs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InfoFragment : BaseFragment() {


    private lateinit var viewModel: InfoViewModel
    lateinit var binding: InfoFragmentBinding

    var matchId = 0
    var matchStatus = ""
    var result = ""
    var toss = ""

    var matchPredicted = false
    var tossPredicted = false
    var fiveOverComplete = false

    var seriesName = ""
    var teamAShort = ""
    var teamBShort = ""
    var teamA = ""
    var teamB = ""
    var teamAId = 0
    var teamBId = 0
    var teamAImg = ""
    var teamBImg = ""
    var type = ""
    var tossPredict = ""
    var matchPredict = ""
    var match_type = ""
    var matchDate = ""

    var matchNo = ""
    var series = ""
    var matchIdCricbuzz = 0

    var venueInfo: Venue? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        binding = InfoFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        val bundle = arguments


        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            result = bundle.getString("result").toString()
            matchNo = arguments?.getString("matchNo", "").toString()
            series = arguments?.getString("series", "").toString()
        } else {
            Log.d("TAG", "bundle is null")
        }

        Log.e(TAG, "onCreateView: " + matchId + " " + matchStatus)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callMatchInfo(matchId)


        binding.goToSquad.setOnClickListener {
            MainApplication.applicationInstance.squadData.value = true
        }


        binding.teamAPollMatch.setOnClickListener {

            if (mPrefs.prefUserDetails != null) {

                if (matchStatus.equals("Finished", true)) {
                    showToast("Match Already Finished")
                } else if (!matchPredicted && result.isNullOrEmpty()) {

                    callCreatePrediction(
                        matchId,
                        mPrefs.prefUserDetails?.id!!,
                        seriesName,
                        teamAShort,
                        teamBShort,
                        teamA,
                        teamB,
                        teamAImg,
                        teamBImg,
                        "match",
                        "",
                        teamAShort,
                        match_type,
                        matchDate
                    )

                } else {
                    showToast("Already Predicted.")
                }

            } else {
                showToast("please login to predict")
            }

        }

        binding.teamBPollMatch.setOnClickListener {

            if (mPrefs.prefUserDetails != null) {

                if (matchStatus.equals("Finished", true)) {
                    showToast("Match Already Finished")
                } else if (!matchPredicted && result.isNullOrEmpty()) {

                    callCreatePrediction(
                        matchId,
                        mPrefs.prefUserDetails?.id!!,
                        seriesName,
                        teamAShort,
                        teamBShort,
                        teamA,
                        teamB,
                        teamAImg,
                        teamBImg,
                        "match",
                        "",
                        teamBShort,
                        match_type,
                        matchDate
                    )

                } else {
                    showToast("Already Predicted.")
                }

            } else {
                showToast("please login to predict")
            }

        }


        binding.teamAPollToss.setOnClickListener {

            if (mPrefs.prefUserDetails != null) {

                if (matchStatus.equals("Finished", true)) {
                    showToast("Match Already Finished")
                } else if (!tossPredicted && toss.isNullOrEmpty()) {

                    callCreatePrediction(
                        matchId,
                        mPrefs.prefUserDetails?.id!!,
                        seriesName,
                        teamAShort,
                        teamBShort,
                        teamA,
                        teamB,
                        teamAImg,
                        teamBImg,
                        "toss",
                        teamAShort,
                        "",
                        match_type,
                        matchDate
                    )

                } else {
                    showToast("Already Predicted.")
                }

            } else {
                showToast("please login to predict")
            }

        }

        binding.teamBPollToss.setOnClickListener {

            if (mPrefs.prefUserDetails != null) {

                if (matchStatus.equals("Finished", true)) {
                    showToast("Match Already Finished")
                } else if (!tossPredicted && toss.isNullOrEmpty()) {

                    callCreatePrediction(
                        matchId,
                        mPrefs.prefUserDetails?.id!!,
                        seriesName,
                        teamAShort,
                        teamBShort,
                        teamA,
                        teamB,
                        teamAImg,
                        teamBImg,
                        "toss",
                        teamBShort,
                        "",
                        match_type,
                        matchDate
                    )

                } else {
                    showToast("Already Predicted.")
                }

            } else {
                showToast("please login to predict")
            }

        }


        binding.stadiumName.setOnClickListener {
            if (venueInfo != null) {

                val intent = Intent(activity, StadiumActivity::class.java)
                intent.putExtra("stadiumName", venueInfo?.name.toString())
                intent.putExtra("stadiumId", venueInfo?.id)
                startActivity(intent)

            }
        }

        if (matchStatus.equals("Upcoming")) {
            getMatchIdCricBuzzUpcoming(series, matchNo)
        } else if (matchStatus.equals("Finished")) {
            getMatchIdCricBuzzFinished(series, matchNo)
        } else if (matchStatus.equals("Live")) {
            getMatchIdCricBuzzLive(series, matchNo)
        }


    }


    private fun callFetchMatchWisePrediction(teamAShort: String, teamBShort: String) {

        if (mPrefs.prefUserDetails != null) {

            if (checkForInternet(activity)) {
                viewModel.fetchMatchWisePoll(
                    mPrefs.prefUserDetails?.id!!,
                    matchId,
                    teamAShort,
                    teamBShort
                )
            }
        }
    }


    private fun callCreatePrediction(
        matchid: Int,
        userId: Int,
        seriesName: String,
        teamAShort: String,
        teamBShort: String,
        teamA: String,
        teamB: String,
        teamAImg: String,
        teamBImg: String,
        type: String,
        tossPredict: String,
        matchPredict: String,
        match_type: String,
        matchDate: String,
    ) {

        if (checkForInternet(activity)) {
            viewModel.createPrediction(
                matchid,
                userId,
                seriesName,
                teamAShort,
                teamBShort,
                teamA,
                teamB,
                teamAImg,
                teamBImg,
                type,
                tossPredict,
                matchPredict,
                match_type,
                matchDate
            )
        }

    }


    fun callRecentMatches() {

        if (isInternetConnection()) {
            viewModel.getRecentMatches()
        }

    }

    fun callMatchInfoCricBuzz() {

        if (matchIdCricbuzz != 0) {

            //Log.e("TAG1111", "apiClientCricBuzz :matchIdCricbuzz " )
            if (isInternetConnection()) {
                viewModel.getMatchInfoCricBuzz(matchIdCricbuzz)
            }
        }

    }


    private fun callMatchInfo(matchId: Int) {
        if (checkForInternet(activity)) {
            viewModel.getMatchInfo(matchId)
        }
    }


    private fun setObservers() {
        observeExtras()
        observeMatchInfo()
        observeMatchId()
        observeMatchInfoCricBuzz()
        observeFetchPrediction()
        observeCreatePrediction()
        //observeTossCompare()
        //observeTeamForm()
        //observeTeamCompare()
        //observeHeadToHead()

    }


    fun observeMatchId() {

        matchIdCricLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                matchIdCricbuzz = it
                Log.e(TAG, "onViewCreated:matchid crci ${matchIdCricbuzz}")
                callMatchInfoCricBuzz()
            }
        })

    }

    var model: MatchByIdMatchInfo? = null

    private fun observeMatchInfoCricBuzz() {

        viewModel.matchInfoCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {

                    if (it.matchInfo != null) {

                        model = it.matchInfo

                        if (!it.matchInfo.matchDescription.isNullOrEmpty()) {
                            binding.tvMatchNo.setText("${it.matchInfo.matchDescription}")
                        } else {
                            binding.tvMatchNo.setText("N.A.")
                        }

                        if (!it.matchInfo.series?.name.isNullOrEmpty()) {
                            binding.tvseriesName.setText("${it.matchInfo.series?.name}")
                        } else {
                            binding.tvseriesName.setText("N.A.")
                        }


                        val timestamp = it.matchInfo.matchStartTimestamp?.toLong()
                        val dateFormat = SimpleDateFormat("E, MMM d", Locale.getDefault())
                        dateFormat.timeZone =
                            TimeZone.getTimeZone("Asia/Kolkata")  // Set the time zone to Indian Standard Time (IST)

                        val date = Date(timestamp!!)
                        val formattedDate = dateFormat.format(date)

                        binding.tvDatetime.setText("${formattedDate}")

                        val timeFormat =
                            SimpleDateFormat("h:mm a, 'Your Time'", Locale.getDefault())
                        timeFormat.timeZone =
                            TimeZone.getDefault()  // Set the time zone to the device's default time zone

                        val time = Date(timestamp)
                        val formattedTime = timeFormat.format(time)

                        binding.tvTime.setText("${formattedTime}")

                        if (!it.matchInfo.umpire1?.name.isNullOrEmpty() && !it.matchInfo.umpire2?.name.isNullOrEmpty()) {
                            binding.tvUmpire.setText("${it.matchInfo.umpire1?.name}, ${it.matchInfo.umpire2?.name}")
                        }
                        else if (!it.matchInfo.umpire1?.name.isNullOrEmpty()) {
                            binding.tvUmpire.setText("${it.matchInfo.umpire1?.name}")
                        }
                        else if (!it.matchInfo.umpire2?.name.isNullOrEmpty()) {
                            binding.tvUmpire.setText("${it.matchInfo.umpire2?.name}")
                        }else{
                            binding.tvUmpire.setText("N.A.")
                        }

                        if (!it.matchInfo.umpire3?.name.isNullOrEmpty()) {
                            binding.thirdUmpire.setText("${it.matchInfo.umpire3?.name}")
                        }else{
                            binding.thirdUmpire.setText("N.A.")
                        }

                        if (!it.matchInfo.referee?.name.isNullOrEmpty()) {
                            binding.referee.setText("${it.matchInfo.referee?.name}")
                        }else{
                            binding.referee.setText("N.A.")
                        }

                        if (it.matchInfo.tossResults != null) {
                            if (it.matchInfo.tossResults.decision.equals("Batting")) {
                                binding.tvMatchinfoTosswon.setText("${it.matchInfo.tossResults.tossWinnerName} opt to bat")
                            } else {
                                binding.tvMatchinfoTosswon.setText("${it.matchInfo.tossResults.tossWinnerName} opt to bowl")
                            }

                        }

                        if (it.matchInfo.venue != null) {


                            if (!it.matchInfo.venue.name.isNullOrEmpty()) {

                                venueInfo = it.matchInfo.venue

                                binding.stadiumName.setText("" + it.matchInfo.venue.name)


                                binding.tvVenue.setText("${it.matchInfo.venue.name}, ${it.matchInfo.venue.city}")

                            }

                            if (it.venueInfo != null) {

                                if (!it.venueInfo.city.isNullOrEmpty()) {
                                    binding.stadiumCity.setText("" + it.venueInfo.city)
                                }
                                if (!it.venueInfo.ends.isNullOrEmpty()) {
                                    binding.stadiumCapacity.setText("" + it.venueInfo.capacity)
                                }
                                if (!it.venueInfo.ends.isNullOrEmpty()) {
                                    binding.stadiumEnds.setText("" + it.venueInfo.ends)
                                }
                                if (!it.venueInfo.homeTeam.isNullOrEmpty()) {
                                    binding.stadiumHosts.setText("" + it.venueInfo.homeTeam)
                                }

                            }



                            if (!it.broadcastInfo.isNullOrEmpty()) {

                                binding.linearTvGuideLayout.visibility = View.VISIBLE

                                val brodcastItem = it.broadcastInfo.get(0)

                                if (!brodcastItem?.country.isNullOrEmpty()) {

                                    binding.tvGuide.setText(getString(R.string.live_info_tv_guide) + brodcastItem?.country)

                                }

                                if (!brodcastItem?.broadcaster.isNullOrEmpty()) {
                                    binding.linearTvGuideLayout.visibility = View.VISIBLE
                                    binding.recyclerTvGuide.adapter = TvGuideAdapter(
                                        brodcastItem?.broadcaster as List<BroadcasterItem>,
                                        activity
                                    )
                                } else {
                                    binding.linearTvGuideLayout.visibility = View.GONE
                                }

                            } else {
                                binding.linearTvGuideLayout.visibility = View.GONE
                            }

                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })

    }


    private fun observeMatchInfo() {
        viewModel.getMatchInfoLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                requestOptions.error(R.mipmap.ic_launcher_round)


                /*seriesName = it.series.toString()
                teamAShort = it.teamAShort.toString()
                teamBShort = it.teamBShort.toString()
                teamA = it.teamA.toString()
                teamB = it.teamB.toString()
                teamAId = it.teamAId!!
                teamBId = it.teamBId!!
                teamAImg = it.teamAImg.toString()
                teamBImg = it.teamBImg.toString()
                match_type = it.matchType.toString()
                matchDate = it.matchDate.toString()*/


                /*viewModel.getTossCompare(it.teamAId!!,it.teamBId!!)
                viewModel.getTeamForm(it.teamAId!!,it.teamBId!!)
                viewModel.getTeamCompare(it.teamAId!!,it.teamBId!!)
                viewModel.getHeadToHead(it.teamAId!!,it.teamBId!!)*/

                toss = it.toss.toString()
                seriesName = it.series.toString()
                teamAShort = it.teamAShort.toString()
                teamBShort = it.teamBShort.toString()
                teamA = it.teamA.toString()
                teamB = it.teamB.toString()
                teamAId = it.teamAId!!
                teamBId = it.teamBId!!
                teamAImg = it.teamAImg.toString()
                teamBImg = it.teamBImg.toString()
                match_type = it.matchType.toString()
                matchDate = it.matchDate.toString()

                binding.teamAMatchPoll.setText(it.teamAShort.toString())
                binding.teamBMatchPoll.setText(it.teamBShort.toString())

                binding.teamATossPoll.setText(it.teamAShort.toString())
                binding.teamBTossPoll.setText(it.teamBShort.toString())

                Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                    .into(binding.teamAFlagToss)
                binding.teamANameToss.setText("${it.teamAShort}")

                Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                    .into(binding.teamBFlagToss)

                binding.teamBNameToss.setText("${it.teamBShort}")

                if (it.tossComparison != null) {

                    if (!it.tossComparison.teamA.isNullOrEmpty()) {

                        binding.recyclerTeamAToss.adapter = TeamTossCompareAdapter(
                            it.tossComparison.teamA as ArrayList<String>,
                            activity
                        )
                    }

                    if (!it.tossComparison.teamB.isNullOrEmpty()) {
                        binding.recyclerTeamBToss.adapter = TeamTossCompareAdapter(
                            it.tossComparison.teamB as ArrayList<String>,
                            activity
                        )
                    }


                }




                Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                    .into(binding.teamAFlag)

                binding.teamAName.setText("${it.teamAShort}")

                Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                    .into(binding.teamBFlag)

                binding.teamBName.setText("${it.teamBShort}")

                if (it.forms != null) {
                    if (!it.forms.teamA.isNullOrEmpty()) {
                        binding.recyclerWinLossTeamA.adapter = TeamMatchCompareAdapter(
                            it.forms.teamA as ArrayList<String>,
                            activity
                        )
                    }
                    if (!it.forms.teamB.isNullOrEmpty()) {
                        binding.recyclerWinLossTeamB.adapter = TeamMatchCompareAdapter(
                            it.forms.teamB as ArrayList<String>,
                            activity
                        )
                    }

                }


                Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                    .into(binding.teamAImageComp)

                binding.teamANameComp.setText("${it.teamAShort}")

                Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                    .into(binding.teamBImageComp)

                binding.teamBNameComp.setText("${it.teamBShort}")

                if (it.teamComparison != null) {

                    binding.winTeamA.setText("" + it.teamComparison.teamAWin)
                    binding.avgScoreTeamA.setText("" + it.teamComparison.teamAAvgScore)
                    binding.highScoreTeamA.setText("" + it.teamComparison.teamAHighScore)
                    binding.lowestScoreTeamA.setText("" + it.teamComparison.teamALowScore)

                    binding.winTeamB.setText("" + it.teamComparison.teamBWin)
                    binding.avgScoreTeamB.setText("" + it.teamComparison.teamBAvgScore)
                    binding.highScoreTeamB.setText("" + it.teamComparison.teamBHighScore)
                    binding.lowestScoreTeamB.setText("" + it.teamComparison.teamBLowScore)

                }


                Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                    .into(binding.headToHeadTeamAImg)
                binding.headToHeadTeamAName.setText("${it.teamAShort}")


                Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                    .into(binding.headToHeadTeamBImg)
                binding.headToHeadTeamBName.setText("${it.teamBShort}")

                if (it.headToHead != null) {

                    binding.headToHeadTeamAWin.setText("${it.headToHead.teamAWinCount}")
                    binding.headToHeadTeamBWin.setText("${it.headToHead.teamBWinCount}")


                    if (!it.headToHead.matches.isNullOrEmpty()) {

                        binding.recyclerHeadToHead.adapter = HeadToHeadAdapter(
                            it.headToHead.matches as ArrayList<MatchesItem>,
                            activity,
                            it.teamAShort.toString(),
                            it.teamAImg.toString(),
                            it.teamBShort.toString(),
                            it.teamBImg.toString(),
                        )

                    }

                }


                if (it.venueWeather != null) {

                    binding.linearWhether.visibility = View.VISIBLE

                    binding.address.setText("${it.venue}")
                    binding.weatherDescription.setText("${it.place}")

                    binding.temperature.setText(
                        "${
                            it.venueWeather.tempC.toString().toDouble().toInt()
                        } °C"
                    )
                    binding.conditions.setText("${it.venueWeather.weather}")
                    binding.humidity.setText("${it.venueWeather.humidity}% humidity")
                    binding.windSpeed.setText("${it.venueWeather.windKph}Km/h Wind Speed")

                    Glide.with(activity).load(it.venueWeather.weatherIcon).apply(requestOptions)
                        .into(binding.weatherImage)


                }

                callFetchMatchWisePrediction(it.teamAShort.toString(), it.teamBShort.toString())


            }

        })
    }

    private fun observeFetchPrediction() {

        viewModel.predictionMatchWiseLiveData.observe(viewLifecycleOwner, Observer {

            try {

                if (!toss.isNullOrEmpty()) {

                    binding.tossVotes.setText("( Total Votes: ${it.toss?.tossPredictCount} )")

                    binding.teamATossPoll.setText("$teamAShort " + it.toss?.teamAPredict + "%")
                    binding.teamBTossPoll.setText("$teamBShort " + it.toss?.teamBPredict + "%")

                    binding.teamATossProgress.setProgress(it.toss?.teamAPredict!!, true)
                    binding.teamBTossProgress.setProgress(it.toss?.teamBPredict!!, true)

                    binding.teamATossProgress.visibility = View.VISIBLE
                    binding.teamBTossProgress.visibility = View.VISIBLE


                    if (toss.contains(teamAShort, true) || toss.contains(teamA, true)) {
                        binding.teamATossPollWinner.visibility = View.VISIBLE
                    } else if (toss.contains(teamBShort, true) || toss.contains(teamB, true)) {
                        binding.teamBTossPollWinner.visibility = View.VISIBLE
                    }

                }

                if (matchStatus.equals("Finished")) {

                    binding.matchVotes.setText("( Total Votes: ${it.match?.matchPredictCount} )")

                    binding.teamAMatchPoll.setText("$teamAShort " + it.match?.teamAPredict + "%")
                    binding.teamBMatchPoll.setText("$teamBShort " + it.match?.teamBPredict + "%")

                    binding.teamAMatchProgress.setProgress(it.match?.teamAPredict!!, true)
                    binding.teamBMatchProgress.setProgress(it.match?.teamBPredict!!, true)

                    binding.teamAMatchProgress.visibility = View.VISIBLE
                    binding.teamBMatchProgress.visibility = View.VISIBLE


                    if (result.contains(teamAShort, true) || result.contains(teamA, true)) {
                        binding.teamAMatchPollWinner.visibility = View.VISIBLE
                    } else if (result.contains(teamBShort, true) || result.contains(teamB, true)) {
                        binding.teamBMatchPollWinner.visibility = View.VISIBLE
                    } else if (result.contains("draw", true)) {
                        binding.teamDrawMatchPollWinner.visibility = View.VISIBLE
                    }

                } else {

                    binding.tossVotes.setText("( Total Votes: ${it.toss?.tossPredictCount} )")
                    binding.matchVotes.setText("( Total Votes: ${it.match?.matchPredictCount} )")

                    if (it.toss?.myPrediction!!) {

                        tossPredicted = true

                        binding.teamATossPoll.setText("$teamAShort " + it.toss?.teamAPredict + "%")
                        binding.teamBTossPoll.setText("$teamBShort " + it.toss?.teamBPredict + "%")

                        binding.teamATossProgress.setProgress(it.toss?.teamAPredict!!, true)
                        binding.teamBTossProgress.setProgress(it.toss?.teamBPredict!!, true)

                        binding.teamATossProgress.visibility = View.VISIBLE
                        binding.teamBTossProgress.visibility = View.VISIBLE


                        if (toss.contains(teamAShort, true) || toss.contains(teamA, true)) {
                            binding.teamATossPollWinner.visibility = View.VISIBLE
                        } else if (toss.contains(teamBShort, true) || toss.contains(teamB, true)) {
                            binding.teamBTossPollWinner.visibility = View.VISIBLE
                        }

                    }


                    if (it.match?.myPrediction!!) {

                        matchPredicted = true

                        binding.teamAMatchPoll.setText("$teamAShort " + it.match?.teamAPredict + "%")
                        binding.teamBMatchPoll.setText("$teamBShort " + it.match?.teamBPredict + "%")

                        binding.teamAMatchProgress.setProgress(it.match?.teamAPredict!!, true)
                        binding.teamBMatchProgress.setProgress(it.match?.teamBPredict!!, true)

                        binding.teamAMatchProgress.visibility = View.VISIBLE
                        binding.teamBMatchProgress.visibility = View.VISIBLE


                    }
                    /*else if (fiveOverComplete){
                            matchPredicted = true

                            binding.teamAMatchPoll.setText("$teamAShort " + it.match?.teamAPredict + "%")
                            binding.teamBMatchPoll.setText("$teamBShort " + it.match?.teamBPredict + "%")

                            binding.teamAMatchProgress.setProgress(it.match?.teamAPredict!!, true)
                            binding.teamBMatchProgress.setProgress(it.match?.teamBPredict!!, true)

                            binding.teamAMatchProgress.visibility = View.VISIBLE
                            binding.teamBMatchProgress.visibility = View.VISIBLE
                        }*/

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        })

    }

    private fun observeCreatePrediction() {

        viewModel.createPredictionLiveData.observe(viewLifecycleOwner, Observer {

            try {

                if (it != null) {
                    callFetchMatchWisePrediction(teamAShort, teamBShort)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        })

    }

    private fun observeTossCompare() {
        viewModel.tossCompareLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                requestOptions.error(R.mipmap.ic_launcher_round)


            }

        })
    }

    private fun observeTeamForm() {
        viewModel.teamFormLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                requestOptions.error(R.mipmap.ic_launcher_round)

                if (it.teamA != null) {

                }

                if (it.teamB != null) {


                }


            }

        })
    }

    private fun observeTeamCompare() {
        viewModel.teamCompareLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                requestOptions.error(R.mipmap.ic_launcher_round)

                if (it.teamA != null) {


                }

                if (it.teamB != null) {


                }


            }

        })
    }

    private fun observeHeadToHead() {
        viewModel.headToHeadLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                requestOptions.error(R.mipmap.ic_launcher_round)

                if (it.teamA != null) {


                }

                if (it.teamB != null) {


                }


            }

        })
    }


    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(activity,
            { isLoading -> handleProgressLoader(isLoading!!) })
        /*viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

}