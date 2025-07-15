package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityPhotoBinding
import com.cricbuzzplus.liveline.databinding.ActivityPhotoGalleryBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PhotoGalleryDetailsItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PhotoGalleryInfo
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PhotosCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PhotosGalleryCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel

class PhotoGalleryActivity : BaseActivity() {

    lateinit var binding: ActivityPhotoGalleryBinding
    private lateinit var viewModel: CricbuzzViewModel

    var id = 0

    var headline = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_gallery)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@PhotoGalleryActivity, R.color.colorPrimaryDark)
        }

        id = intent.getIntExtra("id",0)

        headline = intent.getStringExtra("headline").toString()


        binding.title.setText(headline)

        hideKeyBoard()
        setObservers()


        binding.back.setOnClickListener {
            onBackPressed()
        }


        callPhotos()
    }

    fun callPhotos(){
        if (isInternetConnection()) {
            viewModel.getPhotoGallery(id)
        }
    }




    private fun setObservers(){

        observeExtras()
        observePhoto()

    }


    private fun observePhoto(){

        viewModel.photosGalleryLiveData.observe(this, Observer{

            if (it != null){

                try {


                    if (!it.photoGalleryDetails.isNullOrEmpty()) {

                        binding.recyclerPhoto.adapter = PhotosGalleryCricBuzzAdapter(
                            it.photoGalleryDetails as ArrayList<PhotoGalleryDetailsItem>,
                            this@PhotoGalleryActivity
                        )
                    }

                }catch (e:Exception){
                    e.printStackTrace()
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