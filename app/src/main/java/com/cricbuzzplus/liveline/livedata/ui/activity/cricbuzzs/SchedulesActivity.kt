package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySchedulesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.*
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class SchedulesActivity : BaseActivity() {

    lateinit var binding : ActivitySchedulesBinding


    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_schedules)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SchedulesActivity, R.color.colorPrimaryDark)
        }

        tabArray.add(getString(R.string.series_international))
        tabArray.add(getString(R.string.series_t20leagues))
        tabArray.add(getString(R.string.series_domestic))
        tabArray.add(getString(R.string.series_women))

        binding.back.setOnClickListener {
            onBackPressed()
        }

        val scheduleInternationalFragment = ScheduleInternationalFragment()
        val scheduleLeagueFragment = ScheduleLeagueFragment()
        val scheduleDomesticFragment = ScheduleDomesticFragment()
        val scheduleWomenFragment = ScheduleWomenFragment()


        fragmentList.add(scheduleInternationalFragment)
        fragmentList.add(scheduleLeagueFragment)
        fragmentList.add(scheduleDomesticFragment)
        fragmentList.add(scheduleWomenFragment)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle, fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white_lgt), resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }

    override fun onBackPressed() {
        //super.onBackPressed()

        finish()
    }
}