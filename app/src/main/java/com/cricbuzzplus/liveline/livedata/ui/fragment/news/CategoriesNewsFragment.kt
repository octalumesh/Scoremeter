package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentAllStoriesBinding
import com.cricbuzzplus.liveline.databinding.FragmentCategoriesNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryTypeItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.AllStoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.CategoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TopicsNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel


class CategoriesNewsFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentCategoriesNewsBinding


    var list : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentCategoriesNewsBinding.inflate(inflater, container, false)

        setObservers()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callNewsCategory()
    }

    fun callNewsCategory() {
        if (isInternetConnection()) {
            viewModel.getNewsCategory()
        }
    }


    private fun setObservers() {

        observeExtras()
        observeNews()

    }


    private fun observeNews() {

        viewModel.newsCategoryLiveData.observe(activity, Observer {

            if (it != null) {

                if (!it.storyType.isNullOrEmpty()) {

                    binding.recyclerNews.adapter = CategoriesNewsAdapter(it.storyType as ArrayList<StoryTypeItem>,activity)

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