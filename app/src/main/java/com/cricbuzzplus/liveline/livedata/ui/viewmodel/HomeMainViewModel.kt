package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.NotificationSettingsResponse
import com.cricbuzzplus.liveline.livedata.response.SeriesListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.HomeCricbuzzResponse
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.retrofitLogin
import com.cricbuzzplus.liveline.retrofitMain
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeMainViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    var apiClientLogin: ApiInterface = retrofitLogin.create<ApiInterface>(ApiInterface::class.java)

    var apiClientCricBuzz: ApiInterface = retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    var homeCricBuzzLiveData = MutableLiveData<HomeCricbuzzResponse>()


    private var homeMatchesLiveData = MutableLiveData<List<HomeMatchResponseItem>>()
    private var seriesMatchesLiveData = MutableLiveData<List<SeriesListResponseItem>>()
    private var newsListLiveData = MutableLiveData<List<NewsListResponseItem>>()
    private var notifySettingsLiveData = MutableLiveData<NotificationSettingsResponse>()
    var notifySettingsDataLiveData = MutableLiveData<NotificationSettingsResponse>()
    var notifySettingsStatusLiveData = MutableLiveData<Boolean>()


    fun getHomeCricBuzz() {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getHomeCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                homeCricBuzzLiveData.value = result
                Log.e("TAG", "apiClientCricBuzz home data: " + result)


            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getHomeMatches() {
//        loaderLiveData.value = true
        //    dataLoadError.value = ""

        apiClient.getHomeMatches(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    homeMatchesLiveData.value = result.list

                    //     loaderLiveData.value = false

                    // Log.e("TAGHome", "home : "+ result.list )
                } else {
                    //  dataLoadError.value = result.message
                    //    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //  loaderLiveData.value = false
                //     //      Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getNewsList() {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getNewsList(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    newsListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.list)
                } else {
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    fun getSeries() {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getSeriesList(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    seriesMatchesLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.list)
                } else {
                    // dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getNotificationSetting(
        match_id: Int,
        matchStart: Boolean,
        toss: Boolean,
        result: Boolean,
        fifty: Boolean,
        hundrade: Boolean,
        twohundrade: Boolean,
        four: Boolean,
        six: Boolean,
        wicket: Boolean,
        token: String,
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.notificationSettings(match_id, matchStart, toss, result, fifty, hundrade, twohundrade, four, six, wicket, token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    notifySettingsLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.data)
                } else {
                    // dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getNotificationSettingByMatch(
        match_id: Int,
        token: String,
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getNotificationSettings(match_id, token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    notifySettingsDataLiveData.value = result.data

                    loaderLiveData.value = false

                    notifySettingsStatusLiveData.value = result.status

                    Log.e("TAG", "login : " + result.data)
                } else {
                    notifySettingsStatusLiveData.value = result.status
                    // dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    fun getLoaderLiveData() = loaderLiveData
    // fun getDataLoadErrorLiveData() = dataLoadError

    fun getHomeMatchesLiveData() = homeMatchesLiveData
    //fun getDataLoadErrorLiveData() = dataLoadError

    fun getSeriesMatchesLiveData() = seriesMatchesLiveData
    fun getNewsListLiveData() = newsListLiveData
    fun getNotifySettingsLiveData() = notifySettingsLiveData

}