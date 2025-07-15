package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityBrowsPlayerBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerBrowsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerBrowsSearchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class BrowsPlayerActivity : BaseActivity() {

    lateinit var binding: ActivityBrowsPlayerBinding
    private lateinit var viewModel: CricbuzzViewModel

    var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brows_player)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@BrowsPlayerActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObservers()

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("TAGsearch", "onQueryTextSubmit: "+query )

                if (!query.isNullOrEmpty()){
                    binding.searchRecycler.visibility = View.VISIBLE
                    callPlayerSearch(query)
                }else{
                    binding.searchRecycler.visibility = View.GONE
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("TAGsearch", "onQueryTextChange: "+newText )

                if (!newText.isNullOrEmpty()){
                    binding.searchRecycler.visibility = View.VISIBLE
                    callPlayerSearch(newText)
                }else{
                    binding.searchRecycler.visibility = View.GONE
                }

                return true
            }
        })

        binding.back.setOnClickListener {
            onBackPressed()
        }


        callPlayer()

    }
    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }



    fun callPlayer(){
        if (isInternetConnection()) {
            viewModel.getBrowsPlayer()
        }
    }

    fun callPlayerSearch(search:String){
        if (isInternetConnection()) {
            viewModel.getBrowsPlayerSearch(search)
        }
    }

    private fun setObservers(){

        observeExtras()
        observePlayer()
        observePlayerSearch()

    }


    private fun observePlayer(){

        viewModel.browsePlayerLiveData.observe(this, Observer{

            if (it != null){

                try {

                    binding.category.setText(""+it.category)

                }catch (e:Exception){
                    e.printStackTrace()
                }

                if (!it.player.isNullOrEmpty()) {
                    binding.recyclerSeries.adapter = PlayerBrowsCricBuzzAdapter(
                        it.player as ArrayList<PlayerItem>,
                        this@BrowsPlayerActivity
                    )
                }

            }

        })

    }

    private fun observePlayerSearch(){

        viewModel.browsePlayerSearchLiveData.observe(this, Observer{

            if (it != null){

                try {

                    if (!it.player.isNullOrEmpty()) {

                        binding.searchRecycler.adapter = PlayerBrowsSearchCricBuzzAdapter(
                            it.player as ArrayList<PlayerItem>,
                            this@BrowsPlayerActivity
                        )
                    }else{
                        binding.searchRecycler.visibility = View.GONE
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                    binding.searchRecycler.visibility = View.GONE
                }



            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}