package com.cricbuzzplus.liveline.livedata.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemDialogLiveMoreBinding
import com.cricbuzzplus.liveline.databinding.LiveHomeBinding
import com.cricbuzzplus.liveline.databinding.NotificationSettingsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.LiveResponse
import com.cricbuzzplus.liveline.livedata.response.NotificationSettingsResponse
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.LiveMoreMatchAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.*
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnMatchClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import com.cricbuzzplus.liveline.livedata.ui.widget.WidgetService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class LiveHomeActivity : BaseActivity() {

    lateinit var binding: LiveHomeBinding
    private lateinit var viewModel: LiveViewModel
    var XXrunningBallsTotal2 = 0
    var matchOverInBalls = 0
    var first_circle: String? = null

    var fragmentList = ArrayList<Fragment>()
    lateinit var liveFragment: LiveFragment
    lateinit var liveLineFragment: LiveLineNewFragment
    lateinit var infoFragment: InfoFragment
    lateinit var scoreboardFragment: ScoreboardFragment
    lateinit var squadFragment: SquadFragment
    lateinit var oversFragment: OversFragment

    //lateinit var commentaryFragment: CommentaryFragment
    lateinit var highlightFragment: HighlightFragment
    lateinit var matchOddsFragment: MatchOddsFragment

    // lateinit var chatFragment: ChatFragment
    // lateinit var tipsFragment: TipsFragment
    lateinit var graphFragment: GraphFragment
    var liveModel: LiveResponse? = null
    var match_id = 0
    var matchStatus = ""
    var match_time = ""
    var matchDate = ""
    var matchType = ""
    var testActive = false
    var compValue = ""


    var moreMatchList = arrayListOf<HomeMatchResponseItem>()

    public var hindiActive = true
    public var engActive = false

    var textToSpeech: TextToSpeech? = null
    private var timer: Timer? = null


    var isUiSet = false
    var isUiSetOnetime = false
    var isUiSetOnetimeliveline = false
    var isUiSetOver = false

    val tabArray = arrayListOf<String>()

    var teamA = ""
    var teamB = ""

    var teamAShort = ""
    var teamBShort = ""
    var result = ""
    var matchNo = ""
    var series = ""

    var fcmToken = ""
    lateinit var bottomSheetDialogNoti: BottomSheetDialog

    lateinit var bottomSheetDialog: BottomSheetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_home)


        tabArray.add(getString(R.string.live_info))
        tabArray.add(getString(R.string.live_live))
        tabArray.add(getString(R.string.live_liveline))
        // tabArray.add(getString(R.string.live_chats))
        tabArray.add(getString(R.string.live_scorecard))
        tabArray.add(getString(R.string.live_squad))
        tabArray.add(getString(R.string.live_over))
        tabArray.add(getString(R.string.live_highlights))
        tabArray.add(getString(R.string.live_odds))
        // tabArray.add(getString(R.string.live_chats))
        tabArray.add(getString(R.string.live_graph))
        //  tabArray.add(getString(R.string.live_tips))

        // setContentView(R.layout.activity_live_home)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@LiveHomeActivity, R.color.colorPrimaryDark)
        }

        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_pip)

        liveFragment = LiveFragment()

        liveLineFragment = LiveLineNewFragment()


        match_id = intent.getIntExtra("matchId", 0)
        matchStatus = intent.getStringExtra("matchStatus").toString()
        match_time = intent.getStringExtra("matchTime").toString()
        matchDate = intent.getStringExtra("matchDate").toString()
        matchType = intent.getStringExtra("matchType").toString()
        teamA = intent.getStringExtra("teamA").toString()
        teamB = intent.getStringExtra("teamB").toString()
        teamAShort = intent.getStringExtra("teamAShort").toString()
        teamBShort = intent.getStringExtra("teamBShort").toString()
        result = intent.getStringExtra("result").toString()
        matchNo = intent.getStringExtra("matchNo").toString()
        series = intent.getStringExtra("series").toString()

        /*MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)*/


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcmToken = task.result.toString()

            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${fcmToken}")

        })

        bottomSheetDialogNoti = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialogNoti.setContentView(R.layout.bottom_sheet_notification)


        // titleName = findViewById(R.id.titleName)
        // toolShow = findViewById(R.id.toolShow)
        // backImg = findViewById(R.id.backImg)
        // backImg.setOnClickListener(View.OnClickListener { v: View? -> onBackPressed() })

        //  Log.e("TAG111", "onCreate: match time "+match_time+" match date "+ matchDate )

        hideKeyBoard()
        setObservers()


        if (matchStatus.equals("Live")) {
            binding.notification.visibility = View.VISIBLE
            binding.pinMatch.visibility = View.VISIBLE
        } else if (matchStatus.equals("Finished")) {
            binding.notification.visibility = View.GONE
            binding.pinMatch.visibility = View.GONE
        } else {
            binding.notification.visibility = View.VISIBLE
            binding.pinMatch.visibility = View.GONE
        }

        setUpViewPager()

        //  addActionBar(true)

        binding.back.setOnClickListener(View.OnClickListener {
            onBackPressed()

            if (timer != null) {

                timer?.cancel()
                timer?.purge()
                timer == null

            }
            finish()
        })


        /* binding.share.setOnClickListener {

             //infoFragment.callOversComplete()

             shareScreen()

         }*/

        binding.menu.setOnClickListener {
            showPopup(binding.menu)
        }

        binding.moreMatch.setOnClickListener {

            if (!moreMatchList.isNullOrEmpty()) {
                showMoreDialog(moreMatchList)
            } else {
                viewModel.getHomeMatches()
            }
        }


        binding.pinMatch.setOnClickListener(View.OnClickListener {
            clickToVibrate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(applicationContext)) {
                    // send user to the device settings
                    showBottomSheet()
                } else {
                    //onUserLeaveHint();
                    if ("Live" == matchStatus) {
                        // adBottomSheet()

                        val intent = Intent(this@LiveHomeActivity, WidgetService::class.java)
                        intent.putExtra("match_id", "" + match_id)

                        startService(intent)

                    } else {
                        Toast.makeText(
                            this@LiveHomeActivity,
                            "Pin Score Live Matches only",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        binding.notification.setOnClickListener {
            if (checkPermission()) {
                handleProgressLoader(true)
                getNotificationSetting(match_id)
            } else {
                requestPermission()
            }
        }

    }

    fun getNotificationSetting(
        matchId: Int
    ) {
        if (isInternetConnection()) {
            viewModel.getNotificationSettingByMatch(matchId, fcmToken)
        }

    }

    fun submitNotificationSetting(
        matchId: Int,
        matchStart: Boolean,
        matchToss: Boolean,
        matchResult: Boolean,
        matchFifty: Boolean,
        matchCentury: Boolean,
        matchDoubleCentury: Boolean,
        matchFour: Boolean,
        matchSix: Boolean,
        matchWicket: Boolean
    ) {
        if (isInternetConnection()) {
            viewModel.getNotificationSetting(
                matchId,
                matchStart,
                matchToss,
                matchResult,
                matchFifty,
                matchCentury,
                matchDoubleCentury,
                matchFour,
                matchSix,
                matchWicket,
                fcmToken
            )
        }
    }


    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        } else {
            return true
        }
    }

    fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1025
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1025 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                /*  val intent =
                      Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                  startActivityForResult(intent, 2)*/
                // main logic
            } else {
                showToast("You need to allow notification permissions")

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    requestPermission()
                } else {
                    // requestPermission();
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                }

            }
        }

    }


    var indexP = 0

    fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.live_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_share -> {
                    Log.e("TAG", "showPopup: sssssssss")
                    shareScreen()
                }
                R.id.action_table -> {
                    indexP = 1
                    if (matchStatus.equals("Upcoming")) {
                        getSeriesIdCricBuzzUpcoming(series.toString(), matchNo.toString())
                    } else if (matchStatus.equals("Finished")) {
                        getSeriesIdCricBuzzFinished(series.toString(), matchNo.toString())
                    } else if (matchStatus.equals("Live")) {
                        getSeriesIdCricBuzzLive(series.toString(), matchNo.toString())
                    }

                }
                R.id.action_series -> {
                    indexP = 0
                    if (matchStatus.equals("Upcoming")) {
                        getSeriesIdCricBuzzUpcoming(series.toString(), matchNo.toString())
                    } else if (matchStatus.equals("Finished")) {
                        getSeriesIdCricBuzzFinished(series.toString(), matchNo.toString())
                    } else if (matchStatus.equals("Live")) {
                        getSeriesIdCricBuzzLive(series.toString(), matchNo.toString())
                    }

                }
            }
            true
        }
        popup.show()
    }


    fun observeSeriesId() {

        seriesIdCricLiveData.observe(this, Observer {
            if (it != null) {
                val intent = Intent(this, SeriesDetailsActivity::class.java)
                intent.putExtra("series", series)
                intent.putExtra("seriesId", it)
                intent.putExtra("index", indexP)
                startActivity(intent)
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.live_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.live_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {

                // Handle the menu item click here
                // For example, you can start a new activity, show a dialog, etc.
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showMoreDialog(moreMatchList: ArrayList<HomeMatchResponseItem>) {

        val bindingAlert = ItemDialogLiveMoreBinding.inflate(layoutInflater)

        bindingAlert.recyclerMoreMatch.adapter = LiveMoreMatchAdapter(
            moreMatchList,
            this@LiveHomeActivity,
            object : OnMatchClickInterface {
                override fun onClick(model: HomeMatchResponseItem) {

                    val intent = Intent(this@LiveHomeActivity, LiveHomeActivity::class.java)
                    intent.putExtra("matchId", model.matchId)
                    intent.putExtra("matchStatus", model.matchStatus)
                    intent.putExtra("matchTime", model.matchTime)
                    intent.putExtra("matchDate", model.matchDate)
                    intent.putExtra("teamA", model.teamA)
                    intent.putExtra("teamB", model.teamB)
                    intent.putExtra("teamAShort", model.teamAShort)
                    intent.putExtra("teamBShort", model.teamBShort)
                    intent.putExtra("matchType", model.matchType)
                    intent.putExtra("result", model.result)
                    intent.putExtra("matchNo", model.matchs)
                    intent.putExtra("series", model.series)
                    startActivity(intent)
                    finish()

                }
            })

        val dialog = AlertDialog.Builder(this, R.style.TransparentDialog)
            .setView(bindingAlert.root)
            .create()

        dialog.show()

    }


    private fun shareScreen() {
        try {
            val rootView = window.decorView.rootView
            val bitmap =
                Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            val rect = Rect(0, 0, rootView.width, rootView.height)
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
            rootView.draw(canvas)

            shareBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "shareScreen: " + e.message)
        }
    }

    private fun shareBitmap(bitmap: Bitmap) {
        val cachePath = File(externalCacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "screenshot.png")
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("TAG", "shareBitmap: " + e.message)
        }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/png"
        val uri = FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".provider",
            file
        )

        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))
    }


    fun showBottomSheet() {
        val continueBtn = bottomSheetDialog.findViewById<TextView>(R.id.bottomContinue)
        val notNowBtn = bottomSheetDialog.findViewById<TextView>(R.id.bottomNotNow)
        val closeImg = bottomSheetDialog.findViewById<ImageView>(R.id.closeImg)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (bottomSheetDialog.isShowing) {
        } else {

            bottomSheetDialog.show()

        }
        continueBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkOverlayPermission()
        }
        notNowBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
            // checkOverlayPermission();
        }

        closeImg!!.setOnClickListener {
            bottomSheetDialog.dismiss()
            // checkOverlayPermission();
        }
    }

    fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                if (bottomSheetDialog.isShowing) {
                    bottomSheetDialog.dismiss()
                }
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }
        }
    }


    fun setUpViewPager() {

        val bundle = Bundle()
        bundle.putInt("matchId", match_id)
        bundle.putString("matchStatus", matchStatus)
        bundle.putString("match_time", match_time)
        bundle.putString("matchDate", matchDate)
        bundle.putString("matchType", matchType)
        bundle.putString("teamA", teamA)
        bundle.putString("teamB", teamB)
        bundle.putString("teamAShort", teamAShort)
        bundle.putString("teamBShort", teamBShort)
        bundle.putString("result", result)
        bundle.putString("matchNo", matchNo)
        bundle.putString("series", series)

        binding.teams.setText("${teamAShort} v ${teamBShort}")


        infoFragment = InfoFragment()

        liveLineFragment = LiveLineNewFragment()
        // chatFragment = ChatFragment()
        scoreboardFragment = ScoreboardFragment()
        squadFragment = SquadFragment()
        oversFragment = OversFragment()
        // commentaryFragment = CommentaryFragment()
        highlightFragment = HighlightFragment()
        matchOddsFragment = MatchOddsFragment()

        graphFragment = GraphFragment()
        // tipsFragment = TipsFragment()


        infoFragment.setArguments(bundle)
        liveFragment.setArguments(bundle)
        liveLineFragment.setArguments(bundle)
        // chatFragment.setArguments(bundle)
        scoreboardFragment.setArguments(bundle)
        squadFragment.setArguments(bundle)
        oversFragment.setArguments(bundle)
        // commentaryFragment.setArguments(bundle)
        highlightFragment.setArguments(bundle)
        matchOddsFragment.setArguments(bundle)
        //  chatFragment.setArguments(bundle)
        graphFragment.setArguments(bundle)
        //tipsFragment.setArguments(bundle)


        fragmentList.add(infoFragment)
        fragmentList.add(liveFragment)
        fragmentList.add(liveLineFragment)
        // fragmentList.add(chatFragment)
        fragmentList.add(scoreboardFragment)
        fragmentList.add(squadFragment)
        fragmentList.add(oversFragment)
        //fragmentList.add(commentaryFragment)
        fragmentList.add(highlightFragment)
        fragmentList.add(matchOddsFragment)
        //  fragmentList.add(chatFragment)
        fragmentList.add(graphFragment)
        // fragmentList.add(tipsFragment)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle, fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.tab_unselected), resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        binding.pager.offscreenPageLimit = fragmentList.size


        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()



        if (matchStatus.equals("Upcoming")) {

        } else {
            binding.pager.setCurrentItem(1)

        }

        Handler().postDelayed({
            callLiveScore(match_id)
        }, 200)


    }


    fun callLiveScore(matchId: Int) {
        if (matchStatus.equals("Live", true)) {

            if (timer == null) {
                timer = Timer()
            }

            if (isInternetConnection()) {
                viewModel.getLiveScoreMain(matchId)
            }

            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    // recyclerViewMaincls();
                    if (isUiSet) {
                        if (isInternetConnection()) {
                            viewModel.getLiveScoreMain(matchId)
                        }
                    }
                }
            }, 2000, 2000)

        } else {

            if (isInternetConnection()) {
                viewModel.getLiveScoreMain(matchId)
            }
        }
        //  viewModel.getLiveScoreMain(matchId)
    }


    override fun onResume() {
        super.onResume()
        MainApplication.applicationInstance.setActivityFinished(false)
    }

    override fun onStop() {
        super.onStop()
        MainApplication.applicationInstance.setActivityFinished(true)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            timer?.purge()

            timer = null
        }

    }

    private fun setObservers() {
        observeExtras()
        observeLiveScore()
        observeHomeList()
        observeSeriesId()
        observeSetSquad()

        observeNotificationSet()
        observeNotificationGet()
        observeNotificationStatus()
    }

    private fun observeNotificationSet() {
        viewModel.getNotifySettingsLiveData().observe(this, Observer {
            if (it != null) {
                showToast("notification settings set.")
            }
        })
    }

    private fun observeNotificationStatus() {
        viewModel.notifySettingsStatusLiveData.observe(this, Observer {
            if (!it) {
                // if (model != null) {
                //  Log.e(TAG, "observeNotificationStatus: "+model?.matchId )
                showBottomSheetNoti(match_id)
                //  }
            }
        })
    }

    private fun observeNotificationGet() {
        viewModel.notifySettingsDataLiveData.observe(this, Observer {
            if (it != null) {
                showBottomSheetNotify(it)
            }
        })
    }


    fun showBottomSheetNoti(match_id: Int) {

        var matchStart = false
        var matchToss = false
        var matchResult = false
        var matchFifty = false
        var matchCentury = false
        var matchDoubleCentury = false
        var matchFour = false
        var matchSix = false
        var matchWicket = false

        val bindingBottom = DataBindingUtil.inflate<NotificationSettingsBinding>(
            layoutInflater,
            R.layout.bottom_sheet_notification,
            null,
            false
        )

        bottomSheetDialogNoti = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialogNoti?.setContentView(bindingBottom.root)



        bottomSheetDialogNoti?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (bottomSheetDialogNoti?.isShowing!!) {
            bottomSheetDialogNoti?.dismiss()
        } else {

            bottomSheetDialogNoti?.show()

        }

        handleProgressLoader(false)

        bindingBottom.linearMatchStart.setOnClickListener {
            if (matchStart) {
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgMatchStart.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textMatchStart.setTextColor(
                    this.getResources().getColor(R.color.gray)
                )

                matchStart = false
            } else {
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgMatchStart.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )

                bindingBottom.textMatchStart.setTextColor(
                    this.getResources().getColor(R.color.yellow_lgt)
                )

                matchStart = true
            }
        }

        bindingBottom.linearToss.setOnClickListener {
            if (matchToss) {
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgToss.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textToss.setTextColor(this.getResources().getColor(R.color.gray))

                matchToss = false
            } else {
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgToss.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )

                bindingBottom.textToss.setTextColor(
                    this.getResources().getColor(R.color.yellow_lgt)
                )

                matchToss = true
            }
        }

        bindingBottom.linearResult.setOnClickListener {
            if (matchResult) {
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgResult.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textResult.setTextColor(this.getResources().getColor(R.color.gray))

                matchResult = false
            } else {
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgResult.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )

                bindingBottom.textResult.setTextColor(
                    this.getResources().getColor(R.color.yellow_lgt)
                )

                matchResult = true
            }
        }

        bindingBottom.linear50.setOnClickListener {
            if (matchFifty) {
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img50.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text50.setTextColor(this.getResources().getColor(R.color.gray))

                matchFifty = false
            } else {
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img50.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text50.setTextColor(this.getResources().getColor(R.color.yellow_lgt))

                matchFifty = true
            }
        }

        bindingBottom.linear100.setOnClickListener {
            if (matchCentury) {
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img100.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text100.setTextColor(this.getResources().getColor(R.color.gray))

                matchCentury = false
            } else {
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img100.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text100.setTextColor(this.getResources().getColor(R.color.yellow_lgt))

                matchCentury = true
            }
        }

        bindingBottom.linear200.setOnClickListener {
            if (matchDoubleCentury) {
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img200.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text200.setTextColor(this.getResources().getColor(R.color.gray))

                matchDoubleCentury = false
            } else {
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img200.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text200.setTextColor(this.getResources().getColor(R.color.yellow_lgt))

                matchDoubleCentury = true
            }
        }

        bindingBottom.linear4.setOnClickListener {
            if (matchFour) {
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img4.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text4.setTextColor(this.getResources().getColor(R.color.gray))

                matchFour = false
            } else {
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img4.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text4.setTextColor(this.getResources().getColor(R.color.yellow_lgt))

                matchFour = true
            }
        }

        bindingBottom.linear6.setOnClickListener {
            if (matchSix) {
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img6.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text6.setTextColor(this.getResources().getColor(R.color.gray))

                matchSix = false
            } else {
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img6.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text6.setTextColor(this.getResources().getColor(R.color.yellow_lgt))

                matchSix = true
            }
        }

        bindingBottom.linearWicket.setOnClickListener {
            if (matchWicket) {
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgWicket.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textWicket.setTextColor(this.getResources().getColor(R.color.gray))

                matchWicket = false
            } else {
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgWicket.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.textWicket.setTextColor(
                    this.getResources().getColor(R.color.yellow_lgt)
                )

                matchWicket = true
            }
        }


        bindingBottom.submit!!.setOnClickListener {
            bottomSheetDialogNoti?.dismiss()

            submitNotificationSetting(
                match_id,
                matchStart,
                matchToss,
                matchResult,
                matchFifty,
                matchCentury,
                matchDoubleCentury,
                matchFour,
                matchSix,
                matchWicket
            )


        }
    }


    fun showBottomSheetNotify(model: NotificationSettingsResponse) {

        val activity = this

        val bindingBottom = DataBindingUtil.inflate<NotificationSettingsBinding>(
            layoutInflater,
            R.layout.bottom_sheet_notification,
            null,
            false
        )

        bottomSheetDialogNoti = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialogNoti?.setContentView(bindingBottom.root)


        var matchStart = model.matchStart.toBoolean()
        var matchToss = model.toss.toBoolean()
        var matchResult = model.result.toBoolean()
        var matchFifty = model.fifty.toBoolean()
        var matchCentury = model.hundrade.toBoolean()
        var matchDoubleCentury = model.twohundrade.toBoolean()
        var matchFour = model.four.toBoolean()
        var matchSix = model.six.toBoolean()
        var matchWicket = model.wicket.toBoolean()

        bottomSheetDialogNoti?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (bottomSheetDialogNoti?.isShowing == true) {
            bottomSheetDialogNoti?.dismiss()
        } else {

            bottomSheetDialogNoti?.show()

        }

        handleProgressLoader(false)

        if (matchStart) {
            bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgMatchStart.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.textMatchStart.setTextColor(
                activity.getResources().getColor(R.color.yellow_lgt)
            )

        } else {
            bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgMatchStart.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.textMatchStart.setTextColor(
                activity.getResources().getColor(R.color.gray)
            )
        }

        if (!matchToss) {
            bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgToss.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgToss.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.textToss.setTextColor(
                activity.getResources().getColor(R.color.yellow_lgt)
            )

        }

        if (!matchResult) {
            bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgResult.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgResult.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.textResult.setTextColor(
                activity.getResources().getColor(R.color.yellow_lgt)
            )

        }

        if (!matchFifty) {
            bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img50.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img50.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))
        }

        if (!matchCentury) {
            bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img100.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img100.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchDoubleCentury) {
            bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img200.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img200.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchFour) {
            bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img4.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img4.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchSix) {
            bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img6.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img6.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchWicket) {
            bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgWicket.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            )
            bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.gray))

        } else {
            bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgWicket.setImageTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
                    )
                )
            )
            bindingBottom.textWicket.setTextColor(
                activity.getResources().getColor(R.color.yellow_lgt)
            )

        }


        bindingBottom.linearMatchStart.setOnClickListener {
            if (matchStart) {
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgMatchStart.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textMatchStart.setTextColor(
                    activity.getResources().getColor(R.color.gray)
                )

                matchStart = false
            } else {
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgMatchStart.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.textMatchStart.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchStart = true
            }
        }

        bindingBottom.linearToss.setOnClickListener {
            if (matchToss) {
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgToss.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.gray))

                matchToss = false
            } else {
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgToss.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )

                bindingBottom.textToss.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchToss = true
            }
        }

        bindingBottom.linearResult.setOnClickListener {
            if (matchResult) {
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgResult.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textResult.setTextColor(
                    activity.getResources().getColor(R.color.gray)
                )

                matchResult = false
            } else {
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgResult.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )

                bindingBottom.textResult.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchResult = true
            }
        }

        bindingBottom.linear50.setOnClickListener {
            if (matchFifty) {
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img50.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFifty = false
            } else {
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img50.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text50.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchFifty = true
            }
        }

        bindingBottom.linear100.setOnClickListener {
            if (matchCentury) {
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img100.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.gray))

                matchCentury = false
            } else {
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img100.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text100.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchCentury = true
            }
        }

        bindingBottom.linear200.setOnClickListener {
            if (matchDoubleCentury) {
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img200.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.gray))

                matchDoubleCentury = false
            } else {
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img200.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text200.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchDoubleCentury = true
            }
        }

        bindingBottom.linear4.setOnClickListener {
            if (matchFour) {
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img4.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFour = false
            } else {
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img4.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text4.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchFour = true
            }
        }

        bindingBottom.linear6.setOnClickListener {
            if (matchSix) {
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img6.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.gray))

                matchSix = false
            } else {
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img6.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.text6.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchSix = true
            }
        }

        bindingBottom.linearWicket.setOnClickListener {
            if (matchWicket) {
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgWicket.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray
                        )
                    )
                )
                bindingBottom.textWicket.setTextColor(
                    activity.getResources().getColor(R.color.gray)
                )

                matchWicket = false
            } else {
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgWicket.setImageTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                )
                bindingBottom.textWicket.setTextColor(
                    activity.getResources().getColor(R.color.yellow_lgt)
                )

                matchWicket = true
            }
        }



        bindingBottom.submit!!.setOnClickListener {
            bottomSheetDialogNoti?.dismiss()

            submitNotificationSetting(
                model?.matchId!!.toInt(),
                matchStart,
                matchToss,
                matchResult,
                matchFifty,
                matchCentury,
                matchDoubleCentury,
                matchFour,
                matchSix,
                matchWicket
            )
            // checkOverlayPermission()
        }
    }


    fun observeSetSquad() {
        MainApplication.applicationInstance.squadData.observe(this, Observer {
            if (it) {
                Log.e(
                    "TAGSqud",
                    "observeSetSquad: " + MainApplication.applicationInstance.squadData.value
                )
                MainApplication.applicationInstance.squadData.value = false
                binding.pager.setCurrentItem(4, true)

                //binding.pager?.adapter?.notifyDataSetChanged()
            }
        })
    }

    // @RequiresApi(Build.VERSION_CODES.N)
    fun observeHomeList() {
        viewModel.homeMatchesLiveData.observe(this, Observer {

            if (!it.isNullOrEmpty()) {
                moreMatchList.addAll(it)

                moreMatchList.removeIf { it.matchId == match_id }

                showMoreDialog(moreMatchList)

            }
        })
    }


    fun observeLiveScore() {
        viewModel.getLiveScoreLiveData().observe(this, Observer {
            if (it != null) {
                isUiSet = true

                //    Log.e("TAG", "observeLiveScore ===> : ${it.matchId}")

                liveModel = it

                if (liveFragment.isAdded() && liveFragment.isResumed()) {

                    //   Log.e("TAG", "observeLiveScore livefra--- ===> : ${it.matchId}")

                    isUiSetOnetime = true

                    liveFragment.setData(it)

                }

                if (liveLineFragment.isAdded() && liveLineFragment.isResumed()) {

                    //    Log.e("TAG", "observeLiveScore liveline ===> : ${it.matchId}")
                    isUiSetOnetimeliveline = true

                    liveLineFragment.setData(it)
                }

                if (oversFragment.isAdded() && oversFragment.isResumed()) {

                    //  Log.e("TAG", "observeLiveScore liveline ===> : ${it.matchId}")
                    isUiSetOver = true

                    oversFragment.setData(it)
                }

                // liveFragment.setData(it)
                // liveLineFragment.setData(it)


            }
        })
    }


    private fun clickToVibrate() {
        val vibe = applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator
        //replace yourActivity.this with your own activity or if you declared a context you can write context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80) //80 represents the milliseconds (the duration of the vibration)
    }


    private fun observeExtras() {
        /*  viewModel!!.getLoaderLiveData().observe(this,
              { isLoading -> handleProgressLoader(isLoading!!) })
          viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
              Log.e("TAG", "onChanged: $s")
              handleError(s.toString())
          })*/
    }
}