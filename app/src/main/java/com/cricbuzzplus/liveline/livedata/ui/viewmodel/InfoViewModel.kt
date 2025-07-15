package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.MatchInfoResponse
import com.cricbuzzplus.liveline.livedata.response.MatchOddsResponseItem
import com.cricbuzzplus.liveline.livedata.response.RecentMatchResponseItem
import com.cricbuzzplus.liveline.livedata.response.SquadInfoResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchByIdResponse
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchListCricBuzzResponse
import com.cricbuzzplus.liveline.livedata.response.newresponse.*
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitCricBuzz
import com.cricbuzzplus.liveline.retrofitLogin
import com.cricbuzzplus.liveline.retrofitMain
import com.cricbuzzplus.liveline.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InfoViewModel : ViewModel() {

    var apiClient: ApiInterface = retrofitMain.create<ApiInterface>(ApiInterface::class.java)

    var apiClientCricBuzz: ApiInterface = retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    var apiClientLogin: ApiInterface = retrofitLogin.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    private var squadLiveData = MutableLiveData<SquadInfoResponse>()
    private var matchInfoLiveData = MutableLiveData<MatchInfoResponse>()
    var noDataLiveData = MutableLiveData<Boolean>()
    private var matchOddsLiveData = MutableLiveData<List<MatchOddsResponseItem>>()
    var predictionMatchWiseLiveData = MutableLiveData<PredictionMatchWiseResponse>()
    var createPredictionLiveData = MutableLiveData<CreatePredictionResponse>()
    var teamMatchCompareLiveData = MutableLiveData<TeamWinCompMatchResponse>()
    var teamMatchLastTenCompareLiveData = MutableLiveData<TeamLastTenMatchCompareResponse>()
    var teamTossCompareLiveData = MutableLiveData<TeamTossCompareResponse>()

    //liveline
    var tossCompareLiveData = MutableLiveData<TossCompareResponse>()
    var teamFormLiveData = MutableLiveData<TeamFormResponse>()
    var teamCompareLiveData = MutableLiveData<TeamCompareResponse>()
    var headToHeadLiveData = MutableLiveData<HeadToHeadResponse>()

    var matchListCricBuzzLiveData = MutableLiveData<MatchListCricBuzzResponse>()
    var matchInfoCricBuzzLiveData = MutableLiveData<MatchByIdResponse>()


    var recentMatchesLiveData = MutableLiveData<List<RecentMatchResponseItem>>()


    fun getRecentMatches( ){
       // loaderLiveData.value = true
        //  dataLoadError.value = ""

        apiClient.getRecentMatches(mPrefs.prefApiToken.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    recentMatchesLiveData.value = result.list

                  //  loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.list )
                }else{
                    //   dataLoadError.value = result.message
                 //   loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
              //  loaderLiveData.value = false
                //      Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }


    fun getMatchInfo( match_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getMatchData(mPrefs.prefApiToken.toString(),match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    matchInfoLiveData.value = result.data

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

    fun getTossCompare( team_a_id : Int,team_b_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getTossCompare(mPrefs.prefApiToken.toString(),team_a_id,team_b_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    tossCompareLiveData.value = result.data

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

    fun getTeamForm( team_a_id : Int,team_b_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getTeamForm(mPrefs.prefApiToken.toString(),team_a_id,team_b_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamFormLiveData.value = result.data

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



    fun getTeamCompare( team_a_id : Int,team_b_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getTeamCompare(mPrefs.prefApiToken.toString(),team_a_id,team_b_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamCompareLiveData.value = result.data

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



    fun getHeadToHead( team_a_id : Int,team_b_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getHeadToHead(mPrefs.prefApiToken.toString(),team_a_id,team_b_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    headToHeadLiveData.value = result.data

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



    fun getSquadInfo( match_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getSquadData(mPrefs.prefApiToken.toString(),match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    squadLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.data )
                }else{
                 //   dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
              //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getAllSquad( match_id : Int ){
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClient.getAllSquad(mPrefs.prefApiToken.toString(),match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    squadLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : "+ result.data )
                }else{
                    //   dataLoadError.value = result.message
                    loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
           //     Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getMatchOddds( match_id : Int ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClient.getMatchOdds(mPrefs.prefApiToken.toString(),match_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    matchOddsLiveData.value = result.list
                    noDataLiveData.value = result.status
                   // loaderLiveData.value = false

                   // Log.e("TAG", "login : "+ result.list )
                }else{

                    noDataLiveData.value = result.status
                    //   dataLoadError.value = result.message
                 //   loaderLiveData.value = false
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
               // loaderLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }


    fun fetchMatchWisePoll(userId: Int, match_id: Int, teamAShort: String, teamBShort: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.fetchMatchWisePoll(userId, match_id, teamAShort, teamBShort)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    predictionMatchWiseLiveData.value = result.data
                    //mPrefs.prefAuthToken = result.token

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.data)
                } else {
                    loaderLiveData.value = false

                     if (!result.errors.isNullOrEmpty()) {
                        dataLoadError.value = result.errors
                    } else {
                        dataLoadError.value = result.message
                    }

                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun createPrediction(
        matchid: Int,
        userId: Int,
        seriesName: String,
        teamAShort: String,
        teamBShort: String,
        teamA: String,
        teamB: String,
        teamAImg: String,
        teamBImg: String,
        type: String,
        tossPredict: String,
        matchPredict: String,
        match_type: String,
        matchDate: String,
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.createPolls(
            matchid,
            userId,
            seriesName,
            teamAShort,
            teamBShort,
            teamA,
            teamB,
            teamAImg,
            teamBImg,
            type,
            tossPredict,
            matchPredict,
            match_type,
            matchDate
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    createPredictionLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG", "login : " + result.data)
                } else {
                    loaderLiveData.value = false

                     if (!result.errors.isNullOrEmpty()) {
                        dataLoadError.value = result.errors
                    } else {
                        dataLoadError.value = result.message
                    }
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }


    fun getTeamCompareMatch( teamAId:Int,teamA: String, teamAShort: String,teamBId:Int,teamB: String, teamBShort: String,match_type: String ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClientLogin.teamCompareMatch(teamAId,teamA,teamAShort,teamBId,teamB,teamBShort,match_type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamMatchCompareLiveData.value = result.data
                    noDataLiveData.value = result.status

                }else{

                    noDataLiveData.value = result.status
                }

            }, { error ->
                // loaderLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getTeamCompareMatchTen( teamAId:Int,teamA: String, teamAShort: String,teamBId:Int,teamB: String, teamBShort: String,match_type: String ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClientLogin.teamCompareMatchLastTen(teamAId,teamA,teamAShort,teamBId,teamB,teamBShort,match_type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamMatchLastTenCompareLiveData.value = result.data
                    //noDataLiveData.value = result.status

                }else{

                   // noDataLiveData.value = result.status
                }

            }, { error ->
                // loaderLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
            })
    }

    fun getTeamCompareToss( teamAId:Int,teamA: String, teamAShort: String,teamBId:Int,teamB: String, teamBShort: String,match_type: String ){
        //loaderLiveData.value = true
        //dataLoadError.value = ""

        apiClientLogin.teamCompareToss(teamAId,teamA,teamAShort,teamBId,teamB,teamBShort,match_type)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({result ->

                if(result.status == true){

                    teamTossCompareLiveData.value = result.data
                    noDataLiveData.value = result.status

                }else{

                    noDataLiveData.value = result.status
                }

            }, { error ->
                // loaderLiveData.value = false
                //  Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "login : "+ error.message )
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

        apiClientCricBuzz.getMatchInfoCricbuzz(Constants.cricHeader,matchid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                matchInfoCricBuzzLiveData.value = result

            }, { error ->
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG1111", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getLoaderLiveData() = loaderLiveData
    //fun getDataLoadErrorLiveData() = dataLoadError

    fun getMatchInfoLiveData() = matchInfoLiveData
    fun getSquadLiveData() = squadLiveData
    fun getMatchOddsLiveData() = matchOddsLiveData

}