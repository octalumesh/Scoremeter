package com.cricbuzzplus.liveline.livedata.base

import android.Manifest
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySplashBinding
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.utils.Constants
import com.cricbuzzplus.liveline.utils.StaticData
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.Calendar

public open class BaseActivity : AppCompatActivity(), View.OnClickListener {

    //  val TAG = BaseActivity::class.java.simpleName
    val REQ_IMAGE_PERM = 22
    lateinit var progressDialog: ProgressDialog

    //lateinit var mRewardedVideoAd: RewardedVideoAd

    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var mDialog: AlertDialog? = null

    //lateinit var db:FirebaseFirestore
    lateinit var handler: Handler

    //lateinit var transferUtility : TransferUtility
    // var isAspect : Boolean = false
    lateinit var splashBinding: ActivitySplashBinding

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use an activity context to get the rewarded video instance.
        // mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        // initAwsImageUploading()
        // db= FirebaseFirestore.getInstance()
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        handler = Handler(Looper.getMainLooper())
    }


    fun initAwsImageUploading() {
        // Initializes TransferUtility, always do this before using it.
        //  transferUtility = AWSFileUploading.getTransferUtility(this@BaseActivity)!!
    }

    /** function will be implemented in Child classes whenever required  **/
    override fun onClick(v: View?) {}

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                handleFinalCroppedImage(result.uri)
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Cropping failed: " + result.error, Toast.LENGTH_LONG).show()
//            }
//        }*/
//    }

    fun withBody(s: String): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, "" + s)
    }

    open fun isInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
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

    fun handleProgressLoader(isLoading: Boolean) {
        if (splashBinding.progressLayout.root == null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            return
        }

        if (isLoading) {
            splashBinding.progressLayout.root.visibility = View.VISIBLE
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            splashBinding.progressLayout.root.visibility = View.GONE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
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
            showAlertDialog(message, "", "", getString(R.string.ok), null)
        }
    }

    /** In case no network is available, alert the user with a message **/
    fun showNetworkIssue() {

        showAlertDialog(
            getString(R.string.no_internet),
            getString(R.string.retry),
            getString(R.string.ok),
            "",
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
        listener: DialogInterface.OnClickListener?) {

        val builder = AlertDialog.Builder(this)
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
                mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.limeGreen));
                mDialog!!.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.black));
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
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
        } catch (e: Exception) {
            printLog("BaseActivity", e.toString())
        }

    }

    fun openKeyBoard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, InputMethodManager.SHOW_FORCED, 0)
        } catch (e: Exception) {
            printLog("BaseActivity", e.toString())
        }

    }

    /*  fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
          val safeClickListener = SafeClickListener {
              onSafeClick(it)
          }
          setOnClickListener(safeClickListener)
      }*/

    fun printLog(tag: String, log: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, log)
    }


    var seriesIdCricLiveData = MutableLiveData<Int>()

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

                                        seriesIdCricLiveData.value = matchItem?.seriesAdWrapper?.seriesId!!

                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        /*if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                *//*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*//*

                                                if (match?.matchInfo?.matchDesc.equals(
                                                        matchNo,
                                                        true
                                                    )
                                                ) {


                                                    seriesIdCricLiveData.value = match?.matchInfo?.seriesId!!

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

                                        seriesIdCricLiveData.value = matchItem?.seriesAdWrapper?.seriesId!!
                                        /*Log.e(
                                            "TAGPlayer",
                                            "onCreateView:  true  series -- $series  ,  match -- $matchNo"
                                        )*/

                                        /*if (!matchItem?.seriesAdWrapper?.matches.isNullOrEmpty()) {

                                            for (match in matchItem?.seriesAdWrapper?.matches!!) {

                                                *//*Log.e(
                                                    "TAGPlayer",
                                                    "onCreateView:  match -- ${match?.matchInfo?.matchDesc}"
                                                )*//*

                                                if (match?.matchInfo?.matchDesc.equals(
                                                        matchNo,
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

                                        seriesIdCricLiveData.value = matchItem?.seriesAdWrapper?.seriesId!!
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun replaceFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction = supportFragmentManager.beginTransaction().replace(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun addFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction = supportFragmentManager.beginTransaction().add(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    open fun openUpQuestionaireScreen() {

    }

    fun setTimeFromPicker(textView: TextView) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val picker = TimePickerDialog(textView.context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                val hour = hourOfDay % 12
                textView.setText(java.lang.String.format("%02d:%02d %s", if (hour == 0) 12 else hour,
                    minute, if (hourOfDay < 12) "am" else "pm"))

            }

        }, 7, 30, false)

        picker.setTitle("Setect time")
        picker.show()
    }


    fun logOutFromApp() {
        showAlertDialog(getString(R.string.are_you_sure_to_logout), getString(R.string.yes), getString(R.string.no), "", DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    hitLogoutAPI()
                }
            }
        })
    }

    open fun hitLogoutAPI() {

        // any api call is here otherwise restart//
        mPrefs.clearUserDetails()
        restartApp()
    }

    fun restartApp() {
        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(i)
    }

    fun enableLoadingBar(enable: Boolean) {
        if (enable) {
            loadProgressBar(null, getString(R.string.Loading), false)
        } else {
            dismissProgressBar()
        }
    }

    fun showSnackBar(root: View?, msg: String) {
        if (root != null)
            Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }


    fun loadProgressBar(title: String?, message: String, cancellable: Boolean) {
        if (progressDialog == null && !this.isFinishing) {
            progressDialog = ProgressDialog.show(this, title, message, false, cancellable)
            progressDialog.setContentView(R.layout.custom_progress)
            progressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
    }
    /*  fun getLoginType(type : SocialLoginType) : String{
          when(type){
              SocialLoginType.FACEBOOK-> return StaticData.LOGINTYPE_FACEBOOK
              SocialLoginType.GOOGLE-> return StaticData.LOGINTYPE_GOOGLE
              SocialLoginType.TRUECALLER-> return StaticData.LOGINTYPE_TRUECALLER
              else-> return StaticData.LOGINTYPE_MOBILE
          }
      }
  */

    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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


    fun setDrawableBackground(layout: View, drawable: Int) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable(ContextCompat.getDrawable(this, drawable))
        } else {
            layout.setBackground(ContextCompat.getDrawable(this, drawable))
        }
    }
}
