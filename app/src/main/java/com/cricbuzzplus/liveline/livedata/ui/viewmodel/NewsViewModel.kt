package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.NewsDetailsResponse
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.NewsCategoriesResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.NewsTopicsResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.PlayerNewsResponse
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.retrofitMain
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsViewModel : ViewModel() {
    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var newsListLiveData = MutableLiveData<List<NewsListResponseItem>>()
    private var newsDetailsLiveData = MutableLiveData<NewsDetailsResponse>()

    var newsCricLiveData = MutableLiveData<PlayerNewsResponse>()

    var newsCategoryLiveData = MutableLiveData<NewsCategoriesResponse>()
    var newsByCategoryCricLiveData = MutableLiveData<PlayerNewsResponse>()
    var newsByTopicsCricLiveData = MutableLiveData<PlayerNewsResponse>()


    var newsTopicsLiveData = MutableLiveData<NewsTopicsResponse>()


    fun getNewsCric() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getNewsCategory() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

//        apiClientCricBuzz.getNewsCategory(Constants.cricHeader,"exp=1764493283~acl=/*~hmac=81267f30d2ffe4a483b1f8b553e4a3cda64fb8b10e213cfc9e071406b48ca254")
        apiClientCricBuzz.getNewsCategory(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsCategoryLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getNewsByCategoryCric(id:Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsByCategory(Constants.cricHeader,id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsByCategoryCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getNewsByCategoryPaginate(id:Int,lastId: Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsByCategoryPaginate(Constants.cricHeader,id, lastId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsByCategoryCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getNewsTopics() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsTopics(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsTopicsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getNewsByTopicCric(id:Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsByTopic(Constants.cricHeader,id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsByTopicsCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getNewsByTopicPaginate(id:Int,lastId: Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsByTopicPaginate(Constants.cricHeader,id, lastId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsByTopicsCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getNewsPaginate(lastId: Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getNewsPaginate(Constants.cricHeader, lastId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                newsCricLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
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

    fun getNewsDetail(newsId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getNewsDetails(mPrefs.prefApiToken.toString(), newsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    newsDetailsLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.data)
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


    fun getLoaderLiveData() = loaderLiveData
    fun getDataLoadErrorLiveData() = dataLoadError

    fun getNewsListLiveData() = newsListLiveData
    fun getNewsDetailsLiveData() = newsDetailsLiveData
}