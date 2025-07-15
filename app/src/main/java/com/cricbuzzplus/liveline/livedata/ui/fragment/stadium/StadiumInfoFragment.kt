package com.cricbuzzplus.liveline.livedata.ui.fragment.stadium

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentStadiumInfoBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.StadiumViewModel

class StadiumInfoFragment : BaseFragment() {

    lateinit var binding: FragmentStadiumInfoBinding
    private lateinit var viewModel: StadiumViewModel

    var stadiumId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStadiumInfoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(StadiumViewModel::class.java)

        hideKeyBoard()
        setObserver()

        val bundle = arguments
        if (bundle != null) {
            stadiumId = bundle.getInt("stadiumId", 0)
        } else {
            Log.d("TAG", "bundle is null")
        }

        callPlayerInfo()

        return binding.root
    }

    fun callPlayerInfo() {

        if (stadiumId != 0) {
            if (isInternetConnection()) {
                viewModel.getStadiumInfo(stadiumId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo() {

        viewModel.stadiumInfoLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                try {

                    if (!it.capacity.isNullOrEmpty()) {
                        binding.stadiumCapacity.setText("" + it.capacity)
                    }else{
                        binding.stadiumCapacity.setText("N.A.")
                    }

                    if (!it.ends.isNullOrEmpty()) {
                        binding.stadiumEnds.setText("" + it.ends)
                    }else{
                        binding.stadiumEnds.setText("N.A.")
                    }

                    if (!it.homeTeam.isNullOrEmpty()) {
                        binding.stadiumHosts.setText(""+it.homeTeam)
                    }else{
                        binding.stadiumHosts.setText("N.A.")
                    }

                    if (!it.city.isNullOrEmpty()) {
                        binding.stadiumLocation.setText(""+it.city+", ${it.country}")
                    }else{
                        binding.stadiumLocation.setText("N.A.")
                    }


                    if (it.floodlights != null) {
                        binding.stadiumFloodLights.setText(""+it.floodlights)
                    }else{
                        binding.stadiumFloodLights.setText("N.A.")
                    }

                    if (!it.knownAs.isNullOrEmpty()) {
                        binding.stadiumKnownAs.setText(""+it.knownAs)
                    }else{
                        binding.stadiumKnownAs.setText("N.A.")
                    }

                    if (!it.timezone.isNullOrEmpty()) {
                        binding.stadiumTimeZone.setText(""+it.timezone)
                    }else{
                        binding.stadiumTimeZone.setText("N.A.")
                    }


                    if (!it.profile.isNullOrEmpty()) {
                        binding.stadiumProfile.setText(HtmlCompat.fromHtml(it.profile.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT))
                    }else{
                        binding.stadiumProfile.setText("N.A.")
                    }



                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else {
               // binding.parent.visibility = View.GONE
              //  binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
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