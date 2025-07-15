package com.cricbuzzplus.liveline.livedata.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentCategoriesNewsBinding
import com.cricbuzzplus.liveline.databinding.FragmentCricPlusNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.AllStoriesNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.CricPlusNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.NewsViewModel


class CricPlusNewsFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentCricPlusNewsBinding

    var list : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentCricPlusNewsBinding.inflate(inflater, container, false)


        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")
        list.add("dsfdsf")


        binding.recyclerNews.adapter = CricPlusNewsAdapter(list,activity)


        return binding.root
    }


}