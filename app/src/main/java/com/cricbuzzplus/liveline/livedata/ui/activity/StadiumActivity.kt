package com.cricbuzzplus.liveline.livedata.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityStadiumBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.fragment.stadium.StadiumInfoFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.stadium.StadiumMatchesFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.stadium.StadiumStatsFragment
import java.util.ArrayList

class StadiumActivity : BaseActivity() {

    lateinit var binding : ActivityStadiumBinding

    lateinit var stadiumInfoFragment: StadiumInfoFragment
    lateinit var stadiumMatchesFragment: StadiumMatchesFragment
    lateinit var stadiumStatsFragment: StadiumStatsFragment

    val tabArray = arrayListOf<String>()

    var fragmentList = ArrayList<Fragment>()

    var stadiumName = ""
    var stadiumId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_stadium)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@StadiumActivity, R.color.colorPrimaryDark)
        }

        stadiumName = intent.getStringExtra("stadiumName").toString()
        stadiumId = intent.getIntExtra("stadiumId",0)

        binding.stadiumName.setText(stadiumName)

        tabArray.add(getString(R.string.stadium_info))
        tabArray.add(getString(R.string.stadium_matches))
        tabArray.add(getString(R.string.stadium_stats))


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
        bundle.putString("stadiumName", stadiumName)
        bundle.putInt("stadiumId", stadiumId)


        stadiumInfoFragment = StadiumInfoFragment()
        stadiumMatchesFragment = StadiumMatchesFragment()
        stadiumStatsFragment = StadiumStatsFragment()



        stadiumInfoFragment.setArguments(bundle)
        stadiumMatchesFragment.setArguments(bundle)
        stadiumStatsFragment.setArguments(bundle)



        fragmentList.add(stadiumInfoFragment)
        fragmentList.add(stadiumMatchesFragment)
        fragmentList.add(stadiumStatsFragment)

        val adapter = ViewPagerAdaptorNew(supportFragmentManager, lifecycle,fragmentList)


        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),resources.getColor(R.color.white))

        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabArray[position].toString()
        }.attach()

    }
}