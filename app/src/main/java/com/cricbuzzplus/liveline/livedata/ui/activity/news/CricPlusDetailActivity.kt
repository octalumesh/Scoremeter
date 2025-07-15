package com.cricbuzzplus.liveline.livedata.ui.activity.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityCricPlusDetailBinding
import com.cricbuzzplus.liveline.databinding.ActivityStoriesBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity

class CricPlusDetailActivity : BaseActivity() {

    lateinit var binding :ActivityCricPlusDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cric_plus_detail)


        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}