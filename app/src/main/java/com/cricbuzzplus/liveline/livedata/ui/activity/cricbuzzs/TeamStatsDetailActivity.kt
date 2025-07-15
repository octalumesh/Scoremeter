package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesStatsDetailBinding
import com.cricbuzzplus.liveline.databinding.ActivityTeamStatsDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchtypeItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ValuesItemStats
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.StatsDetailListAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.StatsTypeSpinnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class TeamStatsDetailActivity : BaseActivity() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: ActivityTeamStatsDetailBinding

    var teamId = 0
    var selectedItem = ""
    var header = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_team_stats_detail)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@TeamStatsDetailActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObservers()

        teamId = intent.getIntExtra("teamId",0)
        selectedItem = intent.getStringExtra("selectedItem").toString()
        header = intent.getStringExtra("header").toString()

        binding.type.setText(header)

        binding.back.setOnClickListener {
            onBackPressed()
        }


        callSeries()
    }


    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getTeamStatsDetail(teamId,selectedItem)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    private fun observeSeries(){

        viewModel.teamStatsDetailLiveData.observe(this, Observer{

            if (it != null){

                if (it.filter != null && !it.filter.matchtype.isNullOrEmpty()){
                    setSpinner(it.filter.matchtype as ArrayList<MatchtypeItem>)
                }

                if (!it.headers.isNullOrEmpty()){

                    binding.parentStat.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                    for (i in 0 until it.headers?.size!!) {
                        Log.e("TAG", "onItemSelected test: ${it.headers?.get(i)}", )
                        when(i){
                            0->{
                                binding.title.setText("${it.headers?.get(i)}")
                            }
                            1->{
                                binding.firstValue.visibility= View.VISIBLE
                                binding.firstValue.setText("${it.headers?.get(i)}")
                            }

                            2->{
                                binding.secondValue.visibility= View.VISIBLE
                                binding.secondValue.setText("${it.headers?.get(i)}")
                            }
                            3->{
                                binding.thirdValue.visibility= View.VISIBLE
                                binding.thirdValue.setText("${it.headers?.get(i)}")
                            }
                            4->{
                                binding.fourthValue.visibility= View.VISIBLE
                                binding.fourthValue.setText("${it.headers?.get(i)}")
                            }
                        }
                    }

                    if (!it.values.isNullOrEmpty()){
                        binding.recyclerStats.adapter = StatsDetailListAdapter(it?.values as ArrayList<ValuesItemStats>,this@TeamStatsDetailActivity)
                    }

                }else{
                    binding.parentStat.visibility = View.GONE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                }

            }else{

            }

        })

    }


    fun setSpinner(typeList: ArrayList<MatchtypeItem>) {
        val adapter = StatsTypeSpinnerAdapter(
            this,
            typeList
        )

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle the selected item here
                val type = typeList[position].matchTypeDesc

                Log.e("TAG", "onItemSelected: ${type}", )



                //Log.e(TAG, "onItemSelected: ${selectedItem}", )
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where no item is selected (optional)
            }
        }
    }





    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}