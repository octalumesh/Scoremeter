package com.cricbuzzplus.liveline.livedata.ui.fragment.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentLeaderBoardBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel


class LeaderBoardFragment : BaseFragment() {

    private lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentLeaderBoardBinding


    var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)


        val bundle = arguments

        hideKeyBoard()
        setObservers()

        if (bundle != null) {
            userId = bundle.getInt("userId")
        } else {
            Log.d("TAG", "bundle is null")
        }

        callMyProfile()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.matchExperts.setOnClickListener {
            binding.matchExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.colorAccent
                    )
                )
            )
            binding.tossExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
            )

            binding.matchExperts.setTextColor(activity.getResources().getColor(R.color.white))
            binding.tossExperts.setTextColor(activity.getResources().getColor(R.color.black))

            binding.matchList.visibility= View.VISIBLE
            binding.tossList.visibility= View.GONE
        }

        binding.tossExperts.setOnClickListener {
            binding.tossExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.colorAccent
                    )
                )
            )
            binding.matchExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
            )


            binding.matchExperts.setTextColor(activity.getResources().getColor(R.color.black))
            binding.tossExperts.setTextColor(activity.getResources().getColor(R.color.white))

            binding.matchList.visibility= View.GONE
            binding.tossList.visibility= View.VISIBLE

        }

    }



    fun callMyProfile(){

        if (checkForInternet(activity)) {
            viewModel.getExpertProfile(userId)
        }
    }


    private fun setObservers(){
        observeProfile()
        observeExtras()
    }



    private fun observeProfile(){

        viewModel.expertProfileLiveData.observe(viewLifecycleOwner, Observer {

            if (it.points != null) {
                if (it.points >= 7 ) {
                    binding.firstZonePriceMatch.setText("Silver Zone => 7 Match Pass = ${it.prize?.firstPrize}/-)")
                    binding.firstZonePassMatch.visibility = View.VISIBLE

                    binding.checkUncheckFirstMatch.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlFirstMatch.setBackgroundResource(R.drawable.leaderboard_gradiant_green)

                }else{

                    binding.firstZonePriceMatch.setText("Silver Zone => 7 Match Pass = ${it.prize?.firstPrize}/-)")
                    binding.firstZonePassMatch.visibility = View.GONE

                    binding.checkUncheckFirstMatch.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlFirstMatch.setBackgroundResource(R.drawable.leaderboard_unselect)

                }


                if (it.points >= 15) {

                    binding.secondZonePriceMatch.setText("Gold Zone => 15 Match Pass = ${it.prize?.secondPrize}/-)")
                    binding.secondZonePassMatch.visibility = View.VISIBLE

                    binding.checkUncheckSecondMatch.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlSecondMatch.setBackgroundResource(R.drawable.leaderboard_gradiant_green)

                }else{
                    binding.secondZonePriceMatch.setText("Gold Zone => 15 Match Pass = ${it.prize?.secondPrize}/-)")
                    binding.secondZonePassMatch.visibility = View.GONE

                    binding.checkUncheckSecondMatch.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlSecondMatch.setBackgroundResource(R.drawable.leaderboard_unselect)
                }

                 if (it.points >= 20 ) {
                     binding.thirdZonePriceMatch.setText("Platinum Zone => 20 Match Pass = ${it.prize?.thirdPrize}/-)")
                     binding.thirdZonePassMatch.visibility = View.VISIBLE

                     binding.checkUncheckThirdMatch.setImageResource(R.drawable.ic_baseline_check_circle_24)

                     binding.rlThirdMatch.setBackgroundResource(R.drawable.leaderboard_gradiant_green)
                }else{
                     binding.thirdZonePriceMatch.setText("Platinum Zone => 20 Match Pass = ${it.prize?.thirdPrize}/-)")
                     binding.thirdZonePassMatch.visibility = View.GONE

                     binding.checkUncheckThirdMatch.setImageResource(R.drawable.ic_baseline_cancel_24)

                     binding.rlThirdMatch.setBackgroundResource(R.drawable.leaderboard_unselect)
                 }

                 if (it.points >= 25) {
                     binding.fourthZonePriceMatch.setText("Diamond Zone => 25 Match Pass = ${it.prize?.fourthPrize}/-)")
                     binding.fourthZonePassMatch.visibility = View.VISIBLE

                     binding.checkUncheckFourthMatch.setImageResource(R.drawable.ic_baseline_check_circle_24)

                     binding.rlFourthMatch.setBackgroundResource(R.drawable.leaderboard_gradiant_green)
                }else{
                     binding.fourthZonePriceMatch.setText("Diamond Zone => 25 Match Pass = ${it.prize?.fourthPrize}/-)")
                     binding.fourthZonePassMatch.visibility = View.GONE

                     binding.checkUncheckFourthMatch.setImageResource(R.drawable.ic_baseline_cancel_24)

                     binding.rlFourthMatch.setBackgroundResource(R.drawable.leaderboard_unselect)
                 }
            }



            if (it.tossPoint != null) {
                if (it.tossPoint >= 7 ) {
                    binding.firstZonePriceToss.setText("Silver Zone => 7 Toss Pass = ${it.prize?.firstPrize}/-)")
                    binding.firstZonePassToss.visibility = View.VISIBLE

                    binding.checkUncheckFirstToss.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlFirstToss.setBackgroundResource(R.drawable.leaderboard_gradiant_green)

                }else{

                    binding.firstZonePriceToss.setText("Silver Zone => 7 Toss Pass = ${it.prize?.firstPrize}/-)")
                    binding.firstZonePassToss.visibility = View.GONE

                    binding.checkUncheckFirstToss.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlFirstToss.setBackgroundResource(R.drawable.leaderboard_unselect)

                }


                if (it.tossPoint >= 15) {

                    binding.secondZonePriceToss.setText("Gold Zone => 15 Toss Pass = ${it.prize?.secondPrize}/-)")
                    binding.secondZonePassToss.visibility = View.VISIBLE

                    binding.checkUncheckSecondToss.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlSecondToss.setBackgroundResource(R.drawable.leaderboard_gradiant_green)

                }else{
                    binding.secondZonePriceToss.setText("Gold Zone => 15 Toss Pass = ${it.prize?.secondPrize}/-)")
                    binding.secondZonePassToss.visibility = View.GONE

                    binding.checkUncheckSecondToss.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlSecondToss.setBackgroundResource(R.drawable.leaderboard_unselect)
                }

                if (it.tossPoint >= 20 ) {
                    binding.thirdZonePriceToss.setText("Platinum Zone => 20 Toss Pass = ${it.prize?.thirdPrize}/-)")
                    binding.thirdZonePassToss.visibility = View.VISIBLE

                    binding.checkUncheckThirdToss.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlThirdToss.setBackgroundResource(R.drawable.leaderboard_gradiant_green)
                }else{
                    binding.thirdZonePriceToss.setText("Platinum Zone => 20 Toss Pass = ${it.prize?.thirdPrize}/-)")
                    binding.thirdZonePassToss.visibility = View.GONE

                    binding.checkUncheckThirdToss.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlThirdToss.setBackgroundResource(R.drawable.leaderboard_unselect)
                }

                if (it.tossPoint >= 25) {
                    binding.fourthZonePriceToss.setText("Diamond Zone => 25 Toss Pass = ${it.prize?.fourthPrize}/-)")
                    binding.fourthZonePassToss.visibility = View.VISIBLE

                    binding.checkUncheckFourthToss.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    binding.rlFourthToss.setBackgroundResource(R.drawable.leaderboard_gradiant_green)
                }else{
                    binding.fourthZonePriceToss.setText("Diamond Zone => 25 Toss Pass = ${it.prize?.fourthPrize}/-)")
                    binding.fourthZonePassToss.visibility = View.GONE

                    binding.checkUncheckFourthToss.setImageResource(R.drawable.ic_baseline_cancel_24)

                    binding.rlFourthToss.setBackgroundResource(R.drawable.leaderboard_unselect)
                }
            }


        })

    }

    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(viewLifecycleOwner,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(viewLifecycleOwner, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }


}