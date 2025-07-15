package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityKycBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class KycActivity : BaseActivity() {

    lateinit var binding: ActivityKycBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kyc)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@KycActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()

        binding.verifyAadhaar.setOnClickListener {
            if (!binding.verifyAadhaar.text.equals("Verified")) {
                val intent = Intent(this@KycActivity, AadhaarVerifyActivity::class.java)
                startActivity(intent)
            }
        }

        binding.verifyPan.setOnClickListener {
            if (!binding.verifyPan.text.equals("Verified")) {
                val intent = Intent(this@KycActivity, PanDetailsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.verifyBank.setOnClickListener {
            if (!binding.verifyBank.text.equals("Verified")) {
                val intent = Intent(this@KycActivity, BankVerifyActivity::class.java)
                startActivity(intent)
            }
        }

        callKYCDocument()

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    fun callKYCDocument() {

        if (checkForInternet(this)) {
            viewModel.getKycDocumentDetails("Bearer " + mPrefs.prefAuthToken.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        callKYCDocument()
    }


    fun setObserver() {
        observeExtras()
        observeKycDetail()
    }

    private fun observeKycDetail() {

        viewModel.kycDocumetsLiveData.observe(this, Observer {

            if (it != null) {

                if (!it.aadharNumber.isNullOrEmpty()) {
                    binding.aadharNumber.setText(it.aadharNumber)
                    binding.verifyAadhaar.setText(it.aadharVerify)

                    if (it.aadharVerify.equals("Verified")){

                        binding.verifyAadhaar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verified_color)))
                        binding.verifyAadhaar.setTextColor(getColor(R.color.verified))
                    }

                }

                if (!it.panNumber.isNullOrEmpty()) {
                    binding.panNumber.setText(it.panNumber)
                    binding.verifyPan.setText(it.panVerify)

                    if (it.panVerify.equals("Verified")){

                        binding.verifyPan.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verified_color)))
                        binding.verifyPan.setTextColor(getColor(R.color.verified))
                    }

                }

                if (!it.bankName.isNullOrEmpty()) {
                    binding.bankAccount.setText(it.accountNumber)
                    binding.verifyBank.setText(it.accountVerify)

                    if (it.accountVerify.equals("Verified")){

                        binding.verifyBank.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verified_color)))
                        binding.verifyBank.setTextColor(getColor(R.color.verified))
                    }

                }


            }

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
        // super.onBackPressed()
        finish()
    }
}