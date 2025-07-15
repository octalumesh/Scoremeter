package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesSquadBinding
import com.cricbuzzplus.liveline.databinding.FragmentSeriesNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerItemSquad
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerSquadCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class SeriesSquadActivity : BaseActivity() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: ActivitySeriesSquadBinding

    var seriesId = 0
    var teamId = 0
    var teamName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_series_squad)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SeriesSquadActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObservers()

        seriesId = intent.getIntExtra("seriesId",0)
        teamId = intent.getIntExtra("teamId",0)

        teamName = intent.getStringExtra("teamName").toString()

        binding.team.setText(teamName)

        binding.back.setOnClickListener {
            onBackPressed()
        }


        callSeries()
    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesPlayer(seriesId,teamId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    private fun observeSeries(){

        viewModel.seriesPlayersLiveData.observe(this, Observer{

            if (it != null){

                if (!it.player.isNullOrEmpty() ) {

                    binding.recyclerSeries.adapter = PlayerSquadCricAdapter(it.player as ArrayList<PlayerItemSquad>,this@SeriesSquadActivity)

                }else{
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    binding.recyclerSeries.visibility = View.GONE
                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.recyclerSeries.visibility = View.GONE
            }

        })

    }



    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }

}