package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
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
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.NotificationSettingsBinding
import com.cricbuzzplus.liveline.databinding.UpcomingFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.MatchListModel
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.NotificationSettingsResponse
import com.cricbuzzplus.liveline.livedata.response.UpcomingResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.UpcomingMatchAdapter
import com.cricbuzzplus.liveline.livedata.ui.services.NotificationInterfaceUpcoming
import com.cricbuzzplus.liveline.livedata.ui.services.PinTopInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UpcomingViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Exception

class UpcomingFragment : BaseFragment() ,PinTopInterface,NotificationInterfaceUpcoming{

    lateinit var binding: UpcomingFragmentBinding
     lateinit var viewModel: UpcomingViewModel
     var appContext: Context? = null

    var adapter : UpcomingMatchAdapter? =  null

    var allActive = true
    var t20Active = false
    var odiActive = false
    var testActive = false

    private val list: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val t20List: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val odiList: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val testList: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val t10List: ArrayList<UpcomingResponseItem> = arrayListOf()

    private val listFirstTime: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val t20ListFirstTime: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val odiListFirstTime: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val testListFirstTime: ArrayList<UpcomingResponseItem> = arrayListOf()
    private val t10ListFirstTime: ArrayList<UpcomingResponseItem> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (appContext == null) appContext = context.applicationContext
    }


    var fcmToken = ""
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)
        binding = UpcomingFragmentBinding.inflate(inflater, container, false)

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

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.share.setOnClickListener(View.OnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ground Live Line")
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        })

        buttonsCls()

        getMatches()
    }

    override fun onStart() {
        super.onStart()

        getMatches()
    }
   /* override fun onResume() {
        super.onResume()
        getMatches()
        *//*if (!list.isNullOrEmpty()){
            adapter = null
            if (adapter == null) {
                adapter = UpcomingMatchAdapter(list, context)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(list)
            }
        }
        else{
            adapter = null
            getMatches()
        }*//*
    }

    override fun onStop() {
        super.onStop()
        adapter = null
        list.clear()
    }*/

    fun getMatches(){
       /* if (checkForInternet(appContext!!)){


        }*/
        viewModel.getUpcomingMatches()
    }


    private fun buttonsCls() {


        binding.btnUpcomingT20.setOnClickListener(View.OnClickListener {

            adapter =  null

            t20Active = true
            allActive = false
            odiActive = false
            testActive = false

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = t20List.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = t20List.removeAt(index)
                            t20List.add(0, item)
                        }

                    }

                }
            }

            if (adapter == null) {
                adapter = UpcomingMatchAdapter(t20List, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(t20List, mPrefs.prefMatchListUpcoming)
            }

        })

        binding.btnUpcomingAllmatches.setOnClickListener(View.OnClickListener {
            adapter = null

            t20Active = false
            allActive = true
            odiActive = false
            testActive = false

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = list.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = list.removeAt(index)
                            list.add(0, item)
                        }

                    }

                }
            }


            if (adapter == null) {
                adapter = UpcomingMatchAdapter(list, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(list, mPrefs.prefMatchListUpcoming)
            }
        })
        binding.btnUpcomingOdi.setOnClickListener(View.OnClickListener {

            adapter = null

            t20Active = false
            allActive = false
            odiActive = true
            testActive = false

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = odiList.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = odiList.removeAt(index)
                            odiList.add(0, item)
                        }

                    }

                }
            }


            if (adapter == null) {
                adapter = UpcomingMatchAdapter(odiList, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                adapter?.updateList(odiList, mPrefs.prefMatchListUpcoming)
            }

        })
        binding.btnUpcomingTest.setOnClickListener(View.OnClickListener {
            adapter = null

            t20Active = false
            allActive = false
            odiActive = false
            testActive = true

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.white))

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = testList.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = testList.removeAt(index)
                            testList.add(0, item)
                        }

                    }

                }
            }

            if (adapter == null) {
                adapter = UpcomingMatchAdapter(testList, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {

                adapter?.updateList(testList, mPrefs.prefMatchListUpcoming)
            }
          //  recyclerViewMainclsTest()
        })

       /* binding.btnUpcomingT10.setOnClickListener(View.OnClickListener {
            adapter = null



            if (t10List.isNullOrEmpty()) {
                if (adapter == null) {
                    adapter = UpcomingMatchAdapter(t10List, context)
                    binding.recyclerviewUpcoming1.setAdapter(adapter)
                } else {
                    // adapter?.notifyItemChanged(updateIndex)
                    adapter?.updateList(t10List)
                }
            }
            //  recyclerViewMainclsTest()
        })*/
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


    private fun setObservers(){
        observeExtras()
        observeUpcoming()

        observeNotificationSet()
        observeNotificationGet()
        observeNotificationStatus()
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
                    Log.e(TAG, "observeNotificationStatus: "+model?.matchId )
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

    private fun observeUpcoming() {
        viewModel.getUpcomingMatchesLiveData().observe(viewLifecycleOwner, Observer {

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))

            if (it != null){

                list.clear()
                t20List.clear()
                odiList.clear()
                testList.clear()

                listFirstTime.clear()
                t20ListFirstTime.clear()
                odiListFirstTime.clear()
                testListFirstTime.clear()

                list.addAll(it)
                listFirstTime.addAll(it)


                if (mPrefs.prefMatchListUpcoming != null) {
                    if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                        for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                            // Log.e("TAGmatchId", "observeMatch: "+matchId )

                            val index = list.indexOfFirst { it.matchId == matchId }

                            // If the item was found, remove it and add it to the front of the list
                            if (index >= 0) {
                                val item = list.removeAt(index)
                                list.add(0, item)
                            }

                        }


                        val listMatch = mPrefs.prefMatchListUpcoming?.matchList!!

                        for (matchId in listMatch) {

                            if (list.any { it.matchId == matchId }) {
                                // Log.e("TAGmatchId", "observeMatch: " + matchId)
                            } else {
                                //  Log.e("TAGmatchId", "observeMatch: not match .....")

                                listMatch.remove(matchId)

                                val matchListModel = MatchListModel(listMatch)

                                mPrefs.prefMatchListUpcoming = matchListModel
                            }

                        }

                    }
                }

                if (adapter == null) {
                    adapter = UpcomingMatchAdapter(list, activity,this, mPrefs.prefMatchListUpcoming,this)
                    binding.recyclerviewUpcoming1.setAdapter(adapter)
                } else {
                    // adapter?.notifyItemChanged(updateIndex)
                    adapter?.updateList(list, mPrefs.prefMatchListUpcoming)
                }

                for (match in list){

                    if (match.matchType.equals("T20")){
                        t20List.add(match)
                        t20ListFirstTime.add(match)
                    }else if (match.matchType.equals("ODI")){
                        odiList.add(match)
                        odiListFirstTime.add(match)
                    }else if (match.matchType.equals("Test")){
                        testList.add(match)
                        testListFirstTime.add(match)
                    }else if (match.matchType.equals("T10")){
                        t10List.add(match)
                        t10ListFirstTime.add(match)
                    }

                }

            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        list.clear()
        t20List.clear()
        odiList.clear()
        testList.clear()

        model = null
        listFirstTime.clear()
        t20ListFirstTime.clear()
        odiListFirstTime.clear()
        testListFirstTime.clear()
    }


    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(activity,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.getDataLoadErrorLiveData().observe(activity, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })
    }

    override fun onPinClick(matchId: Int, pin: Boolean) {

        if (pin) {
            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {
                    if (!mPrefs.prefMatchListUpcoming?.matchList!!.contains(matchId)) {


                        val list = mPrefs.prefMatchListUpcoming?.matchList

                        if (list?.size == 5) {

                            showToast("You already pinned 5 matches.")

                        } else {
                            list?.add(matchId)

                            val matchListModel = MatchListModel(list)

                            mPrefs.prefMatchListUpcoming = matchListModel
                            showToast("Pinned to Top.")
                            reSetAdapter(pin)
                        }


                    } else {
                        showToast("Already Pin")
                    }
                } else {
                    val list = arrayListOf<Int>()

                    list.add(matchId)

                    val matchListModel = MatchListModel(list)

                    mPrefs.prefMatchListUpcoming = matchListModel
                    showToast("Pinned to Top.")
                    reSetAdapter(pin)
                }
            } else {
                val list = arrayListOf<Int>()

                list.add(matchId)

                val matchListModel = MatchListModel(list)

                mPrefs.prefMatchListUpcoming = matchListModel

                showToast("Pinned to Top.")

                reSetAdapter(pin)
            }
        }else{
            val listMatch = mPrefs.prefMatchListUpcoming?.matchList!!
            listMatch.remove(matchId)

            val matchListModel = MatchListModel(listMatch)

            mPrefs.prefMatchListUpcoming = matchListModel

            showToast("Unpinned from Top.")

            reSetAdapter(pin)
        }

    }

    fun reSetAdapter(pin: Boolean){

        if (allActive){
            adapter = null

            if (!pin){
                list.clear()
                list.addAll(listFirstTime)
            }

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = list.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = list.removeAt(index)
                            list.add(0, item)
                        }

                    }

                }
            }


            if (adapter == null) {
                adapter = UpcomingMatchAdapter(list, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(list, mPrefs.prefMatchListUpcoming)
            }
        }
        else if (t20Active){
            adapter = null

            if (!pin){
                t20List.clear()
                t20List.addAll(t20ListFirstTime)
            }

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = t20List.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = t20List.removeAt(index)
                            t20List.add(0, item)
                        }

                    }

                }
            }



            if (adapter == null) {
                adapter = UpcomingMatchAdapter(t20List, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(t20List, mPrefs.prefMatchListUpcoming)
            }
        }
        else if (odiActive){
            adapter = null

            if (!pin){
                odiList.clear()
                odiList.addAll(odiListFirstTime)
            }

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = odiList.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = odiList.removeAt(index)
                            odiList.add(0, item)
                        }

                    }

                }
            }




            if (adapter == null) {
                adapter = UpcomingMatchAdapter(odiList, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(odiList, mPrefs.prefMatchListUpcoming)
            }
        }
        else if (testActive){
            adapter = null

            if (!pin){
                testList.clear()
                testList.addAll(testListFirstTime)

            }

            if (mPrefs.prefMatchListUpcoming != null) {
                if (!mPrefs.prefMatchListUpcoming?.matchList.isNullOrEmpty()) {

                    for (matchId in mPrefs.prefMatchListUpcoming?.matchList!!) {

                        // Log.e("TAGmatchId", "observeMatch: "+matchId )

                        val index = testList.indexOfFirst { it.matchId == matchId }

                        // If the item was found, remove it and add it to the front of the list
                        if (index >= 0) {
                            val item = testList.removeAt(index)
                            testList.add(0, item)
                        }

                    }

                }
            }



            if (adapter == null) {
                adapter = UpcomingMatchAdapter(testList, activity,this, mPrefs.prefMatchListUpcoming,this)
                binding.recyclerviewUpcoming1.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(testList, mPrefs.prefMatchListUpcoming)
            }
        }

    }

    var model: UpcomingResponseItem? = null

    override fun onClickNotify(model: UpcomingResponseItem) {
        if (checkPermission()) {
            handleProgressLoader(true)
            this.model = model
            getNotificationSetting(model.matchId!!)
        }else{
            requestPermission()
        }
    }

    fun showBottomSheet(model: UpcomingResponseItem) {

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

        handleProgressLoader(false)

        bindingBottom.linearMatchStart.setOnClickListener {
            if (matchStart){
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.gray))

                matchStart = false
            }else{
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))

                bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchStart = true
            }
        }

        bindingBottom.linearToss.setOnClickListener {
            if (matchToss){
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.gray))

                matchToss = false
            }else{
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))

                bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchToss = true
            }
        }

        bindingBottom.linearResult.setOnClickListener {
            if (matchResult){
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.gray))

                matchResult = false
            }else{
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))

                bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchResult = true
            }
        }

        bindingBottom.linear50.setOnClickListener {
            if (matchFifty){
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFifty = false
            }else{
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchFifty = true
            }
        }

        bindingBottom.linear100.setOnClickListener {
            if (matchCentury){
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.gray))

                matchCentury = false
            }else{
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchCentury = true
            }
        }

        bindingBottom.linear200.setOnClickListener {
            if (matchDoubleCentury){
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.gray))

                matchDoubleCentury = false
            }else{
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchDoubleCentury = true
            }
        }

        bindingBottom.linear4.setOnClickListener {
            if (matchFour){
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFour = false
            }else{
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchFour = true
            }
        }

        bindingBottom.linear6.setOnClickListener {
            if (matchSix){
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.gray))

                matchSix = false
            }else{
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchSix = true
            }
        }

        bindingBottom.linearWicket.setOnClickListener {
            if (matchWicket){
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.gray))

                matchWicket = false
            }else{
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

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

        handleProgressLoader(false)

        if (matchStart){
            bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }else{
            bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.gray))
        }

        if (!matchToss){
            bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchResult){
            bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchFifty){
            bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))
        }

        if (!matchCentury){
            bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchDoubleCentury){
            bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchFour){
            bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchSix){
            bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }

        if (!matchWicket){
            bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
            bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
            bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.gray))

        }else{
            bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
            bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
            bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

        }


        bindingBottom.linearMatchStart.setOnClickListener {
            if (matchStart){
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.gray))

                matchStart = false
            }else{
                bindingBottom.linearMatchStart.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgMatchStart.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.textMatchStart.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchStart = true
            }
        }

        bindingBottom.linearToss.setOnClickListener {
            if (matchToss){
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.gray))

                matchToss = false
            }else{
                bindingBottom.linearToss.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgToss.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))

                bindingBottom.textToss.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchToss = true
            }
        }

        bindingBottom.linearResult.setOnClickListener {
            if (matchResult){
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.gray))

                matchResult = false
            }else{
                bindingBottom.linearResult.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgResult.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))

                bindingBottom.textResult.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchResult = true
            }
        }

        bindingBottom.linear50.setOnClickListener {
            if (matchFifty){
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFifty = false
            }else{
                bindingBottom.linear50.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img50.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text50.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchFifty = true
            }
        }

        bindingBottom.linear100.setOnClickListener {
            if (matchCentury){
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.gray))

                matchCentury = false
            }else{
                bindingBottom.linear100.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img100.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text100.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchCentury = true
            }
        }

        bindingBottom.linear200.setOnClickListener {
            if (matchDoubleCentury){
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.gray))

                matchDoubleCentury = false
            }else{
                bindingBottom.linear200.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img200.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text200.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchDoubleCentury = true
            }
        }

        bindingBottom.linear4.setOnClickListener {
            if (matchFour){
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.gray))

                matchFour = false
            }else{
                bindingBottom.linear4.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img4.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text4.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchFour = true
            }
        }

        bindingBottom.linear6.setOnClickListener {
            if (matchSix){
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.gray))

                matchSix = false
            }else{
                bindingBottom.linear6.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.img6.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.text6.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

                matchSix = true
            }
        }

        bindingBottom.linearWicket.setOnClickListener {
            if (matchWicket){
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_unselected)
                bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray)))
                bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.gray))

                matchWicket = false
            }else{
                bindingBottom.linearWicket.setBackgroundResource(R.drawable.notify_roung_selected)
                bindingBottom.imgWicket.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_lgt)))
                bindingBottom.textWicket.setTextColor(activity.getResources().getColor(R.color.yellow_lgt))

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

    fun checkPermission():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                return false
            }
            return true
        }else{
            return true
        }
    }

    fun requestPermission(){
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

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
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


}