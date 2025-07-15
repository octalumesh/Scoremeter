package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentNewsDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel

class NewsDetailFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentNewsDetailBinding

    var newsId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        newsId = arguments?.getInt("newsId")!!

        hideKeyBoard()
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (isInternetConnection()){
            callNewsDetails()
        }

    }

    fun callNewsDetails(){
        viewModel.getNewsDetail(newsId)
    }

    private fun setObservers(){
        observeExtras()
        observeMatchInfo()
    }

    private fun observeMatchInfo() {
        viewModel.getNewsDetailsLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null){

                Log.e(TAG, "observeMatchInfo: "+ it?.content?.size)
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.ic_launcher)
                requestOptions.error(R.mipmap.ic_launcher)

                binding.title.setText(it.title)
                binding.dateTime.setText(it.pubDate)
                binding.description.setText(it.description)
                Glide.with(this).load(it.image).apply(requestOptions).into(binding.newsImage)
                var content  =""
                for (con in it.content!!){
                    content = content +"\n"+con
                }

                binding.content.setText(HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT))


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
}