package com.cricbuzzplus.liveline.livedata.ui.fragment.ranking

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentBattingWomenRankingBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerRankingRankItem
import com.cricbuzzplus.liveline.livedata.ui.activity.cricbuzzs.PlayerProfileActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz.PlayerRankingCricBuzzAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnRankingClickInterface
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.RankingViewModel


class BattingWomenRankingFragment : BaseFragment() {

    private lateinit var viewModel: RankingViewModel
    lateinit var binding : FragmentBattingWomenRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)
        binding = FragmentBattingWomenRankingBinding.inflate(inflater, container, false)


        hideKeyBoard()
        setObservers()

        callPlayerRanking("odi")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.odiRank.setOnClickListener {

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.odiRank.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )

                binding.t20Rank.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.colorAccent
                        )
                    )

            }*/

            binding.odiRank.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.t20Rank.setBackgroundTintList(null)

            binding.odiRank.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.t20Rank.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))

            callPlayerRanking("odi")
        }

        binding.t20Rank.setOnClickListener {

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.t20Rank.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.yellow_lgt
                        )
                    )

                binding.odiRank.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            activity,
                            R.color.colorAccent
                        )
                    )

            }*/

            binding.t20Rank.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.txt_title)))
            binding.odiRank.setBackgroundTintList(null)

            binding.t20Rank.setTextColor(ContextCompat.getColor(activity, R.color.white))
            binding.odiRank.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))

            callPlayerRanking("t20")
        }


    }


    fun callPlayerRanking(type:String){
        if (isInternetConnection()) {
            viewModel.getBatsmanWomensCricBuzz(type)
        }
    }

    private fun setObservers(){

        observeExtras()
        observePlayerRanking()

    }


    private fun observePlayerRanking(){

        viewModel.batsmenWomensLiveData.observe(viewLifecycleOwner, Observer{

            if (it != null){

                binding.recyclerPlayer.adapter = PlayerRankingCricBuzzAdapter(it.rank as ArrayList<PlayerRankingRankItem>,activity,object : OnRankingClickInterface {

                    override fun onClick(item: PlayerRankingRankItem) {

                        val intent = Intent(activity, PlayerProfileActivity::class.java)
                        intent.putExtra("playerName", item?.name.toString())
                        intent.putExtra("playerId", item?.id?.toInt())
                        startActivity(intent)

                    }

                })

            }

        })

    }


    private fun observeExtras() {
        viewModel.getLoaderLiveData().observe(viewLifecycleOwner,Observer
        { isLoading -> handleProgressLoader(isLoading!!) })

    }


}