package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentHighlightBinding
import com.cricbuzzplus.liveline.databinding.LiveFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.HighlightTypeModel
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.CommentaryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.InningsScoreListItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HighlightCommentaryCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.HighlightTypeSpinnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.InningSpinnerAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.LiveCommentaryCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class HighlightFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: FragmentHighlightBinding

    var matchId = 0
    var matchStatus = ""
    var result = ""
    var toss = ""

    var matchNo = ""
    var series = ""
    var matchIdCricbuzz = 0

    var typeHighlight = -1
    var inning = -1

    private var timer: Timer? = null

    var isUISet = false

    var typeList = arrayListOf<HighlightTypeModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = FragmentHighlightBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()


        val bundle = arguments


        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            result = bundle.getString("result").toString()
            matchNo = arguments?.getString("matchNo", "").toString()
            series = arguments?.getString("series", "").toString()
        } else {
            Log.d("TAG", "bundle is null")
        }

        typeList.add(HighlightTypeModel("All", 0))
        typeList.add(HighlightTypeModel("Fours", 2))
        typeList.add(HighlightTypeModel("Sixes", 4))
        typeList.add(HighlightTypeModel("Wickets", 8))
        typeList.add(HighlightTypeModel("Fifties", 16))
        typeList.add(HighlightTypeModel("Hundreds", 32))
        typeList.add(HighlightTypeModel("Dropped Catches", 128))
        typeList.add(HighlightTypeModel("UDRS", 512))
        typeList.add(HighlightTypeModel("Others", 1))

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!typeList.isNullOrEmpty()) {
            setSpinner()
        }

        if (matchStatus.equals("Upcoming")) {
            getMatchIdCricBuzzUpcoming(series, matchNo)
        } else if (matchStatus.equals("Finished")) {
            getMatchIdCricBuzzFinished(series, matchNo)
        } else if (matchStatus.equals("Live")) {
            getMatchIdCricBuzzLive(series, matchNo)
        }
    }

    fun setSpinner() {
        val adapter = HighlightTypeSpinnerAdapter(
            activity,
            typeList
        )

        binding.typeSpinner.adapter = adapter

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle the selected item here
                typeHighlight = typeList[position].type

                if (inning == -1) {

                } else {

                    //if (!matchStatus.equals("Live")) {
                        callScorecard()
                   // }
                }

                //Log.e(TAG, "onItemSelected: ${selectedItem}", )
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where no item is selected (optional)
            }
        }
    }


    fun callScorecard() {

        if (matchStatus.equals("Upcoming")) {
            //handleProgressLoader(false)
            binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
            binding.parent.visibility = View.GONE
        } else {

            if (isInternetConnection()) {
                viewModel.getMatchHighlightCricBuzz(matchIdCricbuzz, inning, typeHighlight)
            }

           /* if (matchStatus.equals("Finished")) {
                if (isInternetConnection()) {
                    viewModel.getMatchHighlightCricBuzz(matchIdCricbuzz, inning, typeHighlight)
                }
            } else if (matchStatus.equals("Live")) {
                if (isInternetConnection()) {
                    viewModel.getMatchHighlightCricBuzz(matchIdCricbuzz, inning, typeHighlight)
                }
                if (timer == null) {
                    timer = Timer()
                }

                timer!!.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        // recyclerViewMaincls();
                        if (isUISet) {
                            if (isInternetConnection()) {
                                viewModel.getMatchHighlightCricBuzz(
                                    matchIdCricbuzz,
                                    inning,
                                    typeHighlight
                                )
                            }
                        }
                    }
                }, 2000, 2000)
            }*/
            // callScorecard()
        }


    }


    fun callMatchCommentary() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getCommentaryCricBuzz(matchIdCricbuzz)
            }
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

        //  adapter = null
    }


    private fun setObservers() {
        observeMatchId()
        observeCommentary()
        observeHighlight()
        observeExtras()
    }

    fun observeHighlight() {

        viewModel.highlightCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.commentaryList.isNullOrEmpty()) {

               // handleProgressLoader(false)

                binding.linearlayoutOddsNotShow.visibility = View.GONE
                binding.recyclerCommentary.visibility = View.VISIBLE

                val adapterCommentary = HighlightCommentaryCricAdapter(
                    it.commentaryList as ArrayList<CommentaryListItem>,
                    activity
                )
                binding.recyclerCommentary.adapter = adapterCommentary

                isUISet = true

            } else {
               // handleProgressLoader(false)

                binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                binding.recyclerCommentary.visibility = View.GONE

                when(typeHighlight){

                    0->{
                        binding.noData.setText("No All in this Inning")
                    }

                    2->{
                        binding.noData.setText("No Fours in this Inning")
                    }

                    4->{
                        binding.noData.setText("No Sixes in this Inning")
                    }

                    8->{
                        binding.noData.setText("No Wickets in this Inning")
                    }

                    16->{
                        binding.noData.setText("No Fifties in this Inning")
                    }

                    32->{
                        binding.noData.setText("No Hundreds in this Inning")
                    }

                    128->{
                        binding.noData.setText("No Dropped Catches in this Inning")
                    }
                    512->{
                        binding.noData.setText("No UDRS in this Inning")
                    }
                    1->{
                        binding.noData.setText("No Others in this Inning")
                    }

                }



            }

        })

    }

    fun observeCommentary() {

        viewModel.liveCommentaryCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it.miniscore != null) {
                if (it.miniscore.matchScoreDetails != null && !it.miniscore.matchScoreDetails.inningsScoreList.isNullOrEmpty()) {


                    val adapter = InningSpinnerAdapter(
                        activity,
                        it.miniscore.matchScoreDetails.inningsScoreList as List<InningsScoreListItem>
                    )

                    binding.inningsSpinner.adapter = adapter

                    binding.inningsSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                // Handle the selected item here
                                if (inning == -1) {
                                    inning =
                                        it.miniscore.matchScoreDetails.inningsScoreList[position].inningsId!!

                                    callScorecard()
                                } else {
                                    inning =
                                        it.miniscore.matchScoreDetails.inningsScoreList[position].inningsId!!

                                    if (typeHighlight != -1) {

                                       // if (!matchStatus.equals("Live")) {
                                            callScorecard()
                                        //}
                                    }
                                }


                                // Do something with the selected item
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // Handle case where no item is selected (optional)
                            }
                        }

                }
                else{
                   // handleProgressLoader(false)
                    binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                    binding.parent.visibility = View.GONE
                }
            }else{
              //  handleProgressLoader(false)
                binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                binding.parent.visibility = View.GONE
            }

        })

    }


    fun observeMatchId() {

        matchIdCricLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                matchIdCricbuzz = it
               // Log.e(TAG, "onViewCreated:matchid crci ${matchIdCricbuzz}")
                callMatchCommentary()
            }
        })

    }

    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }

}