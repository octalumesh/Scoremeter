package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.response.newresponse.DLSResponse
import com.cricbuzzplus.liveline.livedata.response.newresponse.DRSResponse
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.retrofitLogin
import com.cricbuzzplus.liveline.retrofitMain
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LiveViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    var apiClientLogin: ApiInterface = retrofitLogin.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var scoreLiveData = MutableLiveData<LiveResponse>()
    private var mainScoreLiveData = MutableLiveData<LiveResponse>()
    private var commentaryLiveData =
        MutableLiveData<LinkedHashMap<String, LinkedHashMap<String, List<OverResponseItem>>>>()
    var matchOddsLiveData = MutableLiveData<List<MatchOddsResponseItem>>()
    private var playingSquadLiveData = MutableLiveData<SquadInfoResponse>()
    private var allSquadLiveData = MutableLiveData<SquadInfoResponse>()
    private var checkSquadLiveData = MutableLiveData<Boolean>()

     var homeMatchesLiveData = MutableLiveData<List<HomeMatchResponseItem>>()

    var matchListCricBuzzLiveData = MutableLiveData<MatchListCricBuzzResponse>()
    var matchInfoCricBuzzLiveData = MutableLiveData<MatchByIdResponse>()
    var matchSquadCricBuzzLiveData = MutableLiveData<SquadNewResponse>()
    var highlightCricBuzzLiveData = MutableLiveData<LiveCommentryCricResponse>()
    var liveCommentaryCricBuzzLiveData = MutableLiveData<LiveCommentryCricResponse>()
    var liveCommentaryPaginateCricBuzzLiveData = MutableLiveData<LiveCommentryCricResponse>()

    //var oversCricBuzzLiveData = MutableLiveData<OversResponse>()
    var oversCricBuzzLiveData = MutableLiveData<OversNewResponse>()
    var oversPaginateCricBuzzLiveData = MutableLiveData<OversNewResponse>()
    //var oversPaginateCricBuzzLiveData = MutableLiveData<OversResponse>()

    private var notifySettingsLiveData = MutableLiveData<NotificationSettingsResponse>()
    var notifySettingsDataLiveData = MutableLiveData<NotificationSettingsResponse>()
    var notifySettingsStatusLiveData = MutableLiveData<Boolean>()

    var dlsLiveData = MutableLiveData<DLSResponse>()
    var drsLiveData = MutableLiveData<DRSResponse>()


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

    fun getLiveScore(match_id: Int) {
//      loaderLiveData.value = true
        //   dataLoadError.value = ""

        apiClient.getLiveScore(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status) {

                    scoreLiveData.value = result.data

                    //  loaderLiveData.value = false

                    //  Log.e("TAG", "login : "+ result.data )
                } else {
                    //     dataLoadError.value = result.message
                    //  loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //   loaderLiveData.value = false
                //     Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getLiveScoreMain(match_id: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClient.getLiveScore(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status) {

                    mainScoreLiveData.value = result.data

                    //  loaderLiveData.value = false

                    //  Log.e("TAG", "login : "+ result.data )
                } else {
                    //   dataLoadError.value = result.message
                    // loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAGLogineee", "login :eeeee $error -->" + error.message)
            })
    }

    fun getPlayingSquad(match_id: Int) {


        apiClient.getSquadData(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    playingSquadLiveData.value = result.data

                    checkSquadLiveData.value = result.status
                    Log.e("TAG", "login : " + result.data)
                } else {
                    checkSquadLiveData.value = result.status
                }

            }, { error ->
                checkSquadLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getSquad(match_id: Int) {


        apiClient.getAllSquad(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    allSquadLiveData.value = result.data
                    checkSquadLiveData.value = result.status

                    Log.e("TAG", "login : " + result.data)
                } else {
                    checkSquadLiveData.value = result.status
                }

            }, { error ->
                checkSquadLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getMatchCommentary(match_id: Int) {
        //  loaderLiveData.value = true


        apiClient.matchCommentary(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    commentaryLiveData.value = result.data

                    //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

                    loaderLiveData.value = false

                } else {

                    loaderLiveData.value = true
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                // loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    fun getMatchOdds(match_id: Int) {
        //  loaderLiveData.value = true


        apiClient.getMatchOdds(mPrefs.prefApiToken.toString(), match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    matchOddsLiveData.value = result.list

                    //    Log.e("TAG11", "getMatchCommentary: ${result.data}", )

                    //    loaderLiveData.value = false

                } else {

                    //    loaderLiveData.value = true
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                // loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    ///////////////////cric buzz //////////////


    fun getUpcomingCricBuzz() {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getUpcomingCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchListCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getFinishedCricBuzz() {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getFinishedCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchListCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getLiveCricBuzz() {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getLiveCrickBuzz(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchListCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getMatchInfoCricBuzz(matchid: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getMatchInfoCricbuzz(Constants.cricHeader, matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchInfoCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getMatchHighlightCricBuzz(matchid: Int,inning:Int,type:Int) {
         loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getHighlightsCricbuzz(Constants.cricHeader, matchid,inning,type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                highlightCricBuzzLiveData.value = result

            }, { error ->
                 loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getCommentaryCricBuzz(matchid: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getLiveCommentaryCric(Constants.cricHeader, matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                liveCommentaryCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getLiveCommentaryCricPaginate(matchid: Int,inning:Int,lastTimeStamp:Long) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getLiveCommentaryCricPaginate(Constants.cricHeader, matchid,inning,lastTimeStamp)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                liveCommentaryPaginateCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getOversCricBuzz(matchid: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getOversCric(Constants.cricHeader, matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                oversCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getOversCricPaginate(matchid: Int,inning:Int,lastTimeStamp:Long) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getOversCricPaginate(Constants.cricHeader, matchid,inning,lastTimeStamp)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                oversPaginateCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getMatchSquadCricBuzz(matchid: Int) {
        //  loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getMatchSquadCricbuzz(Constants.cricHeader, matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchSquadCricBuzzLiveData.value = result


            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getDLS(match_id: Int) {
//      loaderLiveData.value = true
        //   dataLoadError.value = ""

        apiClientLogin.getDls( match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status) {

                    dlsLiveData.value = result.data

                    //  loaderLiveData.value = false

                    //  Log.e("TAG", "login : "+ result.data )
                } else {
                    //     dataLoadError.value = result.message
                    //  loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //   loaderLiveData.value = false
                //     Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getDRS(match_id: Int) {
//      loaderLiveData.value = true
        //   dataLoadError.value = ""

        apiClientLogin.getDrs( match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status) {

                    drsLiveData.value = result.data

                    //  loaderLiveData.value = false

                    //  Log.e("TAG", "login : "+ result.data )
                } else {
                    //     dataLoadError.value = result.message
                    //  loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                //   loaderLiveData.value = false
                //     Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    // fun getLoaderLiveData() = loaderLiveData
    // fun getDataLoadErrorLiveData() = dataLoadError
    fun getLoaderLiveData() = loaderLiveData
    fun getScoreLiveData() = scoreLiveData
    fun getLiveScoreLiveData() = mainScoreLiveData
    fun getPlayingSquadLiveData() = playingSquadLiveData
    fun getAllSquadLiveData() = allSquadLiveData
    fun getCheckSquadLiveData() = checkSquadLiveData
    fun getCommentaryLiveData() = commentaryLiveData
    fun getNotifySettingsLiveData() = notifySettingsLiveData

}