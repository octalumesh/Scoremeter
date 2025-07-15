package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentMatchOddsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem
import com.cricbuzzplus.liveline.livedata.response.SliderImage
import com.cricbuzzplus.liveline.livedata.ui.adapter.MatchOddsInsideAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.MatchOddsOverAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnOverClick
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.InfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.cricbuzzplus.liveline.livedata.ui.adapter.MatchOddsNewOverAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import java.util.*

class MatchOddsFragment : BaseFragment(), OnOverClick {

    private lateinit var viewModel: InfoViewModel
    lateinit var binding: FragmentMatchOddsBinding
    var listAd: ArrayList<SliderImage> = arrayListOf()
    var matchStatus = ""
    var matchType = ""
    var matchId = 0

    var firstEnable = true
    var secondEnable = false
    var thirdEnable = false
    var FourEnable = false

    var timer: Timer? = null

    var firstInningList = arrayListOf<MatchOddsResponseItem>()
    var secondInningList = arrayListOf<MatchOddsResponseItem>()
    var overlist = arrayListOf<ArrayList<MatchOddsResponseItem>>()
    var overlist2 = arrayListOf<ArrayList<MatchOddsResponseItem>>()

    private var adapter: MatchOddsOverAdapter? = null
    private var adapterNew: MatchOddsNewOverAdapter? = null
    private var adapter2: MatchOddsInsideAdapter? = null

