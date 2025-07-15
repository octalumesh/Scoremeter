package com.cricbuzzplus.liveline.livedata.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.SeriesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdapter
import com.cricbuzzplus.liveline.livedata.ui.fragment.PointTableFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.SeriesMatchFragment

class SeriesTabActivity : BaseActivity() {

    lateinit var binding :SeriesBinding

    var index = 0

    var teamAId = 0
    var teamBId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_series_tab)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_series_tab)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SeriesTabActivity, R.color.colorPrimaryDark)
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /////////////////get intent match id//////////////////////
        val series_id = intent.getIntExtra("seriesId",0)
        val series_name = intent.getStringExtra("seriesName")
        index = intent.getIntExtra("index",0)

        if (intent.hasExtra("teamAId")){
            teamAId = intent.getIntExtra("teamAId",0)
            teamBId = intent.getIntExtra("teamBId",0)
        }


        setupViewPager(series_id, series_name.toString())

        binding.back.setOnClickListener(View.OnClickListener {
            onBackPressed()
            finish()
        })
        binding.seriesName.setText(series_name.toString())
    }

    private fun setupViewPager( series_id: Int, series_name: String) {

        val bundle = Bundle()
        bundle.putInt("seriesId", series_id)
        bundle.putInt("teamAId", teamAId)
        bundle.putInt("teamBId", teamBId)
        bundle.putString("seriesName", series_name)

        val viewpagerAdapter = ViewPagerAdapter(supportFragmentManager)

        val seriesMatchesFragment = SeriesMatchFragment()
        seriesMatchesFragment.setArguments(bundle)
        viewpagerAdapter.addFragment(seriesMatchesFragment, "Matches")
        ///////////////////////////////////////////////////////////
        val seriesPointTableFragment = PointTableFragment()
        seriesPointTableFragment.setArguments(bundle)
        viewpagerAdapter.addFragment(seriesPointTableFragment, "Point Table")

        binding.viewpageridSeries.adapter = viewpagerAdapter


        //swipe page left to right than fragment change next show
        binding.tabsidSeries.setupWithViewPager( binding.viewpageridSeries)

        binding.tabsidSeries.setSelectedTabIndicatorColor(resources.getColor(R.color.yellow_lgt));
        binding.tabsidSeries.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.viewpageridSeries.post(
            Runnable {
                binding.viewpageridSeries.setCurrentItem(index)
            })


    }
}