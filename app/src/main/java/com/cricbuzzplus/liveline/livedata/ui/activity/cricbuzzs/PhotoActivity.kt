package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityPhotoBinding
import com.cricbuzzplus.liveline.databinding.PlayerProfileActivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PhotoGalleryInfo
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PhotosCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerBrowsCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerBrowsSearchCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class PhotoActivity : BaseActivity() {

    lateinit var binding: ActivityPhotoBinding
    private lateinit var viewModel: CricbuzzViewModel

    var list = arrayListOf<PhotoGalleryInfo>()

    var adapter : PhotosCricBuzzAdapter ?= null

    var timestamp = ""
    var prevtimestamp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@PhotoActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObservers()


        binding.back.setOnClickListener {
            onBackPressed()
        }


        callPhotos()
    }

    fun callPhotos(){
        if (isInternetConnection()) {
            viewModel.getPhotoAlbum()
        }
    }

    fun callPhotosPaginate(){
        if (isInternetConnection()) {
            viewModel.getPhotoAlbumTime(timestamp)
        }
    }



    private fun setObservers(){

        observeExtras()
        observePhoto()
        observePhotoPage()

    }


    private fun observePhoto(){

        viewModel.photosLiveData.observe(this, Observer{

            if (it != null){

                try {


                    if (!it.photoGalleryInfoList.isNullOrEmpty()) {

                        val filteredList = it.photoGalleryInfoList
                            ?.filter { item -> item?.photoGalleryInfo != null }
                            ?.mapNotNull { it?.photoGalleryInfo }


                        if (!filteredList.isNullOrEmpty()) {

                            timestamp = filteredList.get(filteredList.size-1).publishedTime.toString()

                            list.addAll(filteredList as ArrayList<PhotoGalleryInfo>)

                            if (adapter == null) {

                                adapter = PhotosCricBuzzAdapter(
                                    list,
                                    this@PhotoActivity
                                )
                                binding.recyclerPhoto.adapter = adapter

                                addScroll()

                            }else{

                                isLoading = false
                                adapter?.updateList(list)
                            }


                        }
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }



            }

        })

    }

    private fun observePhotoPage(){

        viewModel.photosPageLiveData.observe(this, Observer{

            if (it != null){

                try {


                    if (!it.photoGalleryInfoList.isNullOrEmpty()) {

                        val filteredList = it.photoGalleryInfoList
                            ?.filter { item -> item?.photoGalleryInfo != null }
                            ?.mapNotNull { it?.photoGalleryInfo }


                        if (!filteredList.isNullOrEmpty()) {

                            timestamp = filteredList.get(filteredList.size-1).publishedTime.toString()

                            list.addAll(filteredList as ArrayList<PhotoGalleryInfo>)

                            if (adapter == null) {

                                adapter = PhotosCricBuzzAdapter(
                                    list,
                                    this@PhotoActivity
                                )
                                binding.recyclerPhoto.adapter = adapter

                            }else{

                                isLoading = false
                                adapter?.updateList(list)
                            }


                        }
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }



            }

        })

    }


    var isLoading = false // Used to prevent multiple API calls while loading
    var visibleThreshold = 1 // Number of items from the end to trigger loading

    /*fun addScroll() {


        binding.recyclerPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.recyclerPhoto.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val totalItemCount = layoutManager.itemCount

                //Log.e(TAG, "onScrolled: totalItemCount $totalItemCount  lastVisibleItem $lastVisibleItem" )

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more data here
                    isLoading = true // Set to true to prevent multiple calls

                    if (!prevtimestamp.equals(timestamp)) {
                        prevtimestamp = timestamp

                        //  Log.e(TAG, "visibleThreshold: "+visibleThreshold )

                        callPhotosPaginate()
                    }

                }
            }
        })
    }*/


    fun addScroll() {
        binding.recyclerPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // Only check for scroll down
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                   /* Log.e("TAG126", "onScrolled:isLoading  "+isLoading )
                    Log.e("TAG126", "onScrolled:totalItemCount  "+totalItemCount )
                    Log.e("TAG126", "onScrolled:lastVisibleItem  "+lastVisibleItem )
                    Log.e("TAG126", "onScrolled:visibleThreshold  "+visibleThreshold )*/

                    // Trigger pagination if within visibleThreshold from the end of the list
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        isLoading = true
                        if (prevtimestamp != timestamp) {
                            prevtimestamp = timestamp
                            callPhotosPaginate()
                        }
                    }
                }
            }
        })
    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }
}