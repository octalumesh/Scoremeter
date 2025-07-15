package com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentPlayerNewsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel


class PlayerNewsFragment : BaseFragment() {

    lateinit var binding: FragmentPlayerNewsBinding
    private lateinit var viewModel: PlayersViewModel

    var playerName = ""
    var playerId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlayerNewsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PlayersViewModel::class.java)

        hideKeyBoard()
        setObserver()

        val bundle = arguments
        if (bundle != null) {

            playerName = bundle.getString("playerName").toString()
            playerId = bundle.getInt("playerId", 0)
        } else {
            Log.d("TAG", "bundle is null")
        }

        callPlayerInfo()

        return binding.root
    }

    fun callPlayerInfo() {

        if (playerId != 0) {
            if (isInternetConnection()) {
                viewModel.getPlayerNews(playerId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo() {

        viewModel.playerNewsListLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.recyclerNews.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                binding.recyclerNews.adapter = PlayerNewsAdapter(it.storyList as ArrayList<StoryListItem>,activity,object :OnClickInterface{

                    override fun onClick(newsId: Int) {


                        val intent = Intent(activity,NewsDetailActivity::class.java)
                        intent.putExtra("newsId",newsId)
                        startActivity(intent)

                    }

                })

            } else {
                binding.recyclerNews.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })

    }


    private fun observeExtras() {
        viewModel.loaderLiveData.observe(viewLifecycleOwner, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(viewLifecycleOwner, Observer { s ->
            Log.e(TAG, "onChanged: $s")
            handleError(s.toString())
        })
    }



}