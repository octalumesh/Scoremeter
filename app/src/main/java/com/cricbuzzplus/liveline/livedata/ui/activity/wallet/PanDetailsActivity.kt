package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.DatePicker
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
import com.cricbuzzplus.liveline.databinding.ActivityPanDetailsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants
import okhttp3.MultipartBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PanDetailsActivity : BaseActivity() {

    lateinit var binding: ActivityPanDetailsBinding
    private lateinit var viewModel: UsersViewModel

    var imageUriPan =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pan_details)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@PanDetailsActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()


        binding.panCardImage.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                showImage()
            }else {
                if (checkPermission()) {
                    showImage()
                } else {
                    requestPermission()
                }
            }
        })

        binding.dob.setOnClickListener {
            showDatePickerDialog()
        }

        binding.submit.setOnClickListener {
            if (isValid()){

                var file: File = File(imageUriPan)
                var panCardImg: MultipartBody.Part = getBodyFromFile(file, "panCardImg")!!




                callUploadPanCard(
                    binding.name.text.trim().toString(),
                    binding.panNumber.text.trim().toString(),
                    binding.dob.text.trim().toString(),
                    panCardImg
                )

            }
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun showDatePickerDialog() {


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, monthOfYear, dayOfMonth)
                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = sdf.format(selectedDate.time)
                binding.dob.text = "$formattedDate"
            },
            year,
            month,
            day
        )

        // Set maximum date to today to prevent selecting future dates
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }


    fun isValid():Boolean{

        if (imageUriPan.isNullOrEmpty()){
            showToast("Please add PAN Card image")
            return false
        }

        if (binding.name.text.toString().trim().isNullOrEmpty() ){
            binding.name.error = "please enter name"
            binding.name.requestFocus()
            return false
        }

        if (binding.panNumber.text.toString().trim().isNullOrEmpty() ){
            binding.panNumber.error = "please enter PAN number"
            binding.panNumber.requestFocus()
            return false
        }

        if (binding.confirmPanNumber.text.toString().trim().isNullOrEmpty() || !binding.confirmPanNumber.text.toString().trim().equals(binding.panNumber.text.toString().trim())){
            binding.confirmPanNumber.error = "please enter correct PAN number"
            binding.confirmPanNumber.requestFocus()
            return false
        }



        if (binding.dob.text.toString().trim().equals("Date Of Birth")){
            showToast("please enter dob")
            return false
        }

        return true
    }


    fun callUploadPanCard(
        name: String,
        panNumber: String,
        dob: String,
        panImage: MultipartBody.Part
    ) {

        Log.e("TAGname", "callUploadPanCard: "+name )

        if (checkForInternet(this)) {
            viewModel.uploadPanCardDetails(
                "Bearer " + mPrefs.prefAuthToken.toString(),
                withBody(name)!!,
                withBody(panNumber)!!,
                withBody(dob)!!,
                panImage
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

        viewModel.uploadPanCardDetailLiveData.observe(this, Observer {

            if (it) {
                showToast("Successfully uploaded.")
            }

        })

    }


    private fun checkPermission():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            return true
        }else{
            return true
        }
    }

    private fun requestPermission() {

        requestPermissions( arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.imagepicker)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

                if (shouldShowRequestPermissionRationale( Manifest.permission.READ_EXTERNAL_STORAGE)) {
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

    val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions

                Glide.with(this).load(uri).into(binding.panCardImage)

                imageUriPan = uri.path.toString()



        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            showToast( ImagePicker.getError(data))
        } else {
            showToast( "Task Cancelled")
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
        }
        catch (e: Exception){
            showToast(e.message.toString())
        }
    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }
}