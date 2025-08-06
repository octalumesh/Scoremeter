package com.cricbuzzplus.liveline.livedata.base

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityHomeBinding
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.utils.Constants
import com.cricbuzzplus.liveline.utils.StaticData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.Calendar

open class BaseFragment : Fragment(), View.OnClickListener {

    val TAG = BaseFragment::class.java.simpleName
    val REQ_IMAGE_PERM = 22
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var mDialog: AlertDialog? = null
    var madsDialog: AlertDialog? = null

    // lateinit var transferUtility : TransferUtility
    lateinit var activity: BaseActivity

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)
    // lateinit var mRewardedVideoAd: RewardedVideoAd
    //lateinit var db:FirebaseFirestore

    //var homeBinding: ActivityHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as BaseActivity
        // db= FirebaseFirestore.getInstance()
        //mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        //mRewardedVideoAd.loadAd(getString(R.string.video_ad_unit_id), adRequest)
    }


    /*  override fun onAttach(context: Context?) {
          super.onAttach(LocaleHelper.onAttach(context!!, "en"))

      }*/


    /** function will be implemented in Child classes whenever required  **/
    override fun onClick(v: View?) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    open fun isInternetConnection(): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
    }


    fun handleProgressLoader(isLoading: Boolean) {
//        if(homeBinding == null) {
//            homeBinding = ActivityHomeBinding.inflate(activity.layoutInflater)
//        }
//
//        if (homeBinding?.progressLayout!!.root == null) {
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            return
//        }
//
//        if (isLoading) {
//            homeBinding?.progressLayout!!.root.visibility = View.VISIBLE
//            activity.getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            );
//        } else {
//            homeBinding!!.progressLayout.root.visibility = View.GONE
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }
    }

    fun handleError(error: String) {
        if (!TextUtils.isEmpty(error)) {
            var message = error
            if (error.equals(StaticData.TIMEOUT)) {
                message = getString(R.string.timeout_error)
            } else if (error.equals(StaticData.UNDEFINED)) {
                message = getString(R.string.undefined_error)
            } else if (error.equals(StaticData.NODATA_FOUND))
                message = getString(R.string.no_data_found)
            showAlertDialog(message, "", "", getString(R.string.ok), true, null)
        }
    }

    /** In case no network is available, alert the user with a message **/
    fun showNetworkIssue() {

        showAlertDialog(
            getString(R.string.no_internet),
            getString(R.string.retry),
            getString(R.string.ok),
            "",
            true,
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> retryAction()
                    DialogInterface.BUTTON_NEGATIVE -> failureAccepted()
                }
            })
    }

    fun showAlertDialog(
        message: String?,
        positive: String,
        negative: String,
        neutral: String,
        cancalable: Boolean,
        listener: DialogInterface.OnClickListener?
    ) {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(message)
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive, listener)
        }
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative, listener)
        }
        if (!TextUtils.isEmpty(neutral)) {
            builder.setNeutralButton(neutral, listener)
        }
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
        mDialog = builder.create()

        mDialog!!.setOnShowListener(object : DialogInterface.OnShowListener {
            override fun onShow(dialog: DialogInterface?) {
                mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(activity, R.color.limeGreen));
                mDialog!!.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(activity, R.color.black));
            }
        })
        mDialog!!.show()
    }

    /** UserData has opted to retry API calling which failed earlier due to Network unavailability **/
    open fun retryAction() {

    }

    /** UserData has accepted API calling failure due to Network unavailability **/
    open fun failureAccepted() {

    }

    fun hideKeyBoard() {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity.findViewById<View>(android.R.id.content).windowToken,
                0
            )
        } catch (e: Exception) {
            printLog("BaseActivity", e.toString())
        }

    }

    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    /*fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }*/

    fun printLog(tag: String, log: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, log)
    }

    fun continueWithImageTasks() {
        /* CropImage.activity()
             .setGuidelines(CropImageView.Guidelines.ON)
             .setActivityTitle(getString(R.string.crop_image))
             .setCropShape(CropImageView.CropShape.RECTANGLE)
             .setCropMenuCropButtonTitle(getString(R.string.done))
             .start(activity)*/
    }

    var matchIdCricLiveData = MutableLiveData<Int>()
    var seriesIdCricLiveData = MutableLiveData<Int>()

    fun getMatchIdCricBuzzUpcoming(series: String, matchNo: String) {

        apiClientCricBuzz.getUpcomingCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        ) || matchItem?.seriesAdWrapper?.seriesName.toString()
                                            .contains(
                                                series
                                            )
                                    ) {

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                /*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*/

                                                if (match?.matchInfo?.matchDesc.toString().contains(
                                                        matchNo,
                                                        true
                                                    ) || match?.matchInfo?.matchDesc.toString()
                                                        .contains(
                                                            matchNo.replace("-", " "),
                                                            true
                                                        )
                                                ) {


                                                    matchIdCricLiveData.value =
                                                        match?.matchInfo?.matchId!!

                                                    /*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*/

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getMatchIdCricBuzzFinished(series: String, matchNo: String) {

        apiClientCricBuzz.getFinishedCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        ) || matchItem?.seriesAdWrapper?.seriesName.toString()
                                            .contains(
                                                series
                                            )
                                    ) {

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                /*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*/

                                                if (match?.matchInfo?.matchDesc.toString().contains(
                                                        matchNo,
                                                        true
                                                    ) || match?.matchInfo?.matchDesc.toString()
                                                        .contains(
                                                            matchNo.replace("-", " "),
                                                            true
                                                        )
                                                ) {


                                                    matchIdCricLiveData.value =
                                                        match?.matchInfo?.matchId!!

                                                    /*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*/

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })

    }

    fun getMatchIdCricBuzzLive(series: String, matchNo: String) {


        apiClientCricBuzz.getLiveCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        loop1@ for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                loop2@ for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        ) || matchItem?.seriesAdWrapper?.seriesName.toString()
                                            .contains(
                                                series
                                            )
                                    ) {

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            loop3@ for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                /*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*/

                                                if (match?.matchInfo?.matchDesc.toString().contains(
                                                        matchNo,
                                                        true
                                                    ) || match?.matchInfo?.matchDesc.toString()
                                                        .contains(
                                                            matchNo.replace("-", " "),
                                                            true
                                                        )
                                                ) {

                                                    matchIdCricLiveData.value =
                                                        match?.matchInfo?.matchId!!

                                                    break@loop3
                                                    break@loop2
                                                    break@loop1


                                                    /*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*/

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })

    }


    fun getSeriesIdCricBuzzUpcoming(series: String, matchNo: String) {

        apiClientCricBuzz.getUpcomingCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        )
                                    ) {

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                /*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*/

                                                if (match?.matchInfo?.matchDesc.equals(
                                                        matchNo,
                                                        true
                                                    ) || match?.matchInfo?.matchDesc.toString()
                                                        .contains(
                                                            matchNo.replace("-", " "),
                                                            true
                                                        )
                                                ) {


                                                    seriesIdCricLiveData.value =
                                                        match?.matchInfo?.seriesId!!

                                                    /*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*/

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesIdCricBuzzFinished(series: String, matchNo: String) {

        apiClientCricBuzz.getFinishedCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        )
                                    ) {

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                /*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*/

                                                if (match?.matchInfo?.matchDesc.equals(
                                                        matchNo,
                                                        true
                                                    ) || match?.matchInfo?.matchDesc.toString()
                                                        .contains(
                                                            matchNo.replace("-", " "),
                                                            true
                                                        )
                                                ) {


                                                    seriesIdCricLiveData.value =
                                                        match?.matchInfo?.matchId!!

                                                    /*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*/

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })

    }

    fun getSeriesIdCricBuzzLive(series: String, matchNo: String) {


        apiClientCricBuzz.getLiveCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->

                if (it != null) {

                    if (!it.typeMatches.isNullOrEmpty()) {

                        for (matchListType in it.typeMatches) {

                            if (!matchListType?.seriesMatches.isNullOrEmpty()) {

                                for (matchItem in matchListType?.seriesMatches!!) {

                                    if (matchItem?.seriesAdWrapper?.seriesName.equals(
                                            series,
                                            true
                                        )
                                    ) {

                                        seriesIdCricLiveData.value =
                                            matchItem?.seriesAdWrapper?.seriesId!!
                                        Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  seriesId -- ${matchItem?.seriesAdWrapper?.seriesId}"
                                        )

                                        /*if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                *//*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*//*

                                                if (matchNo.contains(
                                                        match?.matchInfo?.matchDesc.toString(),
                                                        true
                                                    )
                                                ) {

                                                    seriesIdCricLiveData.value = match?.matchInfo?.matchId!!

                                                    *//*Log.e(
                                                        "TAGPlayer",
                                                        "onCreateView: id -- ${match?.matchInfo?.matchId!!}  ,  match -- $matchNo"
                                                    )*//*

                                                }
                                            }
                                        }*/
                                    }
                                }
                            }

                        }

                    }

                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })

    }


    open fun handleFinalCroppedImage(imageUri: Uri) {}

    /*
     * Begins to upload the file specified by the file path to AWS
     */

    open fun fileUploadSuccess(imageKey: String) {}

    open fun fileUploadError() {}

    fun showToast(msg: String) {

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun replaceFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction =
            activity.supportFragmentManager.beginTransaction().replace(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun addFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction =
            activity.supportFragmentManager.beginTransaction().add(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }


    open fun openUpQuestionaireScreen() {

    }

    fun setSpannable(
        textView: TextView,
        message: String,
        matched_string: String,
        color: Int,
        underlineStatus: Boolean /*, setOnClickListener: OnClickListener*/
    ) {

        var spannableStr = SpannableStringBuilder(message)


        var start = message.indexOf(matched_string, 1)
        spannableStr.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            start + matched_string.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan = object : ClickableSpan() {

            override fun onClick(textView: View) {
                // Toast.makeText(textView.context,"clicked",Toast.LENGTH_LONG).show()
                //  setOnClickListener.onClick(textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = underlineStatus
            }
        }

        spannableStr.setSpan(
            clickableSpan,
            start,
            start + matched_string.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )


        textView.setLinkTextColor(color)
        textView.setText(spannableStr, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor =
            ContextCompat.getColor(textView.context, android.R.color.transparent)
    }

    fun getRangedArray(from: Int, to: Int, plusOneMore: Boolean): ArrayList<String> {
        var rangList: ArrayList<String> = ArrayList()
        for (i in from..to) {
            rangList.add("" + i);
            if (i == to && plusOneMore)
                rangList.add("" + i + "+")
        }
        return rangList
    }

    fun logOutFromApp() {
        showAlertDialog(
            getString(R.string.are_you_sure_to_logout),
            getString(R.string.yes),
            getString(R.string.no),
            "",
            true,
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        hitLogoutAPI()
                    }
                }
            })
    }


    open fun hitLogoutAPI() {
        // mPrefs?.clearUserDetails()
        restartApp()
    }

    fun restartApp() {
        val i = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.finish()
        startActivity(i)
    }

    fun withBody(s: String): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, "" + s)
    }

    fun withBody(s: Int): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, "" + s)
    }

    open fun getBodyFromFile(f: File, paramName: String): MultipartBody.Part? {
        return if (f == null) null else { //File file=new File(uri.getPath());
            val requestFile =
                RequestBody.create("image/*".toMediaTypeOrNull(), f)
            Log.e("ImageFile size", "" + f.length())
            // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            //  RequestBody formBody = new FormBody.Builder().add("search", "Jurassic Park").build();
            MultipartBody.Part.createFormData(paramName, f.name, requestFile)
        }
    }

    open fun setDateByPicker(edt: TextView) {

// Get Current Date
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val dateString =
                    String.format("%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year)
                edt.setText(dateString)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis())
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
