package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.PointListResponseItem
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PointTableViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var pointTableLiveData = MutableLiveData<List<PointListResponseItem>>()


    fun getPointTable( id: Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getPointList(mPrefs.prefApiToken.toString(),id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    pointTableLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                // Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }


    fun getLoaderLiveData() = loaderLiveData
   // fun getDataLoadErrorLiveData() = dataLoadError

    fun getPointTableLiveData() = pointTableLiveData
}