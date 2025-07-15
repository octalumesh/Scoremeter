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

class PlayersViewModel : ViewModel() {

    var apiClientCricBuzz: ApiInterface = retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

     var loaderLiveData = MutableLiveData<Boolean>()
     var dataLoadError = MutableLiveData<String>()

     var playerInfoLiveData = MutableLiveData<PlayerInfoResponse>()
     var playerBattingLiveData = MutableLiveData<PlayerBattingResponse>()
     var playerBowlingLiveData = MutableLiveData<PlayerBowlingResponse>()
     var playerCareerLiveData = MutableLiveData<PlayerCareerResponse>()
     var playerNewsListLiveData = MutableLiveData<PlayerNewsResponse>()
     var newsDetailsLiveData = MutableLiveData<NewsDetailResponse>()


    fun getPlayerInfo(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getPlayerInfoCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                playerInfoLiveData.value = result

                    //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                 loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getPlayerBatting(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getPlayerBattingCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                playerBattingLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    fun getPlayerBowling(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getPlayerBowlingCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                playerBowlingLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getPlayerCareer(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getPlayerCareerCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                playerCareerLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getPlayerNews(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getPlayerNewsCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                playerNewsListLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getNewsDetails(playerId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientCricBuzz.getNewsDetailsCricbuzz(Constants.cricHeader,playerId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false

                newsDetailsLiveData.value = result

                //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

            }, { error ->
                loaderLiveData.value = false
                dataLoadError.value = error.message
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

}