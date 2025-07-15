package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySeriesDetailsBinding
import com.cricbuzzplus.liveline.databinding.ActivityWtcactivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PointsTableItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.WTCValuesItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SeriesPointTableCricBuzzMainNewAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.WTCCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class WTCActivity : BaseActivity() {

    lateinit var binding : ActivityWtcactivityBinding
    private lateinit var viewModel: CricbuzzViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wtcactivity)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        hideKeyBoard()
        setObservers()
       // setContentView(R.layout.activity_wtcactivity)

        callSeries()
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        finish()
    }

    fun callSeries(){
        if (isInternetConnection()) {
            viewModel.getSeriesWTC()
        }
    }

    private fun setObservers(){

        observeExtras()
        observeSeries()

    }

    private fun observeSeries(){

        viewModel.wtcResponseLiveData.observe(this, Observer{

            if (it != null){

                if (!it.values.isNullOrEmpty() ) {

                    if (!it.subText.isNullOrEmpty()){
                        binding.wtcText.setText(it.subText)
                    }

                    binding.recyclerWTCTable.adapter = WTCCricBuzzAdapter(
                        it?.values as ArrayList<WTCValuesItem>,this)

                }else{
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    binding.parent.visibility = View.GONE
                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.parent.visibility = View.GONE
            }

        })

    }



    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }
}