package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
import com.cricbuzzplus.liveline.databinding.LiveLineNewFragmentBinding
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.ui.adapter.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class LiveLineNewFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: LiveLineNewFragmentBinding
    var listAd: ArrayList<SliderImage> = arrayListOf()
    var matchId = 0
    var matchStatus = ""
    var matchDate = ""
    var matchType = ""
    var inning1TeamName = ""
    var inning2TeamName = ""
    var inning1TeamImg = ""
    var inning2TeamImg = ""
    private var counterAudio = "HINDI"

    var OverDecimal_b = 0
    var runningBallsTotal2 = 0
    var scoreMain = 0

    var latestBallsAdaptor: LatestBallsAdaptor? = null
    var batsmanAdaptor: BatsmanLiveCricAdapter? = null
    private var adapter3: LastFourOverAdapter? = null
    var oversList: List<Last4OverItem?> = arrayListOf()

    var overlist: ArrayList<ArrayList<MatchOddsResponseItem>>? = ArrayList()

    private val firstInningList = arrayListOf<MatchOddsResponseItem>()
    private val secondInningList = arrayListOf<MatchOddsResponseItem>()
    private val tenOversList1St = arrayListOf<MatchOddsResponseItem>()
    private val tenOversList2nd = arrayListOf<MatchOddsResponseItem>()

    var buttonHindiAct: Boolean = false
    var buttonEngAct = false
    private var timer: Timer? = null

    public var hindiActive = true
    public var engActive = false
    public var voiceActive = false

    var textToSpeech: TextToSpeech? = null

    var compValue = ""
    var first_circle = ""

    var uiSet = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = LiveLineNewFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()


        val bundle = arguments

        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            matchDate = bundle.getString("matchDate").toString()
            matchType = bundle.getString("matchType").toString()
        } else {
            Log.d("TAG", "bundle is null")
        }
        layoutRemoveUpcomingIf(matchStatus, matchDate)
        Log.e(TAG, "onCreateView: " + matchId + " " + matchStatus)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AudioLangaugeActive()

        binding.rl1StSession.setOnClickListener {
            if (binding.linear1stSession.isVisible) {
                binding.session1stUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linear1stSession.visibility = View.GONE
            } else {
                binding.session1stUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
                binding.linear1stSession.visibility = View.VISIBLE
            }
        }

        binding.rl2NdSession.setOnClickListener {
            if (binding.linear2ndSession.isVisible) {
                binding.session2ndUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linear2ndSession.visibility = View.GONE
            } else {
                binding.session2ndUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
                binding.linear2ndSession.visibility = View.VISIBLE
            }
        }

        binding.textviewBowlerName.setOnClickListener {

            if (!binding.textviewBowlerName.text.isNullOrEmpty() && !binding.textviewBowlerName.text.equals(
                    "-"
                )
            ) {

                /* val intent = Intent(activity, PlayerProfileActivity::class.java)
                 intent.putExtra("playerName",binding.textviewBowlerName.text.toString())
                 startActivity(intent)*/
            }
        }


        if (matchStatus.equals("Upcoming", true)) {
            handleProgressLoader(false)
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        } else if (!matchStatus.equals("Live", true)) {

            if (isInternetConnection()) {
                viewModel.getLiveScoreMain(matchId)
            }
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


    @SuppressLint("UseRequireInsteadOfGet")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun AudioLangaugeActive() {
        val sharedPrefAudio = activity!!.getSharedPreferences("saveDataAudio", Context.MODE_PRIVATE)
        counterAudio =
            sharedPrefAudio.getString("countervalueAudio", Context.MODE_PRIVATE.toString())!!

        Log.e("TAG", "onInit: " + counterAudio)

        //Toast.makeText(getActivity(), "shardPref Audio: "+counterAudio, Toast.LENGTH_SHORT).show();
        binding.btnAudioHindi.setOnClickListener {
            buttonHindiAct = true
            buttonEngAct = false
            MainApplication.applicationInstance.setLiveScoreLanguage("hindi")
            Toast.makeText(activity, "Audio Commentry in Hindi", Toast.LENGTH_SHORT).show()
            binding.btnAudioHindi.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.yellow_lgt
                    )
                )
            binding.btnAudioEng.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.colorAccent
                    )
                )

            //buttonInning5.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.scrbordbtn_deact_back)));


            counterAudio = "HINDI"
            val sharedPreferences1 =
                activity!!.getSharedPreferences("saveDataAudio", Context.MODE_PRIVATE)
            val editor = sharedPreferences1.edit()
            editor.putString("countervalueAudio", counterAudio)
            //editor.apply();
            editor.commit()
        }
        binding.btnAudioEng.setOnClickListener {
            buttonEngAct = true
            buttonHindiAct = false

            MainApplication.applicationInstance.setLiveScoreLanguage("english")
            Toast.makeText(activity, "Audio Commentry in English", Toast.LENGTH_SHORT).show()
            binding.btnAudioEng.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.yellow_lgt
                    )
                )
            binding.btnAudioHindi.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.colorAccent
                    )
                )


            counterAudio = "ENG"
            val sharedPreferences1 = activity!!.getSharedPreferences(
                "saveDataAudio",
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences1.edit()
            editor.putString("countervalueAudio", counterAudio)
            //editor.apply();
            editor.commit()
        }
    }

    fun callLiveScore(matchId: Int) {

        if (matchStatus.equals("Finished")) {
            if (isInternetConnection()) {
                viewModel.getLiveScore(matchId)
                //viewModel.getDLS(matchId)
                //viewModel.getDRS(matchId)
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

                            /*if (!matchType.equals("Test", true)) {
                                viewModel.getMatchOdds(matchId)
                            }*/
                        }
                    }
                }
            }, 2000, 2000)
        }

    }

    private fun setObservers() {
        observeExtras()
        observeLiveScore()
        observeLiveScorenotLive()
        observeMatchOdds()
        observeMatchDLS()
        //observeMatchDRS()
    }

    private fun observeMatchDRS() {
        viewModel.drsLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.linerlayoutDrs.visibility = View.VISIBLE


                Glide.with(activity).load(it.teamAImage).placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.drsImgA)
                Glide.with(activity).load(it.teamBImage).placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.drsImgB)

                binding.drsTeamA.setText("" + it.teamAShort)
                binding.drsTeamB.setText("" + it.teamBShort)

                binding.drsRemainTeamA.setText("${it.teamARemaining}")
                binding.drsRemainTeamB.setText("${it.teamBRemaining}")

            }

        })

    }

    private fun observeMatchDLS() {
        viewModel.dlsLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.linerlayoutDls.visibility = View.VISIBLE

                if (it.firstInningTeam.equals(it.teamA)) {

                    Glide.with(activity).load(it.teamAImage).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.dlsImgA)
                    Glide.with(activity).load(it.teamBImage).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.dlsImgB)

                    binding.dlsTeamA.setText("" + it.teamAShort)
                    binding.dlsTeamB.setText("" + it.teamBShort)

                    binding.dlsTeamAScore.setText("${it.firstInningScore}-${it.firstInningWicket} (${it.over})")

                    binding.dlsTeamBScore.setText("${it.secondInningTarget} (${it.secondInningOverTarget} Ovr)")

                    binding.dlsRequire.setText("${it.teamBShort} required ${it.secondInningTarget} in ${it.secondInningOverTarget} Ovr")

                } else {

                    Glide.with(activity).load(it.teamAImage).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.dlsImgB)
                    Glide.with(activity).load(it.teamBImage).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.dlsImgA)

                    binding.dlsTeamB.setText("" + it.teamAShort)
                    binding.dlsTeamA.setText("" + it.teamBShort)

                    binding.dlsTeamAScore.setText("${it.firstInningScore}-${it.firstInningWicket} (${it.over})")

                    binding.dlsTeamBScore.setText("${it.secondInningTarget} (${it.secondInningOverTarget} Ovr)")

                    binding.dlsRequire.setText("${it.teamAShort} required ${it.secondInningTarget} in ${it.secondInningOverTarget} Ovr")

                }

            }

        })

    }

    private fun observeMatchOdds() {
        viewModel.matchOddsLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.isNullOrEmpty()) {

                //  sessionTest(it)
            }

        })

    }

    var firstSessionTest = arrayListOf<TestSessions>()
    var secondSessionTest = arrayListOf<TestSessions>()

    fun sessionTest(matchOddsList: List<MatchOddsResponseItem>) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        if (!firstSessionTest.isNullOrEmpty()) {
            firstSessionTest.clear()
        }

        if (!secondSessionTest.isNullOrEmpty()) {
            secondSessionTest.clear()
        }

        if (!firstInningList.isNullOrEmpty()) {
            firstInningList.clear()
        }
        if (!secondInningList.isNullOrEmpty()) {
            secondInningList.clear()
        }
        if (overlist != null) {
            overlist!!.clear()
        }

        if (!matchOddsList.isNullOrEmpty()) {
            for (item in matchOddsList) {
                if (item.inning == 1) {
                    firstInningList.add(item)
                } else if (item.inning == 2) {
                    secondInningList.add(item)
                }
            }
        }


        if (matchType.equals("ODI")) {

            var firstOvers = 10
            var firstMinOver = 0
            var count = 1

            var firstMin = 0
            var firstMax = 0
            var firstPass = "0"
            var firstOpen = "0"

            var firstWkt = 0
            var firstRate = ""
            var firstFav = ""


            var secondOvers = 10
            var secondMinOver = 0
            var secondcount = 1

            var secondMin = 0
            var secondMax = 0
            var secondPass = "0"
            var secondOpen = "0"

            var secondWkt = 0
            var secondRate = ""
            var secondFav = ""


            for (items in firstInningList) {


                if (items.overs?.toDouble()!! > firstMinOver && items.overs.toDouble() <= ((firstOvers - 1) + 0.5)) {
                    tenOversList1St.add(items)
                    if (items.overs.equals((firstMinOver + 0.1).toString())) {
                        firstOpen = items?.sMax.toString()
                        firstMin = items.sMax!!.toInt()
                        firstMax = items.sMax.toInt()
                    } else if (items.overs.equals((firstMinOver + 0.2).toString())) {
                        if (firstOpen.equals("0")) {
                            firstOpen = items?.sMax.toString()
                            firstMin = items.sMax!!.toInt()
                            firstMax = items.sMax.toInt()
                        }
                    } else if (items.overs.equals((firstMinOver + 0.3).toString())) {
                        if (firstOpen.equals("0")) {
                            firstOpen = items?.sMax.toString()
                            firstMin = items.sMax!!.toInt()
                            firstMax = items.sMax.toInt()
                        }
                    }

                    if (items.runs.equals("WK", true) || items.runs.toString()
                            .contains("New Batter", true) || items.runs.toString()
                            .contains("New Bat", true) || items.runs.toString()
                            .contains("Bowled", true)
                        || items.runs.toString().contains("NEW BAT", true)
                        || items.runs.toString().contains("BAT", true)
                    ) {
                        firstWkt += 1
                    }

                }

                if (items.overs.equals("" + firstOvers)) {
                    firstPass = items.score.toString()
                    firstRate = "${items.minRate}-${items.maxRate}"
                    firstFav = items.team.toString()

                    for (item in tenOversList1St) {
                        if (item.sMax!!.toInt() != 0) {
                            if (item.sMax.toInt() < firstMin) {
                                firstMin = item.sMax.toInt()
                            }
                            if (firstMax < item.sMax.toInt()) {
                                firstMax = item.sMax.toInt()
                            }
                        }
                    }

                    firstSessionTest.add(
                        TestSessions(
                            firstOvers,
                            firstMin,
                            firstMax,
                            firstPass,
                            firstOpen,
                            firstRate,
                            "" + firstWkt,
                            firstFav
                        )
                    )

                    tenOversList1St.clear()

                    /*Log.e(
                        "TAGTestSession",
                        "sessionTest: overs:- ${firstOvers} , min:- ${firstMin} , max:- ${firstMax} , pass:- ${firstPass} , open:- ${firstOpen}"
                    )*/

                    count += 1
                    firstMinOver = firstOvers
                    firstOvers = 5 * (count + 1)
                    firstMin = 0
                    firstMax = 0
                    firstPass = "0"
                    firstOpen = "0"
                    firstRate = ""
                    firstFav = ""
                    firstWkt = 0

                    //     Log.e( "TAGSession", "observeMatchOdds pass  10   : ${items.sMax}")
                }


            }


            for (items in secondInningList) {


                if (items.overs?.toDouble()!! > secondMinOver && items.overs.toDouble() <= ((secondOvers - 1) + 0.5)) {
                    tenOversList2nd.add(items)
                    if (items.overs.equals((secondMinOver + 0.1).toString())) {
                        secondOpen = items?.sMax.toString()
                        secondMin = items.sMax!!.toInt()
                        secondMax = items.sMax.toInt()
                    } else if (items.overs.equals((secondMinOver + 0.2).toString())) {
                        if (secondOpen.equals("0")) {
                            secondOpen = items?.sMax.toString()
                            secondMin = items.sMax!!.toInt()
                            secondMax = items.sMax.toInt()
                        }
                    } else if (items.overs.equals((secondMinOver + 0.3).toString())) {
                        if (secondOpen.equals("0")) {
                            secondOpen = items?.sMax.toString()
                            secondMin = items.sMax!!.toInt()
                            secondMax = items.sMax.toInt()
                        }
                    }

                    if (items.runs.equals("WK", true) || items.runs.toString()
                            .contains("New Batter", true) || items.runs.toString()
                            .contains("New Bat", true) || items.runs.toString()
                            .contains("Bowled", true)
                        || items.runs.toString().contains("NEW BAT", true)
                        || items.runs.toString().contains("BAT", true)
                    ) {
                        secondWkt += 1
                    }

                }

                if (items.overs.equals("" + secondOvers)) {
                    secondPass = items.score.toString()
                    secondRate = "${items.minRate}-${items.maxRate}"
                    secondFav = items.team.toString()

                    for (item in tenOversList2nd) {
                        if (item.sMax!!.toInt() != 0) {
                            if (item.sMax.toInt() < secondMin) {
                                secondMin = item.sMax.toInt()
                            }
                            if (secondMax < item.sMax.toInt()) {
                                secondMax = item.sMax.toInt()
                            }
                        }
                    }

                    secondSessionTest.add(
                        TestSessions(
                            secondOvers,
                            secondMin,
                            secondMax,
                            secondPass,
                            secondOpen,
                            secondRate,
                            "" + secondWkt,
                            secondFav
                        )
                    )

                    tenOversList2nd.clear()

                    /*Log.e(
                        "TAGTestSession",
                        "sessionTest2nd: overs:- ${secondOvers} , min:- ${secondMin} , max:- ${secondMax} , pass:- ${secondPass} , open:- ${secondOpen}"
                    )*/

                    secondcount += 1
                    secondMinOver = secondOvers
                    secondOvers = 5 * (secondcount + 1)
                    secondMin = 0
                    secondMax = 0
                    secondPass = "0"
                    secondOpen = "0"
                    secondRate = ""
                    secondFav = ""
                    secondWkt = 0

                    //     Log.e( "TAGSession", "observeMatchOdds pass  10   : ${items.sMax}")
                }


            }


            if (!firstSessionTest.isNullOrEmpty()) {
                binding.inningsSessionLinearComp.visibility = View.VISIBLE

                Glide.with(activity).load(inning1TeamImg).apply(requestOptions)
                    .into(binding.inning1SessionImage)
                binding.inning1SessionTeamName.setText(inning1TeamName + " - 1st Inning")

                binding.recyclerSessionInning1.adapter =
                    MatchOddsLiveAdapter(firstSessionTest, activity)
            }

            if (!secondSessionTest.isNullOrEmpty()) {
                binding.inning2SessionCard.visibility = View.VISIBLE

                Glide.with(activity).load(inning2TeamImg).apply(requestOptions)
                    .into(binding.inning2SessionImage)
                binding.inning2SessionTeamName.setText(inning2TeamName + " - 2nd Inning")

                binding.recyclerSessionInning2.adapter =
                    MatchOddsLiveAdapter(secondSessionTest, activity)
            }
        } else if (matchType.equals("T20")) {


            var firstOvers = 6
            var firstMinOver = 0
            var count = 1

            var firstMin = 0
            var firstMax = 0
            var firstPass = "0"
            var firstOpen = "0"


            var firstWkt = 0
            var firstRate = ""
            var firstFav = ""


            var secondOvers = 6
            var secondMinOver = 0
            var secondcount = 1

            var secondMin = 0
            var secondMax = 0
            var secondPass = "0"
            var secondOpen = "0"

            var secondWkt = 0
            var secondRate = ""
            var secondFav = ""


            for (items in firstInningList) {


                if (items.overs?.toDouble()!! > firstMinOver && items.overs.toDouble() <= ((firstOvers - 1) + 0.5)) {
                    tenOversList1St.add(items)
                    if (items.overs.equals((firstMinOver + 0.1).toString())) {
                        firstOpen = items?.sMax.toString()
                        firstMin = items.sMax!!.toInt()
                        firstMax = items.sMax.toInt()
                    } else if (items.overs.equals((firstMinOver + 0.2).toString())) {
                        if (firstOpen.equals("0")) {
                            firstOpen = items?.sMax.toString()
                            firstMin = items.sMax!!.toInt()
                            firstMax = items.sMax.toInt()
                        }
                    } else if (items.overs.equals((firstMinOver + 0.3).toString())) {
                        if (firstOpen.equals("0")) {
                            firstOpen = items?.sMax.toString()
                            firstMin = items.sMax!!.toInt()
                            firstMax = items.sMax.toInt()
                        }
                    }
                    if (items.runs.equals("WK", true) || items.runs.toString()
                            .contains("New Batter", true) || items.runs.toString()
                            .contains("New Bat", true) || items.runs.toString()
                            .contains("Bowled", true)
                        || items.runs.toString().contains("NEW BAT", true)
                        || items.runs.toString().contains("BAT", true)
                    ) {
                        firstWkt += 1
                    }

                }

                if (items.overs.equals("" + firstOvers)) {
                    firstPass = items.score.toString()
                    firstRate = "${items.minRate}-${items.maxRate}"

                    firstFav = items.team.toString()

                    for (item in tenOversList1St) {
                        if (item.sMax!!.toInt() != 0) {
                            if (item.sMax.toInt() < firstMin) {
                                firstMin = item.sMax.toInt()
                            }
                            if (firstMax < item.sMax.toInt()) {
                                firstMax = item.sMax.toInt()
                            }
                        }
                    }

                    firstSessionTest.add(
                        TestSessions(
                            firstOvers,
                            firstMin,
                            firstMax,
                            firstPass,
                            firstOpen,
                            firstRate,
                            "" + firstWkt,
                            firstFav
                        )
                    )

                    tenOversList1St.clear()

                    /*Log.e(
                        "TAGTestSession",
                        "sessionTest: overs:- ${firstOvers} , min:- ${firstMin} , max:- ${firstMax} , pass:- ${firstPass} , open:- ${firstOpen}"
                    )*/

                    count += 1
                    if (firstOvers == 6) {
                        firstMinOver = firstOvers
                        firstOvers = 10
                        firstMin = 0
                        firstMax = 0
                        firstPass = "0"
                        firstOpen = "0"
                        firstRate = ""
                        firstFav = ""
                        firstWkt = 0
                    } else {
                        firstMinOver = firstOvers
                        firstOvers = 5 * count
                        firstMin = 0
                        firstMax = 0
                        firstPass = "0"
                        firstOpen = "0"
                        firstRate = ""
                        firstFav = ""
                        firstWkt = 0
                    }


                    //     Log.e( "TAGSession", "observeMatchOdds pass  10   : ${items.sMax}")
                }


            }


            for (items in secondInningList) {


                if (items.overs?.toDouble()!! > secondMinOver && items.overs.toDouble() <= ((secondOvers - 1) + 0.5)) {
                    tenOversList2nd.add(items)
                    if (items.overs.equals((secondMinOver + 0.1).toString())) {
                        secondOpen = items?.sMax.toString()
                        secondMin = items.sMax!!.toInt()
                        secondMax = items.sMax.toInt()
                    } else if (items.overs.equals((secondMinOver + 0.2).toString())) {
                        if (secondOpen.equals("0")) {
                            secondOpen = items?.sMax.toString()
                            secondMin = items.sMax!!.toInt()
                            secondMax = items.sMax.toInt()
                        }
                    } else if (items.overs.equals((secondMinOver + 0.3).toString())) {
                        if (secondOpen.equals("0")) {
                            secondOpen = items?.sMax.toString()
                            secondMin = items.sMax!!.toInt()
                            secondMax = items.sMax.toInt()
                        }
                    }

                    if (items.runs.equals("WK", true) || items.runs.toString()
                            .contains("New Batter", true) || items.runs.toString()
                            .contains("New Bat", true) || items.runs.toString()
                            .contains("Bowled", true)
                        || items.runs.toString().contains("NEW BAT", true)
                        || items.runs.toString().contains("BAT", true)
                    ) {
                        secondWkt += 1
                    }
                }

                if (items.overs.equals("" + secondOvers)) {
                    secondPass = items.score.toString()
                    secondRate = "${items.minRate}-${items.maxRate}"

                    secondFav = items.team.toString()

                    for (item in tenOversList2nd) {
                        if (item.sMax!!.toInt() != 0) {
                            if (item.sMax.toInt() < secondMin) {
                                secondMin = item.sMax.toInt()
                            }
                            if (secondMax < item.sMax.toInt()) {
                                secondMax = item.sMax.toInt()
                            }
                        }
                    }

                    secondSessionTest.add(
                        TestSessions(
                            secondOvers,
                            secondMin,
                            secondMax,
                            secondPass,
                            secondOpen,
                            secondRate,
                            "" + secondWkt,
                            secondFav
                        )
                    )

                    tenOversList2nd.clear()

                    /*Log.e(
                        "TAGTestSession",
                        "sessionTest2nd: overs:- ${secondOvers} , min:- ${secondMin} , max:- ${secondMax} , pass:- ${secondPass} , open:- ${secondOpen}"
                    )*/

                    secondcount += 1


                    if (secondOvers == 6) {

                        secondMinOver = secondOvers
                        secondOvers = 10
                        secondMin = 0
                        secondMax = 0
                        secondPass = "0"
                        secondOpen = "0"
                        secondRate = ""
                        secondFav = ""
                        secondWkt = 0
                    } else {


                        secondMinOver = secondOvers
                        secondOvers = 5 * secondcount
                        secondMin = 0
                        secondMax = 0
                        secondPass = "0"
                        secondOpen = "0"
                        secondRate = ""
                        secondFav = ""
                        secondWkt = 0
                    }

                    //     Log.e( "TAGSession", "observeMatchOdds pass  10   : ${items.sMax}")
                }


            }


            if (!firstSessionTest.isNullOrEmpty()) {
                binding.inningsSessionLinearComp.visibility = View.VISIBLE

                Glide.with(activity).load(inning1TeamImg).apply(requestOptions)
                    .into(binding.inning1SessionImage)
                binding.inning1SessionTeamName.setText(inning1TeamName + " - 1st Inning")

                binding.recyclerSessionInning1.adapter =
                    MatchOddsLiveAdapter(firstSessionTest, activity)
            }

            if (!secondSessionTest.isNullOrEmpty()) {
                binding.inning2SessionCard.visibility = View.VISIBLE

                Glide.with(activity).load(inning2TeamImg).apply(requestOptions)
                    .into(binding.inning2SessionImage)
                binding.inning2SessionTeamName.setText(inning2TeamName + " - 2nd Inning")

                binding.recyclerSessionInning2.adapter =
                    MatchOddsLiveAdapter(secondSessionTest, activity)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }
        batsmanAdaptor = null
        adapter3 = null
        uiSet = false

    }


    var mainScoreLiveData = MutableLiveData<LiveResponse>()


    fun setData(liveResponse: LiveResponse) {

        if (matchStatus.equals("Upcoming", true)) {
            handleProgressLoader(false)
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        } else {
            mainScoreLiveData.value = liveResponse

            if (isInternetConnection()) {
                //viewModel.getDLS(matchId)
                //viewModel.getDRS(matchId)

                /*if (!matchType.equals("Test", true)) {
                    viewModel.getMatchOdds(matchId)
                }*/

            }
        }
    }

    fun observeLiveScorenotLive() {
        viewModel.getLiveScoreLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                mainScoreLiveData.value = it

                if (isInternetConnection()) {
                    //viewModel.getDLS(matchId)
                    //viewModel.getDRS(matchId)

                    /*if (!matchType.equals("Test", true)) {
                        viewModel.getMatchOdds(matchId)
                    }*/

                }

            }
        })

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

                Log.e(TAG, "observeLiveScore ddsd  liveline frag : ${it.matchId}")

                /*if (!it.last36ball.isNullOrEmpty()) {
                    if (latestBallsAdaptor == null) {
                        latestBallsAdaptor = LatestBallsAdaptor(it.last36ball, context)
                        binding.recyclerviewOverLastball.adapter = latestBallsAdaptor
                        binding.recyclerviewOverLastball.scrollToPosition(latestBallsAdaptor?.itemCount!! - 1)
                    } else {
                        latestBallsAdaptor?.updateList(it.last36ball)
                    }
                }*/


                binding.recyclerviewOverLastball.addOnItemTouchListener(object :
                    RecyclerView.OnItemTouchListener {
                    override fun onInterceptTouchEvent(
                        rv: RecyclerView,
                        e: MotionEvent
                    ): Boolean {
                        val action = e.action
                        when (action) {
                            MotionEvent.ACTION_MOVE -> binding.recyclerviewOverLastball.getParent()
                                .requestDisallowInterceptTouchEvent(true)
                        }
                        return false
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
                })
                ///////////////////////////////////////////////////////////

                if (it.last4overs != null) {
                    var jsonArrayLast4Over = it?.last4overs

                    if (!jsonArrayLast4Over.isNullOrEmpty()) {

                        if (adapter3 == null) {
                            oversList = jsonArrayLast4Over
                            adapter3 = LastFourOverAdapter(context, jsonArrayLast4Over)
                            binding.recyclerviewOverLastball.setAdapter(adapter3)
                            binding.recyclerviewOverLastball.scrollToPosition(adapter3?.itemCount!! - 1)
                        } else {
                            adapter3?.updateList(jsonArrayLast4Over)
                            if (!oversList.equals(jsonArrayLast4Over)) {
                                oversList = jsonArrayLast4Over
                                binding.recyclerviewOverLastball.scrollToPosition(adapter3?.itemCount!! - 1)
                            }
                            // binding.recyclerviewOverLastball.scrollToPosition(adapter3?.itemCount!! - 1)
                        }
                    }
                }

                if (it.nextBatsman != null && !it.nextBatsman.toString().isNullOrEmpty()) {
                    binding.textviewNextBatsman.setText("" + it.nextBatsman)
                }


                if (!it.yetToBet.isNullOrEmpty()) {
                    var batsmans = ""

                    for (bat in it.yetToBet) {

                        batsmans = batsmans + bat + ", "

                    }

                    if (!batsmans.isNullOrEmpty()) {

                        if (matchStatus.equals("Finished")) {
                            binding.textviewYetToBat.setText("" + batsmans)
                        } else {
                            binding.textviewYetToBat.setText("" + batsmans)
                        }
                    }
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


                if (!it.currentInning.isNullOrEmpty()) {

                    if (it?.currentInning.equals("1")) {

                        if (it?.battingTeam.toString().equals(it?.teamAId.toString())) {

                            inning1TeamImg = it?.teamAImg.toString()
                            inning1TeamName = it?.teamAShort.toString()

                            inning2TeamImg = it?.teamBImg.toString()
                            inning2TeamName = it?.teamBShort.toString()

                        } else {
                            inning1TeamImg = it?.teamBImg.toString()
                            inning1TeamName = it?.teamBShort.toString()

                            inning2TeamImg = it?.teamAImg.toString()
                            inning2TeamName = it?.teamAShort.toString()
                        }

                    } else if (it?.currentInning.equals("2")) {

                        if (it?.battingTeam.toString().equals(it?.teamAId.toString())) {

                            inning2TeamImg = it?.teamAImg.toString()
                            inning2TeamName = it?.teamAShort.toString()

                            inning1TeamImg = it?.teamBImg.toString()
                            inning1TeamName = it?.teamBShort.toString()

                        } else {
                            inning2TeamImg = it?.teamBImg.toString()
                            inning2TeamName = it?.teamBShort.toString()

                            inning1TeamImg = it?.teamAImg.toString()
                            inning1TeamName = it?.teamAShort.toString()
                        }

                    }
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

                /*if (it.bolwer != null) {
                    binding.textviewBowlerName.setText("" + it.bolwer?.name)
                    binding.textviewLiveBowlerOver.setText("" + it.bolwer?.over)

                    try {
                        if (it.bolwer?.run != null) {
                            binding.textviewLiveBowlerRun.setText(
                                "" + it.bolwer?.run.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler run" + e.message)
                        if (it.bolwer?.run != null) {
                            binding.textviewLiveBowlerRun.setText(
                                "" + it.bolwer?.run.toString()
                            )
                        }
                        e.printStackTrace()
                    }
                    binding.textviewLiveBowlerEco.setText("" + it.bolwer?.economy)

                    try {

                        if (it.bolwer?.maiden != null) {
                            binding.textviewLiveBowlerMaiden.setText(
                                "" + it.bolwer?.maiden.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler maiden" + e.message)
                        if (it.bolwer?.maiden != null) {
                            binding.textviewLiveBowlerMaiden.setText(
                                "" + it.bolwer?.maiden.toString()
                            )
                        }
                    }

                    try {

                        if (it.bolwer?.wicket != null) {
                            binding.textviewLiveBowlerWkt.setText(
                                "" + it.bolwer?.wicket.toString().toDouble().toInt()
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "observeLiveScore: bowler wicket" + e.message)
                        if (it.bolwer?.wicket != null) {
                            binding.textviewLiveBowlerWkt.setText(
                                "" + it.bolwer?.wicket.toString()
                            )
                        }
                    }
                }*/

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


                var lambi_min = ""
                if (it.lambiMin.isNullOrEmpty()) {

                } else {
                    lambi_min = it.lambiMin.toString()
                }

                var lambi_max = ""
                if (it.lambiMax.isNullOrEmpty()) {

                } else {
                    lambi_max = it.lambiMax.toString()
                }

                if (it.sOvr.isNullOrEmpty()) {
                    binding.layoutLivepageSession1.setVisibility(View.GONE)
                } else if (it.sOvr.equals("0")) {
                    binding.layoutLivepageSession1.setVisibility(View.GONE)
                }

                var lambi_ovr = ""
                if (it.lambiOvr.isNullOrEmpty()) {
                    binding.linearlayoutSessionLambi.setVisibility(View.GONE)
                } else {
                    lambi_ovr = it.lambiOvr.toString()
                }

                if (!it.firstCircle.isNullOrEmpty()) {
                    first_circle = it?.firstCircle.toString()
                }

                val team_a_scores: String
                team_a_scores = if (it?.teamAScores.isNullOrEmpty()) {
                    ""
                } else {
                    it?.teamAScores.toString()
                    //Log.d("TAG", "score a team main: "+team_a_scores+"  ");
                }
                val team_a_over: String
                team_a_over = if (it?.teamAOver.isNullOrEmpty()) {
                    ""
                } else {
                    it?.teamAOver.toString()
                }
                val team_b_scores: String
                team_b_scores = if (it?.teamBScores.isNullOrEmpty()) {
                    ""
                } else {
                    it?.teamBScores.toString()
                }
                val team_b_over: String
                team_b_over = if (it?.teamBOver.isNullOrEmpty()) {
                    ""
                } else {
                    it?.teamBOver.toString()
                }



                if (!it.session.isNullOrEmpty()) {

                    if (it.matchType.equals("Test")) {

                        binding.sessionLayout.visibility = View.VISIBLE
                        binding.session.setText(
                            HtmlCompat.fromHtml(
                                it.session.toString(),
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                        )
                    } else {
                        binding.sessionLayout.visibility = View.GONE
                    }

                }

                if (it.matchType.equals("Test")) {
                    binding.inningsSessionLinearComp.visibility = View.GONE

                }

                if (it.battingTeam == it.teamAId && it.ballingTeam == it.teamBId) {
                    if (it.currentInning.equals("1")) {
                        binding.tvTeamAScoreEstIning.setText("${it.teamAShort}: $team_a_scores ($team_a_over)")
                        binding.tvTeamBScoreEstIning.setText("${it.teamBShort} Yet to Bat")
                    } else {
                        binding.tvTeamAScoreEstIning.setText("${it.teamAShort}: $team_a_scores ($team_a_over)")
                        binding.tvTeamBScoreEstIning.setText("${it.teamBShort}: $team_b_scores ($team_b_over)")
                    }
                } else {
                    if (it.currentInning.equals("1")) {
                        binding.tvTeamAScoreEstIning.setText("${it.teamBShort}: $team_b_scores ($team_b_over)")
                        binding.tvTeamBScoreEstIning.setText("${it.teamAShort} Yet to Bat")
                    } else {
                        binding.tvTeamAScoreEstIning.setText("${it.teamAShort}: $team_a_scores ($team_a_over)")
                        binding.tvTeamBScoreEstIning.setText("${it.teamBShort}: $team_b_scores ($team_b_over)")
                    }
                }

                if (!it.sOvr.isNullOrEmpty() && !it.sOvr.equals("0")) {

                    binding.tvSessionLive.setText(it.sOvr.toString())
                    binding.tvSessionLive4.setText(it.sMax.toString())
                    binding.tvSessionLive6.setText(it.sMin.toString())

                    if (!it.sMax.isNullOrEmpty()) {
                        var totalBalls = 0
                        if (it.sOvr.contains(".")) {

                            val overs = it.sOvr?.split(".")!!.toTypedArray()
                            val over = overs[0].toInt()
                            val ball = overs[1].toInt()

                            totalBalls = 6 * (over) + ball

                        } else {
                            totalBalls = 6 * (it.sOvr.toInt())
                        }



                        if (it.battingTeam != null) {
                            if (it.battingTeam?.equals(it.teamAId)!!) {

                                if (it.teamAScore != null) {

                                    if (it.teamAScore.jsonMember1 != null) {

                                        if (!it.teamAScore.jsonMember1.score.isNullOrEmpty() && !it.teamAScore.jsonMember1.ball.isNullOrEmpty()) {

                                            var runNeeded =
                                                it.sMax.toInt() - it.teamAScore.jsonMember1.score.toInt()

                                            var ballremaining = 0

                                            if (it.teamAScore.jsonMember1.ball.contains(".")) {

                                                val overs =
                                                    it.teamAScore.jsonMember1.ball.split(".")
                                                        .toTypedArray()
                                                val over = overs[0].toInt()
                                                val ball = overs[1].toInt()

                                                ballremaining = totalBalls - ((over * 6) + ball)

                                            } else {
                                                val over = it.teamAScore.jsonMember1.ball.toInt()

                                                ballremaining = totalBalls - (over * 6)

                                            }


                                            binding.tvSessionRunNeeded2.setText("" + runNeeded)
                                            binding.tvSessionBallRemain2.setText("" + ballremaining)

                                        }

                                    } else if (it.teamAScore.jsonMember2 != null) {

                                        if (!it.teamAScore.jsonMember2.score.isNullOrEmpty() && !it.teamAScore.jsonMember2.ball.isNullOrEmpty()) {

                                            var runNeeded =
                                                it.sMax.toInt() - it.teamAScore.jsonMember2.score.toInt()

                                            var ballremaining = 0

                                            if (it.teamAScore.jsonMember2.ball.contains(".")) {

                                                val overs =
                                                    it.teamAScore.jsonMember2.ball.split(".")
                                                        .toTypedArray()
                                                val over = overs[0].toInt()
                                                val ball = overs[1].toInt()

                                                ballremaining = totalBalls - ((over * 6) + ball)

                                            } else {
                                                val over = it.teamAScore.jsonMember2.ball.toInt()

                                                ballremaining = totalBalls - (over * 6)

                                            }

                                            binding.tvSessionRunNeeded2.setText("" + runNeeded)
                                            binding.tvSessionBallRemain2.setText("" + ballremaining)

                                        }

                                    }
                                }


                            } else if (it.battingTeam?.equals(it.teamBId)!!) {

                                if (it.teamBScore != null) {

                                    if (it.teamBScore.jsonMember1 != null) {

                                        if (!it.teamBScore.jsonMember1.score.isNullOrEmpty() && !it.teamBScore.jsonMember1.ball.isNullOrEmpty()) {

                                            var runNeeded =
                                                it.sMax.toInt() - it.teamBScore.jsonMember1.score.toInt()

                                            var ballremaining = 0

                                            if (it.teamBScore.jsonMember1.ball.contains(".")) {

                                                val overs =
                                                    it.teamBScore.jsonMember1.ball.split(".")
                                                        .toTypedArray()
                                                val over = overs[0].toInt()
                                                val ball = overs[1].toInt()

                                                ballremaining = totalBalls - ((over * 6) + ball)

                                            } else {
                                                val over = it.teamBScore.jsonMember1.ball.toInt()

                                                ballremaining = totalBalls - (over * 6)

                                            }

                                            binding.tvSessionRunNeeded2.setText("" + runNeeded)
                                            binding.tvSessionBallRemain2.setText("" + ballremaining)

                                        }

                                    } else if (it.teamBScore.jsonMember2 != null) {

                                        if (!it.teamBScore.jsonMember2.score.isNullOrEmpty() && !it.teamBScore.jsonMember2.ball.isNullOrEmpty()) {

                                            var runNeeded =
                                                it.sMax.toInt() - it.teamBScore.jsonMember2.score.toInt()

                                            var ballremaining = 0

                                            if (it.teamBScore.jsonMember2.ball.contains(".")) {

                                                val overs =
                                                    it.teamBScore.jsonMember2.ball.split(".")
                                                        .toTypedArray()
                                                val over = overs[0].toInt()
                                                val ball = overs[1].toInt()

                                                ballremaining = totalBalls - ((over * 6) + ball)

                                            } else {
                                                val over = it.teamBScore.jsonMember2.ball.toInt()

                                                ballremaining = totalBalls - (over * 6)

                                            }

                                            binding.tvSessionRunNeeded2.setText("" + runNeeded)
                                            binding.tvSessionBallRemain2.setText("" + ballremaining)

                                        }

                                    }
                                }
                            }
                        }

                    }

                    binding.layoutLivepageSession1.visibility = View.VISIBLE

                } else {
                    binding.layoutLivepageSession1.visibility = View.GONE
                }

                if (!it.lambiOvr.isNullOrEmpty() && !it.lambiOvr.equals("0")) {

                    binding.tvSessionLiveLambi.setText(it.lambiOvr.toString())
                    binding.tvSessionLive6Lambi.setText(it.lambiMax.toString())
                    binding.tvSessionLive4Lambi.setText(it.lambiMin.toString())

                    if (!it.lambiMax.isNullOrEmpty()) {

                        var totalBalls = 0

                        if (it.lambiOvr?.contains(".") == true) {

                            val overs = it.lambiOvr?.split(".")!!.toTypedArray()
                            val over = overs[0].toInt()
                            val ball = overs[1].toInt()

                            totalBalls = 6 * (over) + ball

                        } else {
                            try {
                                totalBalls = 6 * (it.lambiOvr?.toInt()!!)
                            } catch (e: Exception) {

                            }
                        }


                        if (it.battingTeam?.equals(it.teamAId)!!) {

                            if (it.teamAScore != null) {

                                if (it.teamAScore.jsonMember1 != null) {

                                    if (!it.teamAScore.jsonMember1.score.isNullOrEmpty() && !it.teamAScore.jsonMember1.ball.isNullOrEmpty()) {

                                        var runNeeded =
                                            it.lambiMax.toInt() - it.teamAScore.jsonMember1.score.toInt()

                                        var ballremaining = 0

                                        if (it.teamAScore.jsonMember1.ball.contains(".")) {

                                            val overs = it.teamAScore.jsonMember1.ball.split(".")
                                                .toTypedArray()
                                            val over = overs[0].toInt()
                                            val ball = overs[1].toInt()

                                            ballremaining = totalBalls - ((over * 6) + ball)

                                        } else {
                                            val over = it.teamAScore.jsonMember1.ball.toInt()

                                            ballremaining = totalBalls - (over * 6)

                                        }


                                        binding.tvSessionRunNeeded2Lambi.setText("" + runNeeded)
                                        binding.tvSessionBallRemain2Lambi.setText("" + ballremaining)

                                    }

                                } else if (it.teamAScore.jsonMember2 != null) {

                                    if (!it.teamAScore.jsonMember2.score.isNullOrEmpty() && !it.teamAScore.jsonMember2.ball.isNullOrEmpty()) {

                                        var runNeeded =
                                            it.lambiMax.toInt() - it.teamAScore.jsonMember2.score.toInt()

                                        var ballremaining = 0

                                        if (it.teamAScore.jsonMember2.ball.contains(".")) {

                                            val overs = it.teamAScore.jsonMember2.ball.split(".")
                                                .toTypedArray()
                                            val over = overs[0].toInt()
                                            val ball = overs[1].toInt()

                                            ballremaining = totalBalls - ((over * 6) + ball)

                                        } else {
                                            val over = it.teamAScore.jsonMember2.ball.toInt()

                                            ballremaining = totalBalls - (over * 6)

                                        }

                                        binding.tvSessionRunNeeded2Lambi.setText("" + runNeeded)
                                        binding.tvSessionBallRemain2Lambi.setText("" + ballremaining)

                                    }

                                }
                            }


                        } else if (it.battingTeam?.equals(it.teamBId)!!) {

                            if (it.teamBScore != null) {

                                if (it.teamBScore.jsonMember1 != null) {

                                    if (!it.teamBScore.jsonMember1.score.isNullOrEmpty() && !it.teamBScore.jsonMember1.ball.isNullOrEmpty()) {

                                        var runNeeded =
                                            it.lambiMax.toInt() - it.teamBScore.jsonMember1.score.toInt()

                                        var ballremaining = 0

                                        if (it.teamBScore.jsonMember1.ball.contains(".")) {

                                            val overs = it.teamBScore.jsonMember1.ball.split(".")
                                                .toTypedArray()
                                            val over = overs[0].toInt()
                                            val ball = overs[1].toInt()

                                            ballremaining = totalBalls - ((over * 6) + ball)

                                        } else {
                                            val over = it.teamBScore.jsonMember1.ball.toInt()

                                            ballremaining = totalBalls - (over * 6)

                                        }

                                        binding.tvSessionRunNeeded2Lambi.setText("" + runNeeded)
                                        binding.tvSessionBallRemain2Lambi.setText("" + ballremaining)

                                    }

                                } else if (it.teamBScore.jsonMember2 != null) {

                                    if (!it.teamBScore.jsonMember2.score.isNullOrEmpty() && !it.teamBScore.jsonMember2.ball.isNullOrEmpty()) {

                                        var runNeeded =
                                            it.lambiMax.toInt() - it.teamBScore.jsonMember2.score.toInt()

                                        var ballremaining = 0

                                        if (it.teamBScore.jsonMember2.ball.contains(".")) {

                                            val overs = it.teamBScore.jsonMember2.ball.split(".")
                                                .toTypedArray()
                                            val over = overs[0].toInt()
                                            val ball = overs[1].toInt()

                                            ballremaining = totalBalls - ((over * 6) + ball)

                                        } else {
                                            val over = it.teamBScore.jsonMember2.ball.toInt()

                                            ballremaining = totalBalls - (over * 6)

                                        }

                                        binding.tvSessionRunNeeded2Lambi.setText("" + runNeeded)
                                        binding.tvSessionBallRemain2Lambi.setText("" + ballremaining)

                                    }

                                }
                            }
                        }

                    }

                    binding.linearlayoutSessionLambi.visibility = View.VISIBLE

                } else {
                    binding.linearlayoutSessionLambi.visibility = View.GONE
                }

                if (it.matchType.equals("Test", true)) {
                    if (!it.trailLead.isNullOrEmpty()) {
                        binding.linerlayoutLeadTrial.visibility = View.VISIBLE
                        binding.tvLeadTest.setText(it.trailLead)
                    }
                    binding.linerlayoutOddsOdiShow.visibility = View.GONE
                    binding.layoutLivepageProjectedMain.visibility = View.GONE

                    Log.e(TAG, "observeLiveScore: test  dgssdgdsgdsgdsgd  " + it.matchType)

                } else {
                    Log.e(TAG, "observeLiveScore: notest  dgssdgdsgdsgdsgd  " + it.matchType)

                    try {
                        // get this trick from url - https://stackoverflow.com/questions/3732790/android-split-string/3732820
                        val tokens = StringTokenizer(it.minRate.toString(), ".")
                        val first = tokens.nextToken() // this will contain "Fruit"
                        val second = tokens.nextToken() // this will contain " they taste good"
                        val secondInt = second.length
                        if (secondInt == 1) {
                            binding.tvOddsOdi19.setText(second + "0")
                        } else {
                            //Toast.makeText(mContext, "not 1", Toast.LENGTH_SHORT).show();
                            binding.tvOddsOdi19.setText(second)
                        }
                    } catch (e: Exception) {
                        binding.tvOddsOdi19.setText(it.minRate.toString())
                    }

                    ///////////////////////////////////////////////////////////

                    //String TestMin_rate = String.valueOf(1.9);
                    try {
                        // get this trick from url - https://stackoverflow.com/questions/3732790/android-split-string/3732820
                        val tokensMax = StringTokenizer(it.maxRate.toString(), ".")
                        val first = tokensMax.nextToken() // this will contain "Fruit"
                        val secondMax =
                            tokensMax.nextToken() // this will contain " they taste good"
                        val secondInt = secondMax.length
                        if (secondInt == 1) {
                            binding.tvOddsOdi20.setText(secondMax + "0")
                        } else {
                            //Toast.makeText(mContext, "not 1", Toast.LENGTH_SHORT).show();
                            binding.tvOddsOdi20.setText(secondMax)
                        }
                    } catch (e: Exception) {
                        binding.tvOddsOdi20.setText(it.maxRate.toString())
                    }




                    binding.tvSessionLive.setText(it.sOvr.toString())
                    binding.tvSessionLive4.setText(it.sMin.toString())
                    binding.tvSessionLive6.setText(it.sMax.toString())

                    binding.tvOddsOdi2.setText(it.favTeam.toString())
                    binding.tvSessionLiveLambi.setText("$lambi_ovr ")
                    binding.tvSessionLive4Lambi.setText(lambi_min)
                    binding.tvSessionLive6Lambi.setText(lambi_max)

                    /*setRunRateScore(
                        it.sMax.toString(),
                        it.sOvr.toString(),
                        lambi_max,
                        lambi_ovr,
                        it.battingTeam.toString(),
                        it.ballingTeam.toString(),
                        it.teamAId.toString(),
                        it.teamBId.toString(),
                        it.teamAOver.toString(),
                        it.teamBOver.toString(),
                        it,
                        it.currRate.toString()
                    )*/
                    binding.linerlayoutOddsTestShow.setVisibility(View.GONE)
                    binding.linerlayoutLeadTrial.setVisibility(View.GONE)
                }

                if (it.currentInning.equals("2")) {

                    binding.tvOdiRunNeeded2.setText("Runs Needed " + it.runNeed + " in Balls " + it.ballRem)
                    //   binding.layoutLivepageProjectedMain.visibility = View.GONE
                } else {
                    binding.tvOdiRunNeeded2.setText("${it.toss}")
                    /*if (!it.matchType.equals("Test", true)) {
                        binding.layoutLivepageProjectedMain.visibility = View.VISIBLE
                    }*/
                }

                if (!it.projectedScore.isNullOrEmpty()) {
                    binding.layoutLivepageProjectedMain.visibility = View.VISIBLE

                    var model = it.projectedScore.get(0)

                    binding.textviewProjectedRateA1.setText("" + model?.curRate)
                    binding.textviewProjectedRateA2.setText("" + model?.curRate1)
                    binding.textviewProjectedRateA3.setText("" + model?.curRate2)
                    binding.textviewProjectedRateA4.setText("" + model?.curRate3)

                    binding.recyclerProjected.adapter = ProjectedScoreAdapter(
                        it.projectedScore as List<ProjectedScoreItem>,
                        activity
                    )

                } else {
                    binding.layoutLivepageProjectedMain.visibility = View.GONE
                }


                if (it.sHistory1.isNullOrEmpty() && it.sHistory2.isNullOrEmpty()) {
                    binding.inningsSessionLinearComp.visibility = View.GONE
                } else {
                    binding.inningsSessionLinearComp.visibility = View.VISIBLE

                    if (!it.sHistory1.isNullOrEmpty()) {
                        binding.inning1SessionCard.visibility = View.VISIBLE

                        var model = it.sHistory1.get(0)

                        if (model?.teamId == it.teamAId) {
                            Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                                .into(binding.inning1SessionImage)
                            binding.inning1SessionTeamName.setText(it.teamAShort + " - 1st Inning")
                        } else {
                            Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                                .into(binding.inning1SessionImage)
                            binding.inning1SessionTeamName.setText(it.teamBShort + " - 1st Inning")
                        }

                        binding.recyclerSessionInning1.adapter = MatchSessionLiveAdapter(
                            it.sHistory1 as ArrayList<SHistoryItem>,
                            activity
                        )

                    } else {
                        binding.inning1SessionCard.visibility = View.GONE
                    }

                    if (!it.sHistory2.isNullOrEmpty()) {
                        binding.inning2SessionCard.visibility = View.VISIBLE

                        var model = it.sHistory2.get(0)

                        if (model?.teamId == it.teamAId) {
                            Glide.with(activity).load(it.teamAImg).apply(requestOptions)
                                .into(binding.inning2SessionImage)
                            binding.inning2SessionTeamName.setText(it.teamAShort + " - 2nd Inning")
                        } else {
                            Glide.with(activity).load(it.teamBImg).apply(requestOptions)
                                .into(binding.inning2SessionImage)
                            binding.inning2SessionTeamName.setText(it.teamBShort + " - 2nd Inning")
                        }

                        binding.recyclerSessionInning2.adapter = MatchSessionLiveAdapter(
                            it.sHistory2 as ArrayList<SHistoryItem>,
                            activity
                        )

                    } else {
                        binding.inning2SessionCard.visibility = View.GONE
                    }

                }


                if (it.udrs != null) {

                    binding.linerlayoutDrs.visibility = View.VISIBLE


                    Glide.with(activity).load(it.teamAImg).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.drsImgA)
                    Glide.with(activity).load(it.teamBImg).placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.drsImgB)

                    binding.drsTeamA.setText("" + it.teamAShort)
                    binding.drsTeamB.setText("" + it.teamBShort)

                    binding.drsRemainTeamA.setText("${it.udrs.teamAUdrs?.left}")
                    binding.drsPassTeamA.setText("${it.udrs.teamAUdrs?.pass}")
                    binding.drsFailTeamA.setText("${it.udrs.teamAUdrs?.fail}")


                    binding.drsRemainTeamB.setText("${it.udrs.teamBUdrs?.left}")
                    binding.drsPassTeamB.setText("${it.udrs.teamBUdrs?.pass}")
                    binding.drsFailTeamB.setText("${it.udrs.teamBUdrs?.fail}")

                }


                binding.tvTeamAOdds.setText(it.teamAShort)
                binding.tvTeamAOdds1.setText(it.minRate)
                binding.tvTeamAOdds1T1.setText(it.maxRate)

                binding.tvTeamBOdds.setText(it.teamBShort)
                binding.tvTeamDrawOdds1.setText(it.minRate1)
                binding.tvTeamAOdds1T2.setText(it.maxRate1)

                binding.tvTeamBOdds1.setText(it.minRate2)
                binding.tvTeamAOdds1T3.setText(it.maxRate2)



                when (first_circle.toString()) {
                    "0" -> compValue = first_circle.toString()
                    "1" -> compValue = first_circle.toString()
                    "2" -> compValue = first_circle.toString()
                    "3" -> compValue = first_circle.toString()
                    "Six" -> {
                        first_circle = "6"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 35F
                        )
                    }
                    "Ball" -> compValue = first_circle.toString()
                    "Four" -> {
                        first_circle = "4"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 35F
                        )
                    }
                    "Over" -> compValue = first_circle.toString()
                    "Bowler Stop" -> compValue = first_circle.toString()
                    "Catch" -> {
                        first_circle = "Catch Out"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "Wicket" -> compValue = first_circle.toString()
                    "Stump (Test)" -> {
                        first_circle = "Stumps"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 21F
                        )
                    }
                    "3rd Umpire" -> compValue = first_circle.toString()
                    "NB" -> {
                        first_circle = "No Ball"
                        compValue = first_circle.toString()
                    }
                    "Bolwed" -> compValue = first_circle.toString()
                    "Free Hit" -> compValue = first_circle.toString()
                    "LBW" -> compValue = first_circle.toString()
                    "WB" -> {
                        first_circle = "Wide Ball"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "WB+1" -> compValue = first_circle.toString()
                    "LB" -> {
                        first_circle = "Leg Bye"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "LB+1" -> compValue = first_circle.toString()
                    "Not Out" -> {
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "Rain Start" -> {
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "Rain Stop" -> {
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "Stump" -> {
                        first_circle = "Stump Out"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    "Bowling Review" -> {
                        first_circle = "Bowling Review"
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                    else -> {
                        compValue = first_circle.toString()
                        binding.textviewMatchrunslive.setTextSize(
                            TypedValue.COMPLEX_UNIT_SP, 15F
                        )
                    }
                }

                if (!it.result.isNullOrEmpty()) {

                    binding.resultToss.visibility = View.VISIBLE
                    binding.resultToss.setTextColor(activity.resources.getColor(R.color.bluene))
                    binding.resultToss.setText("${it.result}")

                } else if (!it.trailLead.isNullOrEmpty()) {
                    binding.resultToss.visibility = View.VISIBLE
                    binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                    binding.resultToss.setText("${it.trailLead}")
                } else if (!it.toss.isNullOrEmpty()) {
                    if (it.currentInning.equals("1")) {
                        binding.resultToss.visibility = View.VISIBLE
                        binding.resultToss.setTextColor(activity.resources.getColor(R.color.red))
                        binding.resultToss.setText("${it.toss}")
                    }else{
                        binding.resultToss.visibility = View.GONE
                    }
                }

                if (!it.result.isNullOrEmpty()) {

                    binding.liveLinear.visibility = View.GONE
                    binding.finishedLinear.visibility = View.VISIBLE

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


                } else if ("Live" == matchStatus) {

                    binding.liveLinear.visibility = View.VISIBLE
                    binding.finishedLinear.visibility = View.GONE

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


                    voicrAudioSpeaker(first_circle.toString())

                }


                if (!uiSet) {
                    uiSet = true
                    /*if (!matchType.equals("Test", true)) {
                        viewModel.getMatchOdds(matchId)
                    }*/
                }


            }

        })
    }


    private fun voicrAudioSpeaker(data: String) {
        binding.imgVolumeCommentry.setOnClickListener {
            clickToVibrate()
            if (voiceActive == true) {
                voiceActive = false
                MainApplication.applicationInstance.setVoiceActive(false)
                Toast.makeText(activity, "Commentry Audio Off", Toast.LENGTH_SHORT).show()
                binding.imgVolumeCommentry.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_off_24))
            } else {
                voiceActive = true
                MainApplication.applicationInstance.setVoiceActive(true)
                Toast.makeText(activity, "Commentry Audio On", Toast.LENGTH_SHORT).show()
                binding.imgVolumeCommentry.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_up_24))
            }
        }
        if (voiceActive == true) {
            binding.imgVolumeCommentry.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_up_24))
        } else {
            binding.imgVolumeCommentry.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_off_24))
        }



        if (buttonHindiAct == true) {
            hindiActive = true
            engActive = false
            voiceCommentary(data, activity)
        } else if (buttonEngAct == true) {
            hindiActive = false
            engActive = true
            voiceCommentary(data, activity)
        } else {
            hindiActive = true
            engActive = false
            voiceCommentary(data, activity)
        }

    }


    public fun voiceCommentary(data: String?, context: Context?) {
        textToSpeech = TextToSpeech(
            context
        ) { status ->
            if (status == TextToSpeech.SUCCESS) {
                if (!TextUtils.isEmpty(compValue)) {
                    if (!binding.textviewMatchrunslive.getText().toString().equals(compValue)) {
                        if (hindiActive) {
                            audioHindiActive(data!!)
                        } else if (engActive) {
                            audioEngActive(data!!)
                        } else {
                            audioHindiActive(data!!)
                        }
                    }
                }
            }
        }
    }


    fun audioHindiActive(data: String) {
        val locale = Locale("hi")
        textToSpeech?.setLanguage(
            locale
        )
        textToSpeech?.setSpeechRate(
            1.4f
        )
        when (data) {
            "0" -> {
                textToSpeech?.speak(
                    "Khali BALL Khali", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "1" -> {
                textToSpeech?.speak(
                    "Single Aaya Single", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "2" -> {
                textToSpeech?.speak(
                    "Double Aaya Double", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "3" -> {
                textToSpeech?.speak(
                    "triple Aaya triple", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Six" -> {
                textToSpeech?.speak(
                    "Chcakka Aaya Cchakka", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Ball" -> {
                textToSpeech?.speak(
                    "Ball Chaaloo Ball", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21F)
            }
            "Four" -> {
                textToSpeech?.speak(
                    "Choukka Aaya Choukka", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Over" -> {
                textToSpeech?.speak(
                    "Over Complete", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21F)
            }
            "Bowler Stop" -> {
                textToSpeech?.speak(
                    "Bowler rukkaa Bowler", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            }
            "Catch Out" -> {
                textToSpeech?.speak(
                    "Catch out hua Catch out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                binding.textviewMatchrunslive.text = "Catch Out"
                first_circle = "Catch Out"
            }
            "Wicket" -> {
                textToSpeech?.speak(
                    "Wicket gya wicket", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            }
            "Stump (Test)" -> {
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
                binding.textviewMatchrunslive.text = "Stumps"
                first_circle = "Stumps"
            }
            "3rd Umpire" -> {
                textToSpeech?.speak(
                    "3rd Umpire Gya, 3rd Umpire", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "No Ball" -> {
                textToSpeech?.speak(
                    "No Ball gayi No ball", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                binding.textviewMatchrunslive.text = "No Ball"
                first_circle = "No Ball"
            }
            "Bolwed" -> {
                textToSpeech?.speak(
                    "Bowled out hua Bowled out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Free Hit" -> {
                textToSpeech?.speak(
                    "Free hit mila Free hit", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "LBW" -> {
                textToSpeech?.speak(
                    "L B W out hua L B W out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Wide Ball" -> {
                textToSpeech?.speak(
                    "wide ball gayee wide ball", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Wide Ball"
                first_circle = "Wide Ball"
            }
            "WB+1" -> {
                textToSpeech?.speak(
                    "wide ball ke sath single aaya", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Leg Bye" -> {
                textToSpeech?.speak(
                    "Leg byei gayee leg byei", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "LB+1" -> {
                textToSpeech?.speak(
                    "leg bayie ke sath single aaya", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Not Out" -> {
                textToSpeech?.speak(
                    "not out hua not out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Rain Start" -> {
                textToSpeech?.speak(
                    "barish shuru ho gayi barish rukne par match shuru hoga",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Rain Stop" -> {
                textToSpeech?.speak(
                    "barish ruk gayi match shuru hone wala hai", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Stump Out" -> {
                textToSpeech?.speak(
                    "stump out huaa stump out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Stump Out"
                first_circle = "Stump Out"
            }
            "Bowling Review" -> {
                textToSpeech?.speak(
                    "Bowling side se review ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Bowling Review"
                first_circle = "Bowling Review"
            }
            else -> {
                textToSpeech?.speak(
                    first_circle, TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
        }
        if (data == "4") {
            textToSpeech?.speak(
                "Chooukkaa Aaya Choukkaa", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.four_anim)
            binding.lottieanimationviewOut.playAnimation()
            //binding.lottieanimationviewOut.pauseAnimation();
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
        } else if (data == "6") {
            textToSpeech?.speak(
                "Cchakka Aaya Cchakka", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.six_anim)
            binding.lottieanimationviewOut.playAnimation()
            //binding.lottieanimationviewOut.pauseAnimation();
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
        } else if (data == "Wicket") {
            textToSpeech?.speak(
                "Wicket udaa wicket", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.out_anim)
            binding.lottieanimationviewOut.playAnimation()
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
        } else if (data == "Ball") {
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.ball1)
            binding.lottieanimationviewOut.playAnimation()
            binding.lottieanimationviewOut.loop(true)
            binding.textviewMatchrunslive.visibility = View.GONE
            textToSpeech?.speak(
                "Ball Chaloo ball", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
        } else {
            binding.lottieanimationviewOut.visibility = View.GONE
            binding.textviewMatchrunslive.visibility = View.VISIBLE
            //binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        }
        binding.textviewMatchrunslive.text = data
    }

    fun audioEngActive(data: String) {
        val locale = Locale("en")
        textToSpeech?.setLanguage(
            locale
        )
        textToSpeech?.setSpeechRate(
            1.3f
        )
        when (data) {
            "0" -> {
                textToSpeech?.speak(
                    "zero run zero", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "1" -> {
                textToSpeech?.speak(
                    "one run one", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "2" -> {
                textToSpeech?.speak(
                    "two run two", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "3" -> {
                textToSpeech?.speak(
                    "Three run three", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Six" -> {
                textToSpeech?.speak(
                    "six six six", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Ball" -> {
                textToSpeech?.speak(
                    "Bowler running", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21F)
            }
            "Four" -> {
                textToSpeech?.speak(
                    "four run boundry", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31F)
            }
            "Over" -> {
                textToSpeech?.speak(
                    "Over Complete", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21F)
            }
            "Bowler Stop" -> {
                textToSpeech?.speak(
                    "Bowler stop Bowler", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            }
            "Catch Out" -> {
                textToSpeech?.speak(
                    "Catch out ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                binding.textviewMatchrunslive.text = "Catch Out"
                first_circle = "Catch Out"
            }
            "Wicket" -> {
                textToSpeech?.speak(
                    "Wicket wicket wicket", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            }
            "Stump (Test)" -> {
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
                binding.textviewMatchrunslive.text = "Stumps"
                first_circle = "Stumps"
            }
            "3rd Umpire" -> {
                textToSpeech?.speak(
                    "3rd Umpire ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "No Ball" -> {
                textToSpeech?.speak(
                    "No Ball ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                binding.textviewMatchrunslive.text = "No Ball"
                first_circle = "No Ball"
            }
            "Bolwed" -> {
                textToSpeech?.speak(
                    "Bowled out", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Free Hit" -> {
                textToSpeech?.speak(
                    "Free hit ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "LBW" -> {
                textToSpeech?.speak(
                    "L B W ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Wide Ball" -> {
                textToSpeech?.speak(
                    "wide ball ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Wide Ball"
                first_circle = "Wide Ball"
            }
            "WB+1" -> {
                textToSpeech?.speak(
                    "wide ball with one run", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Leg Bye" -> {
                textToSpeech?.speak(
                    "Leg bye", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "LB+1" -> {
                textToSpeech?.speak(
                    "leg bye with one run", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            "Not Out" -> {
                textToSpeech?.speak(
                    "not out ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Rain Start" -> {
                textToSpeech?.speak(
                    "rain start and wait for stop", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Rain Stop" -> {
                textToSpeech?.speak(
                    "rain stop .. match start in sometime", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            }
            "Stump Out" -> {
                textToSpeech?.speak(
                    "stump out ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Stump Out"
                first_circle = "Stump Out"
            }
            "Bowling Review" -> {
                textToSpeech?.speak(
                    "Bowling side review ", TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                binding.textviewMatchrunslive.text = "Bowling Review"
                first_circle = "Bowling Review"
            }
            else -> {
                textToSpeech?.speak(
                    first_circle, TextToSpeech.QUEUE_FLUSH, null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
        }
        if (data == "4") {
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.four_anim)
            binding.lottieanimationviewOut.playAnimation()
            //binding.lottieanimationviewOut.pauseAnimation();
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
            textToSpeech?.speak(
                "four run four ", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
        } else if (data == "6") {
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.six_anim)
            binding.lottieanimationviewOut.playAnimation()
            //binding.lottieanimationviewOut.pauseAnimation();
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
            textToSpeech?.speak(
                "six run six", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
        } else if (data == "Wicket") {
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.out_anim)
            binding.lottieanimationviewOut.playAnimation()
            binding.lottieanimationviewOut.loop(false)
            binding.textviewMatchrunslive.visibility = View.GONE
            textToSpeech?.speak(
                "Wicket gone wicket", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
        } else if (data == "Ball") {
            binding.lottieanimationviewOut.visibility = View.VISIBLE
            binding.lottieanimationviewOut.setAnimation(R.raw.ball1)
            binding.lottieanimationviewOut.playAnimation()
            binding.lottieanimationviewOut.loop(true)
            binding.textviewMatchrunslive.visibility = View.GONE
            textToSpeech?.speak(
                "Baller Running", TextToSpeech.QUEUE_FLUSH, null
            )
            if (voiceActive == false) {
                textToSpeech?.stop()
            }
        } else {
            binding.lottieanimationviewOut.visibility = View.GONE
            binding.textviewMatchrunslive.visibility = View.VISIBLE
            //binding.textviewMatchrunslive.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        }
        binding.textviewMatchrunslive.text = data
    }


    private fun clickToVibrate() {
        val vibe = activity.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
        //replace yourActivity.this with your own activity or if you declared a context you can write context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80) //80 represents the milliseconds (the duration of the vibration)
    }

    private fun setRunRateScore(
        s_max: String,
        s_ovr: String,
        lambi_max: String,
        lambi_ovr: String,
        battingTeamId: String,
        ballingTeamId: String,
        team_a_id: String,
        team_b_id: String,
        team_a_over: String,
        team_b_over: String,
        jsonObject1: LiveResponse,
        curr_rate: String
    ) {


        /////// projected score for innings //////////////////////////////////
        try {
            binding.textviewProjectedScoreA1Over.setText("$s_ovr ovr")
            binding.textviewProjectedScorB1Over.setText("$lambi_ovr ovr")
            binding.textviewProjectedRateA1.setText("$curr_rate*")
            val curr_rate_double = java.lang.Double.valueOf(curr_rate)
            val Pcurr_rateInt11 = String.format("%.2f", curr_rate_double)
            val PconvertRate11 = Pcurr_rateInt11.split(".").toTypedArray()
            val crrDecimal_a_rate = PconvertRate11[0].toInt()
            val crrDecimal_b_rate = PconvertRate11[1].toInt()
            val crrDecimal_a_rate_1 = crrDecimal_a_rate + 1
            val crrDecimal_a_rate_2 = crrDecimal_a_rate + 2
            val crrDecimal_a_rate_3 = crrDecimal_a_rate + 3
            val crrDecimal_a_rate_4 = crrDecimal_a_rate + 4
            val crrDecimal_a_ratetr1 = crrDecimal_a_rate_1.toString()
            val crrDecimal_a_ratetr2 = crrDecimal_a_rate_2.toString()
            val crrDecimal_a_ratetr3 = crrDecimal_a_rate_3.toString()
            val crrDecimal_a_ratetr4 = crrDecimal_a_rate_4.toString()
            binding.textviewProjectedRateA2.setText(crrDecimal_a_ratetr1)
            binding.textviewProjectedRateA3.setText(crrDecimal_a_ratetr2)
            binding.textviewProjectedRateA4.setText(crrDecimal_a_ratetr3)
            binding.textviewProjectedRateA5.setText(crrDecimal_a_ratetr4)





            if (jsonObject1.battingTeam?.toString().equals(team_a_id)!!) {

                if (jsonObject1.teamAScore != null) {
                    var teamScore = jsonObject1.teamAScore

                    if (teamScore?.jsonMember1 != null) {
                        var over = teamScore?.jsonMember1?.ball
                        var score = teamScore?.jsonMember1?.score

                        if (over?.contains(".")!!) {
                            var overArr = over.split(".")

                            var overCom = overArr[0]
                            var overBall = overArr[1]

                            if (!overBall.equals("0")) {

                                var matchOver1 = 0

                                if (s_ovr.contains(".")) {

                                    matchOver1 = s_ovr.toDouble().toInt()

                                } else {
                                    matchOver1 = s_ovr.toInt()
                                }


                                var remainingOver = matchOver1 - (overCom.toInt() + 1)

                                var predictRunCurrent =
                                    (remainingOver * curr_rate_double) + ((overBall.toInt() / 6) * curr_rate_double)

                                var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()


                                var predictRunCurrent1 =
                                    ((remainingOver * crrDecimal_a_rate_1).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_1).toDouble()

                                var currentscore1 =
                                    score?.toInt()!! + predictRunCurrent1.roundToInt()

                                var predictRunCurrent2 =
                                    ((remainingOver * crrDecimal_a_rate_2).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_2).toDouble()

                                var currentscore2 =
                                    score?.toInt()!! + predictRunCurrent2.roundToInt()

                                var predictRunCurrent3 =
                                    ((remainingOver * crrDecimal_a_rate_3).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_3).toDouble()

                                var currentscore3 =
                                    score?.toInt()!! + predictRunCurrent3.roundToInt()

                                var predictRunCurrent4 =
                                    ((remainingOver * crrDecimal_a_rate_4).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_4).toDouble()

                                var currentscore4 =
                                    score?.toInt()!! + predictRunCurrent4.roundToInt()


                                if (s_max.isNullOrEmpty() || s_ovr.equals("0")) {
                                    //layout_livepage_projected_main.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreA1.setText("-")
                                    binding.textviewProjectedScoreA2.setText("-")
                                    binding.textviewProjectedScoreA3.setText("-")
                                    binding.textviewProjectedScoreA4.setText("-")
                                    binding.textviewProjectedScoreA5.setText("-")
                                } else {
                                    //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                    binding.textviewProjectedScoreA1.setText("" + currentscore)
                                    binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                    binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                    binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                    binding.textviewProjectedScoreA5.setText("" + currentscore4)
                                }

                                if (lambi_max == "" || lambi_ovr.equals("0")) {
                                    //relative_livepage_projected3.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreB1.setText("-")
                                    binding.textviewProjectedScoreB2.setText("-")
                                    binding.textviewProjectedScoreB3.setText("-")
                                    binding.textviewProjectedScoreB4.setText("-")
                                    binding.textviewProjectedScoreB5.setText("-")
                                } else {
                                    //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                    var matchOver1lambi = 0

                                    if (lambi_ovr.contains(".")) {

                                        matchOver1lambi = lambi_ovr.toDouble().toInt()

                                    } else {
                                        matchOver1lambi = lambi_ovr.toInt()
                                    }


                                    var remainingOverlambi = matchOver1lambi - (overCom.toInt() + 1)

                                    var predictRunCurrentlambi =
                                        (remainingOverlambi * curr_rate_double) + ((overBall.toInt() / 6) * curr_rate_double)

                                    var currentscorelambi =
                                        score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                    var predictRunCurrent1lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_1).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_1).toDouble()

                                    var currentscore1lambi =
                                        score?.toInt()!! + predictRunCurrent1lambi.roundToInt()

                                    var predictRunCurrent2lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_2).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_2).toDouble()

                                    var currentscore2lambi =
                                        score?.toInt()!! + predictRunCurrent2lambi.roundToInt()

                                    var predictRunCurrent3lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_3).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_3).toDouble()

                                    var currentscore3lambi =
                                        score?.toInt()!! + predictRunCurrent3lambi.roundToInt()

                                    var predictRunCurrent4lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_4).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_4).toDouble()

                                    var currentscore4lambi =
                                        score?.toInt()!! + predictRunCurrent4lambi.roundToInt()

                                    binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                    binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                    binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                    binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                    binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                                }
                            } else {

                                var matchOver1 = 0

                                if (s_ovr.contains(".")) {

                                    matchOver1 = s_ovr.toDouble().toInt()

                                } else {
                                    matchOver1 = s_ovr.toInt()
                                }

                                var remainingOver = matchOver1 - overCom.toInt()

                                var predictRunCurrent = (remainingOver * curr_rate_double)

                                var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()

                                var predictRunCurrent1 = (remainingOver * crrDecimal_a_rate_1)

                                var currentscore1 = score?.toInt()!! + predictRunCurrent1

                                var predictRunCurrent2 = (remainingOver * crrDecimal_a_rate_2)

                                var currentscore2 = score?.toInt()!! + predictRunCurrent2

                                var predictRunCurrent3 = (remainingOver * crrDecimal_a_rate_3)

                                var currentscore3 = score?.toInt()!! + predictRunCurrent3

                                var predictRunCurrent4 = (remainingOver * crrDecimal_a_rate_4)

                                var currentscore4 = score?.toInt()!! + predictRunCurrent4


                                if (s_max == "" || s_ovr.equals("0")) {
                                    //layout_livepage_projected_main.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreA1.setText("-")
                                    binding.textviewProjectedScoreA2.setText("-")
                                    binding.textviewProjectedScoreA3.setText("-")
                                    binding.textviewProjectedScoreA4.setText("-")
                                    binding.textviewProjectedScoreA5.setText("-")
                                } else {
                                    //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                    binding.textviewProjectedScoreA1.setText("" + currentscore)
                                    binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                    binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                    binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                    binding.textviewProjectedScoreA5.setText("" + currentscore4)
                                }


                                if (lambi_max == "" || lambi_ovr.equals("0")) {
                                    //relative_livepage_projected3.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreB1.setText("-")
                                    binding.textviewProjectedScoreB2.setText("-")
                                    binding.textviewProjectedScoreB3.setText("-")
                                    binding.textviewProjectedScoreB4.setText("-")
                                    binding.textviewProjectedScoreB5.setText("-")
                                } else {
                                    //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                    var matchOver1lambi = 0

                                    if (lambi_ovr.contains(".")) {

                                        matchOver1lambi = lambi_ovr.toDouble().toInt()

                                    } else {
                                        matchOver1lambi = lambi_ovr.toInt()
                                    }

                                    var remainingOverlambi = matchOver1lambi - overCom.toInt()

                                    var predictRunCurrentlambi =
                                        (remainingOverlambi * curr_rate_double)

                                    var currentscorelambi =
                                        score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                    var predictRunCurrent1lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_1)

                                    var currentscore1lambi =
                                        score?.toInt()!! + predictRunCurrent1lambi

                                    var predictRunCurrent2lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_2)

                                    var currentscore2lambi =
                                        score?.toInt()!! + predictRunCurrent2lambi

                                    var predictRunCurrent3lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_3)

                                    var currentscore3lambi =
                                        score?.toInt()!! + predictRunCurrent3lambi

                                    var predictRunCurrent4lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_4)

                                    var currentscore4lambi =
                                        score?.toInt()!! + predictRunCurrent4lambi

                                    binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                    binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                    binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                    binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                    binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                                }

                            }
                        } else {

                            var matchOver1 = 0

                            if (s_ovr.contains(".")) {

                                matchOver1 = s_ovr.toDouble().toInt()

                            } else {
                                matchOver1 = s_ovr.toInt()
                            }

                            var remainingOver = matchOver1 - over.toInt()

                            var predictRunCurrent = (remainingOver * curr_rate_double)

                            var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()

                            var predictRunCurrent1 = (remainingOver * crrDecimal_a_rate_1)

                            var currentscore1 = score?.toInt()!! + predictRunCurrent1

                            var predictRunCurrent2 = (remainingOver * crrDecimal_a_rate_2)

                            var currentscore2 = score?.toInt()!! + predictRunCurrent2

                            var predictRunCurrent3 = (remainingOver * crrDecimal_a_rate_3)

                            var currentscore3 = score?.toInt()!! + predictRunCurrent3

                            var predictRunCurrent4 = (remainingOver * crrDecimal_a_rate_4)

                            var currentscore4 = score?.toInt()!! + predictRunCurrent4

                            if (s_max == "" || s_ovr.equals("0")) {
                                //layout_livepage_projected_main.setVisibility(View.GONE);
                                binding.textviewProjectedScoreA1.setText("-")
                                binding.textviewProjectedScoreA2.setText("-")
                                binding.textviewProjectedScoreA3.setText("-")
                                binding.textviewProjectedScoreA4.setText("-")
                                binding.textviewProjectedScoreA5.setText("-")
                            } else {
                                //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                binding.textviewProjectedScoreA1.setText("" + currentscore)
                                binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                binding.textviewProjectedScoreA5.setText("" + currentscore4)
                            }


                            if (lambi_max == "" || lambi_ovr.equals("0")) {
                                //relative_livepage_projected3.setVisibility(View.GONE);
                                binding.textviewProjectedScoreB1.setText("-")
                                binding.textviewProjectedScoreB2.setText("-")
                                binding.textviewProjectedScoreB3.setText("-")
                                binding.textviewProjectedScoreB4.setText("-")
                                binding.textviewProjectedScoreB5.setText("-")
                            } else {
                                //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                var matchOver1lambi = 0

                                if (lambi_ovr.contains(".")) {

                                    matchOver1lambi = lambi_ovr.toDouble().toInt()

                                } else {
                                    matchOver1lambi = lambi_ovr.toInt()
                                }

                                var remainingOverlambi = matchOver1lambi - over.toInt()

                                var predictRunCurrentlambi = (remainingOverlambi * curr_rate_double)

                                var currentscorelambi =
                                    score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                var predictRunCurrent1lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_1)

                                var currentscore1lambi = score?.toInt()!! + predictRunCurrent1lambi

                                var predictRunCurrent2lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_2)

                                var currentscore2lambi = score?.toInt()!! + predictRunCurrent2lambi

                                var predictRunCurrent3lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_3)

                                var currentscore3lambi = score?.toInt()!! + predictRunCurrent3lambi

                                var predictRunCurrent4lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_4)

                                var currentscore4lambi = score?.toInt()!! + predictRunCurrent4lambi

                                binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                            }

                        }
                    }
                }
            } else if (jsonObject1.battingTeam?.toString().equals(team_b_id)!!) {

                if (jsonObject1.teamBScore != null) {
                    var teamScore = jsonObject1.teamBScore

                    if (teamScore?.jsonMember1 != null) {
                        var over = teamScore?.jsonMember1?.ball
                        var score = teamScore?.jsonMember1?.score

                        Log.e("TAGrunrate", "testingCls: $over $score ")

                        if (over?.contains(".")!!) {
                            var overArr = over.split(".")

                            var overCom = overArr[0]
                            var overBall = overArr[1]

                            if (!overBall.equals("0")) {

                                var matchOver1 = 0

                                if (s_ovr.contains(".")) {

                                    matchOver1 = s_ovr.toDouble().toInt()

                                } else {
                                    matchOver1 = s_ovr.toInt()
                                }

                                var remainingOver = matchOver1 - (overCom.toInt() + 1)

                                var predictRunCurrent =
                                    (remainingOver * curr_rate_double) + ((overBall.toInt() / 6) * curr_rate_double)

                                var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()


                                var predictRunCurrent1 =
                                    ((remainingOver * crrDecimal_a_rate_1).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_1).toDouble()

                                var currentscore1 =
                                    score?.toInt()!! + predictRunCurrent1.roundToInt()

                                var predictRunCurrent2 =
                                    ((remainingOver * crrDecimal_a_rate_2).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_2).toDouble()

                                var currentscore2 =
                                    score?.toInt()!! + predictRunCurrent2.roundToInt()

                                var predictRunCurrent3 =
                                    ((remainingOver * crrDecimal_a_rate_3).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_3).toDouble()

                                var currentscore3 =
                                    score?.toInt()!! + predictRunCurrent3.roundToInt()

                                var predictRunCurrent4 =
                                    ((remainingOver * crrDecimal_a_rate_4).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_4).toDouble()

                                var currentscore4 =
                                    score?.toInt()!! + predictRunCurrent4.roundToInt()



                                if (s_max == "" || s_ovr.equals("0")) {
                                    //layout_livepage_projected_main.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreA1.setText("-")
                                    binding.textviewProjectedScoreA2.setText("-")
                                    binding.textviewProjectedScoreA3.setText("-")
                                    binding.textviewProjectedScoreA4.setText("-")
                                    binding.textviewProjectedScoreA5.setText("-")
                                } else {
                                    //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                    binding.textviewProjectedScoreA1.setText("" + currentscore)
                                    binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                    binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                    binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                    binding.textviewProjectedScoreA5.setText("" + currentscore4)
                                }

                                if (lambi_max == "" || lambi_ovr.equals("0")) {
                                    //relative_livepage_projected3.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreB1.setText("-")
                                    binding.textviewProjectedScoreB2.setText("-")
                                    binding.textviewProjectedScoreB3.setText("-")
                                    binding.textviewProjectedScoreB4.setText("-")
                                    binding.textviewProjectedScoreB5.setText("-")
                                } else {
                                    //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                    var matchOver1lambi = 0

                                    if (lambi_ovr.contains(".")) {

                                        matchOver1lambi = lambi_ovr.toDouble().toInt()

                                    } else {
                                        matchOver1lambi = lambi_ovr.toInt()
                                    }

                                    var remainingOverlambi = matchOver1lambi - (overCom.toInt() + 1)

                                    var predictRunCurrentlambi =
                                        (remainingOverlambi * curr_rate_double) + ((overBall.toInt() / 6) * curr_rate_double)

                                    var currentscorelambi =
                                        score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                    var predictRunCurrent1lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_1).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_1).toDouble()

                                    var currentscore1lambi =
                                        score?.toInt()!! + predictRunCurrent1lambi.roundToInt()

                                    var predictRunCurrent2lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_2).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_2).toDouble()

                                    var currentscore2lambi =
                                        score?.toInt()!! + predictRunCurrent2lambi.roundToInt()

                                    var predictRunCurrent3lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_3).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_3).toDouble()

                                    var currentscore3lambi =
                                        score?.toInt()!! + predictRunCurrent3lambi.roundToInt()

                                    var predictRunCurrent4lambi =
                                        ((remainingOverlambi * crrDecimal_a_rate_4).toDouble() + (overBall.toInt() / 6) * crrDecimal_a_rate_4).toDouble()

                                    var currentscore4lambi =
                                        score?.toInt()!! + predictRunCurrent4lambi.roundToInt()

                                    binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                    binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                    binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                    binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                    binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                                }
                            } else {

                                var matchOver1 = 0

                                if (s_ovr.contains(".")) {

                                    matchOver1 = s_ovr.toDouble().toInt()

                                } else {
                                    matchOver1 = s_ovr.toInt()
                                }

                                var remainingOver = matchOver1 - overCom.toInt()

                                var predictRunCurrent = (remainingOver * curr_rate_double)

                                var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()

                                var predictRunCurrent1 = (remainingOver * crrDecimal_a_rate_1)

                                var currentscore1 = score?.toInt()!! + predictRunCurrent1

                                var predictRunCurrent2 = (remainingOver * crrDecimal_a_rate_2)

                                var currentscore2 = score?.toInt()!! + predictRunCurrent2

                                var predictRunCurrent3 = (remainingOver * crrDecimal_a_rate_3)

                                var currentscore3 = score?.toInt()!! + predictRunCurrent3

                                var predictRunCurrent4 = (remainingOver * crrDecimal_a_rate_4)

                                var currentscore4 = score?.toInt()!! + predictRunCurrent4



                                if (s_max == "" || s_ovr.equals("0")) {
                                    //layout_livepage_projected_main.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreA1.setText("-")
                                    binding.textviewProjectedScoreA2.setText("-")
                                    binding.textviewProjectedScoreA3.setText("-")
                                    binding.textviewProjectedScoreA4.setText("-")
                                    binding.textviewProjectedScoreA5.setText("-")
                                } else {
                                    //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                    binding.textviewProjectedScoreA1.setText("" + currentscore)
                                    binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                    binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                    binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                    binding.textviewProjectedScoreA5.setText("" + currentscore4)
                                }


                                if (lambi_max == "" || lambi_ovr.equals("0")) {
                                    //relative_livepage_projected3.setVisibility(View.GONE);
                                    binding.textviewProjectedScoreB1.setText("-")
                                    binding.textviewProjectedScoreB2.setText("-")
                                    binding.textviewProjectedScoreB3.setText("-")
                                    binding.textviewProjectedScoreB4.setText("-")
                                    binding.textviewProjectedScoreB5.setText("-")
                                } else {
                                    //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                    var matchOver1lambi = 0

                                    if (lambi_ovr.contains(".")) {

                                        matchOver1lambi = lambi_ovr.toDouble().toInt()

                                    } else {
                                        matchOver1lambi = lambi_ovr.toInt()
                                    }

                                    var remainingOverlambi = matchOver1lambi - overCom.toInt()

                                    var predictRunCurrentlambi =
                                        (remainingOverlambi * curr_rate_double)

                                    var currentscorelambi =
                                        score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                    var predictRunCurrent1lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_1)

                                    var currentscore1lambi =
                                        score?.toInt()!! + predictRunCurrent1lambi

                                    var predictRunCurrent2lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_2)

                                    var currentscore2lambi =
                                        score?.toInt()!! + predictRunCurrent2lambi

                                    var predictRunCurrent3lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_3)

                                    var currentscore3lambi =
                                        score?.toInt()!! + predictRunCurrent3lambi

                                    var predictRunCurrent4lambi =
                                        (remainingOverlambi * crrDecimal_a_rate_4)

                                    var currentscore4lambi =
                                        score?.toInt()!! + predictRunCurrent4lambi

                                    binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                    binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                    binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                    binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                    binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                                }

                            }
                        } else {

                            var matchOver1 = 0

                            if (s_ovr.contains(".")) {

                                matchOver1 = s_ovr.toDouble().toInt()

                            } else {
                                matchOver1 = s_ovr.toInt()
                            }

                            var remainingOver = matchOver1 - over.toInt()

                            var predictRunCurrent = (remainingOver * curr_rate_double)

                            var currentscore = score?.toInt()!! + predictRunCurrent.roundToInt()

                            var predictRunCurrent1 = (remainingOver * crrDecimal_a_rate_1)

                            var currentscore1 = score?.toInt()!! + predictRunCurrent1

                            var predictRunCurrent2 = (remainingOver * crrDecimal_a_rate_2)

                            var currentscore2 = score?.toInt()!! + predictRunCurrent2

                            var predictRunCurrent3 = (remainingOver * crrDecimal_a_rate_3)

                            var currentscore3 = score?.toInt()!! + predictRunCurrent3

                            var predictRunCurrent4 = (remainingOver * crrDecimal_a_rate_4)

                            var currentscore4 = score?.toInt()!! + predictRunCurrent4


                            if (s_max == "" || s_ovr.equals("0")) {
                                //layout_livepage_projected_main.setVisibility(View.GONE);
                                binding.textviewProjectedScoreA1.setText("-")
                                binding.textviewProjectedScoreA2.setText("-")
                                binding.textviewProjectedScoreA3.setText("-")
                                binding.textviewProjectedScoreA4.setText("-")
                                binding.textviewProjectedScoreA5.setText("-")
                            } else {
                                //layout_livepage_projected_main.setVisibility(View.VISIBLE);
                                binding.textviewProjectedScoreA1.setText("" + currentscore)
                                binding.textviewProjectedScoreA2.setText("" + currentscore1)
                                binding.textviewProjectedScoreA3.setText("" + currentscore2)
                                binding.textviewProjectedScoreA4.setText("" + currentscore3)
                                binding.textviewProjectedScoreA5.setText("" + currentscore4)
                            }


                            if (lambi_max == "" || lambi_ovr.equals("0")) {
                                //relative_livepage_projected3.setVisibility(View.GONE);
                                binding.textviewProjectedScoreB1.setText("-")
                                binding.textviewProjectedScoreB2.setText("-")
                                binding.textviewProjectedScoreB3.setText("-")
                                binding.textviewProjectedScoreB4.setText("-")
                                binding.textviewProjectedScoreB5.setText("-")
                            } else {
                                //relative_livepage_projected3.setVisibility(View.VISIBLE);


                                var matchOver1lambi = 0

                                if (lambi_ovr.contains(".")) {

                                    matchOver1lambi = lambi_ovr.toDouble().toInt()

                                } else {
                                    matchOver1lambi = lambi_ovr.toInt()
                                }

                                var remainingOverlambi = matchOver1lambi - over.toInt()

                                var predictRunCurrentlambi = (remainingOverlambi * curr_rate_double)

                                var currentscorelambi =
                                    score?.toInt()!! + predictRunCurrentlambi.roundToInt()


                                var predictRunCurrent1lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_1)

                                var currentscore1lambi = score?.toInt()!! + predictRunCurrent1lambi

                                var predictRunCurrent2lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_2)

                                var currentscore2lambi = score?.toInt()!! + predictRunCurrent2lambi

                                var predictRunCurrent3lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_3)

                                var currentscore3lambi = score?.toInt()!! + predictRunCurrent3lambi

                                var predictRunCurrent4lambi =
                                    (remainingOverlambi * crrDecimal_a_rate_4)

                                var currentscore4lambi = score?.toInt()!! + predictRunCurrent4lambi

                                binding.textviewProjectedScoreB1.setText("" + currentscorelambi)
                                binding.textviewProjectedScoreB2.setText("" + currentscore1lambi)
                                binding.textviewProjectedScoreB3.setText("" + currentscore2lambi)
                                binding.textviewProjectedScoreB4.setText("" + currentscore3lambi)
                                binding.textviewProjectedScoreB5.setText("" + currentscore4lambi)
                            }

                        }
                    }
                }
            }


        } catch (e: Exception) {
            Log.e("TAGrunrate", "testingCls:exception 111 " + e.message)
        }
    }

    fun layoutRemoveUpcomingIf(finalmatchStatus: String, matchDate: String?) {
        if (finalmatchStatus == "Upcoming") {
            binding.progressLayout.progressBar.visibility = View.GONE
            binding.linearlayoutLivescorepageMain.setVisibility(View.GONE)
            binding.linearlayoutIfLiveNotShowImage.setVisibility(View.VISIBLE)
            binding.tvLivescorepageUpcomingdateshow.setText(matchDate)
        } else if (finalmatchStatus == "Finished") {
            binding.linerlayoutOddsOdiShow.setVisibility(View.GONE)
            //binding.progressLivescoreFragmentMain.setVisibility(View.GONE)

            binding.linearlayoutSessionLambi.setVisibility(View.GONE)
            //linearLayoutOdiResults.setVisibility(View.VISIBLE);
            binding.linerlayoutLeadTrial.setVisibility(View.GONE)
            binding.linerlayoutOddsTestShow.setVisibility(View.GONE)
        } else {

        }
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