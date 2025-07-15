package com.cricbuzzplus.liveline.livedata.ui.fragment.playerfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentPlayerInfoBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel


class PlayerInfoFragment : BaseFragment() {

    lateinit var binding: FragmentPlayerInfoBinding
    private lateinit var viewModel: PlayersViewModel

    var playerName = ""
    var playerId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
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

        binding.playerName.setText(playerName)


        callPlayerInfo()

        return binding.root
    }

    fun callPlayerInfo() {

        if (playerId != 0) {
            if (isInternetConnection()) {
                viewModel.getPlayerInfo(playerId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo() {

        viewModel.playerInfoLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                binding.parent.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE


                binding.playerName.setText("" + it.name)
                binding.country.setText("" + it.intlTeam)
                binding.playerdob.setText("" + it.doB)
                binding.playerRole.setText("" + it.role)
                binding.playerBirthPlace.setText("" + it.birthPlace)
                binding.playerNickName.setText("" + it.nickName)
                binding.playerBattingStyle.setText("" + it.bat)
                binding.playerBallingStyle.setText("" + it.bowl)
                binding.teamsPlayed.setText("" + it.teams)

                if (!it.bio.isNullOrEmpty()){

                    binding.playerProfile.setText(HtmlCompat.fromHtml(it.bio, HtmlCompat.FROM_HTML_MODE_COMPACT))

                }


                Glide.with(activity).load(it.image).placeholder(R.mipmap.ic_launcher_round)
                    .into(binding.playerImage)

                try {

                    if (it.rankings != null) {

                        if (it.rankings.bat != null) {

                            val batItem = it.rankings.bat

                            if (!batItem?.testRank.isNullOrEmpty()) {
                                binding.rankingBatTest.setText("" + batItem?.testRank)
                            }

                            if (!batItem?.odiRank.isNullOrEmpty()) {
                                binding.rankingBatODI.setText("" + batItem?.odiRank)
                            }

                            if (!batItem?.t20Rank.isNullOrEmpty()) {
                                binding.rankingBatT20.setText("" + batItem?.t20Rank)
                            }


                            if (!batItem?.testBestRank.isNullOrEmpty()) {
                                binding.rankingBatBestTest.setText("Best: " + batItem?.testBestRank)
                                binding.rankingBatBestTest.visibility = View.VISIBLE
                            }

                            if (!batItem?.odiBestRank.isNullOrEmpty()) {
                                binding.rankingBatBestODI.setText("Best: " + batItem?.odiBestRank)
                                binding.rankingBatBestODI.visibility = View.VISIBLE
                            }

                            if (!batItem?.t20BestRank.isNullOrEmpty()) {
                                binding.rankingBatBestT20.setText("Best: " + batItem?.t20BestRank)
                                binding.rankingBatBestT20.visibility = View.VISIBLE
                            }

                        }


                        if (it.rankings.bowl != null) {

                            val bowlItem = it.rankings.bowl

                            if (!bowlItem?.testRank.isNullOrEmpty()) {
                                binding.rankingBowlTest.setText("" + bowlItem?.testRank)
                            }

                            if (!bowlItem?.odiRank.isNullOrEmpty()) {
                                binding.rankingBowlODI.setText("" + bowlItem?.odiRank)
                            }

                            if (!bowlItem?.t20Rank.isNullOrEmpty()) {
                                binding.rankingBowlT20.setText("" + bowlItem?.t20Rank)
                            }

                            if (!bowlItem?.testBestRank.isNullOrEmpty()) {
                                binding.rankingBowlBestTest.setText("Best: " + bowlItem?.testBestRank)
                                binding.rankingBowlBestTest.visibility = View.VISIBLE
                            }

                            if (!bowlItem?.odiBestRank.isNullOrEmpty()) {
                                binding.rankingBowlBestODI.setText("Best: " + bowlItem?.odiBestRank)
                                binding.rankingBowlBestODI.visibility = View.VISIBLE
                            }

                            if (!bowlItem?.t20BestRank.isNullOrEmpty()) {
                                binding.rankingBowlBestT20.setText("Best: " + bowlItem?.t20BestRank)
                                binding.rankingBowlBestT20.visibility = View.VISIBLE
                            }

                        }

                        if (it.rankings.all != null) {

                            val allItem = it.rankings.all

                            if (!allItem?.testRank.isNullOrEmpty()) {
                                binding.rankingAllRoundTest.setText("" + allItem?.testRank)
                            }

                            if (!allItem?.odiRank.isNullOrEmpty()) {
                                binding.rankingAllRoundODI.setText("" + allItem?.odiRank)
                            }

                            if (!allItem?.t20Rank.isNullOrEmpty()) {
                                binding.rankingAllRoundT20.setText("" + allItem?.t20Rank)
                            }

                            if (!allItem?.testBestRank.isNullOrEmpty()) {
                                binding.rankingAllRoundBestTest.setText("Best: " + allItem?.testBestRank)
                                binding.rankingAllRoundBestTest.visibility = View.VISIBLE
                            }

                            if (!allItem?.odiBestRank.isNullOrEmpty()) {
                                binding.rankingAllRoundBestODI.setText("Best: " + allItem?.odiBestRank)
                                binding.rankingAllRoundBestODI.visibility = View.VISIBLE
                            }

                            if (!allItem?.t20BestRank.isNullOrEmpty()) {
                                binding.rankingAllRoundBestT20.setText("Best: " + allItem?.t20BestRank)
                                binding.rankingAllRoundBestT20.visibility = View.VISIBLE
                            }

                        }


                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else {
                binding.parent.visibility = View.GONE
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