package com.cricbuzzplus.liveline.livedata.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.MainApplication
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityHomeBinding
import com.cricbuzzplus.liveline.databinding.ItemDialogPopUpBinding
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.response.SliderImage
import com.cricbuzzplus.liveline.livedata.response.newresponse.AppCheckResponse
import com.cricbuzzplus.liveline.livedata.response.newresponse.BannerListItem
import com.cricbuzzplus.liveline.livedata.response.newresponse.Popup
import com.cricbuzzplus.liveline.livedata.ui.adapter.HomeSliderViewPagerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    var list: ArrayList<SliderImage> = arrayListOf()

    private lateinit var viewModel: UsersViewModel

    lateinit var navController: NavController

    val loginResultLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val dataIntent: Intent? = result.data
            if (dataIntent != null) {
                val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    dataIntent.extras?.getParcelable("data", LoginResponse::class.java)
                } else {
                    dataIntent.extras?.getParcelable("data")!!
                }

                if (data != null) {
                    mPrefs.prefUserDetails = data
                    binding.login.visibility = View.GONE
                    binding.user.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        setContentView(binding.root)
        //renderUpdates();

        window.statusBarColor = ContextCompat.getColor(this@HomeActivity, R.color.colorPrimaryDark)

        /*binding.adView.adSize = AdSize.BANNER
        binding.adView.adUnitId = getString(R.string.testaddId)*/

        /* MobileAds.initialize(this) {}

         val adRequest = AdRequest.Builder().build()
         binding.adView.loadAd(adRequest)*/

        //   getData()

        setObservers()


        binding.whatsapp.setOnClickListener {
            try {

                val sendIntent = Intent("android.intent.action.MAIN")
                sendIntent.action = Intent.ACTION_VIEW
                sendIntent.setPackage("com.whatsapp")
                val url =
                    // "https://api.whatsapp.com/send?phone=+91" + 9167848484
                    "https://api.whatsapp.com/send?phone=+91" + getString(R.string.whatsapp)
                sendIntent.data = Uri.parse(url)
                startActivity(sendIntent)

            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }


        binding.telegram.setOnClickListener(View.OnClickListener {
            try {
                val telegramIntent = Intent(Intent.ACTION_VIEW)
                telegramIntent.data = Uri.parse("" + getString(R.string.telegramlink))
                startActivity(telegramIntent)
            } catch (e: Exception) {
                Log.e("TAG11", "onCreate: " + e.message)
                showToast(e.message.toString())
            }
        })


        binding.share.setOnClickListener(View.OnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        })

        binding.login.setOnClickListener {

            val intent = Intent(this, SignInActivity::class.java)
            loginResultLauncer.launch(intent)

        }

        binding.user.setOnClickListener {

            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)

        }


        if (mPrefs.prefUserDetails == null) {
            binding.login.visibility = View.VISIBLE
            binding.user.visibility = View.GONE
        } else {
            binding.login.visibility = View.GONE
            binding.user.visibility = View.VISIBLE
        }

