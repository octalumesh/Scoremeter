package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityAadhaarVerifyBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants
import okhttp3.MultipartBody
import java.io.File

class AadhaarVerifyActivity : BaseActivity() {

    lateinit var binding: ActivityAadhaarVerifyBinding
    private lateinit var viewModel: UsersViewModel

    var imageUriFront = ""
    var imageUriBack = ""

    var frontApply = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aadhaar_verify)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@AadhaarVerifyActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()



        binding.aadhaarFrontImage.setOnClickListener(View.OnClickListener {

            frontApply = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                showImage()
            } else {
                if (checkPermission()) {
                    showImage()
                } else {
                    requestPermission()
                }
            }
        })

        binding.aadhaarBackImage.setOnClickListener(View.OnClickListener {

            frontApply = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                showImage()
            } else {
                if (checkPermission()) {
                    showImage()
                } else {
                    requestPermission()
                }
            }
        })

        binding.submit.setOnClickListener {

            if (isValid()) {

                var file: File = File(imageUriFront)
                var aadharFront: MultipartBody.Part = getBodyFromFile(file, "aadharFront")!!

                var fileback: File = File(imageUriBack)
                var aadharBack: MultipartBody.Part = getBodyFromFile(fileback, "aadharBack")!!

                callUploadAadhaar(
                    binding.aadhaarNumber.text.trim().toString(),
                    aadharFront,
                    aadharBack
                )

            }

        }


        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    fun isValid(): Boolean {

        if (imageUriFront.isNullOrEmpty()) {
            showToast("Please add aadhaar front image")
            return false
        }

        if (imageUriBack.isNullOrEmpty()) {
            showToast("Please add aadhaar back image")
            return false
        }

        if (binding.aadhaarNumber.text.toString().trim()
                .isNullOrEmpty() || binding.aadhaarNumber.text.toString().trim().length < 12
        ) {
            binding.aadhaarNumber.error = "please enter valid aadhaar number"
            binding.aadhaarNumber.requestFocus()
            return false
        }

        return true
    }


    fun callUploadAadhaar(
        aadharNumber: String,
        imageFront: MultipartBody.Part,
        imageBack: MultipartBody.Part
    ) {

        if (checkForInternet(this)) {
            viewModel.uploadAadharDetail(
                "Bearer " + mPrefs.prefAuthToken.toString(),
                withBody(aadharNumber)!!,
                imageFront,
                imageBack
            )
        }
    }


    fun setObserver() {
        observeExtras()
        observeUpload()
    }

    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(this, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }

    private fun observeUpload() {

        viewModel.uploadAadhaarDetailLiveData.observe(this, Observer {

            if (it) {

                showToast("Successfully uploaded.")
            }

        })

    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
            return true
        } else {
            return true
        }
    }

    private fun requestPermission() {

        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), Constants.imagepicker
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            Constants.imagepicker -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                /*  val intent =
                      Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                  startActivityForResult(intent, 2)*/
                showImage()
                // main logic
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showToast("You need to allow access permissions")
                    requestPermission()
                } else {
                    // requestPermission();
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                }

            }
        }
    }

    val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions

                if (frontApply) {
                    Glide.with(this).load(uri).into(binding.aadhaarFrontImage)

                    imageUriFront = uri.path.toString()
                } else {
                    Glide.with(this).load(uri).into(binding.aadhaarBackImage)

                    imageUriBack = uri.path.toString()
                }


            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showToast(ImagePicker.getError(data))
            } else {
                showToast("Task Cancelled")
            }

        }

    private fun showImage() {

        /*   val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
           startActivityForResult(intent, PERMISSION_REQUEST_CODE)*/

        try {

            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

}