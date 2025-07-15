package com.cricbuzzplus.liveline.livedata.ui.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.databinding.FragmentTossPredictionBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.newresponse.PredictionListResponseItem
import com.cricbuzzplus.liveline.livedata.ui.adapter.UserPredictionListAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel

class TossPredictionFragment : BaseFragment() {


    private lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentTossPredictionBinding

    var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTossPredictionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        val bundle = arguments

        hideKeyBoard()
        setObservers()

        if (bundle != null) {
            userId = bundle.getInt("userId")
        } else {
            Log.d("TAG", "bundle is null")
        }

        if (userId != 0){
            callMyPredictionList()
        }

        return binding.root
    }



    private fun callMyPredictionList(){

        if (checkForInternet(activity)) {
            viewModel.getPredictionList(userId)
        }
    }


    private fun setObservers(){
        observePrediction()
        observeExtras()
    }



    private fun observePrediction(){

        viewModel.predictionListLiveData.observe(viewLifecycleOwner, Observer {

            try {

                if (!it.isNullOrEmpty()){

                    binding.recyclerPredictionMatch.visibility= View.VISIBLE
                    binding.linearlayoutOddsNotShow.visibility= View.GONE


                    val tossList = it.filter { it.type.equals("toss",true) }

                    if (!tossList.isNullOrEmpty()){
                        binding.recyclerPredictionMatch.adapter = UserPredictionListAdapter(
                            tossList as ArrayList<PredictionListResponseItem>,activity,"toss")
                    }else{
                        binding.recyclerPredictionMatch.visibility= View.GONE
                        binding.linearlayoutOddsNotShow.visibility= View.VISIBLE
                    }


                }else{
                    binding.recyclerPredictionMatch.visibility= View.GONE
                    binding.linearlayoutOddsNotShow.visibility= View.VISIBLE
                }


            }catch (e:Exception){
                binding.recyclerPredictionMatch.visibility= View.GONE
                binding.linearlayoutOddsNotShow.visibility= View.VISIBLE
                e.printStackTrace()
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