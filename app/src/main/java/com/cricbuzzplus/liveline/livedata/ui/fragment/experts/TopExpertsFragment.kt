package com.cricbuzzplus.liveline.livedata.ui.fragment.experts

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentTopExpertsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.newresponse.MatchListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.ExpertsProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.TopExpertsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnExpertsClick
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.utils.Constants


class TopExpertsFragment : BaseFragment() {

    private lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentTopExpertsBinding

    var type = "toss"

    var matchList = arrayListOf<MatchListItem>()
    var tossList = arrayListOf<MatchListItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = FragmentTopExpertsBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callTopExperts()

        binding.matchExperts.setOnClickListener {
            binding.matchExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
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


            type = "match"

            if (!matchList.isNullOrEmpty()) {

                var matchLists = arrayListOf<MatchListItem>()

                for (index in matchList.indices) {

                    if (index == 0) {
                        val model = matchList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.firstExpertImage)
                        binding.firstExpertName.setText("" + model.firstName)
                        binding.firstExpertPoints.setText("" + model.points+" Pts.")
                        binding.firstExpertAccuracy.setText("" + model.predicationPrecentMatch + "%")

                    } else if (index == 1) {
                        val model = matchList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.secondExpertImage)
                        binding.secondExpertName.setText("" + model.firstName)
                        binding.secondExpertPoints.setText("" + model.points+" Pts.")
                        binding.secondExpertAccuracy.setText("" + model.predicationPrecentMatch + "%")

                    } else if (index == 2) {
                        val model = matchList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.thirdExpertImage)
                        binding.thirdExpertName.setText("" + model.firstName)
                        binding.thirdExpertPoints.setText("" + model.points+" Pts.")
                        binding.thirdExpertAccuracy.setText("" + model.predicationPrecentMatch + "%")
                    } else {

                        matchLists.add(matchList[index])

                    }

                }

                if (!matchLists.isNullOrEmpty()) {

                    binding.recyclerExperts.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                    binding.recyclerExperts.adapter = TopExpertsAdapter(
                        matchLists, activity, "match", object : OnExpertsClick {
                            override fun onClick(userId: Int) {

                                val intent = Intent(activity, ExpertsProfileActivity::class.java)
                                intent.putExtra("userId", userId)
                                startActivity(intent)

                            }

                        })

                } else {
                    binding.recyclerExperts.visibility = View.GONE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                }

            } else {
                binding.recyclerExperts.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }


        }

        binding.tossExperts.setOnClickListener {
            binding.tossExperts.setBackgroundTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.yellow_lgt
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

            callTopExperts()

            binding.matchExperts.setTextColor(activity.getResources().getColor(R.color.black))
            binding.tossExperts.setTextColor(activity.getResources().getColor(R.color.white))

            type = "toss"

            /*if (!tossList.isNullOrEmpty()) {

                var tossLists = arrayListOf<MatchListItem>()

                for (index in tossList.indices) {

                    if (index == 0) {
                        val model = tossList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.firstExpertImage)
                        binding.firstExpertName.setText("" + model.firstName)
                        binding.firstExpertPoints.setText("" + model.points+" Pts.")
                        binding.firstExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")

                    } else if (index == 1) {
                        val model = tossList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.secondExpertImage)
                        binding.secondExpertName.setText("" + model.firstName)
                        binding.secondExpertPoints.setText("" + model.points)
                        binding.secondExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")

                    } else if (index == 2) {
                        val model = tossList[index]

                        Glide.with(activity).load("" + Constants.ImgURl + model.profilepicture)
                            .placeholder(R.mipmap.ic_launcher_round).into(binding.thirdExpertImage)
                        binding.thirdExpertName.setText("" + model.firstName)
                        binding.thirdExpertPoints.setText("" + model.points)
                        binding.thirdExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")
                    } else {

                        tossLists.add(tossList[index])

                    }

                }

                if (!tossLists.isNullOrEmpty()) {

                    binding.recyclerExperts.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                    binding.recyclerExperts.adapter = TopExpertsAdapter(
                        tossLists, activity, "toss", object : OnExpertsClick {
                            override fun onClick(userId: Int) {

                                val intent = Intent(activity, ExpertsProfileActivity::class.java)
                                intent.putExtra("userId", userId)
                                startActivity(intent)

                            }

                        })

                } else {
                    binding.recyclerExperts.visibility = View.GONE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                }

            } else {
                binding.recyclerExperts.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }*/


        }
    }


    private fun callTopExperts() {

        if (checkForInternet(activity)) {
            viewModel.getTopExperts()
        }
    }


    private fun setObservers() {
        observePrediction()
        observeExtras()
    }


    private fun observePrediction() {

        viewModel.topExpertsLiveData.observe(viewLifecycleOwner, Observer {

            try {

                tossList.clear()
                matchList.clear()

                Glide.with(activity).load("").placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.firstExpertImage)
                binding.firstExpertName.setText("")
                binding.firstExpertPoints.setText("")
                binding.firstExpertAccuracy.setText("0%")

                Glide.with(activity).load("").placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.secondExpertImage)
                binding.secondExpertName.setText("")
                binding.secondExpertPoints.setText("")
                binding.secondExpertAccuracy.setText("0%")

                Glide.with(activity).load("").placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.thirdExpertImage)
                binding.thirdExpertName.setText("")
                binding.thirdExpertPoints.setText("")
                binding.thirdExpertAccuracy.setText("0%")

                if (!it.matchList.isNullOrEmpty() || !it.tossList.isNullOrEmpty()) {

                    //binding.parent.visibility = View.VISIBLE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                    /* if (!it.matchList.isNullOrEmpty()){

                         matchList.addAll(it.matchList  as ArrayList<MatchListItem>)
                         var matchLists = arrayListOf<MatchListItem>()

                         for (index in it.matchList.indices) {

                             if (index == 0){
                                 val model = it.matchList[index]

                                 Glide.with(activity).load(""+Constants.ImgURl+model.profilepicture).placeholder(R.mipmap.ic_launcher_round).into(binding.firstExpertImage)
                                 binding.firstExpertName.setText(""+model.firstName)
                                 binding.firstExpertPoints.setText(""+model.points)
                                 binding.firstExpertAccuracy.setText(""+model.predicationPrecentMatch+"%")

                             }else if (index == 1){
                                 val model = it.matchList[index]

                                 Glide.with(activity).load(""+Constants.ImgURl+model.profilepicture).placeholder(R.mipmap.ic_launcher_round).into(binding.secondExpertImage)
                                 binding.secondExpertName.setText(""+model.firstName)
                                 binding.secondExpertPoints.setText(""+model.points)
                                 binding.secondExpertAccuracy.setText(""+model.predicationPrecentMatch+"%")

                             }else if (index == 2){
                                 val model = it.matchList[index]

                                 Glide.with(activity).load(""+Constants.ImgURl+model.profilepicture).placeholder(R.mipmap.ic_launcher_round).into(binding.thirdExpertImage)
                                 binding.thirdExpertName.setText(""+model.firstName)
                                 binding.thirdExpertPoints.setText(""+model.points)
                                 binding.thirdExpertAccuracy.setText(""+model.predicationPrecentMatch+"%")
                             }
                             else{

                                 matchLists.add(it.matchList[index])

                             }

                         }


                         if (!matchLists.isNullOrEmpty()) {

                             binding.recyclerExperts.adapter = TopExpertsAdapter(
                                 matchLists, activity, "match", object : OnExpertsClick {
                                     override fun onClick(userId: Int) {

                                         val intent =
                                             Intent(activity, ExpertsProfileActivity::class.java)
                                         intent.putExtra("userId", userId)
                                         startActivity(intent)

                                     }

                                 })

                         }else {
                             binding.recyclerExperts.visibility = View.GONE
                             binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                         }

                     }else {
                         binding.recyclerExperts.visibility = View.GONE
                         binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                     }*/



                    if (!it.tossList.isNullOrEmpty()) {

                        tossList.addAll(it.tossList as ArrayList<MatchListItem>)
                        var tossLists = arrayListOf<MatchListItem>()

                        for (index in it.tossList.indices) {

                            if (index == 0) {
                                val model = it.tossList[index]

                                Glide.with(activity)
                                    .load("" + Constants.ImgURl + model.profilepicture)
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(binding.firstExpertImage)
                                binding.firstExpertName.setText("" + model.firstName)
                                binding.firstExpertPoints.setText("" + model.points+" Pts.")
                                binding.firstExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")

                            } else if (index == 1) {
                                val model = it.tossList[index]

                                Glide.with(activity)
                                    .load("" + Constants.ImgURl + model.profilepicture)
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(binding.secondExpertImage)
                                binding.secondExpertName.setText("" + model.firstName)
                                binding.secondExpertPoints.setText("" + model.points+" Pts.")
                                binding.secondExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")

                            } else if (index == 2) {
                                val model = it.tossList[index]

                                Glide.with(activity)
                                    .load("" + Constants.ImgURl + model.profilepicture)
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(binding.thirdExpertImage)
                                binding.thirdExpertName.setText("" + model.firstName)
                                binding.thirdExpertPoints.setText("" + model.points+" Pts.")
                                binding.thirdExpertAccuracy.setText("" + model.predicationPrecenttoss + "%")
                            } else {

                                tossLists.add(it.tossList[index])

                            }

                        }


                        if (!tossLists.isNullOrEmpty()) {

                            binding.recyclerExperts.adapter = TopExpertsAdapter(
                                tossLists, activity, "toss", object : OnExpertsClick {
                                    override fun onClick(userId: Int) {

                                        val intent =
                                            Intent(activity, ExpertsProfileActivity::class.java)
                                        intent.putExtra("userId", userId)
                                        startActivity(intent)
                                    }
                                })

                        } else {
                            binding.recyclerExperts.visibility = View.GONE
                            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                        }

                    } else {

                        binding.recyclerExperts.visibility = View.GONE
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    }

                    if (!it.matchList.isNullOrEmpty()) {
                        matchList.addAll(it.matchList as ArrayList<MatchListItem>)
                    }
                } else {
                    //binding.parent.visibility = View.GONE
                    // binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                //  binding.parent.visibility = View.GONE
                // binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                e.printStackTrace()
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