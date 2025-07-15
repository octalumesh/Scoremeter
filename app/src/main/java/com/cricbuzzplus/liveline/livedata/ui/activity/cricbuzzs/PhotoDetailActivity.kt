package com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityPhotoDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel
import com.cricbuzzplus.liveline.utils.Constants

class PhotoDetailActivity : BaseActivity() {

    lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var viewModel: CricbuzzViewModel

    var url = ""

    var caption = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_detail)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@PhotoDetailActivity, R.color.colorPrimaryDark)
        }



        url = intent.getStringExtra("url").toString()
        caption = intent.getStringExtra("caption").toString()

        Log.e("TAG", "onCreate: "+url )


        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)


        Glide.with(this)
            .load("" + url)
            .apply(requestOptions).into(binding.image)

        binding.title.setText(caption)

        hideKeyBoard()


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.share.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                shareNews()
            }else{
                premissionAllowCls()
            }

        }

    }

    private fun premissionAllowCls() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                shareNews()
            } else {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1536)
            }
        } else {
            shareNews()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1536) {
            shareNews()
        }
    }


    fun shareNews() {
        try {
            val bitmapDrawable = binding.image.getDrawable() as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val intent = Intent(Intent.ACTION_SEND)
            val bitmapPath: String =
                MediaStore.Images.Media.insertImage(contentResolver, bitmap, binding.title.text.toString(), binding.title.text.toString())
            val bitmapUri = Uri.parse(bitmapPath)
            intent.type = "image/*"
            //intent.putExtra("description",niyamList.get(0).description.toString())
            var shareMessage = "\n${binding.title.text.toString()}\n\n"
            shareMessage =
                """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            intent.putExtra(
                Intent.EXTRA_TEXT, shareMessage
            )
            startActivity(Intent.createChooser(intent, "Share Image"))
        } catch (e: java.lang.Exception) {
            Log.e("shareNews", "shareNews: " + e.message)
        }
    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }
}