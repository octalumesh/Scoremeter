package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentGraphBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.BarGraphModel
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.InfoViewModel
import java.util.*

class GraphFragment : BaseFragment(), OnChartValueSelectedListener {

    private lateinit var viewModel: InfoViewModel
    lateinit var binding: FragmentGraphBinding

    var firstInningList = arrayListOf<MatchOddsResponseItem>()
    var secondInningList = arrayListOf<MatchOddsResponseItem>()
    var overlist = arrayListOf<ArrayList<MatchOddsResponseItem>>()
    var overlist2nd = arrayListOf<ArrayList<MatchOddsResponseItem>>()

    var matchStatus = ""
    var matchType = ""
    var matchId = 0

    var uiSet = false

    var timer: Timer? = null

    var score = 20.0f
    var teamA = "inning 1"
    var teamB = "inning 2"

    var teamAName = ""
    var teamBName = ""

    var teamAShort = ""
    var teamBShort = ""

    private val firstInningBarModel = arrayListOf<BarGraphModel>()
    private val secondInningBarModel = arrayListOf<BarGraphModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        binding = FragmentGraphBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        val bundle = arguments


        if (bundle != null) {
            matchId = bundle.getInt("matchId")
            matchStatus = bundle.getString("matchStatus").toString()
            matchType = bundle.getString("matchType").toString()
            teamAName = bundle.getString("teamA").toString()
            teamBName = bundle.getString("teamB").toString()
            teamAShort = bundle.getString("teamAShort").toString()
            teamBShort = bundle.getString("teamBShort").toString()
        } else {
            Log.e("TAG", "bundle is null")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (matchType.equals("Test", true)) {
            handleProgressLoader(false)

            binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
            binding.parentLayout.visibility = View.GONE

        }
        else {
            binding.linearlayoutOddsNotShow.visibility = View.GONE
            binding.parentLayout.visibility = View.VISIBLE
            callMatchOdds(matchId)
        }

        // callMatchOdds(matchId)

        binding.wormGraph.setOnClickListener {
            binding.chart.visibility = View.VISIBLE
            binding.frameWormChart.visibility = View.VISIBLE
            binding.barChart.visibility = View.GONE
            binding.frameBarChart.visibility = View.GONE
            binding.oddsChart.visibility = View.GONE
            binding.frameOddsChart.visibility = View.GONE
            binding.firstInning.visibility = View.GONE
            binding.secondInning.visibility = View.GONE
            binding.firstInningOdds.visibility = View.GONE
            binding.secondInningOdds.visibility = View.GONE

            binding.wormGraph.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.manhattanGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )
            binding.runRateGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )

        }

        binding.manhattanGraph.setOnClickListener {
            binding.chart.visibility = View.GONE
            binding.frameWormChart.visibility = View.GONE
            binding.barChart.visibility = View.VISIBLE
            binding.frameBarChart.visibility = View.VISIBLE
            binding.oddsChart.visibility = View.GONE
            binding.frameOddsChart.visibility = View.GONE
            binding.firstInningOdds.visibility = View.GONE
            binding.secondInningOdds.visibility = View.GONE

            if (!firstInningBarModel.isNullOrEmpty()) {
                binding.firstInning.visibility = View.VISIBLE
            }

            if (!secondInningBarModel.isNullOrEmpty()) {
                binding.secondInning.visibility = View.VISIBLE
            }

            binding.manhattanGraph.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.wormGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )
            binding.runRateGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )

        }

        binding.runRateGraph.setOnClickListener {
            binding.chart.visibility = View.GONE
            binding.frameWormChart.visibility = View.GONE
            binding.barChart.visibility = View.GONE
            binding.frameBarChart.visibility = View.GONE
            binding.oddsChart.visibility = View.VISIBLE
            binding.frameOddsChart.visibility = View.VISIBLE
            binding.firstInning.visibility = View.GONE
            binding.secondInning.visibility = View.GONE

            if (!firstInningList.isNullOrEmpty()) {
                binding.firstInningOdds.visibility = View.VISIBLE
            }

            if (!secondInningList.isNullOrEmpty()) {
                binding.secondInningOdds.visibility = View.VISIBLE
            }

            binding.runRateGraph.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.wormGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )
            binding.manhattanGraph.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )

        }

        binding.firstInning.setOnClickListener { // binding.chart.setVisibility(View.VISIBLE);
            // binding.barChart.setVisibility(View.GONE);

            binding.firstInning.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_lgt))
            binding.secondInning.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_dark
                )
            )


            barGraph(firstInningBarModel as ArrayList<BarGraphModel>, 1)
        }

        binding.secondInning.setOnClickListener {

            binding.secondInning.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_lgt
                    )
                )
            binding.firstInning.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_dark
                    )
                )


            binding.barChart.clear()
            barGraph(secondInningBarModel as ArrayList<BarGraphModel>, 2)
        }

        binding.firstInningOdds.setOnClickListener {

            binding.firstInningOdds.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_lgt
                    )
                )
            binding.secondInningOdds.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_dark
                    )
                )


            setOddsData(firstInningList)
        }

        binding.secondInningOdds.setOnClickListener {


            binding.secondInningOdds.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_lgt
                    )
                )
            binding.firstInningOdds.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_dark
                    )
                )


            binding.oddsChart.clear()
            setOddsData(secondInningList)
        }


    }

    fun callMatchOdds(matchId: Int) {

        if (isInternetConnection()) {
            viewModel.getMatchOddds(matchId)
        }

        /*if (matchStatus.equals("Finished")) {
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
        }*/

    }


    private fun setObservers() {
        // observeExtras()
        observeMatchOdds()
        observeDataFound()
    }

    fun observeDataFound() {
        viewModel.noDataLiveData.observe(viewLifecycleOwner, Observer {

            if (it) {
                binding.parentLayout.visibility = View.VISIBLE
                binding.linearlayoutOddsNotShow.visibility = View.GONE
                handleProgressLoader(false)
            } else {
                binding.parentLayout.visibility = View.GONE
                if (matchStatus.equals("Upcoming")) {
                    //handleProgressLoader(false)
                    binding.linearlayoutOddsNotShow.visibility = View.VISIBLE
                } else {
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

                if (!overlist2nd.isNullOrEmpty()) {
                    overlist2nd.clear()
                }

                for (item in it) {

                    if (item.inning == 1) {
                        firstInningList.add(item)
                    } else if (item.inning == 2) {
                        // binding.btnScoreboardInings2.setVisibility(View.VISIBLE)
                        secondInningList.add(item)
                    }
                }


                //  Log.e("OverList", "onChanged: " +overlist.size());
                if (!firstInningList.isEmpty()) {

                    firstInningList.reverse()

                    for (item in firstInningList) {
                        if (!item.overs.isNullOrEmpty()) {
                            val over: Double? = item.overs.toString().toDouble()
                            val newover = Math.ceil(over!!).toInt()
                            item.newover = newover
                        }
                    }
                    val lastover: Int? = firstInningList[firstInningList.size - 1].newover
                    for (i in 1..lastover!!) {
                        val templist: ArrayList<MatchOddsResponseItem> =
                            ArrayList<MatchOddsResponseItem>()
                        for (item in firstInningList) {
                            if (i == item.newover) {
                                templist.add(item)
                            }
                        }
                        overlist.add(templist)
                    }
                    for (i in overlist.indices) {
                        val templist: ArrayList<MatchOddsResponseItem> = overlist[i]
                        var run = 0
                        var wicket = 0
                        val over = i + 1
                        for (item in templist) {
                            if (!item.runs.isNullOrEmpty()) {
                                if (item.runs.length > 1) {
                                    if (item.runs.equals("WK")) {
                                        wicket += 1
                                    } else if (item.runs.contains("New Batter")) {
                                        wicket += 1
                                    } else if (item.runs.contains("New Bat")) {
                                        wicket += 1
                                    } else if (item.runs.contains("Bowled")) {
                                        wicket += 1
                                    } else if (item.runs.contains("NEW BAT", true)) {
                                        wicket += 1
                                    } else if (item.runs.contains("BAT", true)) {
                                        wicket += 1
                                    }
                                } else {
                                    if (item.runs.equals("W")) {
                                        wicket += 1
                                    } else {
                                        run += item.runs.toInt()
                                    }
                                }
                            }
                        }
                        val model = BarGraphModel(run, over, wicket)
                        firstInningBarModel.add(model)
                    }
                    // setAdapter(overlist);
                }


                if (!secondInningList.isEmpty()) {
                    secondInningList.reverse()

                    for (item in secondInningList) {
                        if (!item.overs.isNullOrEmpty()) {
                            val over: Double = item.overs.toString().toDouble()
                            val newover = Math.ceil(over).toInt()
                            item.newover = newover
                        }
                    }
                    val lastover1: Int? = secondInningList[secondInningList.size - 1].newover
                    if (lastover1 != null) {
                        for (i in 1..lastover1) {
                            val templist: ArrayList<MatchOddsResponseItem> =
                                ArrayList<MatchOddsResponseItem>()
                            for (item in secondInningList) {
                                if (i == item.newover) {
                                    templist.add(item)
                                }
                            }
                            overlist2nd.add(templist)
                        }
                    }
                    for (i in overlist2nd.indices) {
                        val templist: ArrayList<MatchOddsResponseItem> = overlist2nd[i]
                        var run = 0
                        var wicket = 0
                        val over = i + 1
                        for (item in templist) {
                            if (!item.runs.isNullOrEmpty()) {
                                if (item.runs.length > 1) {
                                    if (item.runs.equals("WK")) {
                                        wicket += 1
                                    } else if (item.runs.contains("New Batter")) {
                                        wicket += 1
                                    } else if (item.runs.contains("New Bat")) {
                                        wicket += 1
                                    } else if (item.runs.contains("Bowled")) {
                                        wicket += 1
                                    } else if (item.runs.contains("NEW BAT", true)) {
                                        wicket += 1
                                    } else if (item.runs.contains("BAT", true)) {
                                        wicket += 1
                                    }
                                } else {
                                    if (item.runs.equals("W")) {
                                        wicket += 1
                                    } else {
                                        run += item.runs.toInt()
                                    }
                                }
                            }
                        }
                        val model = BarGraphModel(run, over, wicket)
                        secondInningBarModel.add(model)
                    }

                }

                for (i in firstInningList.indices) {
                    val item: MatchOddsResponseItem = firstInningList[i]
                    if (i == firstInningList.size - 1) {
                        if (!item.score.isNullOrEmpty()) {
                            score += item.score.toFloat()
                        }
                        //  Log.e("TAGGraph", "onChanged: over : " + item.getOvers() + " , run : " + item.getRuns() + " , scores " + item.getScore());
                    }
                    //Log.e("TAGGraph", "onChanged: over : "+item.getOvers() +" , run : "+item.getRuns());
                }

                if (firstInningList != null && !firstInningList.isEmpty()) {
                    setGraph()
                }


            }

        })
    }


    fun setGraph() {
        binding.chart.setOnChartValueSelectedListener(this)

        // no description text
        binding.chart.description.isEnabled = false

        // enable touch gestures
        binding.chart.setTouchEnabled(true)
        binding.chart.axisRight.isEnabled = false
        binding.chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(true)
        binding.chart.scrollBarSize = 20
        binding.chart.setDrawGridBackground(false)
        binding.chart.isHighlightPerDragEnabled = true
        //  binding.chart.setScaleMinima(1.5f, 1f);

        // if disabled, scaling can be done on x- and y-axis separately
        binding.chart.setPinchZoom(true)

        // set an alternative background color
        binding.chart.setBackgroundColor(resources.getColor(R.color.card_view_white))
        binding.chart.animateX(1000)

        // get the legend (only possible after setting data)
        val l = binding.chart.legend //inning1, inning2 text worm
        val l1 = binding.barChart.legend //inning1, inning2 text manhattan
        l1.textColor = resources.getColor(R.color.txt_color)

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        // l.setTypeface(tfLight);
        l.textSize = 11f
        l.textColor = resources.getColor(R.color.txt_color)
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        //        l.setYOffset(11f);
        val xAxis = binding.chart.xAxis //graph ke neeche ka text
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // xAxis.setTypeface(tfLight);
        xAxis.textSize = 11f
        xAxis.textColor = resources.getColor(R.color.txt_color)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        val leftAxis = binding.chart.axisLeft // graph ke left ka text
        //  leftAxis.setTypeface(tfLight);
        leftAxis.textColor = resources.getColor(R.color.txt_color)
        leftAxis.axisMaximum = score
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        setData(20, 30f)
        barGraph(firstInningBarModel as ArrayList<BarGraphModel>, 1)
        setOddsData(firstInningList)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setData(count: Int, range: Float) {
        val values1 = ArrayList<Entry>()
        for (i in firstInningList.indices) {
            //float val = (float) (Math.random() * range) + 150;
            val item: MatchOddsResponseItem = firstInningList[i]
            teamA = item.team.toString()

            values1.add(Entry(item.overs.toString().toFloat(), item.score.toString().toFloat()))
        }
        val values2 = ArrayList<Entry>()
        if (secondInningList != null && !secondInningList.isEmpty()) {
            for (i in secondInningList.indices) {
                //float val = (float) (Math.random() * range) + 150;
                val item: MatchOddsResponseItem = secondInningList[i]
                teamB = item.team.toString()
                // if (!item.getOvers().contains(".")) {
                //  int over = Integer.parseInt(item.getOvers());

                // Log.e("TAGOverGraph", "setData: " + over);
                values2.add(Entry(item.overs.toString().toFloat(), item.score.toString().toFloat()))

                //  }
            }
        }
        val values3 = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() + 450
            values3.add(Entry(i.toFloat(), `val`))
        }
        val set1: LineDataSet
        val set2: LineDataSet
        var set3: LineDataSet
        if (binding.chart.data != null &&
            binding.chart.data.dataSetCount > 0
        ) {
            set1 = binding.chart.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding.chart.data.getDataSetByIndex(1) as LineDataSet
            // set3 = (LineDataSet) binding.chart.getData().getDataSetByIndex(2);
            set1.values = values1
            set2.values = values2
            //set3.setValues(values3);
            binding.chart.data.notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values1, "Inning 1")
            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = resources.getColor(R.color.text_normal_analysis)
            set1.lineWidth = 1.5f
            set1.fillAlpha = 65
            set1.setCircleColor(resources.getColor(R.color.txt_color))
            set1.circleRadius = 3f
            set1.setDrawCircles(false)
            set1.setDrawValues(false)
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            set2 = LineDataSet(values2, "Inning 2")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = resources.getColor(R.color.yelow_mild)
            set2.lineWidth = 1.5f
            set2.fillAlpha = 65
            set2.setCircleColor(resources.getColor(R.color.txt_color))
            set2.circleRadius = 3f
            set2.setDrawCircles(false)
            set2.setDrawValues(false)
            set2.fillColor = resources.getColor(R.color.red)
            set2.highLightColor = Color.rgb(244, 117, 117)
            set2.setDrawCircleHole(false)
            for (i in firstInningList.indices) {
                val item: MatchOddsResponseItem = firstInningList[i]
                if (item.runs.equals("WK") || item.runs.toString()
                        .contains("New Batter") || item.runs.toString()
                        .contains("New Bat") || item.runs.toString().contains("Bowled")
                    || item.runs.toString().contains("NEW BAT", true)
                    || item.runs.toString().contains("BAT", true)
                    || item.runs.equals("W", true)
                ) {
                    set1.getEntryForIndex(i).icon =
                        resources.getDrawable(R.drawable.ic_baseline_circle_24)
                }
            }
            if (secondInningList != null && !secondInningList.isEmpty()) {
                for (i in secondInningList.indices) {
                    val item: MatchOddsResponseItem = secondInningList[i]
                    if (item.runs.equals("WK") || item.runs.toString()
                            .contains("New Batter") || item.runs.toString()
                            .contains("New Bat") || item.runs.toString().contains("Bowled")
                        || item.runs.toString().contains("NEW BAT", true)
                        || item.runs.toString().contains("BAT", true)
                        || item.runs.equals("W", true)
                    ) {
                        set2.getEntryForIndex(i).icon =
                            resources.getDrawable(R.drawable.ic_baseline_circle_24)
                    }
                }
            }
            val data: LineData
            data = if (secondInningList != null && !secondInningList.isEmpty()) {
                LineData(set1, set2)
            } else {
                LineData(set1)
            }
            data.setValueTextColor(resources.getColor(R.color.dark_black))
            data.setValueTextSize(9f)
            binding.chart.axisRight.isEnabled = false

            // set data
            binding.chart.data = data
        }
    }


    var barEntriesArrayList = arrayListOf<BarEntry>()

    fun barGraph(barList: ArrayList<BarGraphModel>, inning: Int) {
        if (!barEntriesArrayList.isEmpty()) {
            barEntriesArrayList.clear()
            if (binding.barChart.data != null) {
                binding.barChart.data.clearValues()
            }
            binding.barChart.clear()
            binding.barChart.notifyDataSetChanged()
            binding.barChart.invalidate()
        }
        for (model in barList) {
            if (model.wicket == 0) {
                barEntriesArrayList.add(
                    BarEntry(
                        model.over.toString().toFloat(),
                        model.run.toString().toFloat(),
                        ""
                    )
                )
            } else {
                barEntriesArrayList.add(
                    BarEntry(
                        model.over.toString().toFloat(),
                        model.run.toString().toFloat(),
                        model.wicket
                    )
                )
            }
        }
        binding.barChart.setDrawBarShadow(false)
        binding.barChart.setDrawValueAboveBar(false)
        binding.barChart.description.isEnabled = false
        binding.barChart.setMaxVisibleValueCount(100)
        binding.barChart.setPinchZoom(false)
        binding.barChart.setScaleEnabled(false)
        binding.barChart.isHighlightPerTapEnabled = true
        binding.barChart.setDrawGridBackground(true)
        binding.barChart.setBackgroundColor(resources.getColor(R.color.card_view_white))
        binding.barChart.setGridBackgroundColor(resources.getColor(R.color.card_view_white))
        for (model in barList) {
            Log.e(
                "BarGraphModel",
                "barGraph: run " + model.run.toString() + " wicket " + model.wicket
                    .toString() + " over " + model.over
            )
        }
        val barDataSet = BarDataSet(barEntriesArrayList, "Inning $inning")
        barDataSet.setDrawValues(false)
        if (inning == 1) {
            barDataSet.color = resources.getColor(R.color.text_normal_analysis)
        } else if (inning == 2) {
            barDataSet.color = resources.getColor(R.color.yelow_mild)
        }
        barDataSet.setDrawIcons(true)

        //barDataSet.setIconsOffset(new MPPointF(0, -10));
        for (model in barList) {
            if (model.wicket == 1) {
                val ball = resources.getDrawable(R.drawable.ic_baseline_circle_white)
                barDataSet.addEntry(
                    BarEntry(
                        model.over.toFloat(),
                        model.run.toFloat(),
                        ball
                    )
                )
            } else if (model.wicket == 2) {
                val ball = resources.getDrawable(R.drawable.ic_sports_2ball)
                barDataSet.addEntry(
                    BarEntry(
                        model.over.toFloat(),
                        model.run.toFloat(),
                        ball
                    )
                )
            } else if (model.wicket == 3) {
                val ball = resources.getDrawable(R.drawable.ic_sports_3ball)
                barDataSet.addEntry(
                    BarEntry(
                        model.over.toFloat(),
                        model.run.toFloat(),
                        ball
                    )
                )
            } else if (model.wicket == 4) {
                val ball = resources.getDrawable(R.drawable.ic_sports_4ball)
                barDataSet.addEntry(
                    BarEntry(
                        model.over.toFloat(),
                        model.run.toFloat(),
                        ball
                    )
                )
            }
        }
        barDataSet.iconsOffset = MPPointF(0F, -25F)
        val BarXAxis = binding.barChart.xAxis
        val BarYAxis = binding.barChart.axisLeft
        BarXAxis.textColor = resources.getColor(R.color.txt_color)
        BarYAxis.textColor = resources.getColor(R.color.txt_color)
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.barChart.xAxis.setDrawGridLines(false)
        val barData = BarData(barDataSet)
        binding.barChart.data = barData
    }


    private fun setOddsData(inningList: List<MatchOddsResponseItem>) {
        val teamNames = ArrayList<String>()
        binding.teamANameOdds.setText(teamAName)
        binding.teamBNameOdds.setText(teamBName)
        val sets = ArrayList<ILineDataSet>()
        binding.oddsChart.setOnChartValueSelectedListener(this)

        // no description text
        binding.oddsChart.description.isEnabled = false

        // enable touch gestures
        binding.oddsChart.setTouchEnabled(true)
        binding.oddsChart.axisRight.isEnabled = false
        binding.oddsChart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        binding.oddsChart.isDragEnabled = true
        binding.oddsChart.setScaleEnabled(true)
        binding.oddsChart.scrollBarSize = 20
        binding.oddsChart.setDrawGridBackground(false)
        binding.oddsChart.isHighlightPerDragEnabled = true
        //  binding.chart.setScaleMinima(1.5f, 1f);

        // if disabled, scaling can be done on x- and y-axis separately
        binding.oddsChart.setPinchZoom(true)

        // set an alternative background color
        binding.oddsChart.setBackgroundColor(Color.LTGRAY)
        binding.oddsChart.animateX(1000)

        // get the legend (only possible after setting data)
        val l = binding.oddsChart.legend

        // modify the legend ...
        //   l.setForm(Legend.LegendForm.LINE);
        // l.setTypeface(tfLight);
        l.textSize = 11f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        //        l.setYOffset(11f);
        val xAxis = binding.oddsChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 11f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        val leftAxis = binding.oddsChart.axisLeft
        //  leftAxis.setTypeface(tfLight);
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = score
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        val values1 = ArrayList<Entry>()
        try {
            for (i in inningList.indices) {
                val item: MatchOddsResponseItem = inningList[i]
                /*Log.e(
                    "TAGexception",
                    "setOddsData: over " + item.overs.toString() + " rate " + item.maxRate
                        .toString() + " team " + item.team
                )*/

                //  Log.e(TAG, "setOddsData: "+item.maxRate.toString() )

                var maxrate = item.maxRate.toString().toFloat()


                val decimalPart = item.maxRate.toString().split(".").getOrElse(1) { "" }

                if (decimalPart.isEmpty()) {
                    maxrate =
                        item.maxRate.toString().toFloat() // No decimal part, return integer value
                } else {
                    maxrate = decimalPart.padEnd(2, '0').toString()
                        .toFloat() // Ensure two digits after the decimal
                }


                if (item.runs.equals("WK") || item.runs.equals("W") || item.runs.toString()
                        .contains("New Batter") || item.runs.toString()
                        .contains("New Bat") || item.runs.toString().contains("Bowled")
                    || item.runs.toString().contains("NEW BAT", true)
                    || item.runs.toString().contains("BAT", true)
                ) {
                    values1.add(
                        Entry(
                            item.overs.toString().toFloat(),
                            maxrate,
                            resources.getDrawable(R.drawable.ic_baseline_circle_24),
                            item.favTeam
                        )
                    )
                } else {
                    if (!item.overs.toString().isEmpty() && !item.maxRate.toString()
                            .isEmpty() && !item.team.toString().isEmpty()
                    ) {
                        values1.add(
                            Entry(
                                item.overs.toString().toFloat(),
                                maxrate,
                                item.favTeam
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAGexception", "setOddsData:" + e.message)
        }
        for (i in values1.indices) {
            val segment = ArrayList<Entry>()
            segment.add(values1[i])
            if (i < values1.size - 1) {
                segment.add(values1[i + 1])
            }
            val teamName = values1[i].data.toString()
            var set: LineDataSet
            if (teamNames.contains(teamName)) {
                set = LineDataSet(segment, "")
                set.formLineWidth = 0f
                set.formSize = 0f
            } else {
                teamNames.add(teamName)
                set = LineDataSet(segment, "")
                set.formLineWidth = 0f
                set.formSize = 0f
            }
            if (teamAName.contains(teamName) || teamAShort.contains(teamName)) {
                set.color = resources.getColor(R.color.yelow_mild)
            } else if (teamBName.contains(teamName) || teamBShort.contains(teamName)) {
                set.color = resources.getColor(R.color.text_normal_analysis)
            } else if (teamName.contains("Dono")) {
                set.color = resources.getColor(R.color.text_normal_analysis2)
            }
            set.lineWidth = 2f
            set.circleRadius = 3f
            set.setDrawCircles(false)
            set.setDrawValues(false)
            set.setDrawCircleHole(false)
            sets.add(set)
        }
        val data = LineData(sets)
        binding.oddsChart.axisRight.isEnabled = false
        binding.oddsChart.data = data
    }


    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onNothingSelected() {

    }


}