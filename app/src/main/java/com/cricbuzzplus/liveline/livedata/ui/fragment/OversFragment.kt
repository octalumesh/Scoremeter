package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentOversBinding
import com.cricbuzzplus.liveline.databinding.FragmentSquadBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.BatsmanItem
import com.cricbuzzplus.liveline.livedata.response.LiveResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.CommentaryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.OverSepItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.OverSummaryListItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.BatsmanLiveCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.LiveCommentaryCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.OversCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import com.cricbuzzplus.liveline.utils.Constants
import java.lang.Exception


class OversFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: FragmentOversBinding

    var batsmanAdaptor: BatsmanLiveCricAdapter? = null


    var listOvers = arrayListOf<OverSummaryListItem>()
    var listOversNew = arrayListOf<OverSepItem>()

    var adapterOver: OversCricAdapter? = null

    var matchStatus = ""
    var matchId = 0
    var matchDate = ""

    var matchNo = ""
    var series = ""

    var inning = 1
    var lastTimeStamp: Long = 0
    var prevTimeStamp: Long = 0

    var matchIdCricbuzz = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = FragmentOversBinding.inflate(inflater, container, false)

        matchStatus = arguments?.getString("matchStatus", "").toString()
        matchNo = arguments?.getString("matchNo", "").toString()
        series = arguments?.getString("series", "").toString()
        matchDate = arguments?.getString("matchDate", "").toString()
        matchId = arguments?.getInt("matchId")!!


        hideKeyBoard()
        setObservers()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (matchStatus.equals("Upcoming", true)) {
            handleProgressLoader(false)
            binding.linearMainScore.visibility = View.GONE
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        } else if (!matchStatus.equals("Live", true)) {

            if (isInternetConnection()) {
                viewModel.getLiveScoreMain(matchId)
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


    private fun setObservers() {

        observeLiveScore()
        observeLiveScorenotLive()
        observeMatchId()
        observeOvers()
        observeOversPaginate()

    }

    fun observeLiveScorenotLive() {
        viewModel.getLiveScoreLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                handleProgressLoader(false)
                mainScoreLiveData.value = it

                callMatchOvers()

            }
        })

    }

    fun observeOvers() {

        viewModel.oversCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it.overSepList != null && !it.overSepList.overSep.isNullOrEmpty()) {

                if ((it.overSepList.overSep.get(it.overSepList.overSep.size - 1))?.inningsId != null) {

                    if (!listOversNew.isNullOrEmpty()) {
                        if (listOversNew.get((listOversNew.size - 1))?.inningsId != null) {
                            visibleThreshold = (listOversNew.size - 1)

                            inning = listOversNew.get((listOversNew.size - 1))?.inningsId!!
                            lastTimeStamp =
                                listOversNew.get(listOversNew.size - 1)?.timestamp?.toLong()!!
                        }
                    } else {
                        visibleThreshold = (it.overSepList.overSep.size - 1)

                        inning =
                            it.overSepList.overSep.get((it.overSepList.overSep.size - 1))?.inningsId!!
                        lastTimeStamp =
                            it.overSepList.overSep.get(it.overSepList.overSep.size - 1)?.timestamp?.toLong()!!
                    }


                }
                //  Log.e(TAG, "observeCommentary: lastTimeStamp  $lastTimeStamp", )

                if (adapterOver == null) {

                    listOversNew.addAll(it.overSepList.overSep as ArrayList<OverSepItem>)

                    adapterOver = OversCricAdapter(
                        listOversNew,
                        activity
                    )

                    binding.recyclerOvers.adapter = adapterOver

                    addScroll()

                } else {

                    for (item in it.overSepList.overSep) {
                        /*if (!listOvers.contains(item)) {
                            listOvers.add(0, item!!)
                        }*/

                        //val existingItemIndex = listOvers.indexOfFirst { it.timestamp == item?.timestamp }

                        val existingItemIndex = listOversNew.indexOfFirst {
                            Math.ceil(it.overNum.toString().toDouble())
                                .toInt() == Math.ceil(item?.overNum.toString().toDouble()).toInt()
                                    && it.inningsId == item?.inningsId
                        }


                        //Log.e(TAG, "observeCommentaryPaginate: ${existingItemIndex}", )

                        if (existingItemIndex != -1) {
                            // Remove the existing item with the matching timestamp
                            listOversNew[existingItemIndex] = item!!
                        } else {
                            // If no item with the same timestamp exists, add the new item
                            listOversNew.add(item!!)
                        }

                    }

                    try {
                        listOversNew.sortByDescending { it.timestamp }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    isLoading = false

                    //  listOvers.addAll(it.overSummaryList as ArrayList<CommentaryListItem>)

                    adapterOver?.updateList(listOversNew)

                }
            } else {
                // binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

            /* if (!it.overSummaryList.isNullOrEmpty()) {

                 if ((it.overSummaryList.get(it.overSummaryList.size - 1))?.inningsId != null) {

                     if(!listOvers.isNullOrEmpty()){
                         if(listOvers.get((listOvers.size - 1))?.inningsId != null) {
                             visibleThreshold = (listOvers.size - 1)

                             inning = listOvers.get((listOvers.size - 1))?.inningsId!!
                             lastTimeStamp = listOvers.get(listOvers.size - 1)?.timestamp!!
                         }
                     }else{
                         visibleThreshold = (it.overSummaryList.size - 1)

                         inning = it.overSummaryList.get((it.overSummaryList.size - 1))?.inningsId!!
                         lastTimeStamp = it.overSummaryList.get(it.overSummaryList.size - 1)?.timestamp!!
                     }


                 }
                 //  Log.e(TAG, "observeCommentary: lastTimeStamp  $lastTimeStamp", )

                 if (adapterOver == null) {

                     listOvers.addAll(it.overSummaryList as ArrayList<OverSummaryListItem>)

                     adapterOver = OversCricAdapter(
                         listOvers,
                         activity
                     )

                     binding.recyclerOvers.adapter = adapterOver

                     addScroll()

                 }
                 else {

                     for (item in it.overSummaryList) {
                         *//*if (!listOvers.contains(item)) {
                            listOvers.add(0, item!!)
                        }*//*

                        //val existingItemIndex = listOvers.indexOfFirst { it.timestamp == item?.timestamp }

                        val existingItemIndex = listOvers.indexOfFirst {
                            Math.ceil(it.overNum.toString().toDouble()).toInt() == Math.ceil(item?.overNum.toString().toDouble()).toInt()
                                    && it.inningsId == item?.inningsId
                        }


                        //Log.e(TAG, "observeCommentaryPaginate: ${existingItemIndex}", )

                        if (existingItemIndex != -1) {
                            // Remove the existing item with the matching timestamp
                            listOvers[existingItemIndex] = item!!
                        } else {
                            // If no item with the same timestamp exists, add the new item
                            listOvers.add(item!!)
                        }

                    }

                    try {
                        listOvers.sortByDescending { it.timestamp }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    isLoading = false

                    //  listOvers.addAll(it.overSummaryList as ArrayList<CommentaryListItem>)

                    adapterOver?.updateList(listOvers)

                }
            } else {
                // binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }*/

            if (it.matchHeaders != null) {

                if (it.matchHeaders.momPlayers != null && !it.matchHeaders.momPlayers?.player.isNullOrEmpty()) {

                    var playerMatch = it.matchHeaders.momPlayers?.player?.get(0)

                    binding.linearPlayerOfMatch.visibility = View.VISIBLE
                    binding.linearScore.visibility = View.GONE

                    binding.playerName.setText("" + playerMatch?.name)
                    Glide.with(activity)
                        .load("" + Constants.cricbuzzImgFirst + playerMatch?.faceImageId + Constants.cricbuzzImgSecond)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.playerImage)

                } else {
                    binding.linearPlayerOfMatch.visibility = View.GONE
                    binding.linearScore.visibility = View.VISIBLE
                }
            }

        })

    }

    fun observeOversPaginate() {

        viewModel.oversPaginateCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.overSepList?.overSep.isNullOrEmpty()) {

                if ((it.overSepList.overSep[it.overSepList.overSep.size - 1])?.inningsId != null) {
                    visibleThreshold = (it.overSepList.overSep.size - 1)

                    inning = it.overSepList.overSep[(it.overSepList.overSep.size - 1)]?.inningsId!!
                    lastTimeStamp = it.overSepList.overSep[it.overSepList.overSep.size - 1]?.timestamp?.toLong()!!

                }
                //  Log.e(TAG, "observeCommentary: lastTimeStamp  $lastTimeStamp", )

                if (adapterOver == null) {

                    listOversNew.addAll(it.overSepList?.overSep as ArrayList<OverSepItem>)

                    adapterOver = OversCricAdapter(
                        listOversNew,
                        activity
                    )

                    binding.recyclerOvers.adapter = adapterOver

                } else {

                    isLoading = false

                    if (!it.overSepList?.overSep.isNullOrEmpty()) {
                        for (item in it.overSepList?.overSep!!) {
                            val existingItemIndex =
                                listOversNew.indexOfFirst { it.timestamp == item?.timestamp }


                            //Log.e(TAG, "observeCommentaryPaginate: ${existingItemIndex}", )

                            if (existingItemIndex != -1) {
                                // Remove the existing item with the matching timestamp
                                listOversNew[existingItemIndex] = item!!
                            } else {
                                // If no item with the same timestamp exists, add the new item
                                listOversNew.add(item!!)
                            }
                        }
                        //listOvers.addAll(it.overSummaryList as ArrayList<CommentaryListItem>)

                        adapterOver?.updateList(listOversNew)
                    }

                }
            }

        })

    }

    var isLoading = false // Used to prevent multiple API calls while loading
    var visibleThreshold = 0 // Number of items from the end to trigger loading

    fun addScroll() {


        binding.recyclerOvers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.recyclerOvers.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val totalItemCount = layoutManager.itemCount

                //Log.e(TAG, "onScrolled: totalItemCount $totalItemCount  lastVisibleItem $lastVisibleItem" )

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more data here
                    isLoading = true // Set to true to prevent multiple calls

                    if (prevTimeStamp != lastTimeStamp) {
                        prevTimeStamp = lastTimeStamp

                        //  Log.e(TAG, "visibleThreshold: "+visibleThreshold )

                        callMatchOversPaginate()
                    }

                }
            }
        })
    }


    fun observeMatchId() {

        matchIdCricLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                matchIdCricbuzz = it
                Log.e(TAG, "onViewCreated:matchid crci ${matchIdCricbuzz}")

                callMatchOvers()

            }
        })

    }


    fun callMatchOvers() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getOversCricBuzz(matchIdCricbuzz)
            }
        }

    }

    fun callMatchOversPaginate() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getOversCricPaginate(matchIdCricbuzz, inning, lastTimeStamp)
            }
        }

    }


    var mainScoreLiveData = MutableLiveData<LiveResponse>()


    fun setData(liveResponse: LiveResponse) {


        mainScoreLiveData.value = liveResponse

        handleProgressLoader(false)

        //Handler(Looper.getMainLooper()).postDelayed({
        callMatchOvers()
        // },1000)

    }

    private fun observeLiveScore() {
        mainScoreLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {


                handleProgressLoader(false)
                val requestOptions = RequestOptions()
                //     requestOptions.placeholder(R.mipmap.ic_launcher)
                //     requestOptions.placeholder(R.mipmap.ic_launcher)
                requestOptions.placeholder(R.drawable.viratone)
                requestOptions.error(R.drawable.viratone)

                Log.e(TAG, "observeLiveScore  live frag1 : ${it.matchId}")


                if (!it.result.isNullOrEmpty()) {

                    binding.resultToss.setTextColor(activity.resources.getColor(R.color.bluene))
                    binding.resultToss.setText("${it.result}")

                } else if (!it.trailLead.isNullOrEmpty()) {
                    binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                    binding.resultToss.setText("${it.trailLead}")
                } else if (!it.toss.isNullOrEmpty()) {
                    binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                    binding.resultToss.setText("${it.toss}")
                }

                Log.e(TAG, "observeLiveScore  live frag222 : ${it.matchId}")

                if (!it.result.isNullOrEmpty()) {

                    Log.e(TAG, "observeLiveScore:==> if")

                    binding.liveLinear.visibility = View.GONE
                    binding.upcomingLinear.visibility = View.GONE
                    binding.finishedLinear.visibility = View.VISIBLE
                    binding.appbarCollpsingToolbarLive.visibility = View.VISIBLE

                    if (it.result.contains(it.teamAShort.toString()) || it.result.contains(it.teamA.toString())) {
                        binding.teamAShortFinish.setTextColor(activity.resources.getColor(R.color.txt_color))
                        binding.teamAScoreFinish.setTextColor(activity.resources.getColor(R.color.txt_color))
                        binding.teamBShortFinish.setTextColor(activity.resources.getColor(R.color.txt_lgt))
                        binding.teamBScoreFinish.setTextColor(activity.resources.getColor(R.color.txt_lgt))
                    } else if (it.result.contains(it.teamBShort.toString()) || it.result.contains(it.teamB.toString())) {
                        binding.teamAShortFinish.setTextColor(activity.resources.getColor(R.color.txt_lgt))
                        binding.teamAScoreFinish.setTextColor(activity.resources.getColor(R.color.txt_lgt))
                        binding.teamBShortFinish.setTextColor(activity.resources.getColor(R.color.txt_color))
                        binding.teamBScoreFinish.setTextColor(activity.resources.getColor(R.color.txt_color))
                    }

                    binding.teamAShortFinish.setText(it.teamAShort)
                    binding.teamBShortFinish.setText(it.teamBShort)

                    if (!it.teamAScores.isNullOrEmpty()) {
                        binding.teamAScoreFinish.setText("${it.teamAScores} (${it.teamAOver})")
                    }

                    if (!it.teamBScores.isNullOrEmpty()) {
                        binding.teamBScoreFinish.setText("${it.teamBScores} (${it.teamBOver})")
                    }


                } else if ("Live".equals(matchStatus)) {

                    Log.e(TAG, "observeLiveScore: else if")

                    binding.liveLinear.visibility = View.VISIBLE
                    binding.finishedLinear.visibility = View.GONE
                    binding.upcomingLinear.visibility = View.GONE
                    binding.appbarCollpsingToolbarLive.visibility = View.VISIBLE

                    if (!it.currRate.isNullOrEmpty()) {
                        binding.currRate.setText(it.currRate)
                    }

                    if (!it.rrRate.isNullOrEmpty()) {
                        binding.reqRRLinear.visibility = View.VISIBLE
                        binding.reqRate.setText(it.rrRate)
                    } else {
                        binding.reqRRLinear.visibility = View.GONE
                    }

                    if (it.battingTeam == it.teamAId) {

                        binding.teamShortLive.setText(it.teamAShort)

                        if (!it.teamAScores.isNullOrEmpty()) {
                            binding.teamScoreLive.setText(it.teamAScores)
                            binding.teamOverLive.setText("(" + it.teamAOver + ")")
                        }

                    } else {
                        binding.teamShortLive.setText(it.teamBShort)
                        if (!it.teamBScores.isNullOrEmpty()) {
                            binding.teamScoreLive.setText(it.teamBScores)
                            binding.teamOverLive.setText("(" + it.teamBOver + ")")
                        }
                    }

                } else {

                    Log.e(TAG, "observeLiveScore: else")

                    binding.liveLinear.visibility = View.GONE
                    binding.finishedLinear.visibility = View.GONE
                    binding.upcomingLinear.visibility = View.VISIBLE

                    binding.teamAShortUpcoming.setText("${it.teamAShort}")
                    binding.teamBShortUpcoming.setText("${it.teamBShort}")
                    binding.resultToss.setText("Match starts at: ${matchDate}")

                    Glide.with(activity).load(it.teamAImg).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.teamAImageUpcoming)
                    Glide.with(activity).load(it.teamBImg).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.teamBImageUpcoming)


                }


                if (it.lastwicket != null) {
                    if (it.lastwicket?.player.toString().isNullOrEmpty()) {
                        binding.textviewLastWicket.setText("-")
                    } else {
                        binding.textviewLastWicket.setText(
                            "" + it.lastwicket?.player + " " + it.lastwicket?.run.toString()
                                .toDouble().toInt() + " ( " + it.lastwicket?.ball.toString()
                                .toDouble().toInt() + " )"
                        )
                    }
                } else {
                    binding.textviewLastWicket.setText("-")
                }

                if (!it.batsman.isNullOrEmpty()) {
                    if (batsmanAdaptor == null) {
                        batsmanAdaptor = BatsmanLiveCricAdapter(
                            it.batsman as ArrayList<BatsmanItem>,
                            requireContext()
                        )
                        binding.recyclerviewBatsmanLive.adapter = batsmanAdaptor
                    } else {
                        batsmanAdaptor?.updateList(it.batsman as ArrayList<BatsmanItem>)
                    }
                }

                binding.textviewNxtbatsman.setText("" + it.nextBatsman.toString())
                /*if (it.partnership?.ball.isNullOrEmpty()){
                    binding.textviewParternship.setText("-(-)")
                }else {
                    binding.textviewParternship.setText(it.partnership?.run + " ( " + it.partnership?.ball + " )")
                }*/
                if (it.partnership != null) {
                    binding.textviewParternship.setText(
                        "" + it.partnership.run.toString().toDouble()
                            .toInt() + "(" + it.partnership.ball.toString().toDouble()
                            .toInt() + ")"
                    )
                } else {
                    binding.textviewParternship.setText("-(-)")
                }


                if (it.bolwer != null) {
                    binding.textviewBowlerName.setText("" + it.bolwer?.name)
                    binding.bowlerOver.setText("" + it.bolwer?.over)

                    try {
                        if (it.bolwer?.run != null) {
                            binding.bowlerRun.setText(
                                "" + it.bolwer?.run.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler run" + e.message)
                        if (it.bolwer?.run != null) {
                            binding.bowlerRun.setText(
                                "" + it.bolwer?.run.toString()
                            )
                        }
                        e.printStackTrace()
                    }
                    binding.bowlerEco.setText("" + it.bolwer?.economy)

                    try {

                        if (it.bolwer?.maiden != null) {
                            binding.bowlerMaiden.setText(
                                "" + it.bolwer?.maiden.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler maiden" + e.message)
                        if (it.bolwer?.maiden != null) {
                            binding.bowlerMaiden.setText(
                                "" + it.bolwer?.maiden.toString()
                            )
                        }
                    }

                    try {

                        if (it.bolwer?.wicket != null) {
                            binding.bowlerWkt.setText(
                                "" + it.bolwer?.wicket.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler wicket" + e.message)
                        if (it.bolwer?.wicket != null) {
                            binding.bowlerWkt.setText(
                                "" + it.bolwer?.wicket.toString()
                            )
                        }
                    }
                }

            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()

        batsmanAdaptor = null
        adapterOver = null
        listOvers.clear()

    }

}