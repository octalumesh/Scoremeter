package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.SeriesListResponseItem
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SeriesViewModel : ViewModel() {
    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var seriesMatchesLiveData = MutableLiveData<List<SeriesListResponseItem>>()


    fun getSeries( ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getSeriesList(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    seriesMatchesLiveData.value = result.list

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


    fun getLoaderLiveData() = loaderLiveData
    fun getDataLoadErrorLiveData() = dataLoadError

    fun getSeriesMatchesLiveData() = seriesMatchesLiveData
}