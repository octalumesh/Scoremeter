package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentCricPlusNewsBinding
import com.cricbuzzplus.liveline.databinding.FragmentTopicsNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryTypeItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TopicsItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.AllStoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.CategoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TopicsNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel

class TopicsNewsFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentTopicsNewsBinding

    var list : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentTopicsNewsBinding.inflate(inflater, container, false)

        setObservers()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callNewsTopics()
    }

    fun callNewsTopics() {
        if (isInternetConnection()) {
            viewModel.getNewsTopics()
        }
    }


    private fun setObservers() {

        observeExtras()
        observeNews()

    }


    private fun observeNews() {

        viewModel.newsTopicsLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                if (!it.topics.isNullOrEmpty()) {

                    binding.recyclerNews.adapter = TopicsNewsAdapter(it.topics as ArrayList<TopicsItem>,activity)

                } else {
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    binding.recyclerNews.visibility = View.GONE
                }
            } else {
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.recyclerNews.visibility = View.GONE
            }

        })

    }

    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}