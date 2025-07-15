package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.RecentMatchesFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.RecentMatchResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.RecentMatchesAdaptor
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.RecentMatchesViewModel

class RecentMatchesFragment : BaseFragment() {

    lateinit var binding: RecentMatchesFragmentBinding
    private lateinit var viewModel: RecentMatchesViewModel

    var adapter : RecentMatchesAdaptor? = null
    
    private val list: ArrayList<RecentMatchResponseItem> = arrayListOf()
    private val t20List: ArrayList<RecentMatchResponseItem> = arrayListOf()
    private val odiList: ArrayList<RecentMatchResponseItem> = arrayListOf()
    private val testList: ArrayList<RecentMatchResponseItem> = arrayListOf()
    private val t10List: ArrayList<RecentMatchResponseItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RecentMatchesViewModel::class.java)
        binding = RecentMatchesFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.share.setOnClickListener(View.OnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ground Live Line")
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

        buttonsCls()
        getRecentMatches()

    }

    override fun onResume() {
        super.onResume()

        getRecentMatches()
        /*if (!list.isNullOrEmpty()){
            adapter = null
            if (adapter == null) {
                adapter = RecentMatchesAdaptor(list, context)
                binding.recyclerRecent.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(list)
            }
        }
        else{
            adapter = null
            getRecentMatches()
        }*/
    }

    fun getRecentMatches(){
        /*if (checkForInternet(activity!!)){


        }*/
        viewModel.getRecentMatches()

    }

    private fun buttonsCls() {


        binding.btnUpcomingT20.setOnClickListener(View.OnClickListener {
            //  list.clear()
            adapter =  null

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))


            if (adapter == null) {
                adapter = RecentMatchesAdaptor(t20List, context)
                binding.recyclerRecent.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(t20List)
            }

            // recyclerViewMainclsT20()
        })

        binding.btnUpcomingAllmatches.setOnClickListener(View.OnClickListener {
            adapter = null

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))




            if (adapter == null) {
                adapter = RecentMatchesAdaptor(list, context)
                binding.recyclerRecent.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(list)
            }
            //  recyclerViewMaincls()
        })

        binding.btnUpcomingOdi.setOnClickListener(View.OnClickListener {

            adapter = null

            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))



            if (adapter == null) {
                adapter = RecentMatchesAdaptor(odiList, context)
                binding.recyclerRecent.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(odiList)
            }
            //  recyclerViewMainclsODI()
        })

        binding.btnUpcomingTest.setOnClickListener(View.OnClickListener {
            adapter = null


            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.white))


            if (adapter == null) {
                adapter = RecentMatchesAdaptor(testList, context)
                binding.recyclerRecent.setAdapter(adapter)
            } else {
                // adapter?.notifyItemChanged(updateIndex)
                adapter?.updateList(testList)
            }
            //  recyclerViewMainclsTest()
        })

        /*binding.btnUpcomingT10.setOnClickListener(View.OnClickListener {
            adapter = null

            binding.btnUpcomingT20.setBackground(activity.resources.getDrawable(R.drawable.text_line_remove))
            binding.btnUpcomingT10.setBackground(activity.resources.getDrawable(R.drawable.text_line))
            binding.btnUpcomingAllmatches.setBackground(activity.resources.getDrawable(R.drawable.text_line_remove))
            binding.btnUpcomingOdi.setBackground(activity.resources.getDrawable(R.drawable.text_line_remove))
            binding.btnUpcomingTest.setBackground(activity.resources.getDrawable(R.drawable.text_line_remove))


            if (t10List.isNullOrEmpty()) {
                if (adapter == null) {
                    adapter = RecentMatchesAdaptor(t10List, context)
                    binding.recyclerRecent.setAdapter(adapter)
                } else {
                    // adapter?.notifyItemChanged(updateIndex)
                    adapter?.updateList(t10List)
                }
            }
            //  recyclerViewMainclsTest()
        })*/
    }

    /*override fun onStop() {
        super.onStop()
        adapter= null
        list.clear()
    }*/


    private fun setObservers(){
        observeExtras()
        observeRecentMatches()
    }

    private fun observeRecentMatches() {
        viewModel.getRecentMatchesLiveData().observe(viewLifecycleOwner, Observer {


            binding.btnUpcomingT20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingAllmatches.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.btnUpcomingOdi.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))
            binding.btnUpcomingTest.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.gray_lgt)))

            binding.btnUpcomingT20.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingAllmatches.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.btnUpcomingOdi.setTextColor(ContextCompat.getColor(activity, R.color.black))
            binding.btnUpcomingTest.setTextColor(ContextCompat.getColor(activity, R.color.black))


            if (it != null){
                list.clear()
                t20List.clear()
                odiList.clear()
                testList.clear()


                list.addAll(it)

                if (adapter == null) {
                    adapter = RecentMatchesAdaptor(it as java.util.ArrayList<RecentMatchResponseItem>?, context)
                    binding.recyclerRecent.setAdapter(adapter)
                } else {
                    // adapter?.notifyItemChanged(updateIndex)
                    adapter?.updateList(it as java.util.ArrayList<RecentMatchResponseItem>?)
                }

                for (match in list){

                    if (match.matchType.equals("T20")){
                        t20List.add(match)
                    }else if (match.matchType.equals("ODI")){
                        odiList.add(match)
                    }else if (match.matchType.equals("Test")){
                        testList.add(match)
                    }else if (match.matchType.equals("T10")){
                        t10List.add(match)
                    }

                }

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        list.clear()
        t20List.clear()
        odiList.clear()
        testList.clear()
    }

    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(activity,
            { isLoading -> handleProgressLoader(isLoading!!) })
        /*viewModel!!.getDataLoadErrorLiveData().observe(this, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })*/
    }

}