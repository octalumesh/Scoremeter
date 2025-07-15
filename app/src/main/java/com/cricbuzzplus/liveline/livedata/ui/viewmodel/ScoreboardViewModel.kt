package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.ScoreboardResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.ScorecardCricbuzzResponse
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.retrofitMain
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ScoreboardViewModel : ViewModel() {
    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)
    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)


    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var scoreCardLiveData = MutableLiveData<ScoreboardResponse>()
    var scoreCardCricbuzzLiveData = MutableLiveData<ScorecardCricbuzzResponse>()

    fun getScoreCard(match_id: Int) {
        //   loaderLiveData.value = true
        //   dataLoadError.value = ""

        apiClient.getScoreCard(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status) {

                    scoreCardLiveData.value = result.data

                    //  loaderLiveData.value = false

                    // Log.e("TAG", "login : "+ result.data )
                } else {
                    //     dataLoadError.value = result.message
                    //  loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                // loaderLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getScorecardCricBuzz(matchid: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getScorecardCricbuzz(Constants.cricHeader, matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                scoreCardCricbuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    // fun getLoaderLiveData() = loaderLiveData
    // fun getDataLoadErrorLiveData() = dataLoadError

    fun getScoreCardLiveData() = scoreCardLiveData
}