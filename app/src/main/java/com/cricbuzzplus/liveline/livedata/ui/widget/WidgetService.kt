package com.cricbuzzplus.liveline.livedata.ui.widget

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PixelFormat
import android.os.*
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.LiveResponse
import com.cricbuzzplus.liveline.livedata.ui.activity.LiveHomeActivity
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitMain
import io.reactivex.schedulers.Schedulers
import java.util.*


class WidgetService : Service() {


    var windowManager: WindowManager? = null
    var chatHead: ImageView? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var layoutInflater: LayoutInflater? = null
    private var mView: View? = null
    lateinit var intent: Intent
    lateinit var apiClient: ApiInterface

    lateinit var liveHomeActivity: LiveHomeActivity

    var voiceActive = false

    var timer: Timer? = null

    var matchId = ""

    var compValue = ""
    var compValue1 = ""
    var currentRunText = ""

    var hindiActive = false
    var engActive = true
    var banglaActive = false

    private var popupFirstCircle1: LinearLayout? = null

    var textToSpeech: TextToSpeech? = null

    var countDownTimer: CountDownTimer? = null

    var liveScoreData = MutableLiveData<LiveResponse>()


    override fun onBind(intent: Intent?): IBinder? {
        this.intent = intent!!
        //  Log.e("TAG3423", "onBind: ")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.intent = intent!!
        //Log.e("TAG3423", "onStartCommand: "+intent.getStringExtra("match_id"))

        matchId = intent.getStringExtra("match_id").toString()

        startTimer()
        //getActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        return super.onStartCommand(intent, flags, startId)
    }

