package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeMainFragmentBinding
import com.cricbuzzplus.liveline.databinding.NotificationSettingsBinding
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.MatchListModel
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomeCricbuzzResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomepageItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.response.newresponse.AppCheckResponse
import com.cricbuzzplus.liveline.livedata.ui.activity.SeriesTabActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.*
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HomeMatchCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HomeNewsCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HomeNewsCricInnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.HomeMatchClickInterface
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterface
import com.cricbuzzplus.liveline.livedata.ui.services.PinTopInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeMainViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.utils.Constants
import com.cricbuzzplus.liveline.utils.SnapHelperByOne
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class HomeMainFragment : BaseFragment(), SeriesAdaptor.MyClickListener, OnClickInterface,
    PinTopInterface, NotificationInterface, HomeMatchClickInterface {

    lateinit var binding: HomeMainFragmentBinding
    private lateinit var viewModel: HomeMainViewModel

    var adapter: HomeListAdaptor? = null
    private var list: ArrayList<HomeMatchResponseItem> = arrayListOf()
    var listAd: ArrayList<SliderImage> = arrayListOf()

    private val serieslist: ArrayList<SeriesListResponseItem> = arrayListOf()
    var seriesadapter: SeriesAdaptor? = null

    private var timer: Timer? = null
    var isUISet = false

    var fcmToken = ""

    var listVideo = arrayListOf<String>()

    var bottomSheetDialog: BottomSheetDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeMainViewModel::class.java)
        binding = HomeMainFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        listVideo.add("hghg")
        listVideo.add("hghg")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcmToken = task.result.toString()

            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${fcmToken}")

        })

        // bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        // bottomSheetDialog.setContentView(R.layout.bottom_sheet_notification)

        /*binding.recyclerFeatureVideo.adapter = FeatureVideosAdapter(listVideo,requireContext(),object : OnClickInterface{
            override fun onClick(newsId: Int) {
            }
        })

        binding.recyclerTrendingVideo.adapter = TrendingVideosAdapter(listVideo,requireContext(),object : OnClickInterface{
            override fun onClick(newsId: Int) {
            }
        })*/

        Log.e("TAG138", "onCreateView: ")



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollView.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > 2000) {
                binding.moveTop.visibility = View.VISIBLE
            } else if (scrollY == 0) {
                binding.moveTop.visibility = View.GONE
            } else {
                binding.moveTop.visibility = View.GONE
            }


        }

        binding.moveTop.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }

        // callNewsList()


        callHomeCricBuzz()
        callHomeData()
    }

    fun callHomeCricBuzz() {

        if (isInternetConnection()) {

            Log.e("TAG138", "onCreateView:call ")
             viewModel.getHomeCricBuzz()


        }

    }

    fun callTestApi(){

        /*val retrofit = Retrofit.Builder()
            .baseUrl(Constants.CricBuzzURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

// Create the Retrofit API interface instance
        val retrofitAPI: ApiInterface = retrofit.create(ApiInterface::class.java)

// Define the header value (replace with the actual user value you want to pass)
        val headerValue = Constants.cricHeader

// Make the API call
        val call: Call<HomeCricbuzzResponse> = retrofitAPI.getHomeCrickBuzzs(headerValue)

        call.enqueue(object : Callback<HomeCricbuzzResponse> {
            override fun onResponse(
                call: Call<HomeCricbuzzResponse>,
                response: Response<HomeCricbuzzResponse>
            ) {
                Log.e("TAG ======", "onResponse: " + response.body())
                val homeCricbuzzResponse: HomeCricbuzzResponse? = response.body()
                if (homeCricbuzzResponse != null && !homeCricbuzzResponse?.homepage.isNullOrEmpty()) {
                    //binding.news.setText(homeCricbuzzResponse?.homepage?.get(0)?.stories?.headline)


                    Log.e("TAG138", "onCreateView:observe2 ")
                    val groupedItems = homeCricbuzzResponse.homepage?.groupBy {
                        it?.stories?.analyticsTag?.substringBefore("-")
                    }

                    Log.e("TAG138", "onCreateView:observe3 ")
                    // Convert the grouped map to a list of lists
                    val listNews = groupedItems?.values?.map { it.toList() }

                    Log.e("TAG138", "onCreateView:observe4 ")


                    binding.recyclerNews.adapter =
                        HomeNewsCricAdapter(listNews as List<List<HomepageItem>>, activity)
                    // binding.recyclerNews.adapter = HomeNewsCricInnerAdapter(data.homepage as ArrayList<HomepageItem>,activity)
                    Log.e("TAG138", "onCreateView:observe5 ")


                }
            }

            override fun onFailure(call: Call<HomeCricbuzzResponse>, t: Throwable) {
                Log.e("TAG ===", "onFailure: " + t.message)
            }
        })*/
    }

    fun callHomeData() {
        if (timer == null) {
            timer = Timer()
        }
        if (isInternetConnection()) {

            viewModel.getHomeMatches()
        }
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isInternetConnection()) {
                    // recyclerViewMaincls();
                    if (isUISet) {
                        viewModel.getHomeMatches()
                    }
                }
            }
        }, 3000, 3000)

    }


    fun callNewsList() {
        viewModel.getNewsList()
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


    override fun onDestroyView() {
        super.onDestroyView()

        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
            isUISet = false
        }
        model = null

        adapter = null

    }

    override fun onResume() {
        super.onResume()
        //adapter = null
        //binding.indicator.removeIndicators()
        seriesadapter = null
        //callHomeData()
        //   callSeries()
    }

    private fun setObservers() {
        observeExtras()
        observeNews()
        observeMatch()
        observeSeriesId()
        observeNotificationSet()
        observeNotificationGet()
        observeNotificationStatus()
        observeHomeCricbuzz()

    }

    private fun observeHomeCricbuzz() {
        viewModel.homeCricBuzzLiveData.observe(viewLifecycleOwner, Observer { data ->

            Log.e("TAG138", "onCreateView:observe1 ")

            if (data != null && !data.homepage.isNullOrEmpty()) {

                Log.e("TAG138", "onCreateView:observe2 ")
                val groupedItems = data.homepage.groupBy {
                    it?.stories?.analyticsTag?.substringBefore("-")
                }

                Log.e("TAG138", "onCreateView:observe3 ")
                // Convert the grouped map to a list of lists
                val listNews = groupedItems.values.map { it.toList() }

                Log.e("TAG138", "onCreateView:observe4 ")


                binding.recyclerNews.adapter =
                    HomeNewsCricAdapter(listNews as List<List<HomepageItem>>, activity)
                // binding.recyclerNews.adapter = HomeNewsCricInnerAdapter(data.homepage as ArrayList<HomepageItem>,activity)
                Log.e("TAG138", "onCreateView:observe5 ")


            }

        })
    }

    private fun observeNews() {
        viewModel.getNewsListLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.recyclerNews.adapter =
                    NewsAdapter(it as ArrayList<NewsListResponseItem>, activity, this)

            }

        })
    }

    val listener = object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val action = e.action
            if (binding.recyclerHome.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> rv.parent
                        .requestDisallowInterceptTouchEvent(true)
                }
                return false
            } else {
                when (action) {
                    MotionEvent.ACTION_MOVE -> rv.parent
                        .requestDisallowInterceptTouchEvent(true)
                }
                //  binding.recyclerHome.removeOnItemTouchListener(this)
                return false
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    private fun observeMatch() {
        viewModel.getHomeMatchesLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.recyclerHome.setHasFixedSize(true)

                list = it as ArrayList<HomeMatchResponseItem>

                binding.progressBar.visibility = View.GONE


                if (mPrefs.prefMatchList != null) {
                    if (!mPrefs.prefMatchList?.matchList.isNullOrEmpty()) {

                        for (matchId in mPrefs.prefMatchList?.matchList!!) {

                            // Log.e("TAGmatchId", "observeMatch: "+matchId )

                            val index = list.indexOfFirst { it.matchId == matchId }

                            // If the item was found, remove it and add it to the front of the list
                            if (index >= 0) {
                                val item = list.removeAt(index)
                                list.add(0, item)
                            }

                        }


                        val listMatch = mPrefs.prefMatchList?.matchList!!

                        for (matchId in listMatch) {

                            if (list.any { it.matchId == matchId }) {
                                // Log.e("TAGmatchId", "observeMatch: " + matchId)
                            } else {
                                //  Log.e("TAGmatchId", "observeMatch: not match .....")

                                listMatch.remove(matchId)

                                val matchListModel = MatchListModel(listMatch)

                                mPrefs.prefMatchList = matchListModel
                            }

                        }

                    }
                }


                if (adapter == null) {
                    adapter =
                        HomeListAdaptor(list, activity, this, mPrefs.prefMatchList, this, this)
                    binding.recyclerHome.setAdapter(adapter)
                    val linearSnapHelper = SnapHelperByOne()
                    linearSnapHelper.attachToRecyclerView(binding.recyclerHome)
                    binding.indicator.attachTo(binding.recyclerHome, false)

                } else {
                    adapter?.updateDta(list, mPrefs.prefMatchList)

                }
                isUISet = true

            }

        })
    }


    fun observeSeriesId() {

        seriesIdCricLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val intent = Intent(context, SeriesDetailsActivity::class.java)
                intent.putExtra("series", series)
                intent.putExtra("seriesId", it)
                intent.putExtra("index", indexP)
                startActivity(intent)
            }
        })

    }


    var series = ""
    var indexP = 0


    override fun onClick(item: HomeMatchResponseItem, index: Int) {

        series = item.series.toString()
        indexP = index


        if (item.matchStatus.equals("Upcoming")) {
            getSeriesIdCricBuzzUpcoming(item.series.toString(), item.matchs.toString())
        } else if (item.matchStatus.equals("Finished")) {
            getSeriesIdCricBuzzFinished(item.series.toString(), item.matchs.toString())
        } else if (item.matchStatus.equals("Live")) {
            getSeriesIdCricBuzzLive(item.series.toString(), item.matchs.toString())
        }

    }


    override fun onItemClick(item: SeriesListResponseItem) {
        // Log.e(TAG, "onItemClick: " + item)
        val intent = Intent(activity, SeriesTabActivity::class.java)
        intent.putExtra("seriesId", item.seriesId)
        intent.putExtra("seriesName", item.series)
        startActivity(intent)
    }

    private fun observeExtras() {
        /* viewModel!!.getLoaderLiveData().observe(this,
             { isLoading -> handleProgressLoader(isLoading!!) })*/
        /*viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

    private fun observeNotificationSet() {
        viewModel.getNotifySettingsLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showToast("notification settings set.")
            }
        })
    }

    private fun observeNotificationStatus() {
        viewModel.notifySettingsStatusLiveData.observe(viewLifecycleOwner, Observer {
            if (!it) {
                if (model != null) {
                    // Log.e(TAG, "observeNotificationStatus: "+model?.matchId )
                    showBottomSheet(model!!)
                }
            }
        })
    }

    private fun observeNotificationGet() {
        viewModel.notifySettingsDataLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showBottomSheetNotify(it)
            }
        })
    }


    override fun onClick(newsId: Int) {

        val bundle = bundleOf("newsId" to newsId)

        activity.findNavController(R.id.nav_host_fragment_activity_home)
            .navigate(R.id.redirect_news_details, bundle)
    }

    override fun onPinClick(matchId: Int, pin: Boolean) {

        // Log.e("TAGmatchId", "observeMatch: Toast "+matchId )
        if (pin) {
            if (mPrefs.prefMatchList != null) {
                if (!mPrefs.prefMatchList?.matchList.isNullOrEmpty()) {
                    if (!mPrefs.prefMatchList?.matchList!!.contains(matchId)) {


                        val list = mPrefs.prefMatchList?.matchList

                        if (list?.size == 5) {

                            showToast("You already pinned 5 matches.")

                        } else {
                            list?.add(matchId)

                            val matchListModel = MatchListModel(list)

                            mPrefs.prefMatchList = matchListModel
                            showToast("Pinned to Top.")
                        }


                    } else {
                        showToast("Already Pin")
                    }
                } else {
                    val list = arrayListOf<Int>()

                    list.add(matchId)

                    val matchListModel = MatchListModel(list)

                    mPrefs.prefMatchList = matchListModel
                    showToast("Pinned to Top.")
                }
            } else {
                val list = arrayListOf<Int>()

                list.add(matchId)

                val matchListModel = MatchListModel(list)

                mPrefs.prefMatchList = matchListModel
                showToast("Pinned to Top.")
            }
        } else {
            val listMatch = mPrefs.prefMatchList?.matchList!!
            listMatch.remove(matchId)

            val matchListModel = MatchListModel(listMatch)

            mPrefs.prefMatchList = matchListModel
            showToast("Unpinned from Top.")
        }
    }


    var model: HomeMatchResponseItem? = null

    override fun onClickNotify(model: HomeMatchResponseItem) {
        if (checkPermission()) {
            binding.progressBar.visibility = View.VISIBLE
            this.model = model
            //  Log.e(TAG, "onClickNotify: vcvcvvfgdfgdffdgfdgfdgffgfgffdfgfdgffg", )
            getNotificationSetting(model.matchId!!)
        } else {
            requestPermission()
        }

    }


    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(
                    activity,
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
                Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                /*  val intent =
                      Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                  startActivityForResult(intent, 2)*/
                // main logic
            } else {
                showToast("You need to allow notification permissions")

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    requestPermission()
                } else {
                    // requestPermission();
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity.getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                }

            }
        }

    }


    fun showBottomSheet(model: HomeMatchResponseItem) {

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

        bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        bottomSheetDialog?.setContentView(bindingBottom.root)



        bottomSheetDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (bottomSheetDialog?.isShowing!!) {
            bottomSheetDialog?.dismiss()
        } else {

            bottomSheetDialog?.show()

        }

        binding.progressBar.visibility = View.GONE

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
            bottomSheetDialog?.dismiss()

            submitNotificationSetting(
                model.matchId!!,
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

            /* matchStart?.isChecked = false
             matchToss!!.isChecked = false
             matchResult!!.isChecked = false
             matchFifty!!.isChecked = false
             matchCentury!!.isChecked = false
             matchDoubleCentury!!.isChecked = false
             matchFour!!.isChecked = false
             matchSix!!.isChecked = false
             matchWicket!!.isChecked = false*/

            // checkOverlayPermission()
        }
    }


    fun showBottomSheetNotify(model: NotificationSettingsResponse) {

        val bindingBottom = DataBindingUtil.inflate<NotificationSettingsBinding>(
            layoutInflater,
            R.layout.bottom_sheet_notification,
            null,
            false
        )

        bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        bottomSheetDialog?.setContentView(bindingBottom.root)


        var matchStart = model.matchStart.toBoolean()
        var matchToss = model.toss.toBoolean()
        var matchResult = model.result.toBoolean()
        var matchFifty = model.fifty.toBoolean()
        var matchCentury = model.hundrade.toBoolean()
        var matchDoubleCentury = model.twohundrade.toBoolean()
        var matchFour = model.four.toBoolean()
        var matchSix = model.six.toBoolean()
        var matchWicket = model.wicket.toBoolean()

        bottomSheetDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (bottomSheetDialog?.isShowing == true) {
            bottomSheetDialog?.dismiss()
        } else {

            bottomSheetDialog?.show()

        }

        binding.progressBar.visibility = View.GONE

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
            bottomSheetDialog?.dismiss()

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


}