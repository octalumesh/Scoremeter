package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.NotificationSettingsResponse
import com.cricbuzzplus.liveline.livedata.response.UpcomingResponseItem
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitLogin
import com.cricbuzzplus.liveline.retrofitMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    var apiClientLogin: ApiInterface = retrofitLogin.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var upcomingMatchesLiveData = MutableLiveData<List<UpcomingResponseItem>>()

    var notifySettingsDataLiveData = MutableLiveData<NotificationSettingsResponse>()
    var notifySettingsStatusLiveData = MutableLiveData<Boolean>()
    private var notifySettingsLiveData = MutableLiveData<NotificationSettingsResponse>()

    fun getUpcomingMatches( ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getUpcomingMatches(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    upcomingMatchesLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
            //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : "+ error.message )
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
    fun getDataLoadErrorLiveData() = dataLoadError

    fun getUpcomingMatchesLiveData() = upcomingMatchesLiveData
    fun getNotifySettingsLiveData() = notifySettingsLiveData

}