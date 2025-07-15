package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityWalletBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class WalletActivity : BaseActivity() {

    lateinit var binding: ActivityWalletBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@WalletActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
     //   setObserver()


        binding.totalPoints.setText(""+ mPrefs.prefUserDetails?.referamount)
        binding.totalAmount.setText("\u20B9 "+ mPrefs.prefUserDetails?.amount)


        binding.withdrawNow.setOnClickListener {
            val intent = Intent(this@WalletActivity, WithdrawActivity::class.java)
            intent.putExtra("typeWithdraw",1)
            startActivity(intent)
        }

        binding.kycApply.setOnClickListener {
            val intent = Intent(this@WalletActivity, KycActivity::class.java)
            startActivity(intent)
        }

        binding.transactionHistory.setOnClickListener {
            val intent = Intent(this@WalletActivity, RecentTransactionActivity::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.withdrawPoints.setOnClickListener {
            val intent = Intent(this@WalletActivity, WithdrawActivity::class.java)
            intent.putExtra("typeWithdraw",2)
            startActivity(intent)
        }


        if (mPrefs.prefUserDetails?.referamount!! >= 1000){
            binding.withdrawPoints.visibility = View.VISIBLE
        }else{
            binding.withdrawPoints.visibility = View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
        binding.totalPoints.setText(""+ mPrefs.prefUserDetails?.referamount)
        binding.totalAmount.setText("\u20B9 "+ mPrefs.prefUserDetails?.amount)
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        finish()
    }

}