    var uiSet = false
    //var overlist: ArrayList<java.util.ArrayList<DataItem>> = ArrayList<java.util.ArrayList<DataItem>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        binding = FragmentMatchOddsBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        val bundle = arguments


        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            matchType = bundle.getString("matchType").toString()
        } else {
            Log.d("TAG", "bundle is null")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inningsShowMain()

        //   getData()

        if (matchType.equals("Test",true)){
            handleProgressLoader(false)

            binding.linearlayoutOddsNotShow.visibility = View.VISIBLE

        }else {

            callMatchOdds(matchId)
        }

        // callMatchOdds()

    }

    fun callMatchOdds(matchId: Int) {

       if (matchStatus.equals("Finished")) {
            if (isInternetConnection()) {
                viewModel.getMatchOddds(matchId)
            }
        } else {
            if (isInternetConnection()) {
                viewModel.getMatchOddds(matchId)
            }
            if (timer == null) {
                timer = Timer()
            }
            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    // recyclerViewMaincls();
                    if (uiSet) {
                        if (isInternetConnection()) {
                            viewModel.getMatchOddds(matchId)
                        }
                    }
                }
            }, 2000, 2000)
        }

    }


    fun getData() {

        if (listAd != null) {
            listAd.clear()
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
                        listAd.add(prediction)

                    }
                    setSlider(listAd)
                }

            }

    }

    fun setSlider(res: List<SliderImage>) {

        //    binding.viewPage.adapter= HomeSliderViewPagerAdapter(activity,res)


    }

    @SuppressLint("ResourceAsColor")
    private fun inningsShowMain() {
        binding.btnScoreboardInings1.setOnClickListener {
            adapterNew = null
            firstEnable = true
            secondEnable = false
            thirdEnable = false
            FourEnable = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnScoreboardInings1.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                binding.btnScoreboardInings2.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings3.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings4.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )

            }

            if (matchStatus.equals("Finished")){
                setAdapter(overlist)
            }
            // DataShowBtnClick1(jsonObject2);
        }
        binding.btnScoreboardInings2.setOnClickListener {
            adapterNew = null
            firstEnable = false
            secondEnable = true
            thirdEnable = false
            FourEnable = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnScoreboardInings2.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                binding.btnScoreboardInings1.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings3.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings4.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )

            }

            if (matchStatus.equals("Finished")){
                setAdapter(overlist2)
            }
            //  DataShowBtnClick2(jsonObject2);
        }
        binding.btnScoreboardInings3.setOnClickListener {
            adapterNew = null
            firstEnable = false
            secondEnable = false
            thirdEnable = true
            FourEnable = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnScoreboardInings3.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                binding.btnScoreboardInings2.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings1.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings4.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )

            }
            // DataShowBtnClick3(jsonObject2);
        }
        binding.btnScoreboardInings4.setOnClickListener {
            adapterNew = null
            firstEnable = false
            secondEnable = false
            thirdEnable = false
            FourEnable = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.btnScoreboardInings4.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )
                binding.btnScoreboardInings2.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings3.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )
                binding.btnScoreboardInings1.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.gray_dark
                        )
                    )

            }
            // DataShowBtnClick4(jsonObject2);
        }
    }

    /* fun callMatchOdds(){
        viewModel.getMatchOddds(matchId)
      }*/


    private fun setObservers() {
        // observeExtras()
        observeMatchOdds()
        observeDataFound()
    }

    fun observeDataFound() {
        viewModel.noDataLiveData.observe(viewLifecycleOwner, Observer {

            if (it) {
                binding.overLayout.visibility = View.VISIBLE
                binding.linearlayoutOddsNotShow.visibility = View.GONE
                handleProgressLoader(false)
            } else {
                binding.overLayout.visibility = View.GONE
                if (matchStatus.equals("Upcoming")) {
                    //handleProgressLoader(false)
                    binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                }else {
                    binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                    binding.noData.setText("No Data Found.")
                }
                handleProgressLoader(false)
            }

        })
    }


    private fun observeMatchOdds() {
        viewModel.getMatchOddsLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                // handleProgressLoader(false)

                if (!firstInningList.isNullOrEmpty()) {
                    firstInningList.clear()
                }

                if (!secondInningList.isNullOrEmpty()) {
                    secondInningList.clear()
                }

                if (!overlist.isNullOrEmpty()) {
                    overlist.clear()
                }
                if (!overlist2.isNullOrEmpty()) {
                    overlist2.clear()
                }

                for (item in it) {

                    if (item.inning == 1) {
                        firstInningList.add(item)
                    } else if (item.inning == 2) {
                        binding.btnScoreboardInings2.setVisibility(View.VISIBLE)
                        secondInningList.add(item)
                    }
                }
                //  Log.e("OverList", "onChanged: " +overlist.size());

                if (!secondInningList.isEmpty()) {

                    secondInningList.reverse()

                }

                if (firstEnable) {
                    if (!firstInningList.isEmpty()) {
                        
                        firstInningList.reverse()
                        
                        for (item in firstInningList) {
                            val over = item.overs?.toDouble()
                            val newover = over?.toInt()
                            item.newover = newover
                        }
                        val lastover = firstInningList[firstInningList.size - 1].newover
                        for (i in 0 .. lastover!!) {
                            var templist = ArrayList<MatchOddsResponseItem>()
                            for (item in firstInningList) {
                                if ((i == item.newover && !(i).toString().equals(item.overs)) || (i+1).toString().equals(item.overs)) {
                                    templist.add(item)
                                }
                            }
                            if (!templist.isNullOrEmpty()) {
                                overlist.add(templist)
                            }
                        }
                        setAdapter(overlist)
                    }
                } else {
                    if (secondEnable) {
                        if (!secondInningList.isEmpty()) {

                            secondInningList.reverse()
                            for (item in secondInningList) {
                                val over = item.overs?.toDouble()
                                val newover = over?.toInt()
                                item.newover = newover
                            }
                            val lastover1 = secondInningList[secondInningList.size - 1].newover
                            for (i in 0 .. lastover1!!) {
                                val templist = ArrayList<MatchOddsResponseItem>()
                                for (item in secondInningList) {
                                    if ((i == item.newover && !(i).toString().equals(item.overs)) || (i+1).toString().equals(item.overs)) {
                                        templist.add(item)
                                    }
                                }
                                if (!templist.isNullOrEmpty()) {
                                    overlist2.add(templist)
                                }
                            }
                            setAdapter(overlist2)
                        }
                    }
                }


                if (matchStatus.equals("Finished")) {
                    if (!secondInningList.isEmpty()) {
                        for (item in secondInningList) {
                            val over = item.overs?.toDouble()
                            val newover = over?.toInt()
                            item.newover = newover
                        }
                        val lastover1 = secondInningList[secondInningList.size - 1].newover
                        for (i in 0..lastover1!!) {
                            val templist = ArrayList<MatchOddsResponseItem>()
                            for (item in secondInningList) {
                                if ((i == item.newover && !(i).toString()
                                        .equals(item.overs)) || (i + 1).toString()
                                        .equals(item.overs)
                                ) {
                                    templist.add(item)
                                }
                            }
                            if (!templist.isNullOrEmpty()) {
                                overlist2.add(templist)
                            }
                        }

                    }

                }


            }

        })
    }


    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(activity,
            { isLoading -> handleProgressLoader(isLoading!!) })
        /*viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

    fun setAdapter(overlist: ArrayList<ArrayList<MatchOddsResponseItem>>) {
        try {
            /*if (adapter == null) {
                adapter = MatchOddsOverAdapter(overlist, context, overlist.size, this)
                binding.overRecycler.adapter = adapter
                binding.overRecycler.scrollToPosition(overlist.size - 1)
                onOverClick(overlist[overlist.size - 1])
            } else {
                adapter?.updateList(overlist, this)
            }*/

            if (adapterNew == null) {
                adapterNew = MatchOddsNewOverAdapter(overlist, requireContext(),object :OnClickInterface{
                    override fun onClick(newsId: Int) {
                        binding.overRecycler.scrollToPosition(newsId)
                    }

                })
                binding.overRecycler.adapter = adapterNew
                binding.overRecycler.scrollToPosition(overlist.size - 1)

            } else {
                adapterNew?.updateList(overlist)
            }


            uiSet = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        adapter2 = null

        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }

        uiSet  = false
    }

    override fun onOverClick(data: ArrayList<MatchOddsResponseItem>?) {

        if (adapter2 == null) {
            adapter2 = MatchOddsInsideAdapter(data, context)
            binding.recyclerOdds.adapter = adapter2
        } else {
            adapter2?.updateList(data)
        }

    }


}