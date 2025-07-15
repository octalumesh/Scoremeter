package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentSquadBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.PlayerItem
import com.cricbuzzplus.liveline.livedata.response.TeamA
import com.cricbuzzplus.liveline.livedata.response.TeamB
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchByIdTeam1
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchByIdTeam2
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayingXIItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.SupportStaffItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.SquadAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SquadCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SquadCricBuzzTeamBAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SupportStaffCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.SupportStaffCricBuzzTeamBAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnPlayerClickInterface
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnSquadClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import com.cricbuzzplus.liveline.utils.Constants


class SquadFragment : BaseFragment(), OnPlayerClickInterface {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: FragmentSquadBinding

    var matchStatus = ""
    var teamA = ""
    var teamB = ""
    var matchId = 0

    var matchNo = ""
    var series = ""

    var matchIdCricbuzz = 0

    var teamAModel: TeamA? = null
    var teamBModel: TeamB? = null

    var team1: MatchByIdTeam1? = null
    var team2: MatchByIdTeam2? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        binding = FragmentSquadBinding.inflate(inflater, container, false)

        matchStatus = arguments?.getString("matchStatus", "").toString()
        matchNo = arguments?.getString("matchNo", "").toString()
        series = arguments?.getString("series", "").toString()
        matchId = arguments?.getInt("matchId")!!

        hideKeyBoard()
        setObservers()
        Log.e("TAGSqud", "onCreateView: series -- $series  ,  match -- $matchNo")


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*if (matchStatus.equals("Upcoming")) {
            callAllSquad()
        } else {
            callPlayingSquad()
        }*/

