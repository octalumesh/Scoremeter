package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.PlayerProfileActivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment.*
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import java.util.ArrayList

class PlayerProfileActivity : BaseActivity() {

    lateinit var binding: PlayerProfileActivityBinding
    private lateinit var viewModel: LiveViewModel

    lateinit var playerInfoFragment: PlayerInfoFragment
    lateinit var playerBattingFragment: PlayerBattingFragment
    lateinit var playerBowlingFragment: PlayerBowlingFragment
    lateinit var playerCarrerFragment: PlayerCareerFragment
    lateinit var newsFragment: PlayerNewsFragment


    val tabArray = arrayListOf<String>()

    var fragmentList = ArrayList<Fragment>()

    var playerName = ""
    var playerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_player_profile)
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_profile)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@PlayerProfileActivity, R.color.colorPrimaryDark)
        }

        playerName = intent.getStringExtra("playerName").toString()
        playerId = intent.getIntExtra("playerId",0)

        Log.e("TAGPlayer", "onCreate: "+playerId )

        binding.playerName.setText(playerName)

        tabArray.add(getString(R.string.player_info))
        tabArray.add(getString(R.string.player_batting))
        tabArray.add(getString(R.string.player_bowling))
        tabArray.add(getString(R.string.player_career))
        tabArray.add(getString(R.string.player_news))


        binding.back.setOnClickListener {
            onBackPressed()
        }

        setUpViewPager()

    }

    override fun onBackPressed() {
       // super.onBackPressed()
        finish()
    }

    fun setUpViewPager(){

        val bundle = Bundle()
        bundle.putString("playerName", playerName)
        bundle.putInt("playerId", playerId)


        playerInfoFragment = PlayerInfoFragment()
        playerBattingFragment = PlayerBattingFragment()
        playerBowlingFragment = PlayerBowlingFragment()
        playerCarrerFragment = PlayerCareerFragment()
        newsFragment = PlayerNewsFragment()


        playerInfoFragment.setArguments(bundle)
        playerBattingFragment.setArguments(bundle)
        playerBowlingFragment.setArguments(bundle)
        playerCarrerFragment.setArguments(bundle)
        newsFragment.setArguments(bundle)



        fragmentList.add(playerInfoFragment)
        fragmentList.add(playerBattingFragment)
        fragmentList.add(playerBowlingFragment)
        fragmentList.add(playerCarrerFragment)
        fragmentList.add(newsFragment)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle,fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }

}