package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentCommentaryBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.OverResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.CommentaryRecyclerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import java.util.*


class CommentaryFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding : FragmentCommentaryBinding

    var matchStatus = ""
    var teamA = ""
    var teamB = ""
    var matchId = 0

    var allCommentary = true
    var overCommentary = false
    var wicketCommentary = false
    var fourCommentary = false
    var sixCommentary = false

    var firstInningCommentary = false
    var secondInningCommentary = false
    var thirdInningCommentary = false
    var fourthInningCommentary = false

    var commentaryDataList = arrayListOf<OverResponseItem>()
    var commentaryOverDataList = arrayListOf<OverResponseItem>()
    var commentaryWicketDataList = arrayListOf<OverResponseItem>()
    var commentarySixDataList = arrayListOf<OverResponseItem>()
    var commentaryFourDataList = arrayListOf<OverResponseItem>()
    var firstInningDataList = arrayListOf<OverResponseItem>()
    var secondInningDataList = arrayListOf<OverResponseItem>()
    var thirdInningDataList = arrayListOf<OverResponseItem>()
    var fourthInningDataList = arrayListOf<OverResponseItem>()

   // var commentaryDataList = arrayListOf<OverResponseItem>()
    var adapter : CommentaryRecyclerAdapter?= null

    private var timer: Timer? = null

    var isUISet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = FragmentCommentaryBinding.inflate(inflater, container, false)

        matchStatus = arguments?.getString("matchStatus","").toString()
        matchId = arguments?.getInt("matchId")!!

        hideKeyBoard()
        setObservers()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.commentaryAll.setOnClickListener {
            allCommentary = true
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

             binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
             binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

             binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
             binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            allCommentary()
        }

        binding.commentaryOvers.setOnClickListener {
            allCommentary = false
            overCommentary = true
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            overCommentary()
        }

        binding.commentaryFours.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = true
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            fourCommentary()
        }

        binding.commentarySix.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = true
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
           
            sixCommentary()
        }

        binding.commentaryWicket.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = true
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            wicketCommentary()
        }


        binding.firstInning.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = true
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            firstInningCommentary()
        }

        binding.secondInning.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = true
            thirdInningCommentary = false
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            secondInningCommentary()
        }

        binding.thirdInning.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = true
            fourthInningCommentary = false

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            
            thirdInningCommentary()
        }

        binding.fourthInning.setOnClickListener {
            allCommentary = false
            overCommentary = false
            wicketCommentary = false
            fourCommentary = false
            sixCommentary = false
            firstInningCommentary = false
            secondInningCommentary = false
            thirdInningCommentary = false
            fourthInningCommentary = true

            binding.commentaryAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryFours.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryOvers.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentarySix.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.commentaryWicket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))

            binding.firstInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.thirdInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            binding.fourthInning.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            
            fourthInningCommentary()
        }


        try {
            callMatchCommentary()

        }catch (e :Exception){
            Log.e(TAG, "onViewCreated: ${e.message}" )
        }

    }

    fun callMatchCommentary(){


        if (isInternetConnection()) {
            viewModel.getMatchCommentary(matchId)
        }
        if (matchStatus.equals("Live")){

            if (timer == null) {
                timer = Timer()
            }

            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    // recyclerViewMaincls();
                    if (isUISet) {
                        if (isInternetConnection()) {
                            viewModel.getMatchCommentary(matchId)
                        }
                    }
                }
            }, 2000, 2000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
            isUISet = false
        }

        if (adapter != null){
            adapter = null
        }
    }

    private fun setObservers(){
        observeLoader()
        observeCommentary()
    }

    private fun observeCommentary() {
        viewModel.getCommentaryLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.commentaryLayout.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.setVisibility(View.GONE)

                commentaryDataList.clear()
                commentaryOverDataList.clear()
                commentaryWicketDataList.clear()
                commentarySixDataList.clear()
                commentaryFourDataList.clear()

                firstInningDataList.clear()
                secondInningDataList.clear()
                thirdInningDataList.clear()
                fourthInningDataList.clear()

                for ( data : Map.Entry<String, LinkedHashMap<String, List<OverResponseItem>>> in it) {

                    val innigName: String = data.key
                    val inningData: LinkedHashMap<String, List<OverResponseItem>> = data.value

                    // Log("TAG11""observeCommentary:${innigNa )

                    for (innings: Map.Entry<String, List<OverResponseItem>> in inningData.entries) {
                        val overName = innings.key
                        val overData = innings.value
                        // oversName.add(overName)
                        commentaryDataList.addAll(overData)

                        //  Log.e("TAG11", "observeCommentary: ${overName} ${overData}")
                        // Log.e("TAG11", );
                    }

                }


/*
                    for (OverResponseItem data1 : oversData) {
                        Log.e("TAG11", "onChanged: " + data1.getData().getOvers());
                    }

                    for (String data1 : oversName) {
                        Log.e("TAG11", "oversName: " + data1);
                    }*/binding.progressBar.visibility = View.GONE

                for (overResponseItem in commentaryDataList) {
                    if (overResponseItem.data?.team != null) {
                        commentaryOverDataList.add(overResponseItem)
                    } else if (overResponseItem.data?.wicket.equals("1")) {
                        commentaryWicketDataList.add(overResponseItem)
                    } else if (overResponseItem.data?.runs.equals("4")) {
                        commentaryFourDataList.add(overResponseItem)
                    } else if (overResponseItem.data?.runs.equals("6")) {
                        commentarySixDataList.add(overResponseItem)
                    }
                    if (overResponseItem.inning == 1) {
                        firstInningDataList.add(overResponseItem)
                    } else if (overResponseItem.inning == 2) {
                        binding.secondInning.visibility = View.VISIBLE
                        secondInningDataList.add(overResponseItem)
                    } else if (overResponseItem.inning == 3) {
                        binding.thirdInning.visibility = View.VISIBLE
                        thirdInningDataList.add(overResponseItem)
                    } else if (overResponseItem.inning == 4) {
                        binding.fourthInning.visibility = View.VISIBLE
                        fourthInningDataList.add(overResponseItem)
                    }
                }

                if (allCommentary) {
                    allCommentary()
                } else if (overCommentary) {
                    overCommentary()
                } else if (wicketCommentary) {
                    wicketCommentary()
                } else if (fourCommentary) {
                    fourCommentary()
                } else if (sixCommentary) {
                    sixCommentary()
                } else if (firstInningCommentary) {
                    firstInningCommentary()
                } else if (secondInningCommentary) {
                    secondInningCommentary()
                } else if (thirdInningCommentary) {
                    thirdInningCommentary()
                } else if (fourthInningCommentary) {
                    fourthInningCommentary()
                }

               // allCommentary()
            }
        })
    }

    fun allCommentary() {
        if (commentaryDataList != null) {
            if (adapter == null) {
                adapter = CommentaryRecyclerAdapter(activity, commentaryDataList)
                binding.recyclerCommentary.adapter = adapter
            } else {
                adapter?.updateList(commentaryDataList)
            }

            isUISet = true
        }
    }


