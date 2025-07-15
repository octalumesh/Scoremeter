package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityIccmensRankingBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.ranking.AllrounderMensFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.ranking.BattingMensRankingFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.ranking.BowlingMensFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.ranking.TeamMensFragment
import java.util.ArrayList

class ICCMensRankingActivity : BaseActivity() {

    lateinit var binding: ActivityIccmensRankingBinding

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_iccmens_ranking)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@ICCMensRankingActivity, R.color.colorPrimaryDark)
        }



        tabArray.add(getString(R.string.ranking_batting))
        tabArray.add(getString(R.string.ranking_bowling))
        tabArray.add(getString(R.string.ranking_allrounder))
        tabArray.add(getString(R.string.ranking_team))


        binding.back.setOnClickListener {
            onBackPressed()
        }

        if (fragmentList.isEmpty()) {

            val battingMens = BattingMensRankingFragment()
            val bowlingMens = BowlingMensFragment()
            val allrounderMens = AllrounderMensFragment()
            val teamMens = TeamMensFragment()


            fragmentList.add(battingMens)
            fragmentList.add(bowlingMens)
            fragmentList.add(allrounderMens)
            fragmentList.add(teamMens)
        }

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle, fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white_lgt), resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        binding.pager.offscreenPageLimit = fragmentList.size - 1

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }

    override fun onBackPressed() {
        //super.onBackPressed()

        finish()
    }

}