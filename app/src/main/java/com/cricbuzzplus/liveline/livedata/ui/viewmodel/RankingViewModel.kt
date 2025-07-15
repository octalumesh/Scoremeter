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

class RankingViewModel : ViewModel() {

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    var allrounderMensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var allrounderWomensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var batsmanMensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var batsmenWomensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var bowlerMensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var bowlerWomensLiveData = MutableLiveData<PlayerRankingCricbuzzResponse>()
    var teamRankingMenLiveData = MutableLiveData<TeamRankingCricbuzzResponse>()
    var teamRankingWomenLiveData = MutableLiveData<TeamRankingCricbuzzResponse>()




    ///////////////////cric buzz //////////////


    fun getAllrounderMensCricBuzz(type:String) {
          loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getAllrounderMenRanking(Constants.cricHeader,type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                allrounderMensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getAllrounderWomensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getAllrounderWomensRanking(Constants.cricHeader,type,1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                allrounderWomensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getBatsmanMensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBattingMenRanking(Constants.cricHeader,type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                batsmanMensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getBatsmanWomensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBattingWomensRanking(Constants.cricHeader,type,1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                batsmenWomensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getBowlerMensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBowlerMenRanking(Constants.cricHeader,type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                bowlerMensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getBowlerWomensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBowlerWomensRanking(Constants.cricHeader,type,1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                bowlerWomensLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamMensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamMensRanking(Constants.cricHeader,type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamRankingMenLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getTeamWomensCricBuzz(type:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamWomensRanking(Constants.cricHeader,type,1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamRankingWomenLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    // fun getLoaderLiveData() = loaderLiveData
    // fun getDataLoadErrorLiveData() = dataLoadError
    fun getLoaderLiveData() = loaderLiveData

}