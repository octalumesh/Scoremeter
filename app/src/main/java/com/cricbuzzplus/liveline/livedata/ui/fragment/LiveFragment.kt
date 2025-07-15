package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.LiveFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.BroadcasterItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.CommentaryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.*
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.LiveCommentaryCricAdapter
import com.cricbuzzplus.liveline.utils.Constants
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class LiveFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: LiveFragmentBinding
    var listAd: ArrayList<SliderImage> = arrayListOf()
    var matchId = 0
    var matchStatus = ""
    var matchDate = ""
    var matchType = ""

    var batsmanAdaptor: BatsmanLiveCricAdapter? = null
    private var adapter3: LastFourOverAdapter? = null

    var overlist: ArrayList<ArrayList<MatchOddsResponseItem>>? = ArrayList()


    private var timer: Timer? = null

    var uiSet = false

    var matchNo = ""
    var series = ""
    var matchIdCricbuzz = 0
    var inning = 1
    var lastTimeStamp: Long = 0
    var prevTimeStamp: Long = 0

    var adapterCommentary: LiveCommentaryCricAdapter? = null

    var listCommentary = arrayListOf<CommentaryListItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = LiveFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()


        val bundle = arguments

        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            matchDate = bundle.getString("matchDate").toString()
            matchType = bundle.getString("matchType").toString()
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


        if (!matchStatus.equals("Live", true)) {

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


    fun getData() {

        if (listAd != null) {
            listAd.clear()
        }
        FirebaseFirestore.getInstance().collection("slider")
            .orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    //   showToast(e.message.toString())
                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    for (dc in snapshot!!.documentChanges) {

                        val prediction =
                            dc.document.toObject(SliderImage::class.java) as SliderImage
                        listAd.add(prediction)

                    }
                    setSlider(listAd)
                }

            }

    }

    fun setSlider(res: List<SliderImage>) {

        //  binding.viewPage.adapter= HomeSliderViewPagerAdapter(activity,res)


    }

    fun callMatchInfoCricBuzz() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getMatchInfoCricBuzz(matchIdCricbuzz)
            }
        }

    }


    fun callLiveScore(matchId: Int) {

        if (matchStatus.equals("Finished")) {
            if (isInternetConnection()) {
                viewModel.getLiveScore(matchId)
               // viewModel.getDLS(matchId)
               // viewModel.getDRS(matchId)
            }
        } else {
            if (isInternetConnection()) {
                viewModel.getLiveScore(matchId)
                //viewModel.getDLS(matchId)
                //viewModel.getDRS(matchId)
            }
            if (timer == null) {
                timer = Timer()
            }
            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    // recyclerViewMaincls();
                    if (uiSet) {
                        if (isInternetConnection()) {
                            viewModel.getLiveScore(matchId)

                            if (!matchType.equals("Test", true)) {
                                viewModel.getMatchOdds(matchId)
                            }
                        }
                    }
                }
            }, 2000, 2000)
        }

    }


    fun callMatchCommentary() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getCommentaryCricBuzz(matchIdCricbuzz)
            }
        }

    }

    fun callMatchCommentaryPaginate() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getLiveCommentaryCricPaginate(matchIdCricbuzz, inning, lastTimeStamp)
            }
        }

    }


    private fun setObservers() {
        observeExtras()
        observeLiveScore()
        observeLiveScorenotLive()
        observeMatchId()
        observeCommentary()
        observeCommentaryPaginate()
        observeMatchInfoCricBuzz()
    }

    fun observeLiveScorenotLive() {
        viewModel.getLiveScoreLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                mainScoreLiveData.value = it

            }
        })

    }

    fun observeCommentary() {

        viewModel.liveCommentaryCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it.miniscore != null){
                if (it.miniscore.matchScoreDetails != null && !it.miniscore.matchScoreDetails.customStatus.isNullOrEmpty()){
                    binding.resultToss.setText("${it.miniscore.matchScoreDetails.customStatus}")
                    if (it.miniscore.matchScoreDetails.customStatus.contains("opt to")){
                        binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                    }
                }
            }

            if (!it.commentaryList.isNullOrEmpty()) {

                if ((it.commentaryList.get(it.commentaryList.size - 1))?.inningsId != null) {

                    if (!listCommentary.isNullOrEmpty()) {
                        if (listCommentary.get((listCommentary.size - 1))?.inningsId != null) {
                            visibleThreshold = (listCommentary.size - 1)

                            inning = listCommentary.get((listCommentary.size - 1))?.inningsId!!
                            lastTimeStamp = listCommentary.get(listCommentary.size - 1)?.timestamp!!
                        }
                    } else {
                        visibleThreshold = (it.commentaryList.size - 1)

                        inning = it.commentaryList.get((it.commentaryList.size - 1))?.inningsId!!
                        lastTimeStamp =
                            it.commentaryList.get(it.commentaryList.size - 1)?.timestamp!!
                    }


                }
                //  Log.e(TAG, "observeCommentary: lastTimeStamp  $lastTimeStamp", )

                if (adapterCommentary == null) {

                    listCommentary.addAll(it.commentaryList as ArrayList<CommentaryListItem>)

                    adapterCommentary = LiveCommentaryCricAdapter(
                        listCommentary,
                        activity
                    )

                    binding.recyclerCommentary.adapter = adapterCommentary

                    addScroll()

                } else {

                    for (item in it.commentaryList) {
                        /*if (!listCommentary.contains(item)) {
                            listCommentary.add(0, item!!)
                        }*/

                        val existingItemIndex =
                            listCommentary.indexOfFirst { it.timestamp == item?.timestamp }


                        //Log.e(TAG, "observeCommentaryPaginate: ${existingItemIndex}", )

                        if (existingItemIndex != -1) {
                            // Remove the existing item with the matching timestamp
                            listCommentary[existingItemIndex] = item!!
                        } else {
                            // If no item with the same timestamp exists, add the new item
                            listCommentary.add(item!!)
                        }

                    }

                    try {
                        listCommentary.sortByDescending { it.timestamp }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    isLoading = false

                    //  listCommentary.addAll(it.commentaryList as ArrayList<CommentaryListItem>)

                    adapterCommentary?.updateList(listCommentary)

                }
            } else {
                // binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })

    }

    fun observeCommentaryPaginate() {

        viewModel.liveCommentaryPaginateCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.commentaryList.isNullOrEmpty()) {

                if ((it.commentaryList.get(it.commentaryList.size - 1))?.inningsId != null) {
                    visibleThreshold = (it.commentaryList.size - 1)

                    inning = it.commentaryList.get((it.commentaryList.size - 1))?.inningsId!!
                    lastTimeStamp = it.commentaryList.get(it.commentaryList.size - 1)?.timestamp!!

                }
                //  Log.e(TAG, "observeCommentary: lastTimeStamp  $lastTimeStamp", )

                if (adapterCommentary == null) {

                    listCommentary.addAll(it.commentaryList as ArrayList<CommentaryListItem>)

                    adapterCommentary = LiveCommentaryCricAdapter(
                        listCommentary,
                        activity
                    )

                    binding.recyclerCommentary.adapter = adapterCommentary

                } else {

                    isLoading = false

                    for (item in it.commentaryList) {
                        val existingItemIndex =
                            listCommentary.indexOfFirst { it.timestamp == item?.timestamp }


                        //Log.e(TAG, "observeCommentaryPaginate: ${existingItemIndex}", )

                        if (existingItemIndex != -1) {
                            // Remove the existing item with the matching timestamp
                            listCommentary[existingItemIndex] = item!!
                        } else {
                            // If no item with the same timestamp exists, add the new item
                            listCommentary.add(item!!)
                        }
                    }
                    //listCommentary.addAll(it.commentaryList as ArrayList<CommentaryListItem>)

                    adapterCommentary?.updateList(listCommentary)

                }
            }

        })

    }

    private fun observeMatchInfoCricBuzz() {

        viewModel.matchInfoCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {

                    if (it.matchInfo != null) {

                        if (matchStatus.equals("Finished")) {

                            if (!it.matchInfo.playersOfTheMatch.isNullOrEmpty()) {

                                var playerMatch = it.matchInfo.playersOfTheMatch.get(0)

                                binding.linearPlayerOfMatch.visibility = View.VISIBLE
                                binding.linearScore.visibility = View.GONE

                                binding.playerName.setText("" + playerMatch?.fullName)
                                Glide.with(activity)
                                    .load("" + Constants.cricbuzzImgFirst + playerMatch?.faceImageId + Constants.cricbuzzImgSecond)
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(binding.playerImage)

                            } else {
                                binding.linearPlayerOfMatch.visibility = View.GONE
                                binding.linearScore.visibility = View.VISIBLE
                            }
                        } else {
                            binding.linearPlayerOfMatch.visibility = View.GONE
                            binding.linearScore.visibility = View.VISIBLE
                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })

    }

    var isLoading = false // Used to prevent multiple API calls while loading
    var visibleThreshold = 0 // Number of items from the end to trigger loading

    fun addScroll() {


        binding.recyclerCommentary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.recyclerCommentary.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val totalItemCount = layoutManager.itemCount

                //Log.e(TAG, "onScrolled: totalItemCount $totalItemCount  lastVisibleItem $lastVisibleItem" )

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more data here
                    isLoading = true // Set to true to prevent multiple calls

                    if (prevTimeStamp != lastTimeStamp) {
                        prevTimeStamp = lastTimeStamp

                        //  Log.e(TAG, "visibleThreshold: "+visibleThreshold )

                        callMatchCommentaryPaginate()
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
                callMatchInfoCricBuzz()

                callMatchCommentary()

            }
        })

    }


    var mainScoreLiveData = MutableLiveData<LiveResponse>()


    fun setData(liveResponse: LiveResponse) {


        mainScoreLiveData.value = liveResponse

        handleProgressLoader(false)

        //Handler(Looper.getMainLooper()).postDelayed({
        callMatchCommentary()
        // },1000)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }
        batsmanAdaptor = null
        adapterCommentary = null
        listCommentary.clear()
        adapter3 = null
        uiSet = false

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
                   // binding.resultToss.setText("${it.trailLead}")
                } else if (!it.toss.isNullOrEmpty()) {
                   //binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                   //binding.resultToss.setText("${it.toss}")
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


    private fun observeExtras() {
        /*viewModel!!.getLoaderLiveData().observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

}