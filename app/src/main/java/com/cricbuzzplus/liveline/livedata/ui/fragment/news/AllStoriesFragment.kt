package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentAllStoriesBinding
import com.cricbuzzplus.liveline.databinding.FragmentNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.AllStoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel


class AllStoriesFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentAllStoriesBinding

    var lastId = 0
    var prevId = 0

    var list = arrayListOf<StoryListItem>()

    var adapter: AllStoriesNewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentAllStoriesBinding.inflate(inflater, container, false)

        setObservers()


        callNews()


        return binding.root
    }


    fun callNews() {
        if (isInternetConnection()) {
            viewModel.getNewsCric()
        }
    }

    fun callNewsPaginate() {
        if (isInternetConnection()) {
            viewModel.getNewsPaginate(lastId)
        }
    }

    private fun setObservers() {

        observeExtras()
        observeNews()

    }


    private fun observeNews() {

        viewModel.newsCricLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                if (!it.storyList.isNullOrEmpty()) {

                    list.addAll(it.storyList as ArrayList<StoryListItem>)

                    lastId = it.storyList.last().story?.id!!

                    if (adapter == null) {

                        adapter = AllStoriesNewsAdapter(list, activity, object :
                            OnClickInterface {

                            override fun onClick(newsId: Int) {


                                val intent = Intent(activity, NewsDetailActivity::class.java)
                                intent.putExtra("newsId", newsId)
                                startActivity(intent)

                            }

                        })

                        binding.recyclerNews.adapter = adapter

                        addScroll()

                    } else {
                        isLoading = false
                        adapter?.notifyDataSetChanged()
                    }


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


    var isLoading = false // Used to prevent multiple API calls while loading
    var visibleThreshold = 1 // Number of items from the end to trigger loading

    fun addScroll() {


        binding.recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.recyclerNews.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val totalItemCount = layoutManager.itemCount


                //Log.e(TAG, "onScrolled: totalItemCount $totalItemCount  lastVisibleItem $lastVisibleItem" )

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more data here
                    isLoading = true // Set to true to prevent multiple calls

                    if (prevId != lastId) {
                        prevId = lastId

                        //  Log.e(TAG, "visibleThreshold: "+visibleThreshold )

                        callNewsPaginate()
                    }

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lastId = 0
        prevId = 0
        list.clear()
        adapter = null

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }

}