package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.databinding.MoreFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.response.SliderImage
import com.cricbuzzplus.liveline.livedata.ui.activity.*
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.*
import com.cricbuzzplus.liveline.livedata.ui.activity.wallet.ReferEarnActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class MoreFragment : BaseFragment() {

    lateinit var binding: MoreFragmentBinding

    private lateinit var viewModel: UsersViewModel

    var list: ArrayList<SliderImage> = arrayListOf()

    val loginResultLauncer =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val dataIntent: Intent? = result.data
                if (dataIntent != null) {
                    val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        dataIntent?.extras?.getParcelable("data", LoginResponse::class.java)
                    } else {
                        dataIntent?.extras?.getParcelable<LoginResponse>("data")!!
                    }

                    if (data != null) {
                        mPrefs.prefUserDetails = data

                        val intent = Intent(activity, UserProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = MoreFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getData()
       // MobileAds.initialize(context) {}

      // val adRequest = AdRequest.Builder().build()
      // binding.adView.loadAd(adRequest)
      // binding.adView1.loadAd(adRequest)

        binding.aboutUs.setOnClickListener(View.OnClickListener {
            //    goToPdf("https://cricliveguru.com/about.html")
        })

        binding.contactUs.setOnClickListener(View.OnClickListener {
            //   goToPdf("https://cricliveguru.com/contact.html")
        })

        binding.termsCondition.setOnClickListener(View.OnClickListener {
            goToPdf("https://cricliveguru.com/term_condition.html")
        })

        binding.privacyPolicy.setOnClickListener(View.OnClickListener {
            //   goToPdf("https://cricliveguru.com/privacy_policy.html")
        })

        binding.cardviewIccmensRanking.setOnClickListener {

            val intent = Intent(activity, ICCMensRankingActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewIccwomensRanking.setOnClickListener {

            val intent = Intent(activity, ICCWomensRankingActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewBrowsePlayer.setOnClickListener {

            val intent = Intent(activity, BrowsPlayerActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewSettings.setOnClickListener {

            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewArchives.setOnClickListener {

            val intent = Intent(activity, ArchivesActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewBrowseTeams.setOnClickListener {

            val intent = Intent(activity, BrowsTeamsActivity::class.java)
            startActivity(intent)

        }

        binding.cardviewShareapp.setOnClickListener(View.OnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()

                if (mPrefs.prefUserDetails != null && !mPrefs?.prefUserDetails?.referId.isNullOrEmpty()){
                    shareMessage = "${shareMessage}\n Use Refer Code:  ${mPrefs?.prefUserDetails?.referId}"
                }

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        })

        binding.cardviewRefer.setOnClickListener {
            if (mPrefs.prefUserDetails != null && !mPrefs?.prefUserDetails?.referId.isNullOrEmpty()){

                val intent = Intent(activity,ReferEarnActivity::class.java)
                startActivity(intent)

            }else{
                showToast("Login to generate refer code")
            }
        }

        binding.cardviewRateus.setOnClickListener {
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                )
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.cardviewTeamRanking.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_team_type)
        })


        binding.cardviewPlayerRanking.setOnClickListener(View.OnClickListener {
            activity.findNavController(R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_player_type)

        })

        binding.cardviewSeries.setOnClickListener(View.OnClickListener {
            /*activity.findNavController(R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_series)*/

            val intent = Intent(activity, SeriesBrowsActivity::class.java)
            startActivity(intent)

        })

        binding.cardviewWtc.setOnClickListener {
            val intent = Intent(activity, WTCActivity::class.java)
            startActivity(intent)
        }

        binding.cardviewLeague.setOnClickListener {
            val intent = Intent(activity, SuperLeagueActivity::class.java)
            startActivity(intent)
        }

        binding.cardviewPhotos.setOnClickListener(View.OnClickListener {
            /*activity.findNavController(R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_series)*/

            val intent = Intent(activity, PhotoActivity::class.java)
            startActivity(intent)

        })

        binding.cardviewSchedule.setOnClickListener(View.OnClickListener {
            /*activity.findNavController(R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_series)*/

            val intent = Intent(activity, SchedulesActivity::class.java)
            startActivity(intent)

        })


        binding.profileRl.setOnClickListener {

            if (mPrefs.prefUserDetails != null) {
                val intent = Intent(activity, UserProfileActivity::class.java)
                startActivity(intent)
            } else {
                showToast("Login to proceed...")
                val intent = Intent(activity, SignInActivity::class.java)
                loginResultLauncer.launch(intent)
            }
        }


     //   calculateDLS()


    }

    val teamAScore = 130
    val teamAwickets = 9
    val teamAovers = 20
    val teamBScore = 70
    val teamBWicket = 3
    val teamBOver = 8

    val maxOver = 20

    fun calculateDLS() {


        val teamAResourceUsed = if (maxOver != 0) (teamAovers.toDouble() / maxOver) * 100 else 0.0
        val parScoreTeamA = (teamAScore * teamAResourceUsed / 100)

        val parScoreAdjustTeamA = parScoreTeamA - (teamAwickets * 2)

        val teamBResourceUsed = if (maxOver != 0) (teamBOver.toDouble() / maxOver) * 100 else 0.0

        val targetScoreTeamB = (parScoreAdjustTeamA * teamBResourceUsed / 100) + 1



        Log.e("TAGDLS", "calculateDLS: target  $targetScoreTeamB")

    }


    fun getData() {

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
                    setSlider(list)
                }

            }

    }

    fun setSlider(res: List<SliderImage>) {

        // binding.viewPage1.adapter=HomeSliderViewPagerAdapter(activity,res)

    }

    fun goToPdf(pdf: String) {
        val intent = Intent(
            activity,
            WebPDFActivity::class.java
        )
        intent.putExtra("pdf", pdf)
        startActivity(intent)
    }

}