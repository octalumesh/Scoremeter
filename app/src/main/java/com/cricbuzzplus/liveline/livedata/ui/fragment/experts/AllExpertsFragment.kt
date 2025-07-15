package com.cricbuzzplus.liveline.livedata.ui.fragment.experts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cricbuzzplus.liveline.databinding.FragmentAllExpertsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.newresponse.AllExpertsResponseItem
import com.cricbuzzplus.liveline.livedata.ui.activity.ExpertsProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.AllExpertsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnExpertsClick
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel


class AllExpertsFragment : BaseFragment() {

    private lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentAllExpertsBinding

    var currentPage = 1

    var isLoading = false
    var visibleThreshold = 20
    var lastVisibleItem = 0
    var totalItemCount = 0

    var list = arrayListOf<AllExpertsResponseItem>()

    var adapter: AllExpertsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = FragmentAllExpertsBinding.inflate(inflater, container, false)

        hideKeyBoard()
        setObservers()

        callAllExperts()

        return binding.root
    }


    private fun callAllExperts() {

        if (checkForInternet(activity)) {
            viewModel.getAllExperts(currentPage)
        }
    }


    private fun setObservers() {
        observeExperts()
        observeExtras()
    }


    private fun observeExperts() {

        viewModel.allExpertsLiveData.observe(viewLifecycleOwner, Observer {

            try {

                if (!it.isNullOrEmpty()) {
                    currentPage++

                    list.addAll(it)

                    setAdapter()
                }
                else{
                    if (currentPage == 1){
                        binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                        binding.recyclerExperts.visibility = View.GONE
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }


        })

    }

    private fun setAdapter() {

        if (adapter == null) {
            adapter = AllExpertsAdapter(list, activity,object : OnExpertsClick {
                override fun onClick(userId: Int) {

                    val intent = Intent(activity, ExpertsProfileActivity::class.java)
                    intent.putExtra("userId",userId)
                    startActivity(intent)

                }

            })
            binding.recyclerExperts.adapter = adapter
            addScroll()
        } else {
            isLoading = false
            adapter?.updateList(list)
        }

    }


    fun addScroll() {
        binding.recyclerExperts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleThreshold =
                        (binding.recyclerExperts.layoutManager as LinearLayoutManager?)!!.childCount
                    totalItemCount =
                        (binding.recyclerExperts.layoutManager as LinearLayoutManager?)!!.itemCount
                    lastVisibleItem =
                        (binding.recyclerExperts.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        callAllExperts()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(viewLifecycleOwner,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(viewLifecycleOwner, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }

}