//        window.setBackgroundDrawable(d);    //full screen background set color


        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_home)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_news, R.id.navigation_matches, R.id.navigation_more
            )
        )
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!checkPermission()) {
            requestPermission()
        }

        appCheck()

        getPopUp()
    }


    fun getPopUp() {
        if (isInternetConnection()) {
            viewModel.getSliderPopup()
        }

    }


    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            return true
        } else {
            return true
        }
    }

    fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1025
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1025 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                /*  val intent =
                      Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                  startActivityForResult(intent, 2)*/
                // main logic
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                    showToast("You need to allow notification permissions")
                    requestPermission()
                } else {
                    // requestPermission();
                    /*val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)*/
                }

            }
        }

    }


    fun getData() {

        if (list != null) {
            list.clear()
        }
        FirebaseFirestore.getInstance().collection("slider")
            .orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    //   showToast(e.message.toString())
                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    for (dc in snapshot!!.documentChanges) {

                        val prediction =
                            dc.document.toObject(SliderImage::class.java) as SliderImage
                        list.add(prediction)
                    }
                    // setSlider(list)
                }
            }
    }

    /*  fun setSlider(res:List<SliderImage>){

        //  binding.viewPage.adapter= HomeSliderViewPagerAdapter(this,res)
       //   binding.viewPagetop.adapter= HomeSliderViewPagerAdapter(this,res)

      }*/

    override fun onResume() {
        super.onResume()
        if (mPrefs.prefUserDetails == null) {
            binding.login.visibility = View.VISIBLE
            binding.user.visibility = View.GONE
        } else {
            binding.login.visibility = View.GONE
            binding.user.visibility = View.VISIBLE
        }
    }

    var isFirstBackPressed: Boolean = false

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        /*if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }*/

        if (navController.graph.startDestinationId != navController.currentDestination?.id) {
            super.onBackPressed()
        } else {

            if (isFirstBackPressed) {
                super.onBackPressed()
            } else {
                isFirstBackPressed = true
                showToast("Press back again to exit")
                Handler().postDelayed(Runnable { isFirstBackPressed = false }, 1000)
            }

        }
    }


    private fun setObservers() {
        observePopUp()
    }

    private fun observePopUp() {
        viewModel.sliderPopUpLiveData.observe(this, Observer {
            if (it != null) {

                if (it.popup != null) {
                    showPopAd(it.popup)
                }

                if (!it.bannerList.isNullOrEmpty()) {
                    setSlider(it.bannerList)
                }
            }
        })
    }


    fun setSlider(res: List<BannerListItem?>) {

        if (!res.isNullOrEmpty()) {
            binding.viewPage.visibility = View.VISIBLE
            //binding.appBarDrawer.viewPager.visibility =View.VISIBLE

            //  binding.appBarDrawer.viewPager.adapter = HomeSliderViewPagerAdapterNew(this, res)
            binding.viewPage.adapter = HomeSliderViewPagerAdapter(this, res)

        } else {
            binding.viewPage.visibility = View.GONE
        }

    }


    fun showPopAd(item: Popup?) {

        if (!MainApplication.applicationInstance.getPopUp()) {

            MainApplication.applicationInstance.setPopUp(true)

            val bindingAlert = ItemDialogPopUpBinding.inflate(layoutInflater)


            Glide.with(this).load(Constants.ImgURl + item?.image).into(bindingAlert.popUpImage)


            val dialog = AlertDialog.Builder(this, R.style.TransparentDialog)
                .setView(bindingAlert.root)
                .create()


            bindingAlert.popUpImage.setOnClickListener {
                dialog.dismiss()

                try {
                    val url = item?.link.toString()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: Exception) {
                    // Toast.makeText(activity,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            bindingAlert.closeBtn.setOnClickListener {

                dialog.dismiss()
            }


            dialog.show()
        }

    }


    private fun appCheck() {


        val retrofit = Retrofit.Builder()
            .baseUrl("https://frontapi.devhubtech.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI: ApiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<AppCheckResponse> =
            retrofitAPI.getAppCheck("android", BuildConfig.APPLICATION_ID)
        call.enqueue(object : Callback<AppCheckResponse> {
            override fun onResponse(
                call: Call<AppCheckResponse>,
                response: Response<AppCheckResponse>
            ) {
                Log.e("TAG ======", "onResponse: " + response.body())
                val appCheckResponse: AppCheckResponse? = response.body()
                if (appCheckResponse?.status == true) {
                    if (appCheckResponse?.data != null) {
                        if (!appCheckResponse?.data?.paid!!) {
                            val builder =
                                androidx.appcompat.app.AlertDialog.Builder(this@HomeActivity)
                            builder.setTitle(appCheckResponse?.data?.name)
                                .setMessage(appCheckResponse?.data?.deactivemessage)
                                .setCancelable(false)
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<AppCheckResponse>, t: Throwable) {
                Log.e("TAG ===", "onFailure: " + t.message)
            }
        })
    }

}