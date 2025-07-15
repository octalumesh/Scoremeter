package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityBrowsTeamDetailBinding
import com.cricbuzzplus.liveline.databinding.ActivityBrowsTeamsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.browsteams.*
import com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment.*
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class BrowsTeamDetailActivity : BaseActivity() {

    lateinit var binding : ActivityBrowsTeamDetailBinding

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    var teamId =0
    var teamName =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_brows_team_detail)

        teamName = intent.getStringExtra("teamName").toString()
        teamId = intent.getIntExtra("teamId",0)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@BrowsTeamDetailActivity, R.color.colorPrimaryDark)
        }


        binding.team.setText(teamName)

        tabArray.add(getString(R.string.team_schedule))
        tabArray.add(getString(R.string.team_result))
        tabArray.add(getString(R.string.team_news))
        tabArray.add(getString(R.string.team_players))
        tabArray.add(getString(R.string.team_stats))


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
        bundle.putString("teamName", teamName)
        bundle.putInt("teamId", teamId)


        val teamScheduleFragment = TeamScheduleFragment()
        val teamResultFragment = TeamResultFragment()
        val teamNewsFragment = TeamNewsFragment()
        val teamPlayersFragment = TeamPlayersFragment()
        val teamStatsFragment = TeamStatsFragment()


        teamScheduleFragment.setArguments(bundle)
        teamResultFragment.setArguments(bundle)
        teamNewsFragment.setArguments(bundle)
        teamPlayersFragment.setArguments(bundle)
        teamStatsFragment.setArguments(bundle)



        fragmentList.add(teamScheduleFragment)
        fragmentList.add(teamResultFragment)
        fragmentList.add(teamNewsFragment)
        fragmentList.add(teamPlayersFragment)
        fragmentList.add(teamStatsFragment)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle,fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white_lgt), resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }

}