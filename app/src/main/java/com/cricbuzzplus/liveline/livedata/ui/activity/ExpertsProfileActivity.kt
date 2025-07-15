package com.cricbuzzplus.liveline.livedata.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ExpertsProfileActivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.profile.MatchPredictionFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.profile.TossPredictionFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel

class ExpertsProfileActivity : BaseActivity() {

    lateinit var binding : ExpertsProfileActivityBinding
    private lateinit var viewModel: UsersViewModel

    lateinit var matchPredictionFragment: MatchPredictionFragment
    lateinit var tossPredictionFragment: TossPredictionFragment

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_experts_profile)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@ExpertsProfileActivity, R.color.colorPrimaryDark)
        }

        userId = intent.getIntExtra("userId",0)

        hideKeyBoard()
        setObserver()

        tabArray.add(getString(R.string.user_toss_prediction))
        tabArray.add(getString(R.string.user_match_prediction))

        binding.back.setOnClickListener {
            onBackPressed()
        }

        callExpertProfile()

        setUpViewPager()

    }

    private fun callExpertProfile(){

        if (checkForInternet(this)) {
            viewModel.getExpertProfile(userId)
        }
    }


    private fun setObserver(){
        observeProfile()
        observeExtras()
    }



    private fun observeProfile(){

        viewModel.expertProfileLiveData.observe(this, Observer {

            binding.userName.setText(""+it.firstName)
            binding.email.setText(""+it.email)

            Glide.with(this).load(""+it?.profilepicture).placeholder(R.mipmap.ic_launcher_round).into(binding.image)


            if (it.tossPred == 0){
                binding.winTossPercent.setText("0%")
            }else{

                val tossPredPercent =  (it.tossPass?.toFloat()!! / it.tossPred?.toFloat()!!) * 100

                binding.winTossPercent.setText("${tossPredPercent.toInt()} %")
            }

            if (it.matchPred == 0){
                binding.winMatchPercent.setText("0%")
            }else{

                val matchPredPercent =  (it.matchPass?.toFloat()!! / it.matchPred?.toFloat()!!) * 100

                binding.winMatchPercent.setText("${matchPredPercent.toInt()} %")
            }

            binding.winTotalToss.setText("${it.tossPass}/${it.tossPred}")
            binding.winTotalMatch.setText("${it.matchPass}/${it.matchPred}")

            if (it.matchPred == 0 && it.tossPred == 0){

                binding.progress.setText("0%")
                binding.progressBar.setProgress(0,true)

            }else{

                val totalPredPercent =  ((it.matchPass!! + it.tossPass!!).toFloat() / (it.matchPred + it.tossPred).toFloat()) * 100

                binding.progress.setText("${totalPredPercent.toInt()} %")

                binding.progressBar.setProgress(totalPredPercent.toInt(),true)

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
        super.onBackPressed()
        finish()
    }

    fun setUpViewPager(){

        val bundle = Bundle()
        bundle.putInt("userId", userId)


        tossPredictionFragment = TossPredictionFragment()
        tossPredictionFragment.setArguments(bundle)
        matchPredictionFragment = MatchPredictionFragment()
        matchPredictionFragment.setArguments(bundle)



        fragmentList.add(tossPredictionFragment)
        fragmentList.add(matchPredictionFragment)


        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle,fragmentList)

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lite));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }
}