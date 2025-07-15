package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityRecentTransactionBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.newresponse.WithdrawListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.RecentTransactionAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class RecentTransactionActivity : BaseActivity() {

    lateinit var binding: ActivityRecentTransactionBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recent_transaction)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@RecentTransactionActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()

        callWithdrawList()

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }


    fun callWithdrawList() {

        if (checkForInternet(this)) {
            viewModel.getWithdrawList("Bearer " + mPrefs.prefAuthToken.toString())
        }
    }


    fun setObserver() {
        observeExtras()
        observeWithdrawList()
    }

    private fun observeWithdrawList() {

        viewModel.withdrawListLiveData.observe(this, Observer {

            if (it != null) {

                binding.recyclerTransaction.adapter = RecentTransactionAdapter(it as ArrayList<WithdrawListResponseItem>,this)

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