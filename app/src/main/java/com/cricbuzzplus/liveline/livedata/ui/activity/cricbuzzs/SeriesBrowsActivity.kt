package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesBrowsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.SeriesDomesticFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.SeriesInternationalFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.SeriesT20LeaguesFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.SeriesWomenFragment
import java.util.ArrayList

class SeriesBrowsActivity : BaseActivity() {


    lateinit var binding : ActivitySeriesBrowsBinding

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_series_brows)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SeriesBrowsActivity, R.color.colorPrimaryDark)
        }

        tabArray.add(getString(R.string.series_international))
        tabArray.add(getString(R.string.series_t20leagues))
        tabArray.add(getString(R.string.series_domestic))
        tabArray.add(getString(R.string.series_women))

        binding.back.setOnClickListener {
            onBackPressed()
        }

        val seriesInternational = SeriesInternationalFragment()
        val seriesLeague = SeriesT20LeaguesFragment()
        val seriesDomestic = SeriesDomesticFragment()
        val seriesWomen = SeriesWomenFragment()


        fragmentList.add(seriesInternational)
        fragmentList.add(seriesLeague)
        fragmentList.add(seriesDomestic)
        fragmentList.add(seriesWomen)

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