        if (matchStatus.equals("Upcoming")) {
            getMatchIdCricBuzzUpcoming(series,matchNo)
        } else if (matchStatus.equals("Finished")) {
            getMatchIdCricBuzzFinished(series,matchNo)
        } else if (matchStatus.equals("Live")) {
            getMatchIdCricBuzzLive(series,matchNo)
        }

    }


    fun callMatchInfoCricBuzz() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getMatchInfoCricBuzz(matchIdCricbuzz)
            }
        }

    }

    fun callMatchSquadCricBuzz() {

        if (matchIdCricbuzz != 0) {

            if (isInternetConnection()) {
                viewModel.getMatchSquadCricBuzz(matchIdCricbuzz)
            }
        }

    }




    private fun setObservers() {
        observeCheckSquad()
        observeMatchInfoCricBuzz()
        observeMatchSquadCricBuzz()
        observeMatchId()
    }

    fun observeMatchId(){

        matchIdCricLiveData.observe(viewLifecycleOwner,Observer{
            if (it != null){
                matchIdCricbuzz  = it
                Log.e(TAG, "onViewCreated:matchid squad crci ${matchIdCricbuzz}", )
                callMatchSquadCricBuzz()
            }
        })

    }


    private fun observeMatchSquadCricBuzz() {

        viewModel.matchSquadCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            val requestOptions = RequestOptions()
            //     requestOptions.placeholder(R.mipmap.ic_launcher)
            //     requestOptions.placeholder(R.mipmap.ic_launcher)
            requestOptions.placeholder(R.mipmap.ic_launcher)
            requestOptions.error(R.mipmap.ic_launcher)

            binding.progressBar.visibility = View.GONE


            if (it != null) {

                if (it.team1 != null) {

                    if (it.team1.team != null) {

                        Glide.with(activity)
                            .load("" + Constants.cricbuzzImgFirst + it.team1.team.imageId + Constants.cricbuzzImgSecond)
                            .apply(requestOptions).into(binding.teamAImage)

                        binding.teamAName.setText(it.team1.team.teamSName.toString())

                        if (it.team1.players != null) {

                            if (!it.team1.players.playingXI.isNullOrEmpty()) {

                                binding.textSquad.setText("Playing XI")

                                binding.recyclerTeamASquad.adapter = SquadCricBuzzAdapter(
                                    it.team1.players.playingXI as ArrayList<PlayingXIItem>,
                                    activity,
                                    object : OnSquadClickInterface {
                                        override fun onClick(item: PlayingXIItem) {

                                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                                            intent.putExtra("playerName", item?.fullName.toString())
                                            intent.putExtra("playerId", item?.id)
                                            startActivity(intent)


                                        }

                                    })

                                if (!it.team1.players.bench.isNullOrEmpty()){

                                    binding.linearBench.visibility = View.VISIBLE

                                    binding.recyclerTeamABench.adapter = SquadCricBuzzAdapter(
                                        it.team1.players.bench as ArrayList<PlayingXIItem>,
                                        activity,
                                        object : OnSquadClickInterface {
                                            override fun onClick(item: PlayingXIItem) {

                                                val intent = Intent(activity, PlayerProfileActivity::class.java)
                                                intent.putExtra("playerName", item?.fullName.toString())
                                                intent.putExtra("playerId", item?.id)
                                                startActivity(intent)


                                            }

                                        })

                                }

                                if (!it.team1.players.supportStaff.isNullOrEmpty()){

                                    binding.linearStaff.visibility = View.VISIBLE

                                    binding.recyclerTeamAStaff.adapter = SupportStaffCricBuzzAdapter(
                                        it.team1.players.supportStaff as ArrayList<SupportStaffItem>,
                                        activity)

                                }

                            } else if (!it.team1.players.squad.isNullOrEmpty()) {

                                binding.textSquad.setText("Squad")

                                binding.recyclerTeamASquad.adapter = SquadCricBuzzAdapter(
                                    it.team1.players.squad as ArrayList<PlayingXIItem>,
                                    activity,
                                    object : OnSquadClickInterface {
                                        override fun onClick(item: PlayingXIItem) {


                                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                                            intent.putExtra("playerName", item?.fullName.toString())
                                            intent.putExtra("playerId", item?.id)
                                            startActivity(intent)

                                        }

                                    })

                                if (!it.team1.players.supportStaff.isNullOrEmpty()){

                                    binding.linearStaff.visibility = View.VISIBLE

                                    binding.recyclerTeamAStaff.adapter = SupportStaffCricBuzzAdapter(
                                        it.team1.players.supportStaff as ArrayList<SupportStaffItem>,
                                        activity)

                                }

                            }

                        }

                    }

                }


                if (it.team2 != null) {

                    if (it.team2.team != null) {

                        Glide.with(activity)
                            .load("" + Constants.cricbuzzImgFirst + it.team2.team.imageId + Constants.cricbuzzImgSecond)
                            .apply(requestOptions).into(binding.teamBImage)

                        binding.teamBName.setText(it.team2.team.teamSName.toString())

                        if (it.team2.players != null) {

                            if (!it.team2.players.playingXI.isNullOrEmpty()) {

                                binding.textSquad.setText("Playing XI")

                                binding.recyclerTeamBSquad.adapter = SquadCricBuzzTeamBAdapter(
                                    it.team2.players.playingXI as ArrayList<PlayingXIItem>,
                                    activity,
                                    object : OnSquadClickInterface {
                                        override fun onClick(item: PlayingXIItem) {

                                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                                            intent.putExtra("playerName", item?.fullName.toString())
                                            intent.putExtra("playerId", item?.id)
                                            startActivity(intent)

                                        }

                                    })


                                if (!it.team2.players.bench.isNullOrEmpty()){

                                    binding.linearBench.visibility = View.VISIBLE

                                    binding.recyclerTeamBBench.adapter = SquadCricBuzzTeamBAdapter(
                                        it.team2.players.bench as ArrayList<PlayingXIItem>,
                                        activity,
                                        object : OnSquadClickInterface {
                                            override fun onClick(item: PlayingXIItem) {

                                                val intent = Intent(activity, PlayerProfileActivity::class.java)
                                                intent.putExtra("playerName", item?.fullName.toString())
                                                intent.putExtra("playerId", item?.id)
                                                startActivity(intent)


                                            }

                                        })

                                }

                                if (!it.team2.players.supportStaff.isNullOrEmpty()){

                                    binding.linearStaff.visibility = View.VISIBLE

                                    binding.recyclerTeamBStaff.adapter = SupportStaffCricBuzzTeamBAdapter(
                                        it.team2.players.supportStaff as ArrayList<SupportStaffItem>,
                                        activity)

                                }


                            } else if (!it.team2.players.squad.isNullOrEmpty()) {

                                binding.textSquad.setText("Squad")

                                binding.recyclerTeamBSquad.adapter = SquadCricBuzzTeamBAdapter(
                                    it.team2.players.squad as ArrayList<PlayingXIItem>,
                                    activity,
                                    object : OnSquadClickInterface {
                                        override fun onClick(item: PlayingXIItem) {

                                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                                            intent.putExtra("playerName", item?.fullName.toString())
                                            intent.putExtra("playerId", item?.id)
                                            startActivity(intent)

                                        }

                                    })

                                if (!it.team2.players.supportStaff.isNullOrEmpty()){

                                    binding.linearStaff.visibility = View.VISIBLE

                                    binding.recyclerTeamBStaff.adapter = SupportStaffCricBuzzTeamBAdapter(
                                        it.team2.players.supportStaff as ArrayList<SupportStaffItem>,
                                        activity)

                                }

                            }

                        }

                    }

                }


            }

        })

    }

    private fun observeMatchInfoCricBuzz() {

        viewModel.matchInfoCricBuzzLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                if (it.matchInfo != null) {

                    if (it.matchInfo.team1 != null) {

                        team1 = it.matchInfo.team1

                    }

                    if (it.matchInfo.team2 != null) {

                        team2 = it.matchInfo.team2

                    }

                }

            }

        })

    }


    private fun observeCheckSquad() {
        viewModel.getCheckSquadLiveData().observe(viewLifecycleOwner, Observer {

            if (it == false) {
                binding.progressBar.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })
    }

    override fun onClick(item: PlayerItem) {

        if (teamAModel != null) {

            if (!teamAModel?.player.isNullOrEmpty() && teamAModel?.player!!.contains(item)) {

                if (team1 != null && !team1?.playerDetails.isNullOrEmpty()) {

                    for (player in team1?.playerDetails!!) {

                        if (player?.name.equals(
                                item.name,
                                true
                            ) || player?.nickName.equals(
                                item.name,
                                true
                            ) || player?.fullName.equals(item.name, true)
                        ) {

                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                            intent.putExtra("playerName", player?.fullName.toString())
                            intent.putExtra("playerId", player?.id)
                            startActivity(intent)

                        }

                    }

                }

            }
        }

        if (teamBModel != null) {

            if (!teamBModel?.player.isNullOrEmpty() && teamBModel?.player!!.contains(item)) {

                if (team2 != null && !team2?.playerDetails.isNullOrEmpty()) {

                    for (player in team2?.playerDetails!!) {

                        if (player?.name.equals(
                                item.name,
                                true
                            ) || player?.nickName.equals(
                                item.name,
                                true
                            ) || player?.fullName.equals(item.name, true)
                        ) {

                            val intent = Intent(activity, PlayerProfileActivity::class.java)
                            intent.putExtra("playerName", player?.fullName.toString())
                            intent.putExtra("playerId", player?.id)
                            startActivity(intent)

                        }

                    }

                }

            }
        }


    }


}