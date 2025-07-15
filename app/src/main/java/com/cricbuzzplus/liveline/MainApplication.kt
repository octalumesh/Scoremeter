package com.cricbuzzplus.liveline

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDexApplication
import com.cricbuzzplus.liveline.utils.AppPrefs
import com.cricbuzzplus.liveline.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitMain: Retrofit by lazy {
    MainApplication.retrofitMain
}

val retrofitLogin: Retrofit by lazy {
    MainApplication.retrofitLogin
}

val retrofitCricBuzz: Retrofit by lazy {
    MainApplication.retrofitCricBuzz
}

val mPrefs: AppPrefs by lazy {
    MainApplication.mPrefs!!
}

class MainApplication() : MultiDexApplication() {

    companion object {
        var mPrefs: AppPrefs? = null
        lateinit var retrofitMain: Retrofit
        lateinit var retrofitLogin: Retrofit
        lateinit var retrofitCricBuzz: Retrofit
        lateinit var applicationInstance: MainApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate() {
        super.onCreate()
        mPrefs = AppPrefs(this)
        applicationInstance = this
        initRetrofit()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    fun initRetrofit() {
        retrofitMain = Retrofit.Builder()
            .baseUrl(Constants.BaseURl)
            .client(initOkHttp())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitCricBuzz = Retrofit.Builder()
            .baseUrl(Constants.CricBuzzURl)
            .client(initOkHttp())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitLogin = Retrofit.Builder()
            .baseUrl(Constants.LoginURl)
            .client(initOkHttp())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()




    }


    private val REQUEST_TIMEOUT = 150
    private fun initOkHttp(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
        }
        return httpClient.build()
    }



    var popUpAd = false

    fun setPopUp(popUp :Boolean){
        popUpAd = popUp
    }

    fun getPopUp():Boolean{
        return popUpAd
    }


    var language = "hindi"
    fun setLiveScoreLanguage(language: String) {
        this.language = language
    }

    fun getLiveScoreLanguage(): String {
        return language
    }

    var isLiveScoreActivityFinished = false

    fun setActivityFinished(finished: Boolean) {
        isLiveScoreActivityFinished = finished
    }

    fun getLiveActivityIsFininshed(): Boolean {
        return isLiveScoreActivityFinished
    }

    var squadData = MutableLiveData<Boolean>()


    var voiceActive1 = false

    fun setVoiceActive(voiceActive1: Boolean) {
        this.voiceActive1 = voiceActive1
    }

    fun getVoiceActive(): Boolean {
        return voiceActive1
    }

}