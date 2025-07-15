package com.cricbuzzplus.liveline.livedata.ui.activity

import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityForgotPasswordBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel

class ForgotPasswordActivity : BaseActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var binding: ActivityForgotPasswordBinding

    var loginType =""
    var otpSendTo =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@ForgotPasswordActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.ivPasswordToggle.setOnClickListener {
            val selectionStart = binding.etPassword.selectionStart
            val selectionEnd = binding.etPassword.selectionEnd

            if (binding.etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                // Show password
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_baseline_eye_24)
            } else {
                // Hide password
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_baseline_eye_off_24)
            }

            // Restore cursor position after transformation
            binding.etPassword.setSelection(selectionStart, selectionEnd)
        }

        binding.ivPasswordToggle1.setOnClickListener {
            val selectionStart = binding.etCnfmPassword.selectionStart
            val selectionEnd = binding.etCnfmPassword.selectionEnd

            if (binding.etCnfmPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                // Show password
                binding.etCnfmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPasswordToggle1.setImageResource(R.drawable.ic_baseline_eye_24)
            } else {
                // Hide password
                binding.etCnfmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPasswordToggle1.setImageResource(R.drawable.ic_baseline_eye_off_24)
            }

            // Restore cursor position after transformation
            binding.etCnfmPassword.setSelection(selectionStart, selectionEnd)
        }

        binding.sendOtp.setOnClickListener {

            if (isValidMobileEmail()){

                otpSendTo = binding.etNumberEmailOtp.text.trim().toString()

                if (loginType.equals("email")){

                    callSendOtpEmail(binding.etNumberEmailOtp.text.trim().toString())

                }else if (loginType.equals("number")){
                    callSendOtpMobile(binding.etNumberEmailOtp.text.trim().toString())
                }

            }

        }


        binding.verifyOtp.setOnClickListener {

            if (binding.otpView.text.toString().length < 6 ){
                binding.otpView.setError("please enter valid otp!")
                binding.otpView.requestFocus()
            }else{
                if (loginType.equals("email")){

                    callVerifyOtpEmail(binding.etNumberEmailOtp.text.trim().toString(),binding.otpView.text.toString())

                }else if (loginType.equals("number")){
                    callVerifyOtpMobile(binding.etNumberEmailOtp.text.trim().toString(),binding.otpView.text.toString(),"ww")
                }
            }

        }


        binding.submit.setOnClickListener {

            if (isValidMobilePassword()){
                if (loginType.equals("email")){

                    callPasswordEmail(binding.etNumberEmailOtp.text.trim().toString(),binding.etPassword.text?.trim().toString())

                }else if (loginType.equals("number")){
                    callPasswordMobile(binding.etNumberEmailOtp.text.trim().toString(),binding.etPassword.text?.trim().toString())
                }
            }

        }


    }


    fun callSendOtpMobile(mobile: String) {

        if (checkForInternet(this)) {
            viewModel.sendOtpForgetPasswordmobile(mobile)
        }
    }

    fun callSendOtpEmail(email: String) {

        if (checkForInternet(this)) {
            viewModel.sendOtpForgetPasswordemail(email)
        }
    }

    fun callVerifyOtpMobile(mobile: String,otp:String,token:String) {

        if (checkForInternet(this)) {
            viewModel.verifyOtp(mobile,otp,token)
        }
    }

    fun callVerifyOtpEmail(email: String,otp: String) {

        if (checkForInternet(this)) {
            viewModel.emailverifyOtp(email,otp)
        }
    }

    fun callPasswordMobile(mobile: String,password:String) {

        if (checkForInternet(this)) {
            viewModel.forgetPasswordnumber(mobile,password)
        }
    }

    fun callPasswordEmail(email: String,password: String) {

        if (checkForInternet(this)) {
            viewModel.forgetPasswordemail(email,password)
        }
    }


    private fun isValidMobileEmail(): Boolean {


        if (binding.etNumberEmailOtp.text.trim()
                .isNullOrEmpty() || binding.etNumberEmailOtp.text.trim().length < 10
        ) {

            binding.etNumberEmailOtp.error = "please enter correct email or number"
            binding.etNumberEmailOtp.requestFocus()
            return false

        } else {

            if (binding.etNumberEmailOtp.text?.matches(("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").toRegex())!!) {

                loginType = "email"
            } else if (binding.etNumberEmailOtp.text?.matches("[0-9]{10}".toRegex())!!) {
                loginType = "number"
            } else {
                binding.etNumberEmailOtp.error = "please enter valid email or number"
                binding.etNumberEmailOtp.requestFocus()
                return false
            }

        }


        return true
    }

    private fun isValidMobilePassword(): Boolean {



         if (binding.etPassword.text.toString().trim()
                 .isNullOrEmpty() || binding.etPassword.text.toString().trim().length < 6
         ) {
             binding.etPassword.error = "please enter valid password"
             binding.etPassword.requestFocus()
             return false
         }

        if (binding.etCnfmPassword.text.toString().trim()
                .isNullOrEmpty() || binding.etCnfmPassword.text.toString().trim().length < 6
        ) {
            binding.etCnfmPassword.error = "please enter confirm password"
            binding.etCnfmPassword.requestFocus()
            return false
        }


        if (!binding.etCnfmPassword.text.toString().trim().equals(binding.etPassword.text.toString().trim())
        ) {
            binding.etCnfmPassword.error = "confirm password not matched."
            binding.etCnfmPassword.requestFocus()
            return false
        }


        return true
    }



    fun setObserver(){

        observeOtp()
        observeOtpSubmit()
        observePassword()

        observeExtras()
    }

    private fun observeOtp() {

        viewModel.otpForgotPasswordLiveData.observe(this, Observer {

            binding.sendOtpCard.visibility = View.GONE
            binding.otpCard.visibility = View.VISIBLE

            binding.otpNumber.setText("Enter the OTP send to $otpSendTo")

        })

    }

    private fun observeOtpSubmit() {

        viewModel.forgotPasswordLiveData.observe(this, Observer {

            showToast("Password changed, Login now.")
            onBackPressed()


        })

    }

    private fun observePassword() {

        viewModel.userLoginLiveData.observe(this, Observer {


            binding.otpCard.visibility = View.GONE
            binding.submitPasswordCard.visibility = View.VISIBLE

            binding.numberMobile.setText("$otpSendTo")

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}