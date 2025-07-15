package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)


    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var seriesListLiveData = MutableLiveData<List<SeriesListResponseItem>>()
    private var newsListLiveData = MutableLiveData<List<NewsListResponseItem>>()
    private var matchListLiveData = MutableLiveData<List<HomeMatchResponseItem>>()
    private var seriesMatchListLiveData = MutableLiveData<List<SeriesMatchResponseItem>>()
    private var pointListLiveData = MutableLiveData<List<PointListResponseItem>>()
    private var teamRankingLiveData = MutableLiveData<List<TeamRankingResponseItem>>()
    private var playerRankingLiveData = MutableLiveData<List<PlayerRankingResponseItem>>()
    private var newsDetailsLiveData = MutableLiveData<NewsDetailsResponse>()

    fun getSeriesList( ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getSeriesList(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    seriesListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getNewsList(  ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getNewsList(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    newsListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getHomeMatches(  ){
        // loaderLiveData.value = true
        // dataLoadError.value = ""

        apiClient.getHomeMatches(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    matchListLiveData.value = result.list

                    // loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    //  dataLoadError.value = result.message
                    //  loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                // loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getSeriesMatches( seriesId :Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getSeriesMatch(mPrefs.prefApiToken.toString(),seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    seriesMatchListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getPointList( seriesId :Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getPointList(mPrefs.prefApiToken.toString(),seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    pointListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getNewsDetail( newsId :Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getNewsDetails(mPrefs.prefApiToken.toString(),newsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    newsDetailsLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.data )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getTeamRanking( type :Int ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClient.getTeamRanking(mPrefs.prefApiToken.toString(),type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamRankingLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    //dataLoadError.value = result.message
                    loaderLiveData.value = true
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //  loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getPlayerRanking( type :Int ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClient.getPlayerRanking(mPrefs.prefApiToken.toString(),type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    playerRankingLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    //dataLoadError.value = result.message
                    loaderLiveData.value = true
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //  loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getLoaderLiveData() = loaderLiveData
    fun getDataLoadErrorLiveData() = dataLoadError

    fun getSeriesListLiveData() = seriesListLiveData
    fun getNewsListLiveData() = newsListLiveData
    fun getMatchListLiveData() = matchListLiveData
    fun getSeriesMatchListLiveData() = seriesMatchListLiveData
    fun getPointListLiveData() = pointListLiveData
    fun getNewsDetailsLiveData() = newsDetailsLiveData
    fun getTeamRankingLiveData() = teamRankingLiveData
    fun getPlayerRankingLiveData() = playerRankingLiveData


}