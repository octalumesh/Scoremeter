package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityWithdrawBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.newresponse.KYCDetailsResponse
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class WithdrawActivity : BaseActivity() {
    lateinit var binding: ActivityWithdrawBinding
    private lateinit var viewModel: UsersViewModel

    lateinit var bottomSheetDialog: BottomSheetDialog

    var kycModel: KYCDetailsResponse? = null

    var type = ""
    var typeWithdraw = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@WithdrawActivity, R.color.colorPrimaryDark)
        }

        typeWithdraw = intent.getIntExtra("typeWithdraw", 1)

        hideKeyBoard()
        setObserver()

        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_upi)

        if (typeWithdraw == 2) {

            binding.totalAmount.setText("\u20B9 " + mPrefs.prefUserDetails?.referamount)
            binding.winningText.setText("Your Winning Points")

        } else {
            binding.totalAmount.setText("\u20B9 " + mPrefs.prefUserDetails?.amount)
            binding.winningText.setText("Your Winning Balance")
        }


        callKYCDocument()


        /*binding.upiRl.setOnClickListener {

            binding.upiRadio.isChecked = true
            binding.upiRl.setBackgroundResource(R.drawable.shape_rectangle_green)

            binding.bankRadio.isChecked = false
            binding.bankRl.setBackgroundResource(R.drawable.shape_rectangle_polls)

        }*/

        binding.upiRadio.setOnClickListener {

            type = "upi"

            binding.upiRadio.isChecked = true
            binding.upiRl.setBackgroundResource(R.drawable.shape_rectangle_green)

            binding.bankRadio.isChecked = false
            binding.bankRl.setBackgroundResource(R.drawable.shape_rectangle_polls)

        }

        /*binding.bankRl.setOnClickListener {

            binding.upiRadio.isChecked = false
            binding.upiRl.setBackgroundResource(R.drawable.shape_rectangle_polls)

            binding.bankRadio.isChecked = true
            binding.bankRl.setBackgroundResource(R.drawable.shape_rectangle_green)

        }*/

        binding.bankRadio.setOnClickListener {

            type = "account"
            binding.upiRadio.isChecked = false
            binding.upiRl.setBackgroundResource(R.drawable.shape_rectangle_polls)

            binding.bankRadio.isChecked = true
            binding.bankRl.setBackgroundResource(R.drawable.shape_rectangle_green)

        }

        binding.addUpi.setOnClickListener {

            showBottomSheet()

        }

        binding.addBank.setOnClickListener {
            val intent = Intent(this@WithdrawActivity, BankVerifyActivity::class.java)
            startActivity(intent)
        }


        binding.withdrawNow.setOnClickListener {

            if (isValid()) {

                callWithdraw(binding.withdrawAmount.text.trim().toString())
            }

        }


        binding.back.setOnClickListener {
            onBackPressed()
        }

    }

    fun isValid(): Boolean {

        if (kycModel == null) {
            showToast("Please Submit KYC Details.")
            return false
        }

        if (kycModel?.panVerify.isNullOrEmpty() || !kycModel?.panVerify.equals("Verified")) {

            showToast("Your PAN detail is not Verified yet.")
            return false

        }

        if (kycModel?.aadharVerify.isNullOrEmpty() || !kycModel?.aadharVerify.equals("Verified")) {

            showToast("Your Aadhaar detail is not Verified yet.")
            return false

        }

        if (binding.withdrawAmount.text.toString().isNullOrEmpty()) {
            binding.withdrawAmount.error = "please enter amount!"
            binding.withdrawAmount.requestFocus()
            return false
        }

        if (binding.withdrawAmount.text.trim().toString().toInt() < 100) {

            binding.withdrawAmount.error = "min. withdraw amount is \u20B9 100."
            binding.withdrawAmount.requestFocus()
            return false

        }

        if (typeWithdraw == 2) {

            if (binding.withdrawAmount.text.toString()
                    .toInt() > mPrefs?.prefUserDetails?.referamount!!
            ) {

                showToast("you don't have enough balance.")
                return false

            }

        } else {
            if (binding.withdrawAmount.text.toString()
                    .toInt() > mPrefs?.prefUserDetails?.amount!!
            ) {

                showToast("you don't have enough balance.")
                return false

            }
        }

        if (type.isNullOrEmpty()) {

            showToast("please select withdraw option")
            return false

        }

        if (type.equals("account")) {

            if (kycModel?.accountVerify.isNullOrEmpty() || !kycModel?.accountVerify.equals("Verified")) {

                showToast("Your Bank detail is not Verified yet.")
                return false

            } else {
                return true
            }

        }

        return true
    }


    fun showBottomSheet() {

        val verifyUpi = bottomSheetDialog.findViewById<TextView>(R.id.verifyUpi)
        val upiId = bottomSheetDialog.findViewById<EditText>(R.id.upiId)


        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (bottomSheetDialog.isShowing) {
        } else {

            bottomSheetDialog.show()

        }
        verifyUpi!!.setOnClickListener {

            if (!upiId?.text?.trim().toString().isNullOrEmpty()) {

                bottomSheetDialog.dismiss()

                callUploadUpi(upiId?.text?.trim().toString())
            } else {
                upiId?.error = "please enter UPI Id"
                upiId?.requestFocus()
            }

        }
    }

    fun callUploadUpi(
        upiId: String,
    ) {

        Log.e("TAG", "callUploadUpi:ddd " + upiId)

        if (checkForInternet(this)) {
            viewModel.uploadUpiDetails("Bearer " + mPrefs.prefAuthToken.toString(), upiId)
        }
    }

    fun callWithdraw(
        amount: String,
    ) {

        if (checkForInternet(this)) {

            if (typeWithdraw == 2){
                viewModel.withdrawalReferAmount(
                    "Bearer " + mPrefs.prefAuthToken.toString(),
                    kycModel?.id.toString(),
                    type,
                    amount
                )
            }
            else {
                viewModel.withdrawalAmount(
                    "Bearer " + mPrefs.prefAuthToken.toString(),
                    kycModel?.id.toString(),
                    type,
                    amount
                )
            }
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
        observeUpload()
        observeWithdraw()
        observeKycDetail()
    }


    private fun observeKycDetail() {

        viewModel.kycDocumetsLiveData.observe(this, Observer {

            if (it != null) {

                kycModel = it

                if (!it.upiNumber.isNullOrEmpty()) {
                    binding.upiId.setText(it.upiNumber)
                    binding.upiId.visibility = View.VISIBLE
                    binding.upiRadio.visibility = View.VISIBLE
                    binding.addUpi.visibility = View.GONE
                } else {
                    binding.upiRadio.visibility = View.GONE
                    binding.upiId.visibility = View.GONE
                    binding.addUpi.visibility = View.VISIBLE
                }


                if (!it.accountNumber.isNullOrEmpty()) {
                    binding.bankId.setText(it.accountNumber)
                    binding.bankRadio.visibility = View.VISIBLE
                    binding.bankId.visibility = View.VISIBLE
                    binding.addBank.visibility = View.GONE
                } else {
                    binding.bankRadio.visibility = View.GONE
                    binding.bankId.visibility = View.GONE
                    binding.addBank.visibility = View.VISIBLE
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

    private fun observeUpload() {

        viewModel.uploadUpiDetailLiveData.observe(this, Observer {

            if (it) {
                showToast("Successfully uploaded.")
                callKYCDocument()
            }

        })

    }

    private fun observeWithdraw() {

        viewModel.withdrawAmountLiveData.observe(this, Observer {

            if (it != null) {
                showToast("Successfully requested.")
                if (typeWithdraw == 2) {

                    binding.totalAmount.setText("\u20B9 " + it?.userDetails?.referamount)
                    binding.winningText.setText("Your Winning Points")

                } else {
                    binding.totalAmount.setText("\u20B9 " + it?.userDetails?.amount)
                    binding.winningText.setText("Your Winning Balance")
                }


                mPrefs.prefUserDetails = it.userDetails


            }

        })

    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }
}