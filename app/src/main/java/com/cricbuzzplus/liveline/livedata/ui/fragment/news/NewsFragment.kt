package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.NewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.ViewPagerAdapter
import com.cricbuzzplus.liveline.livedata.ui.fragment.HomeMainFragment
import com.cricbuzzplus.liveline.livedata.ui.fragment.LiveHomeFragment
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel

class NewsFragment : BaseFragment() , OnClickInterface {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentNewsBinding

   // var listAd : ArrayList<SliderImage> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()



        val viewpagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewpagerAdapter.addFragment(AllStoriesFragment(), "ALL STORIES")
        //viewpagerAdapter.addFragment(CricPlusNewsFragment(), "CRICBUZZ PLUS")
        viewpagerAdapter.addFragment(CategoriesNewsFragment(), "CATEGORIES")
        viewpagerAdapter.addFragment(TopicsNewsFragment(), "TOPICS")

        binding.pager.adapter = viewpagerAdapter


        //swipe page left to right than fragment change next show
        binding.tabLayout.setupWithViewPager( binding.pager)

        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white));
        binding.tabLayout.setTabTextColors(resources.getColor(R.color.tab_unselected),
            resources.getColor(R.color.white))


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.recyclerStory.adapter = NewsStoryAdapter(list,requireContext())
       // getData()
        if (isInternetConnection()){
           // callNewsList()
        }

    }
/*
    fun getData() {

        if(listAd != null){
            listAd.clear()
        }
        FirebaseFirestore.getInstance().collection("slider")
            .orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    //   showToast(e.message.toString())
                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    for (dc in snapshot!!.documentChanges) {

                        val prediction = dc.document.toObject(SliderImage::class.java) as SliderImage
                        listAd.add(prediction)

                    }
                    setSlider(listAd)
                }

            }

    }

    fun setSlider(res:List<SliderImage>){

        binding.viewPage.adapter= HomeSliderViewPagerAdapter(activity,res)


    }*/

    fun callNewsList(){
        viewModel.getNewsList()
    }

    private fun setObservers(){
        observeExtras()
      //  observeNews()
    }

    private fun observeNews() {
        viewModel.getNewsListLiveData().observe(viewLifecycleOwner, Observer {

            if (it != null){

                /*binding.recyclerNews.adapter = NewsAdapter(it as ArrayList<NewsListResponseItem>,activity,this)
                val requestOptions = RequestOptions()
                //     requestOptions.placeholder(R.mipmap.ic_launcher)
                //     requestOptions.placeholder(R.mipmap.ic_launcher)
                requestOptions.placeholder(R.mipmap.ic_launcher)
                requestOptions.error(R.mipmap.ic_launcher)


                var item = it[0]

                if (item != null){

                    Glide.with(activity).load(item.image).apply(requestOptions).into(binding.imageNews)

                    binding.titleNews.setText(item.title)
                    binding.desNews.setText(item.description)
                    binding.dateTimeNews.setText(item.pubDate)

                }*/


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

    override fun onClick(newsId: Int) {

        val bundle = bundleOf("newsId" to newsId)

        activity.findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.redirect_news_details,bundle)
    }

}