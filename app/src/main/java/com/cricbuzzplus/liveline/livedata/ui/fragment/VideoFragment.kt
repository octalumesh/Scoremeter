package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentVideoBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.FeatureVideosAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.TrendingVideosAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.TrendingVideosBigAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.HomeViewModel


class VideoFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentVideoBinding

    var listVideo = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentVideoBinding.inflate(inflater, container, false)

        listVideo.add("hghg")
        listVideo.add("hghg")
        listVideo.add("hghg")
        listVideo.add("hghg")
        listVideo.add("hghg")
        listVideo.add("hghg")
        listVideo.add("hghg")


        binding.recyclerFeatureVideo.adapter = FeatureVideosAdapter(listVideo,requireContext(),object :
            OnClickInterface {
            override fun onClick(newsId: Int) {
            }
        })

        binding.recyclerTrendingVideo.adapter = TrendingVideosBigAdapter(listVideo,requireContext(),object : OnClickInterface{
            override fun onClick(newsId: Int) {
            }
        })

        return binding.root
    }


}