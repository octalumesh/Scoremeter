package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StadiumViewModel : ViewModel() {

    var apiClientCricBuzz: ApiInterface = retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

     var loaderLiveData = MutableLiveData<Boolean>()
     var dataLoadError = MutableLiveData<String>()

     var stadiumInfoLiveData = MutableLiveData<StadiumInfoResponse>()
     var stadiumMatchesLiveData = MutableLiveData<StadiumMatchesResponse>()
     var stadiumStatsLiveData = MutableLiveData<StadiumStatsResponse>()



    fun getStadiumInfo(venueId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getVenueInfoCricbuzz(Constants.cricHeader,venueId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                stadiumInfoLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getStadiumMatches(venueId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getVenueMatchesCricbuzz(Constants.cricHeader,venueId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                stadiumMatchesLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getStadiumStats(venueId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getVenueStatsCricbuzz(Constants.cricHeader,venueId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                stadiumStatsLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

}