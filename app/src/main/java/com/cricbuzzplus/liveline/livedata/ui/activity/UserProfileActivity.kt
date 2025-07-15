package com.cricbuzzplus.liveline.livedata.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.UserProfileActivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.activity.wallet.WalletActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.profile.LeaderBoardFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.profile.MatchPredictionFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.profile.TossPredictionFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants

class UserProfileActivity : BaseActivity() {

    lateinit var binding: UserProfileActivityBinding
    private lateinit var viewModel: UsersViewModel

    lateinit var matchPredictionFragment: MatchPredictionFragment
    lateinit var tossPredictionFragment: TossPredictionFragment
    lateinit var leaderBoardBinding: LeaderBoardFragment

    val tabArray = arrayListOf<String>()

    private lateinit var firebaseAuth: FirebaseAuth


    var fragmentList = ArrayList<Fragment>()

    val loginResultLauncer =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val dataIntent: Intent? = result.data
                if (dataIntent != null) {
                    val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        dataIntent?.extras?.getString("data")
                    } else {
                        dataIntent?.extras?.getString("data")!!
                    }

                    if (data != null) {
                        callMyProfile()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_user_profile)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@UserProfileActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()


        firebaseAuth = FirebaseAuth.getInstance()
        tabArray.add(getString(R.string.user_toss_prediction))
        tabArray.add(getString(R.string.user_match_prediction))
        tabArray.add(getString(R.string.user_leaderboard))


        binding.back.setOnClickListener {
            onBackPressed()
        }


        binding.withdrawMoney.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, WalletActivity::class.java)
            startActivity(intent)
        }


        binding.logout.setOnClickListener {
            showAlertDialog(
                "Are you sure to logout!",
                "Yes",
                "cancel",
                "",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog?.dismiss()
                                var apiKey = mPrefs.prefApiToken

                                firebaseAuth.signOut()
                                //mPrefs.prefUserDetails = null
                                mPrefs.clearUserDetails()

                                mPrefs.prefApiToken = apiKey
                                onBackPressed()
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                                dialog?.dismiss()
                            }
                        }
                    }

                })
        }

        callMyProfile()

        setUpViewPager()

        binding.edit.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, UpdateProfileActivity::class.java)
            // startActivity(intent)
            loginResultLauncer.launch(intent)
        }

        binding.frameImg.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, UpdateProfileActivity::class.java)
            // startActivity(intent)
            loginResultLauncer.launch(intent)
        }

    }


    fun callMyProfile() {

        if (checkForInternet(this)) {
            viewModel.getMyProfile("Bearer " + mPrefs.prefAuthToken.toString())
        }
    }


    private fun setObserver() {
        observeProfile()
        observeExtras()
    }


    override fun onResume() {
        super.onResume()
        binding.points.setText(""+ (mPrefs.prefUserDetails?.referamount!! + mPrefs.prefUserDetails?.tossPoint!! +mPrefs.prefUserDetails?.points!!)+ " Pts.")
        binding.referEarn.setText("Refer & Earn \n(${mPrefs.prefUserDetails?.referamount})")
        binding.totalAmount.setText("Total Amount \n(${mPrefs.prefUserDetails?.amount})")
    }

    private fun observeProfile() {

        viewModel.userProfileLiveData.observe(this, Observer {

            binding.userName.setText("" + it.firstName)
            binding.points.setText("" + (it.referamount!! + it.tossPoint!! + it.points!!) + " Pts.")
            binding.referEarn.setText("Refer & Earn \n(${it.referamount})")
            binding.totalAmount.setText("Total Amount \n(${it.amount})")

            mPrefs.prefUserDetails = it

            if (it.points != null) {
                if (it.points >= 7 && it.points < 15) {
                    binding.matchZonePass.setText("Zone 1 match \n(pass - Rs.${it.prize?.firstPrize}/-)")
                }
                else if (it.points >= 15 && it.points < 20) {
                    binding.matchZonePass.setText("Zone 2 match \n(pass - Rs.${it.prize?.secondPrize}/-)")
                }
                else if (it.points >= 20 && it.points < 25) {
                    binding.matchZonePass.setText("Zone 3 match \n(pass - Rs.${it.prize?.thirdPrize}/-)")
                }
                else if (it.points >= 25) {
                    binding.matchZonePass.setText("Zone 4 match \n(pass - Rs.${it.prize?.fourthPrize}/-)")
                }else{
                    binding.matchZonePass.setText("Zone 1 match \n(No Zone pass)")
                }
            }

            if (it.tossPoint != null) {
                if (it.tossPoint >= 7 && it.tossPoint < 15) {
                    binding.tossZonePass.setText("Zone 1 toss \n(pass - Rs.${it.prize?.firstPrize}/-)")
                }
                else if (it.tossPoint >= 15 && it.tossPoint < 20) {
                    binding.tossZonePass.setText("Zone 2 toss \n(pass - Rs.${it.prize?.secondPrize}/-)")
                }
                else if (it.tossPoint >= 20 && it.tossPoint < 25) {
                    binding.tossZonePass.setText("Zone 3 toss \n(pass - Rs.${it.prize?.thirdPrize}/-)")
                }
                else if (it.tossPoint >= 25) {
                    binding.tossZonePass.setText("Zone 4 toss \n(pass - Rs.${it.prize?.fourthPrize}/-)")
                }else{
                    binding.tossZonePass.setText("Zone 1 toss \n(No Zone pass)")
                }
            }



            Glide.with(this).load(Constants.ImgURl + it?.profilepicture)
                .placeholder(R.mipmap.ic_launcher_round).into(binding.image)


            if (it.tossPred == 0) {
                binding.winTossPercent.setText("0%")
            } else {

                val tossPredPercent = (it.tossPass?.toFloat()!! / it.tossPred?.toFloat()!!) * 100

                binding.winTossPercent.setText("${tossPredPercent.toInt()} %")
            }

            if (it.matchPred == 0) {
                binding.winMatchPercent.setText("0%")
            } else {

                val matchPredPercent = (it.matchPass?.toFloat()!! / it.matchPred?.toFloat()!!) * 100

                binding.winMatchPercent.setText("${matchPredPercent.toInt()} %")
            }

            binding.winTotalToss.setText("${it.tossPass}/${it.tossPred}")
            binding.winTotalMatch.setText("${it.matchPass}/${it.matchPred}")

            if (it.matchPred == 0 && it.tossPred == 0) {

                binding.progress.setText("0%")
                binding.progressBar.setProgress(0, true)

            } else {

                val totalPredPercent =
                    ((it.matchPass!! + it.tossPass!!).toFloat() / (it.matchPred + it.tossPred).toFloat()) * 100

                binding.progress.setText("${totalPredPercent.toInt()} %")

                binding.progressBar.setProgress(totalPredPercent.toInt(), true)

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
        //super.onBackPressed()
        finish()
    }

    fun setUpViewPager() {

        val bundle = Bundle()
        bundle.putInt("userId", mPrefs.prefUserDetails?.id!!)

        tossPredictionFragment = TossPredictionFragment()
        tossPredictionFragment.setArguments(bundle)

        matchPredictionFragment = MatchPredictionFragment()
        matchPredictionFragment.setArguments(bundle)

       // leaderBoardBinding = LeaderBoardFragment()
       // leaderBoardBinding.setArguments(bundle)


        fragmentList.add(tossPredictionFragment)
        fragmentList.add(matchPredictionFragment)
       // fragmentList.add(leaderBoardBinding)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle, fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lite));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white),
            resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }
}