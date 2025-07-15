package com.cricbuzzplus.liveline.livedata.ui.fragment.cricbuzzfrag.browsteams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentTeamNewsBinding
import com.cricbuzzplus.liveline.databinding.FragmentTeamPlayersBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.StoryListItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.TeamPlayerItem
import com.cricbuzzplus.liveline.livedata.ui.activity.NewsDetailActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.PlayerNewsAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.TeamPlayerCricAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.CricbuzzViewModel


class TeamNewsFragment : BaseFragment() {

    private lateinit var viewModel: CricbuzzViewModel
    lateinit var binding: FragmentTeamNewsBinding

    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamNewsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CricbuzzViewModel::class.java)

        hideKeyBoard()
        setObservers()

        teamId = arguments?.getInt("teamId")!!

        callTeam()

        return binding.root
    }


    fun callTeam(){
        if (isInternetConnection()) {
            viewModel.getTeamsNews(teamId)
        }
    }

    private fun setObservers(){

        observeExtras()
        observeTeam()

    }


    private fun observeTeam(){

        viewModel.teamsNewsLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                if (!it.storyList.isNullOrEmpty() ) {

                    binding.recyclerSeries.adapter = PlayerNewsAdapter(it.storyList as ArrayList<StoryListItem>,activity,object :
                        OnClickInterface {

                        override fun onClick(newsId: Int) {


                            val intent = Intent(activity, NewsDetailActivity::class.java)
                            intent.putExtra("newsId",newsId)
                            startActivity(intent)

                        }

                    })

                }else{
                    binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                    binding.recyclerSeries.visibility = View.GONE
                }
            }else{
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
                binding.recyclerSeries.visibility = View.GONE
            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}