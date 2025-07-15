package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesSquadBinding
import com.cricbuzzplus.liveline.databinding.ActivitySeriesStatsDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.LiveResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdaptorNew
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HighlightTypeSpinnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerSquadCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.StatsDetailListAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.StatsTypeSpinnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.seriesdetail.SeriesStatsDetailFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.stadium.StadiumInfoFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.annotations.SerializedName

class SeriesStatsDetailActivity : BaseActivity() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: ActivitySeriesStatsDetailBinding

    var seriesId = 0
    var selectedItem = ""
    var header = ""

    var odiStatsList: OdiStatsList? = null
    var testStatsList: TestStatsList? = null
    var t20StatsList: T20StatsList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_series_stats_detail)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SeriesStatsDetailActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObservers()

        seriesId = intent.getIntExtra("seriesId",0)
        selectedItem = intent.getStringExtra("selectedItem").toString()
        header = intent.getStringExtra("header").toString()

        binding.type.setText(header)

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
            viewModel.getSeriesStatsDetail(seriesId,selectedItem)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }



    private fun observeSeries(){

        viewModel.seriesStatsDetailLiveData.observe(this, Observer{

            if (it != null){

                if (it.filter != null && !it.filter.matchtype.isNullOrEmpty()){
                    setSpinner(it.filter.matchtype as ArrayList<MatchtypeItem>)
                }

                if (it.testStatsList != null){
                    testStatsList = it.testStatsList
                }

                if (it.t20StatsList != null){
                    t20StatsList = it.t20StatsList
                }

                if (it.odiStatsList != null){
                    odiStatsList = it.odiStatsList
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

                if (type.equals("test")){

                    if (testStatsList != null && !testStatsList?.headers.isNullOrEmpty()){

                        binding.parentStat.visibility = View.VISIBLE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                        for (i in 0 until testStatsList?.headers?.size!!) {
                            Log.e("TAG", "onItemSelected test: ${testStatsList?.headers?.get(i)}", )
                            when(i){
                                0->{
                                    binding.title.setText("${testStatsList?.headers?.get(i)}")
                                }
                                1->{
                                    binding.firstValue.visibility= View.VISIBLE
                                    binding.firstValue.setText("${testStatsList?.headers?.get(i)}")
                                }

                                2->{
                                    binding.secondValue.visibility= View.VISIBLE
                                    binding.secondValue.setText("${testStatsList?.headers?.get(i)}")
                                }
                                3->{
                                    binding.thirdValue.visibility= View.VISIBLE
                                    binding.thirdValue.setText("${testStatsList?.headers?.get(i)}")
                                }
                                4->{
                                    binding.fourthValue.visibility= View.VISIBLE
                                    binding.fourthValue.setText("${testStatsList?.headers?.get(i)}")
                                }
                            }
                        }

                        if (!testStatsList?.values.isNullOrEmpty()){
                            binding.recyclerStats.adapter = StatsDetailListAdapter(testStatsList?.values as ArrayList<ValuesItemStats>,this@SeriesStatsDetailActivity)
                        }

                    }else{
                        binding.parentStat.visibility = View.GONE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    }

                }else if (type.equals("odi")){

                    if (odiStatsList != null && !odiStatsList?.headers.isNullOrEmpty()){

                        binding.parentStat.visibility = View.VISIBLE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                        for (i in 0 until odiStatsList?.headers?.size!!) {
                            Log.e("TAG", "onItemSelected odi: ${odiStatsList?.headers?.get(i)}", )
                            when(i){
                                0->{
                                    binding.title.setText("${odiStatsList?.headers?.get(i)}")
                                }
                                1->{
                                    binding.firstValue.visibility= View.VISIBLE
                                    binding.firstValue.setText("${odiStatsList?.headers?.get(i)}")
                                }

                                2->{
                                    binding.secondValue.visibility= View.VISIBLE
                                    binding.secondValue.setText("${odiStatsList?.headers?.get(i)}")
                                }
                                3->{
                                    binding.thirdValue.visibility= View.VISIBLE
                                    binding.thirdValue.setText("${odiStatsList?.headers?.get(i)}")
                                }
                                4->{
                                    binding.fourthValue.visibility= View.VISIBLE
                                    binding.fourthValue.setText("${odiStatsList?.headers?.get(i)}")
                                }
                            }
                        }

                        if (!odiStatsList?.values.isNullOrEmpty()){
                            binding.recyclerStats.adapter = StatsDetailListAdapter(odiStatsList?.values as ArrayList<ValuesItemStats>,this@SeriesStatsDetailActivity)
                        }

                    }else{
                        binding.parentStat.visibility = View.GONE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    }

                }else if (type.equals("t20")){

                    if (t20StatsList != null && !t20StatsList?.headers.isNullOrEmpty()){

                        binding.parentStat.visibility = View.VISIBLE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                        for (i in 0 until t20StatsList?.headers?.size!!) {
                            Log.e("TAG", "onItemSelected t20: ${t20StatsList?.headers?.get(i)}", )
                            when(i){
                                0->{
                                    binding.title.setText("${t20StatsList?.headers?.get(i)}")
                                }
                                1->{
                                    binding.firstValue.visibility= View.VISIBLE
                                    binding.firstValue.setText("${t20StatsList?.headers?.get(i)}")
                                }

                                2->{
                                    binding.secondValue.visibility= View.VISIBLE
                                    binding.secondValue.setText("${t20StatsList?.headers?.get(i)}")
                                }
                                3->{
                                    binding.thirdValue.visibility= View.VISIBLE
                                    binding.thirdValue.setText("${t20StatsList?.headers?.get(i)}")
                                }
                                4->{
                                    binding.fourthValue.visibility= View.VISIBLE
                                    binding.fourthValue.setText("${t20StatsList?.headers?.get(i)}")
                                }
                            }
                        }

                        if (!t20StatsList?.values.isNullOrEmpty()){
                            binding.recyclerStats.adapter = StatsDetailListAdapter(t20StatsList?.values as ArrayList<ValuesItemStats>,this@SeriesStatsDetailActivity)
                        }

                    }else{
                        binding.parentStat.visibility = View.GONE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    }

                }

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