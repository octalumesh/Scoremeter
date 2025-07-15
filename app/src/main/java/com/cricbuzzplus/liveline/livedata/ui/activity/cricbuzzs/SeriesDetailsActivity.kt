package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesDetailsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail.*
import java.util.ArrayList

class SeriesDetailsActivity : BaseActivity() {

    lateinit var binding : ActivitySeriesDetailsBinding

    val tabArray = arrayListOf<String>()
    var fragmentList = ArrayList<Fragment>()

    var seriesId = 0
    var indexP = 0
    var series = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_series_details)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SeriesDetailsActivity, R.color.colorPrimaryDark)
        }

        series = intent.getStringExtra("series").toString()
        seriesId = intent.getIntExtra("seriesId",0)


        if (intent.hasExtra("index")){
            indexP = intent.getIntExtra("index",0)
        }


        binding.series.setText("$series")


        tabArray.add(getString(R.string.series_matches))
        tabArray.add(getString(R.string.series_table))
        tabArray.add(getString(R.string.series_squad))
        tabArray.add(getString(R.string.series_stats))
        tabArray.add(getString(R.string.series_vanues))
        tabArray.add(getString(R.string.series_news))

        binding.back.setOnClickListener {
            onBackPressed()
        }

        val bundle = Bundle()
        bundle.putInt("seriesId", seriesId)
        bundle.putString("series", series)

        val seriesMatches = SeriesMatchesFragment()
        val seriesTable = SeriesTableFragment()
        val seriesSquad = SeriesSquadFragment()
        val seriesStats = SeriesStatesFragment()
        val seriesVenues = SeriesVenuesFragment()
        val seriesNews = SeriesNewsFragment()

        seriesMatches.setArguments(bundle)
        seriesTable.setArguments(bundle)
        seriesSquad.setArguments(bundle)
        seriesStats.setArguments(bundle)
        seriesVenues.setArguments(bundle)
        seriesNews.setArguments(bundle)




        fragmentList.add(seriesMatches)
        fragmentList.add(seriesTable)
        fragmentList.add(seriesSquad)
        fragmentList.add(seriesStats)
        fragmentList.add(seriesVenues)
        fragmentList.add(seriesNews)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle, fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.white_lgt), resources.getColor(R.color.white)
        )

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

        if (indexP != 0) {
            binding.pager.setCurrentItem(1, true)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()

        finish()
    }
}