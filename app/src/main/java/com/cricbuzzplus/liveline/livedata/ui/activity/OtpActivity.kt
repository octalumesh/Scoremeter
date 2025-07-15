package com.cricbuzzplus.liveline.livedata.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityOtpBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel

class OtpActivity : BaseActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var binding : ActivityOtpBinding

    var mobile =""
    var token =""
    var otp =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@OtpActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()

        mobile = intent.getStringExtra("mobile").toString()
        otp = intent.getIntExtra("otp",0)


        binding.otpView.setText(""+otp)
        binding.otpNumber.setText("Enter the OTP send to $mobile")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result.toString()

            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${token}", )

        })


        binding.submit.setOnClickListener {
            if (binding.otpView.text.toString().length < 6 ){
                binding.otpView.setError("please enter valid otp!")
                binding.otpView.requestFocus()
            }else{
                verifyOtp(mobile,binding.otpView.text.toString(),token)
            }
        }

    }

    fun verifyOtp(mobile:String,otp:String,fcmToken:String){

        if (checkForInternet(this)) {
            viewModel.verifyOtp(mobile,otp, fcmToken)
        }

    }


    private fun setObserver(){
        observeLogin()
        observeExtras()
    }


    private fun observeLogin(){

        viewModel.userLoginLiveData.observe(this, Observer {

            val i = Intent()
            val bundel = Bundle()
            bundel.putParcelable("data", it)
            i.putExtras(bundel)
            setResult(Activity.RESULT_OK, i)
            finish()

        })

    }


    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(this, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }

}