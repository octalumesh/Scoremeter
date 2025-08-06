package com.cricbuzzplus.liveline.livedata.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.model.KeyModel
import com.cricbuzzplus.liveline.mPrefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity : BaseActivity() {

    lateinit var mAppUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.colorPrimaryDark)

        if (mPrefs.prefDarkCheck) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            // recreate()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //  recreate()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result.toString()
            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${token}")
        })

        mAppUpdateManager = AppUpdateManagerFactory.create(this)
        //printKeyHash()
        getData()

        Handler(Looper.myLooper()!!).postDelayed({
            checkUpdate()
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }, 1000)
    }


    fun checkUpdate() {
        mAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                requestUpdate(appUpdateInfo)
            } else {
                Log.e(ContentValues.TAG, "No Update available")
                //   showToast("No Update available")
                // redirect();
            }
        }.addOnFailureListener { e ->
            Log.e(
                ContentValues.TAG,
                "No Update availablesfdsdf " + e.message
            )
        }
    }

    fun requestUpdate(appUpdateInfo: AppUpdateInfo?) {
        try {
            mAppUpdateManager.startUpdateFlowForResult( // Pass the intent that is returned by 'getAppUpdateInfo()'.
                appUpdateInfo!!,  // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                AppUpdateType.FLEXIBLE,  // The current activity making the update request.
                this,  // Include a request code to later monitor this update request.
                11098
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
            Log.e(ContentValues.TAG, "No Update available asas" + e.message)
        }
    }

    private val installStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                Log.e("InAppUpdate--->d", "mAppUpdateManager $mAppUpdateManager")
                mAppUpdateManager.completeUpdate()
                //showCompleteUPdate();
            }
            if (state.installStatus() == InstallStatus.CANCELED) {
                //  redirect()
            }
            if (state.installStatus() == InstallStatus.FAILED) {
                Toast.makeText(
                    this@SplashActivity,
                    "install Error : " + state.installErrorCode(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onStart() {
        super.onStart()
        mAppUpdateManager.registerListener(installStateUpdatedListener)
        mAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // if (!MainApplication.Companion.getOneTimeUPdate()) {
                try {
                    //     MainApplication.Companion.setOneTimeUPdate(true)
                    mAppUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this@SplashActivity,
                        11098
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                    Log.e(ContentValues.TAG, "No Update availabledd " + e.message)
                }
                //  }
            }
        }
    }

    override fun onStop() {
        mAppUpdateManager.unregisterListener(installStateUpdatedListener)
        super.onStop()
    }


    fun printKeyHash() {

        // Add code to print out the key hash
        try {
            val info: PackageInfo = packageManager.getPackageInfo("com.cricbuzzplus.liveline", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures!!) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun getData() {
        FirebaseFirestore.getInstance().collection("liveapikey").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("TAG", "Listen failed.", e)
                //   showToast(e.message.toString())
                return@addSnapshotListener
            }
            if (snapshot != null) {
                for (dc in snapshot.documentChanges) {
                    val keyModel = dc.document.toObject(KeyModel::class.java) as KeyModel
                    mPrefs.prefApiToken = "270a45b49e775380cd3ca724f3473913"//keyModel.key
                    //  redirect()
                }
            }
        }
    }
}