fun overCommentary() {
    if (commentaryOverDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity, commentaryOverDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(commentaryOverDataList)
        }
    }
}

fun wicketCommentary() {
    if (commentaryWicketDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity, commentaryWicketDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(commentaryWicketDataList)
        }
    }
}

fun fourCommentary() {
    if (commentaryFourDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,commentaryFourDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(commentaryFourDataList)
        }
    }
}

fun sixCommentary() {
    if (commentarySixDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,commentarySixDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(commentarySixDataList)
        }
    }
}

fun firstInningCommentary() {
    if (firstInningDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,firstInningDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(firstInningDataList)
        }
    }
}

fun secondInningCommentary() {
    if (secondInningDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,secondInningDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(secondInningDataList)
        }
    }
}

fun thirdInningCommentary() {
    if (thirdInningDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,thirdInningDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(thirdInningDataList)
        }
    }
}

fun fourthInningCommentary() {
    if (fourthInningDataList != null) {
        if (adapter == null) {
            adapter = CommentaryRecyclerAdapter(activity,fourthInningDataList)
            binding.recyclerCommentary.setAdapter(adapter)
        } else {
            adapter?.updateList(fourthInningDataList)
        }
    }
}

    private fun observeLoader() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner, Observer {

            if (it) {

                binding.progressBar.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE

            }else{
                if (!binding.recyclerCommentary.isVisible) {
                    binding.progressBar.visibility = View.GONE
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE
                    binding.recyclerCommentary.visibility = View.VISIBLE
                }
            }
        })
    }

}