    fun startTimer() {

        if (timer == null) {
            timer = Timer()
        }
        var count = 0

        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                //Log.e("startTimer", "run: ", )
                /*count += 2

                if (count >= 1800 ){
                    if(mView != null){

                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            timer?.cancel()
                            timer?.purge()
                            timer = null
                        }
                    }
                }else {

                }*/

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    getScore()
                }

            }
        }, 2000, 2000)

    }

    fun close() {
        try {
            // remove the view from the window
            (getSystemService(WINDOW_SERVICE) as WindowManager).removeView(mView)
            // invalidate the view
            mView?.invalidate()
            // remove all views
            if (mView != null) {
                windowManager!!.removeView(mView)

            }
            //(mView?.getParent() as ViewGroup).removeAllViews()

            if (intent != null) {

                if (timer != null) {
                    timer?.cancel()
                    timer?.purge()
                    timer = null
                }
                if (textToSpeech != null) {
                    textToSpeech?.stop()
                    textToSpeech = null
                }

                if (countDownTimer != null) {
                    countDownTimer?.cancel()
                    countDownTimer = null
                }

                stopService(intent)
            }

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: Exception) {
            Log.d("Error2", e.toString())
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Log.e("TAG3423", "onCreate: ")
        apiClient = retrofitMain.create<ApiInterface>(ApiInterface::class.java)
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        chatHead = ImageView(this)

        liveHomeActivity = LiveHomeActivity()
        // chatHead!!.setImageResource(R.drawable.)


        // set the layout parameters of the window
        mParams = WindowManager.LayoutParams( // Shrink the window to wrap the content rather
            // than filling the screen
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,  // Display it on top of other application windows
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Don't let it grab the input focus
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  // Make the underlying application window visible
            // through any transparent parts
            PixelFormat.TRANSLUCENT
        )

        layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // inflating the view with the custom layout we created
        mView = layoutInflater?.inflate(R.layout.popup_window, null)
        // set onClickListener on the remove button, which removes
        // the view from the window
        popupFirstCircle1 = mView?.findViewById<LinearLayout>(R.id.popup_firstcircle)



        mView?.findViewById<ImageView>(R.id.window_close)
            ?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    // onDestroy()
                    close()
                    clickToVibrate()
                }
            })


        // Define the position of the
        // window within the screen
        mParams!!.gravity = Gravity.CENTER

        windowManager!!.addView(mView, mParams)
        try {
            mView!!.setOnTouchListener(object : OnTouchListener {
                private val paramsF = mParams
                private var initialX = 0
                private var initialY = 0
                private var initialTouchX = 0f
                private var initialTouchY = 0f
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {

                            // Get current time in nano seconds.
                            initialX = paramsF?.x!!
                            initialY = paramsF!!.y
                            initialTouchX = event.rawX
                            initialTouchY = event.rawY
                        }
                        MotionEvent.ACTION_UP -> {}
                        MotionEvent.ACTION_MOVE -> {
                            paramsF?.x = initialX + (event.rawX - initialTouchX).toInt()
                            paramsF?.y = initialY + (event.rawY - initialTouchY).toInt()
                            windowManager!!.updateViewLayout(mView, paramsF)
                        }
                    }
                    return false
                }
            })
        } catch (e: Exception) {
            // TODO: handle exception
        }


    }


    @SuppressLint("NewApi")
    fun getScore() {

        try {

            if (!matchId.isNullOrEmpty()) {

                val hashMap = HashMap<String, String>()
                hashMap.put("match_id", matchId.toString())
                var imageVolume = mView?.findViewById<ImageView>(R.id.img_volume)
                var teamImage = mView?.findViewById<ImageView>(R.id.teamImg)
                var teams = mView?.findViewById<TextView>(R.id.teams)
                var teamsRun = mView?.findViewById<TextView>(R.id.teamRun)
                var teamOver = mView?.findViewById<TextView>(R.id.teamOver)
                var teamName = mView?.findViewById<TextView>(R.id.teamName)
                var teamCurrentRun = mView?.findViewById<TextView>(R.id.teamCurrentRun)
                var teamCRR = mView?.findViewById<TextView>(R.id.teamRunRate)
                var teamTossOrRequire = mView?.findViewById<TextView>(R.id.teamTossOrRequire)
                if (teamTossOrRequire != null) {
                    teamTossOrRequire.isSelected = true
                }
                var testRuns = mView?.findViewById<TextView>(R.id.testRuns)
                var teamNameSession = mView?.findViewById<TextView>(R.id.teamNameSession)
                var sessionMin = mView?.findViewById<TextView>(R.id.sessionMin)
                var sessionMax = mView?.findViewById<TextView>(R.id.sessionMax)
                var sessionOver = mView?.findViewById<TextView>(R.id.sessionOver)
                var sessionSMax = mView?.findViewById<TextView>(R.id.sessionSMax)
                var sessionSMin = mView?.findViewById<TextView>(R.id.sessionSMin)
                var linearSessionTeam = mView?.findViewById<LinearLayout>(R.id.linearSessionTeam)
                var linearSessionOver = mView?.findViewById<LinearLayout>(R.id.linearSessionOver)
                var parentWidget = mView?.findViewById<RelativeLayout>(R.id.parentWidget)
                var sessionLinear = mView?.findViewById<LinearLayout>(R.id.sessionLinear)


                if (apiClient != null) {

                    apiClient.getLiveScore(mPrefs.prefApiToken.toString(),matchId.toInt())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->


                            if (result?.status == true) {

                                Handler(Looper.getMainLooper()).post(Runnable {
                                    //liveScoreData.value = result

                                    var model = result.data

                                    var first_circle = model?.firstCircle.toString()

                                    currentRunText = first_circle

                                    var language =
                                        MainApplication.applicationInstance.getLiveScoreLanguage()

                                    if (language.equals("hindi")) {
                                        hindiActive = true
                                        engActive = false
                                    } else {
                                        engActive = true
                                        hindiActive = false
                                    }

                                    //  hindiActive = LiveScoreHomeNewActivity().hindiActive
                                    // engActive = LiveScoreHomeNewActivity().engActive
                                    popupFirstCircle1?.setBackgroundTintList(
                                        ColorStateList.valueOf(
                                            ContextCompat.getColor(
                                                applicationContext,
                                                R.color.yellow_lgt
                                            )
                                        )
                                    );

                                    when (first_circle.toString()) {
                                        "0" -> compValue = first_circle
                                        "1" -> compValue = first_circle
                                        "2" -> compValue = first_circle
                                        "3" -> compValue = first_circle
                                        "Six" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.green
                                                    )
                                                )
                                            );
                                            first_circle = "6"
                                            compValue = first_circle
                                        }
                                        "Ball" -> compValue = first_circle
                                        "Four" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.blue_aqva
                                                    )
                                                )
                                            );
                                            first_circle = "4"
                                            compValue = first_circle
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                                popupFirstCircle1?.setBackgroundTintList(
//                                                    ColorStateList.valueOf(
//                                                        ContextCompat.getColor(applicationContext, R.color.limeGreen)))
//                                            }

                                        }
                                        "Over" -> compValue = first_circle

                                        "Catch" -> {
                                            first_circle = "C-O"
                                            compValue = first_circle

                                        }
                                        "Wicket" -> compValue = "W"


                                        "NB" -> {
                                            first_circle = "NB"
                                            compValue = first_circle
                                        }
                                        "Bolwed" -> compValue = "W"

                                        "LBW" -> compValue = first_circle
                                        "WB" -> {
                                            first_circle = "Wide"
                                            compValue = first_circle
                                        }
                                        "WB+1" -> compValue = first_circle
                                        "LB" -> {
                                            first_circle = "L-Bye"
                                            compValue = first_circle

                                        }
                                        "LB+1" -> compValue = first_circle

                                    }

                                    when (first_circle) {
                                        "0" -> {
                                            compValue1 = first_circle
                                        }
                                        "1" -> {
                                            compValue1 = first_circle
                                        }
                                        "2" -> compValue1 = first_circle
                                        "3" -> compValue1 = first_circle
                                        "Six" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.green
                                                    )
                                                )
                                            );
                                            first_circle = "6"
                                            compValue1 = first_circle
                                        }
                                        "Ball" -> compValue1 = first_circle
                                        "Four" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.blue_aqva
                                                    )
                                                )
                                            );
                                            first_circle = "4"
                                            compValue1 = first_circle

                                        }
                                        "Over" -> compValue1 = first_circle
                                        "Bowler Stop" -> {
                                            first_circle = "B-stop"
                                            compValue1 = first_circle

                                        }
                                        "Catch" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.red
                                                    )
                                                )
                                            );
                                            first_circle = "C^O"
                                            compValue1 = first_circle

                                        }
                                        "Catch Out" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.red
                                                    )
                                                )
                                            );
                                            first_circle = "C^O"
                                            compValue1 = first_circle

                                        }
                                        "Wicket" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.red
                                                    )
                                                )
                                            );
                                            compValue1 = first_circle
                                        }
                                        "Stump (Test)" -> {
                                            first_circle = "Stumps"
                                            compValue1 = first_circle

                                        }
                                        "3rd Umpire" -> compValue1 = first_circle
                                        "NB" -> {
                                            first_circle = "No-B"
                                            compValue1 = first_circle
                                        }
                                        "Bolwed" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.red
                                                    )
                                                )
                                            );
                                            compValue1 = first_circle
                                        }
                                        "Free Hit" -> compValue1 = first_circle
                                        "LBW" -> {
                                            popupFirstCircle1?.setBackgroundTintList(
                                                ColorStateList.valueOf(
                                                    ContextCompat.getColor(
                                                        applicationContext,
                                                        R.color.red
                                                    )
                                                )
                                            );
                                            compValue1 = first_circle
                                        }
                                        "WB" -> {
                                            first_circle = "Wide"
                                            compValue1 = first_circle

                                        }
                                        "WB+1" -> compValue1 = first_circle
                                        "LB" -> {
                                            first_circle = "L-Bye"
                                            compValue1 = first_circle
                                        }
                                        "LB+1" -> compValue1 = first_circle
                                        "Not Out" -> {
                                            first_circle = "N-Out"
                                            compValue1 = first_circle

                                        }
                                        "Rain Start" -> {
                                            first_circle = "Rain"
                                            compValue1 = first_circle

                                        }
                                        "Rain Stop" -> {
                                            first_circle = "Rain^"
                                            compValue1 = first_circle
                                        }
                                        "Stump" -> {
                                            first_circle = "Stump"
                                            compValue1 = first_circle

                                        }
                                        "Bowling Review" -> {
                                            first_circle = "Review"
                                            compValue1 = first_circle
                                        }
                                        else -> {
                                            compValue1 = first_circle
                                        }
                                    }


                                    //var compValue = LiveScoreHomeNewActivity().compValue
                                    //    Log.e("TAG3423", "onStartCommand: active" +" ${LiveScoreHomeNewActivity.textToSpeech}   " +model?.firstCircle)


                                    teams?.setText(model?.teamAShort + " Vs " + model?.teamBShort)

                                    if (model?.currentInning.equals("1")) {

                                        teamTossOrRequire?.setText(model?.toss)

                                        if (model?.battingTeam.toString()
                                                .equals(model?.teamAId.toString())
                                        ) {


                                            Glide.with(this@WidgetService).load(model?.teamAImg)
                                                .placeholder(R.mipmap.ic_launcher_round)
                                                .into(teamImage!!)

                                            teamName?.setText(model?.teamAShort)

                                            teamsRun?.setText(model?.teamAScores)
                                            teamOver?.setText(model?.teamAOver)
                                            teamCRR?.setText("CRR: " + model?.currRate)

                                        } else if (model?.battingTeam.toString()
                                                .equals(model?.teamBId.toString())
                                        ) {


                                            Glide.with(this@WidgetService).load(model?.teamBImg)
                                                .placeholder(R.mipmap.ic_launcher_round)
                                                .into(teamImage!!)

                                            teamName?.setText(model?.teamBShort)

                                            teamsRun?.setText(model?.teamBScores)
                                            teamOver?.setText(model?.teamBOver)
                                            teamCRR?.setText("CRR: " + model?.currRate)

                                        }

                                    } else if (model?.currentInning.equals("2")) {

                                        if (model?.battingTeam.toString()
                                                .equals(model?.teamAId.toString())
                                        ) {


                                            Glide.with(this@WidgetService).load(model?.teamAImg)
                                                .placeholder(R.mipmap.ic_launcher_round)
                                                .into(teamImage!!)

                                            teamName?.setText(model?.teamAShort)

                                            if (model?.matchType.equals("Test")) {
                                                teamTossOrRequire?.setText(model?.trailLead)
                                            } else {
                                                teamTossOrRequire?.setText(model?.teamAShort + " need ${model?.runNeed} runs in ${model?.ballRem} balls")
                                            }
                                            teamsRun?.setText(model?.teamAScores)
                                            teamOver?.setText(model?.teamAOver)
                                            teamCRR?.setText("CRR: " + model?.currRate)

                                        } else if (model?.battingTeam.toString()
                                                .equals(model?.teamBId.toString())
                                        ) {


                                            Glide.with(this@WidgetService).load(model?.teamBImg)
                                                .placeholder(R.mipmap.ic_launcher_round)
                                                .into(teamImage!!)

                                            teamName?.setText(model?.teamBShort)

                                            if (model?.matchType.equals("Test")) {
                                                teamTossOrRequire?.setText(model?.trailLead)
                                            } else {
                                                teamTossOrRequire?.setText(model?.teamBShort + " need ${model?.runNeed} runs in ${model?.ballRem} balls")
                                            }
                                            teamsRun?.setText(model?.teamBScores)
                                            teamOver?.setText(model?.teamBOver)
                                            teamCRR?.setText("CRR: " + model?.currRate)

                                        }
                                    }

                                    if (model?.matchType.equals("Test")) {
                                        if (model?.currentInning.equals("3")) {

                                            teamTossOrRequire?.setText(model?.trailLead)

                                            if (model?.battingTeam.toString()
                                                    .equals(model?.teamAId.toString())
                                            ) {


                                                Glide.with(this@WidgetService).load(model?.teamAImg)
                                                    .placeholder(R.mipmap.ic_launcher_round)
                                                    .into(teamImage!!)

                                                teamName?.setText(model?.teamAShort)

                                                teamsRun?.setText(model?.teamAScores)
                                                teamOver?.setText(model?.teamAOver)
                                                teamCRR?.setText("CRR: " + model?.currRate)

                                            } else if (model?.battingTeam.toString()
                                                    .equals(model?.teamBId.toString())
                                            ) {


                                                Glide.with(this@WidgetService).load(model?.teamBImg)
                                                    .placeholder(R.mipmap.ic_launcher_round)
                                                    .into(teamImage!!)

                                                teamName?.setText(model?.teamBShort)

                                                teamsRun?.setText(model?.teamBScores)
                                                teamOver?.setText(model?.teamBOver)
                                                teamCRR?.setText("CRR: " + model?.currRate)

                                            }

                                        }

                                        if (model?.currentInning.equals("4")) {

                                            teamTossOrRequire?.setText(model?.trailLead)

                                            if (model?.battingTeam.toString()
                                                    .equals(model?.teamAId.toString())
                                            ) {


                                                Glide.with(this@WidgetService).load(model?.teamAImg)
                                                    .placeholder(R.mipmap.ic_launcher_round)
                                                    .into(teamImage!!)

                                                teamName?.setText(model?.teamAShort)

                                                teamsRun?.setText(model?.teamAScores)
                                                teamOver?.setText(model?.teamAOver)
                                                teamCRR?.setText("CRR: " + model?.currRate)

                                            } else if (model?.battingTeam.toString()
                                                    .equals(model?.teamBId.toString())
                                            ) {

                                                Glide.with(this@WidgetService).load(model?.teamBImg)
                                                    .placeholder(R.mipmap.ic_launcher_round)
                                                    .into(teamImage!!)

                                                teamName?.setText(model?.teamBShort)

                                                teamsRun?.setText(model?.teamBScores)
                                                teamOver?.setText(model?.teamBOver)
                                                teamCRR?.setText("CRR: " + model?.currRate)

                                            }

                                        }
                                    }


                                    parentWidget?.visibility = View.VISIBLE

                                    if (!model?.firstCircle.isNullOrEmpty()) {


                                        if (!MainApplication.applicationInstance.getLiveActivityIsFininshed()) {
                                            // hindiActive = LiveScoreHomeNewActivity().hindiActive
                                            // engActive = LiveScoreHomeNewActivity().engActive
                                            Log.e(
                                                "TAGWidget1111aaas",
                                                "voiceCommentary: hindicircle ${model?.firstCircle.toString()} "
                                            )
                                            /*liveHomeActivity.voiceCommentary(
                                                model?.firstCircle.toString(),
                                                applicationContext
                                            )*/

                                            teamCurrentRun?.setText(compValue)
                                        } else {
                                            teamCurrentRun?.setText(compValue)
                                            voiceCommentary(
                                                first_circle,
                                                teamCurrentRun!!, testRuns!!
                                            )
                                        }
                                    }

                                    try {

                                        if (!model?.sOvr.isNullOrEmpty()) {
                                            linearSessionOver?.visibility = View.VISIBLE
                                            sessionOver?.setText("Ovr " + model?.sOvr.toString())
                                            sessionSMin?.setText("" + model?.sMin.toString())
                                            sessionSMax?.setText("" + model?.sMax.toString())
                                        } else {
                                            linearSessionOver?.visibility = View.GONE
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                    if (!model?.minRate.toString().isNullOrEmpty()) {
                                        linearSessionTeam?.visibility = View.VISIBLE

                                        sessionMin?.setText(model?.minRate.toString())
                                        sessionMax?.setText(model?.maxRate.toString())
                                        teamNameSession?.setText(model?.favTeam.toString())
                                    } else {
                                        linearSessionTeam?.visibility = View.GONE
                                    }


                                })
                            }
                            // Log.e("getLocationUpdate", "success : "+ result )
                        }, { error ->
                            // Log.e("getLocationUpdate", "error : "+ error.message )
                        })

                }



                teamTossOrRequire?.setOnClickListener {
                    if (sessionLinear?.visibility == View.VISIBLE) {
                        sessionLinear?.visibility = View.GONE
                    } else {
                        sessionLinear?.visibility = View.VISIBLE
                    }
                }


                if (MainApplication.applicationInstance.getVoiceActive() == true) {
                    imageVolume?.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_up_24))
                    voiceActive = true

                } else {
                    imageVolume?.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_off_24))
                    voiceActive = false
                }

                imageVolume?.setOnClickListener {

                    clickToVibrate()
                    if (voiceActive == true) {
                        MainApplication.applicationInstance.setVoiceActive(false)
                        //liveHomeActivity.voiceActive = false
                        voiceActive = false
                        Toast.makeText(
                            applicationContext,
                            "Commentry Audio Off",
                            Toast.LENGTH_SHORT
                        ).show()
                        imageVolume?.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_off_24))
                    } else {
                        //liveHomeActivity.voiceActive = true
                        MainApplication.applicationInstance.setVoiceActive(true)
                        voiceActive = true
                        Toast.makeText(applicationContext, "Commentry Audio On", Toast.LENGTH_SHORT)
                            .show()
                        imageVolume?.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_volume_up_24))
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()

            Log.e("TAGWidget", "getScore: ${e.message}   ${e.printStackTrace()}")

        }


    }


    fun voiceCommentary(data: String?, teamRun: TextView, testRuns: TextView) {
        if (voiceActive) {
            textToSpeech = TextToSpeech(
                applicationContext
            ) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    if (!TextUtils.isEmpty(compValue1)) {
                        if (!testRuns.text.toString().equals(compValue1)) {
                            // Log.e("TAGWidget", "voiceCommentary: hindi ${hindiActive} -- english ${engActive}" )
                            if (hindiActive) {
                                audioHindiActive(data.toString(), teamRun, testRuns);
                            } else if (engActive) {
                                audioEngActive(data.toString(), teamRun, testRuns);
                            } else {
                                audioHindiActive(data.toString(), teamRun, testRuns);
                            }
                        }
                    }
                }
            }
        }
    }


    @SuppressLint("NewApi")
    fun audioHindiActive(data: String, teamRun: TextView, testRuns: TextView) {
        val locale = Locale("hi")
        textToSpeech?.language = locale
        textToSpeech?.setSpeechRate(1.4f)

        when (data) {
            "0" -> {
                textToSpeech?.speak(
                    "Khali BALL Khali",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }


            }
            "1" -> {
                textToSpeech?.speak(
                    "Single Aaya Single",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "2" -> {
                textToSpeech?.speak(
                    "Double Aaya Double",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "3" -> {
                textToSpeech?.speak(
                    "triple Aaya triple",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Six" -> {
                textToSpeech?.speak(
                    "Chaakka Aaya Cchakka",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Ball" -> {
                textToSpeech?.speak(
                    "Ball Chaaloo Ball",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Four" -> {
                textToSpeech?.speak(
                    "Choukka Aaya Choukka",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Over" -> {
                textToSpeech?.speak(
                    "Over Complete",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Bowler Stop" -> {
                textToSpeech?.speak(
                    "Bowler rukkaa Bowler",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Catch Out" -> {
                textToSpeech?.speak(
                    "Catch out hua Catch out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

                //first_circle = "Catch Out"
            }
            "Wicket" -> {
                textToSpeech?.speak(
                    "Wicket gya wicket",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }


            }
            "Stump (Test)" -> {

            }
            "3rd Umpire" -> {
                textToSpeech?.speak(
                    "3rd Umpire Gya, 3rd Umpire",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "No Ball" -> {
                textToSpeech?.speak(
                    "No Ball gayi No ball",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Bolwed" -> {
                textToSpeech?.speak(
                    "Bowled out hua Bowled out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Free Hit" -> {
                textToSpeech?.speak(
                    "Free hit mila Free hit",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "LBW" -> {
                textToSpeech?.speak(
                    "L B W out hua L B W out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Wide Ball" -> {
                textToSpeech?.speak(
                    "wide ball gayee wide ball",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "WB+1" -> {
                textToSpeech?.speak(
                    "wide ball ke sath single aaya",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Leg Bye" -> {
                textToSpeech?.speak(
                    "Leg byei gayee leg byei",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "LB+1" -> {
                textToSpeech?.speak(
                    "leg bayie ke sath single aaya",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Not Out" -> {
                textToSpeech?.speak(
                    "not out hua not out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
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
            }
            "Rain Stop" -> {
                textToSpeech?.speak(
                    "barish ruk gayi match shuru hone wala hai",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Stump Out" -> {
                textToSpeech?.speak(
                    "stump out huaa stump out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Bowling Review" -> {
                textToSpeech?.speak(
                    "Bowling side se review ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            else -> {
                textToSpeech?.speak(
                    data,
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
        }

        teamRun.setText(compValue)
        testRuns.setText(data)

    }

    fun audioEngActive(data: String, teamRun: TextView, testRuns: TextView) {
        val locale = Locale("en")
        textToSpeech?.language = locale
        textToSpeech?.setSpeechRate(1.3f)
        when (data) {
            "0" -> {
                textToSpeech?.speak(
                    "zero run zero",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "1" -> {
                textToSpeech?.speak(
                    "one run one",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "2" -> {
                textToSpeech?.speak(
                    "two run two",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "3" -> {
                textToSpeech?.speak(
                    "Three run three",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Six" -> {
                textToSpeech?.speak(
                    "six six six",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Ball" -> {
                textToSpeech?.speak(
                    "Bowler running",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Four" -> {
                textToSpeech?.speak(
                    "four run boundry",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Over" -> {
                textToSpeech?.speak(
                    "Over Complete",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Bowler Stop" -> {
                textToSpeech?.speak(
                    "Bowler stop Bowler",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Catch Out" -> {
                textToSpeech?.speak(
                    "Catch out ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Wicket" -> {
                textToSpeech?.speak(
                    "Wicket wicket wicket",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Stump (Test)" -> {

            }
            "3rd Umpire" -> {
                textToSpeech?.speak(
                    "3rd Umpire ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "No Ball" -> {
                textToSpeech?.speak(
                    "No Ball ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Bolwed" -> {
                textToSpeech?.speak(
                    "Bowled out",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Free Hit" -> {
                textToSpeech?.speak(
                    "Free hit ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "LBW" -> {
                textToSpeech?.speak(
                    "L B W ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Wide Ball" -> {
                textToSpeech?.speak(
                    "wide ball ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

                //first_circle = "Wide Ball"
            }
            "WB+1" -> {
                textToSpeech?.speak(
                    "wide ball with one run",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Leg Bye" -> {
                textToSpeech?.speak(
                    "Leg bye",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "LB+1" -> {
                textToSpeech?.speak(
                    "leg bye with one run",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Not Out" -> {
                textToSpeech?.speak(
                    "not out ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Rain Start" -> {
                textToSpeech?.speak(
                    "rain start and wait for stop",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
            "Rain Stop" -> {
                textToSpeech?.speak(
                    "rain stop .. match start in sometime",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            "Stump Out" -> {
                textToSpeech?.speak(
                    "stump out ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
                //first_circle = "Stump Out"
            }
            "Bowling Review" -> {
                textToSpeech?.speak(
                    "Bowling side review ",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }

            }
            else -> {
                textToSpeech?.speak(
                    data,
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
                if (voiceActive == false) {
                    textToSpeech?.stop()
                }
            }
        }

        teamRun.setText(compValue)
        testRuns.setText(data)
    }


    private fun clickToVibrate() {
        val vibe = applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator
        //replace yourActivity.this with your own activity or if you declared a context you can write context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80) //80 represents the milliseconds (the duration of the vibration)
    }


}