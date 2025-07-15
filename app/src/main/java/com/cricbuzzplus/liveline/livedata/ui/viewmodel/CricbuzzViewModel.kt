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

class CricbuzzViewModel : ViewModel() {

    var apiClientCricBuzz: ApiInterface =
        retrofitCricBuzz.create<ApiInterface>(ApiInterface::class.java)

    private var loaderLiveData = MutableLiveData<Boolean>()
    private var dataLoadError = MutableLiveData<String>()

    var seriesInternationalLiveData = MutableLiveData<SeriesCricbuzzResponse>()
    var seriesDomesticLiveData = MutableLiveData<SeriesCricbuzzResponse>()
    var seriesT20LiveData = MutableLiveData<SeriesCricbuzzResponse>()
    var seriesWomenLiveData = MutableLiveData<SeriesCricbuzzResponse>()
    var seriesMatchesLiveData = MutableLiveData<SeriesScheduleResponse>()

    var scheduleInternationalLiveData = MutableLiveData<ScheduleMatchResponse>()
    var scheduleLeagueLiveData = MutableLiveData<ScheduleMatchResponse>()
    var scheduleDomesticLiveData = MutableLiveData<ScheduleMatchResponse>()
    var scheduleWomenLiveData = MutableLiveData<ScheduleMatchResponse>()

    var teamScheduleLiveData = MutableLiveData<TeamScheduleResponse>()
    var teamResultsLiveData = MutableLiveData<TeamScheduleResponse>()
    var teamPlayersLiveData = MutableLiveData<TeamPlayersResponse>()
    var teamsNewsLiveData = MutableLiveData<PlayerNewsResponse>()
    var teamStatsLiveData = MutableLiveData<SeriesStatsCricResponse>()
    var teamStatsDetailLiveData = MutableLiveData<TeamStatsDetailResponse>()

    //var seriesPointTableLiveData = MutableLiveData<PointTableCricResponse>()
    var seriesPointTableLiveData = MutableLiveData<PointTableNewCricResponse>()
    var wtcResponseLiveData = MutableLiveData<WTCResponse>()
    var superLeagueLiveData = MutableLiveData<SuperLeagueResponse>()

    var seriesSquadLiveData = MutableLiveData<SeriesSquadCricResponse>()
    var seriesStatsLiveData = MutableLiveData<SeriesStatsCricResponse>()

    var seriesVenuesLiveData = MutableLiveData<SeriesVenuesResponse>()
    var seriesNewsLiveData = MutableLiveData<PlayerNewsResponse>()
    var seriesPlayersLiveData = MutableLiveData<SeriesSquadMemberResponse>()

    var seriesStatsDetailLiveData = MutableLiveData<SeriesStatsDetailResponse>()

    var browsePlayerLiveData = MutableLiveData<BrowsePlayerResponse>()
    var photosLiveData = MutableLiveData<PhotosResponse>()
    var photosPageLiveData = MutableLiveData<PhotosResponse>()
    var photosGalleryLiveData = MutableLiveData<PhotoGalleryCricResponse>()
    var browsePlayerSearchLiveData = MutableLiveData<BrowsePlayerResponse>()

    var teamsInternationalLiveData = MutableLiveData<TeamsCricbuzzResponse>()
    var teamsDomesticLiveData = MutableLiveData<TeamsCricbuzzResponse>()
    var teamsT20LeagueLiveData = MutableLiveData<TeamsCricbuzzResponse>()
    var teamsWomenLiveData = MutableLiveData<TeamsCricbuzzResponse>()


    var archivesInternationalLiveData = MutableLiveData<ArchivesCricbuzzResponse>()
    var archivesDomesticLiveData = MutableLiveData<ArchivesCricbuzzResponse>()
    var archivesT20LeagueLiveData = MutableLiveData<ArchivesCricbuzzResponse>()
    var archivesWomenLiveData = MutableLiveData<ArchivesCricbuzzResponse>()



    ///////////////////cric buzz //////////////


    fun getSeriesInternational() {
          loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesInternational(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesInternationalLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getSeriesLeagues() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesLeagues(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesT20LiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getSeriesDomestic() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesDomestic(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesDomesticLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getSeriesWomen() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesWomen(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesWomenLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getScheduleInternational() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getScheduleInternational(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                scheduleInternationalLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getScheduleLeague() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getScheduleLeague(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                scheduleLeagueLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getScheduleDomestic() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getScheduleDomestic(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                scheduleDomesticLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getScheduleWomen() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getScheduleWomen(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                scheduleWomenLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesMatches(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesMatches(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesMatchesLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamSchedule(teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamSchedule(Constants.cricHeader,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamScheduleLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamResults(teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamResults(Constants.cricHeader,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamResultsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getTeamPlayers(teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamPlayers(Constants.cricHeader,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamPlayersLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getTeamsNews(teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamNews(Constants.cricHeader,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamsNewsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getTeamStats(teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamsStats(Constants.cricHeader,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamStatsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamStatsDetail(teamId : Int, state : String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamStatsDetail(Constants.cricHeader,teamId,state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamStatsDetailLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getSeriesPointTable(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesPointTable(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesPointTableLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getSeriesWTC() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesWTC(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                wtcResponseLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getSuperLeague() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSuperLeague(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                superLeagueLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getSeriesSquad(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesSquads(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesSquadLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesStats(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesStats(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesStatsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }



    fun getSeriesVenues(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesVenues(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesVenuesLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesNews(seriesId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesNews(Constants.cricHeader,seriesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesNewsLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesPlayer(seriesId : Int, teamId : Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesSquadPlayer(Constants.cricHeader,seriesId,teamId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesPlayersLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getSeriesStatsDetail(seriesId : Int, state : String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getSeriesStatsDetail(Constants.cricHeader,seriesId,state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                seriesStatsDetailLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getBrowsPlayer() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBrowsePlayer(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                browsePlayerLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getPhotoAlbum() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getPhotoAlbum(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                photosLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getPhotoAlbumTime(time:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getPhotoAlbumTime(Constants.cricHeader, time)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                photosPageLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getPhotoGallery(id:Int) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getPhotoGallery(Constants.cricHeader, id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                photosGalleryLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getBrowsPlayerSearch(search:String) {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getBrowsePlayerSearch(Constants.cricHeader,search)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                browsePlayerSearchLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamsInternational() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamsInternational(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamsInternationalLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getTeamsLeague() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamsLeagues(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamsT20LeagueLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamsDomestic() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamsDomestic(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamsDomesticLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getTeamsWomen() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getTeamsWomen(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                teamsWomenLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getArchivesInternational() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getArchivesInternational(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                archivesInternationalLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }

    fun getArchivesLeagues() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getArchivesLeagues(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                archivesT20LeagueLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getArchivesDomestic() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getArchivesDomestic(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                archivesDomesticLiveData.value = result

            }, { error ->
                loaderLiveData.value = false
                // loaderLiveData.value = false
                //           Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "apiClientCricBuzz : " + error.message)
            })
    }


    fun getArchivesWomen() {
        loaderLiveData.value = true
//        dataLoadError.value = ""

        apiClientCricBuzz.getArchivesWomen(Constants.cricHeader)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                loaderLiveData.value = false
                archivesWomenLiveData.value = result

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