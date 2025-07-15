package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.databinding.SeriesFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.SeriesListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.activity.SeriesTabActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.SeriesAdaptor
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.SeriesViewModel
import java.lang.Exception

class SeriesFragment : BaseFragment(),SeriesAdaptor.MyClickListener {

    lateinit var binding: SeriesFragmentBinding
    private lateinit var viewModel: SeriesViewModel
    private val list: ArrayList<SeriesListResponseItem> = arrayListOf()
    var adapter :SeriesAdaptor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SeriesViewModel::class.java)
        binding = SeriesFragmentBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callSeries()

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
    }

    fun callSeries(){
        viewModel.getSeries()
    }

   /* override fun onResume() {
        super.onResume()
        callSeries()
    }*/

    private fun setObservers(){
        observeExtras()
        observeSeries()
    }

   /* override fun onStop() {
        super.onStop()
        list.clear()
        adapter = null
    }*/

    private fun observeSeries() {
        viewModel.getSeriesMatchesLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null){

                list.addAll(it)

                if (adapter == null) {
                    adapter = SeriesAdaptor(it as ArrayList<SeriesListResponseItem>?, context,this)
                    binding.recyclerviewSeriesList.setAdapter(adapter)
                } else {
                    // adapter?.notifyItemChanged(updateIndex)
                    adapter?.updateList(it as ArrayList<SeriesListResponseItem>?)
                }


            }

        })
    }



    private fun observeExtras() {
        viewModel!!.getLoaderLiveData().observe(viewLifecycleOwner,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.getDataLoadErrorLiveData().observe(viewLifecycleOwner, { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })
    }

    override fun onItemClick(item: SeriesListResponseItem) {
        Log.e(TAG, "onItemClick: " +item)
        val intent = Intent(activity, SeriesTabActivity::class.java)
        intent.putExtra("seriesId", item.seriesId)
        intent.putExtra("seriesName", item.series)
        intent.putExtra("index", 0)
        startActivity(intent)
    }

}