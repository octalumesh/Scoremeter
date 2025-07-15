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
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentLiveHomeBinding
import com.cricbuzzplus.liveline.databinding.NotificationSettingsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.MatchListModel
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.NotificationSettingsResponse
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.SeriesDetailsActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.HomeListAdaptor
import com.cricbuzzplus.liveline.livedata.ui.interfaces.HomeMatchClickInterface
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterface
import com.cricbuzzplus.liveline.livedata.ui.services.PinTopInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeMainViewModel
import com.cricbuzzplus.liveline.mPrefs
import java.util.*


class LiveHomeFragment : BaseFragment(), PinTopInterface, NotificationInterface,
    HomeMatchClickInterface {

    private lateinit var viewModel: HomeMainViewModel

    lateinit var binding: FragmentLiveHomeBinding
    private var timer: Timer? = null

    var adapter: HomeListAdaptor? = null
    var liveList = arrayListOf<HomeMatchResponseItem>()

    var isUISet = false
    var fcmToken = ""
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeMainViewModel::class.java)
        binding = FragmentLiveHomeBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcmToken = task.result.toString()

            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${fcmToken}")

        })

        bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_notification)

        callHomeData()

        return binding.root
    }




    fun callHomeData() {
        /* if (timer == null) {
             timer = Timer()
         }*/
        if (isInternetConnection()) {
            viewModel.getHomeMatches()
        }
        /* timer!!.scheduleAtFixedRate(object : TimerTask() {
             override fun run() {
                 if (isInternetConnection()) {
                     // recyclerViewMaincls();
                     if (isUISet) {
                         viewModel.getHomeMatches()
                     }
                 }
             }
         }, 2000, 2000)*/

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


    private fun setObservers() {
        observeMatch()
        observeNotificationSet()
        observeNotificationGet()
        observeNotificationStatus()
        observeSeriesId()
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
                    Log.e(TAG, "observeNotificationStatus: " + model?.matchId)
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


    override fun onStop() {
        super.onStop()
        //adapter = null
        //  isUISet = false
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }
        model = null
    }

    private fun observeMatch() {
        viewModel.getHomeMatchesLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.progressBar.visibility = View.GONE
                liveList.clear()
                for (list in it) {
                    if (list.matchStatus.equals("Live")) {
                        liveList?.add(list)
                    }
                }

                setAdapter()
                /*if (adapter == null) {
                    adapter = HomeListAdaptor(it, activity)
                    // binding.recyclerHome.setHasFixedSize(true)
                    // ViewCompat.setNestedScrollingEnabled(binding.recyclerHome, false);
                    binding.recyclerMatchesHome.setAdapter(adapter)


                } else {
                    adapter?.updateDta(it)

                }*/


            }

        })
    }

    fun setAdapter() {
        if (!liveList.isNullOrEmpty()) {

            if (mPrefs.prefMatchList != null) {
                if (!mPrefs.prefMatchList?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchList?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = liveList.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = liveList.removeAt(index)
                            liveList.add(0, item)
                        }

                    }

                }
            }


            if (adapter == null) {
                adapter =
                    HomeListAdaptor(liveList, activity, this, mPrefs.prefMatchList, this, this)
                binding.recyclerMatchesHome.adapter = adapter
                isUISet = true
            } else {
                adapter?.updateDta(
                    liveList as ArrayList<HomeMatchResponseItem>,
                    mPrefs.prefMatchList
                )
            }
        } else {
            if (!isUISet) {
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.recyclerMatchesHome.visibility = View.GONE
            }
        }
    }

    override fun onPinClick(matchId: Int, pin: Boolean) {

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

                            adapter = null
                            callHomeData()
                        }


                    } else {
                        showToast("Already Pin")
                    }
                } else {
                    val list = arrayListOf<Int>()

                    list.add(matchId)

                    val matchListModel = MatchListModel(list)

                    mPrefs.prefMatchList = matchListModel
                    adapter = null
                    callHomeData()
                }
            } else {
                val list = arrayListOf<Int>()

                list.add(matchId)

                val matchListModel = MatchListModel(list)

                mPrefs.prefMatchList = matchListModel
                adapter = null
                callHomeData()
            }
        } else {
            val listMatch = mPrefs.prefMatchList?.matchList!!
            listMatch.remove(matchId)

            val matchListModel = MatchListModel(listMatch)

            mPrefs.prefMatchList = matchListModel
            adapter = null
            callHomeData()
        }

    }

    var model: HomeMatchResponseItem? = null

    override fun onClickNotify(model: HomeMatchResponseItem) {
        if (checkPermission()) {
            binding.progressBar.visibility = View.VISIBLE
            this.model = model
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