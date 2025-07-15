package com.cricbuzzplus.liveline.livedata.ui.activity.news

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityNewsCategoryBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.AllStoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel

class NewsCategoryActivity : BaseActivity() {

    lateinit var binding : ActivityNewsCategoryBinding
    private lateinit var viewModel: NewsViewModel

    var lastId = 0
    var prevId = 0

    var catId = 0
    var title = ""

    var list = arrayListOf<StoryListItem>()

    var adapter: AllStoriesNewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news_category)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@NewsCategoryActivity, R.color.colorPrimaryDark)
        }

        setObservers()

        catId = intent.getIntExtra("catId",0)
        title = intent.getStringExtra("title").toString()


        binding.title.setText(title)

        callNews()


        binding.back.setOnClickListener {
            onBackPressed()
        }
        //binding.recyclerNews.adapter = AllStoriesNewsAdapter(list,this)

    }


    fun callNews() {
        if (isInternetConnection()) {
            viewModel.getNewsByCategoryCric(catId)
        }
    }

    fun callNewsPaginate() {
        if (isInternetConnection()) {
            viewModel.getNewsByCategoryPaginate(catId,lastId)
        }
    }

    private fun setObservers() {

        observeExtras()
        observeNews()

    }


    private fun observeNews() {

        viewModel.newsByCategoryCricLiveData.observe(this, Observer {

            if (it != null) {

                if (!it.storyList.isNullOrEmpty()) {

                    list.addAll(it.storyList as ArrayList<StoryListItem>)

                    lastId = it.storyList.last().story?.id!!

                    if (adapter == null) {

                        adapter = AllStoriesNewsAdapter(list, this, object :
                            OnClickInterface {

                            override fun onClick(newsId: Int) {


                                val intent = Intent(this@NewsCategoryActivity, NewsDetailActivity::class.java)
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

   /* override fun onDestroyView() {
        super.onDestroyView()

        lastId = 0
        prevId = 0
        list.clear()
        adapter = null

    }*/


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(this, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }

}