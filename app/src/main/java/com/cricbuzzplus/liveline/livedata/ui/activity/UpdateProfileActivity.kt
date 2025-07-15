package com.cricbuzzplus.liveline.livedata.ui.activity

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
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
import com.cricbuzzplus.liveline.databinding.UpdateProfileActivityBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants
import okhttp3.MultipartBody
import java.io.File

class UpdateProfileActivity : BaseActivity() {

    lateinit var binding: UpdateProfileActivityBinding
    private lateinit var viewModel: UsersViewModel

    var imageUri = ""
    var email = ""
    var mobile = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_profile)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@UpdateProfileActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()


        binding.addNow.setOnClickListener {
            if (binding.mobileRl.visibility == View.VISIBLE){
                binding.mobileRl.visibility = View.GONE
            }else{
                binding.mobileRl.visibility = View.VISIBLE
            }
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        try {

            Glide.with(this).load(Constants.ImgURl + mPrefs.prefUserDetails?.profilepicture)
                .placeholder(R.mipmap.ic_launcher_round).into(binding.userImage)

            binding.userName.setText(mPrefs.prefUserDetails?.firstName)

            if (!mPrefs.prefUserDetails?.email2.isNullOrEmpty()) {

                binding.email.setText(mPrefs.prefUserDetails?.email2)
            }

            if (!mPrefs.prefUserDetails?.mobile2.isNullOrEmpty()) {

                binding.mobileRl.visibility = View.VISIBLE
                binding.mobile.setText(mPrefs.prefUserDetails?.mobile2)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.changeName.setOnClickListener {
            binding.userName.requestFocus()
            openKeyBoard()
        }

        /*binding.userImage.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                showImage()
            } else {
                if (checkPermission()) {
                    showImage()
                } else {
                    requestPermission()
                }
            }
        })*/

        binding.frameImg.setOnClickListener(View.OnClickListener {
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

        binding.update.setOnClickListener {

            if (!binding.email.text.trim().isNullOrEmpty()){
                email = binding.email.text.trim().toString()
            }

            if (!binding.mobile.text.trim().isNullOrEmpty()){
                mobile = binding.mobile.text.trim().toString()
            }


            if (!imageUri.isNullOrEmpty()) {

                if (binding.userName.text.trim().toString().isNullOrEmpty()) {
                    binding.userName.setError("Please enter name!")
                    binding.userName.requestFocus()
                } else {

                    var file: File = File(imageUri)
                    var image: MultipartBody.Part = getBodyFromFile(file, "profilepicture")!!

                    callUpdateProfileWithImage(binding.userName.text.trim().toString(), image)
                }

            } else {

                if (binding.userName.text.trim().toString().isNullOrEmpty()) {
                    binding.userName.setError("Please enter name!")
                    binding.userName.requestFocus()
                } else {

                    callUpdateProfile(binding.userName.text.trim().toString())

                }

            }

        }


        binding.deleteAccount.setOnClickListener {

            showAlertDialog(
                "Are You sure to delete account!",
                "Yes",
                "No",
                "",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {

                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog?.dismiss()
                                callDeleteProfile()
                            }

                            DialogInterface.BUTTON_NEGATIVE -> {

                                dialog?.dismiss()

                            }
                        }
                    }

                })

        }


    }

    fun callDeleteProfile() {

        if (checkForInternet(this)) {
            viewModel.deleteAccount("Bearer " + mPrefs.prefAuthToken.toString())
        }
    }

    fun callUpdateProfile(name: String) {

        if (checkForInternet(this)) {
            viewModel.updateProfile("Bearer " + mPrefs.prefAuthToken.toString(), name,email,mobile)
        }
    }

    fun callUpdateProfileWithImage(name: String, image: MultipartBody.Part) {

        if (checkForInternet(this)) {
            viewModel.updateProfileWithImage(
                "Bearer " + mPrefs.prefAuthToken.toString(),
                withBody(name)!!,
                withBody(email)!!,
                withBody(mobile)!!,
                image
            )
        }
    }


    private fun setObserver() {
        observeProfile()
        observeExtras()
        observeDeleteProfile()
    }

    private fun observeDeleteProfile() {

        viewModel.deleteProfileLiveData.observe(this, Observer {

            if (it) {
                hitLogoutAPI()
            }

        })

    }

    private fun observeProfile() {

        viewModel.updateProfileLiveData.observe(this, Observer {

            mPrefs.prefUserDetails = it

            showToast("Successfully Updated.")

            val i = Intent()
            val bundel = Bundle()
            bundel.putString("data", "data")
            i.putExtras(bundel)
            setResult(Activity.RESULT_OK, i)
            finish()

        })

    }

    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(this, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
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
                Glide.with(this).load(uri).into(binding.userImage)

                imageUri = uri.path.toString()


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
        super.onBackPressed()
        finish()
    }
}