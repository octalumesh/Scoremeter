package com.cricbuzzplus.liveline.livedata.apiservice


import com.cricbuzzplus.liveline.livedata.response.*
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.*
import com.cricbuzzplus.liveline.livedata.response.newresponse.*
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("upcomingMatches/"+ "{key}")
    fun getUpcomingMatches(
        @Path("key") key :String
    ): Single<JsonArrayResponse<UpcomingResponseItem>>

    @GET("recentMatches/"+ "{key}")
    fun getRecentMatches(
        @Path("key") key :String
    ): Single<JsonArrayResponse<RecentMatchResponseItem>>

    @GET("homeList/"+ "{key}")
    fun getHomeMatches(
        @Path("key") key :String
    ): Single<JsonArrayResponse<HomeMatchResponseItem>>

    @GET("seriesList/"+ "{key}")
    fun getSeriesList(
        @Path("key") key :String
    ): Single<JsonArrayResponse<SeriesListResponseItem>>

    @FormUrlEncoded
    @POST("matchInfo/"+ "{key}")
    fun getMatchData(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectResponse<MatchInfoResponse>>


    @FormUrlEncoded
    @POST("tossComparisonByTeamId/"+ "{key}")
    fun getTossCompare(
        @Path("key") key :String,
        @Field("team_a_id") team_a_id: Int,
        @Field("team_b_id") team_b_id: Int,
    ): Single<JsonObjectResponse<TossCompareResponse>>



    @FormUrlEncoded
    @POST("teamFormByTeamId/"+ "{key}")
    fun getTeamForm(
        @Path("key") key :String,
        @Field("team_a_id") team_a_id: Int,
        @Field("team_b_id") team_b_id: Int,
    ): Single<JsonObjectResponse<TeamFormResponse>>

    @FormUrlEncoded
    @POST("teamComparisonByTeamId/"+ "{key}")
    fun getTeamCompare(
        @Path("key") key :String,
        @Field("team_a_id") team_a_id: Int,
        @Field("team_b_id") team_b_id: Int,
    ): Single<JsonObjectResponse<TeamCompareResponse>>


    @FormUrlEncoded
    @POST("headToHeadByTeamId/"+ "{key}")
    fun getHeadToHead(
        @Path("key") key :String,
        @Field("team_a_id") team_a_id: Int,
        @Field("team_b_id") team_b_id: Int,
    ): Single<JsonObjectResponse<HeadToHeadResponse>>



    @FormUrlEncoded
    @POST("squadByMatchId/"+ "{key}")
    fun getSquadData(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectResponse<SquadInfoResponse>>


    @FormUrlEncoded
    @POST("playersByMatchId/"+ "{key}")
    fun getAllSquad(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectResponse<SquadInfoResponse>>


    @FormUrlEncoded
    @POST("liveMatch/"+ "{key}")
    fun getLiveScore(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectResponse<LiveResponse>>

    @FormUrlEncoded
    @POST("scorecardByMatchId/"+ "{key}")
    fun getScoreCard(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectResponse<ScoreboardResponse>>

    @FormUrlEncoded
    @POST("matchesBySeriesId/"+ "{key}")
    fun getSeriesMatch(
        @Path("key") key :String,
        @Field("series_id") matchId: Int
    ): Single<JsonArrayResponse<SeriesMatchResponseItem>>


    @POST("commentary/"+ "{key}")
    @FormUrlEncoded
    fun matchCommentary(
        @Path("key") key :String,
        @Field("match_id") matchId: Int
    ): Single<JsonObjectDynamicResponse>


    @FormUrlEncoded
    @POST("pointsTable/"+ "{key}")
    fun getPointList(
        @Path("key") key :String,
        @Field("series_id") matchId: Int
    ): Single<JsonArrayResponse<PointListResponseItem>>


    @GET("news/"+ "{key}")
    fun getNewsList(
        @Path("key") key :String
    ): Single<JsonArrayResponse<NewsListResponseItem>>

    @FormUrlEncoded
    @POST("newsDetail/"+ "{key}")
    fun getNewsDetails(
        @Path("key") key :String,
        @Field("news_id") matchId: Int
    ): Single<JsonObjectResponse<NewsDetailsResponse>>


    @FormUrlEncoded
    @POST("teamRanking/"+ "{key}")
    fun getTeamRanking(
        @Path("key") key :String,
        @Field("type") type: Int
    ): Single<JsonArrayResponse<TeamRankingResponseItem>>

    @FormUrlEncoded
    @POST("matchOddHistory/"+ "{key}")
    fun getMatchOdds(
        @Path("key") key :String,
        @Field("match_id") match_id: Int
    ): Single<JsonArrayResponse<MatchOddsResponseItem>>

    @FormUrlEncoded
    @POST("playerRanking/"+ "{key}")
    fun getPlayerRanking(
        @Path("key") key :String,
        @Field("type") type: Int
    ): Single<JsonArrayResponse<PlayerRankingResponseItem>>

    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("name") firstname: String,
        @Field("mobile_no") mobile: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("referBy") referBy: String,
    ): Single<JsonObjectResponse<LoginResponse>>

    @FormUrlEncoded
    @POST("users/emailLogin")
    fun emailLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fcmTokan") fcmTokan: String,
    ): Single<JsonObjectResponse<LoginResponse>>

    @FormUrlEncoded
    @POST("users/socialLogin")
    fun socialLogin(
        @Field("firstName") firstName: String,
        @Field("socialId") socialId: String,
        @Field("socialType") socialType: String,
        @Field("fcmTokan") fcmTokan: String,
    ): Single<JsonObjectResponse<LoginResponse>>


    @FormUrlEncoded
    @POST("users/mobileLoginwithPassword")
    fun mobileLogin(
        @Field("mobile_no") mobile: String,
        @Field("password") password: String,
        @Field("fcmTokan") fcmTokan: String,
    ): Single<JsonObjectResponse<LoginResponse>>

    @FormUrlEncoded
    @POST("users/sendOtpForgetPasswordmobile")
    fun sendOtpForgetPasswordmobile(
        @Field("mobile_no") mobile: String,
    ): Single<JsonObjectResponse<Any>>

    @FormUrlEncoded
    @POST("users/ForgetPasswordnumber")
    fun forgetPasswordnumber(
        @Field("mobile_no") mobile: String,
        @Field("password") password: String
    ): Single<JsonObjectResponse<Any>>

    @FormUrlEncoded
    @POST("users/sendOtpForgetPasswordemail")
    fun sendOtpForgetPasswordemail(
        @Field("email") email: String,
    ): Single<JsonObjectResponse<Any>>

    @FormUrlEncoded
    @POST("users/emailverifyOtp")
    fun emailverifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Single<JsonObjectResponse<LoginResponse>>


    @FormUrlEncoded
    @POST("users/ForgetPasswordemail")
    fun ForgetPasswordemail(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<JsonObjectResponse<Any>>


    @FormUrlEncoded
    @POST("users/verifyOtp")
    fun verifyOtp(
        @Field("mobile_no") mobile: String,
        @Field("otp") otp: String,
        @Field("fcmTokan") fcmTokan: String,
    ): Single<JsonObjectResponse<LoginResponse>>



    @GET("users/getSliderList")
    fun getSliderList(): Single<JsonObjectResponse<SliderResponse>>



    @FormUrlEncoded
    @POST("users/subscribeMatch")
    fun notificationSettings(
        @Field("match_id") match_id: Int,
        @Field("matchStart") matchStart: Boolean,
        @Field("toss") toss: Boolean,
        @Field("result") result: Boolean,
        @Field("fifty") fifty: Boolean,
        @Field("hundrade") hundrade: Boolean,
        @Field("twohundrade") twohundrade: Boolean,
        @Field("four") four: Boolean,
        @Field("six") six: Boolean,
        @Field("wicket") wicket: Boolean,
        @Field("token") token: String,
    ): Single<JsonObjectResponse<NotificationSettingsResponse>>

    @FormUrlEncoded
    @POST("users/subscribeMatchDetails")
    fun getNotificationSettings(
        @Field("match_id") match_id: Int,
        @Field("token") token: String,
    ): Single<JsonObjectResponse<NotificationSettingsResponse>>


    @GET("api/getUser")
    fun getMyProfile(
        @Header("Authorization") token: String
    ): Single<JsonObjectResponse<LoginResponse>>


    @GET("polls/topExperts")
    fun getTopExperts(
    ): Single<JsonObjectResponse<TopExpertsResponse>>


    @GET("polls/allExperts/{id}")
    fun getAllExperts(
        @Path("id") page :Int
    ): Single<JsonArrayResponse<AllExpertsResponseItem>>


    @FormUrlEncoded
    @POST("polls/getExpertProfile")
    fun getExpertProfile(
        @Field("id") userId: Int
    ): Single<JsonObjectResponse<LoginResponse>>


    @FormUrlEncoded
    @POST("polls/fetchAllPoll")
    fun fetchAllPrediction(
        @Field("userId") userId: Int
    ): Single<JsonArrayResponse<PredictionListResponseItem>>


    @FormUrlEncoded
    @POST("polls/fetchMatchwisePoll")
    fun fetchMatchWisePoll(
        @Field("userId") userId: Int,
        @Field("matchid") matchid: Int,
        @Field("teamAShort") teamAShort: String,
        @Field("teamBShort") teamBShort: String,
    ): Single<JsonObjectResponse<PredictionMatchWiseResponse>>


    @FormUrlEncoded
    @POST("polls/createPolls")
    fun createPolls(
        @Field("matchid") matchid: Int,
        @Field("userId") userId: Int,
        @Field("seriesName") seriesName: String,
        @Field("teamAShort") teamAShort: String,
        @Field("teamBShort") teamBShort: String,
        @Field("teamA") teamA: String,
        @Field("teamB") teamB: String,
        @Field("teamAImg") teamAImg: String,
        @Field("teamBImg") teamBImg: String,
        @Field("type") type: String,
        @Field("tossPredict") tossPredict: String,
        @Field("matchPredict") matchPredict: String,
        @Field("match_type") match_type: String,
        @Field("matchDate") matchDate: String,
    ): Single<JsonObjectResponse<CreatePredictionResponse>>


    @FormUrlEncoded
    @POST("polls/getlastTosswin")
    fun teamCompareToss(
        @Field("team_a_id") team_a_id: Int,
        @Field("team_a") team_a: String,
        @Field("team_a_short") team_a_short: String,
        @Field("team_b_id") team_b_id: Int,
        @Field("team_b") team_b: String,
        @Field("team_b_short") team_b_short: String,
        @Field("match_type") match_type: String
    ): Single<JsonObjectResponse<TeamTossCompareResponse>>


    @FormUrlEncoded
    @POST("polls/getlastwin")
    fun teamCompareMatch(
        @Field("team_a_id") team_a_id: Int,
        @Field("team_a") team_a: String,
        @Field("team_a_short") team_a_short: String,
        @Field("team_b_id") team_b_id: Int,
        @Field("team_b") team_b: String,
        @Field("team_b_short") team_b_short: String,
        @Field("match_type") match_type: String
    ): Single<JsonObjectResponse<TeamWinCompMatchResponse>>

    @FormUrlEncoded
    @POST("polls/getlastTen")
    fun teamCompareMatchLastTen(
        @Field("team_a_id") team_a_id: Int,
        @Field("team_a") team_a: String,
        @Field("team_a_short") team_a_short: String,
        @Field("team_b_id") team_b_id: Int,
        @Field("team_b") team_b: String,
        @Field("team_b_short") team_b_short: String,
        @Field("match_type") match_type: String
    ): Single<JsonObjectResponse<TeamLastTenMatchCompareResponse>>


    @Multipart
    @POST("api/updateprofile")
    fun updateProfileWithImage(
        @Header("Authorization") token: String,
        @Part("name") firstName: RequestBody,
        @Part("email2") email2: RequestBody,
        @Part("mobile2") mobile2: RequestBody,
        @Part image: MultipartBody.Part,
    ): Single<JsonObjectResponse<LoginResponse>>

    @Multipart
    @POST("api/uploadAadharDetails")
    fun uploadAadharDetails(
        @Header("Authorization") token: String,
        @Part("aadharNumber") aadharNumber: RequestBody,
        @Part aadharFront: MultipartBody.Part,
        @Part aadharBack: MultipartBody.Part,
    ): Single<JsonObjectResponse<Any>>

    @Multipart
    @POST("api/uploadBankDetails")
    fun uploadBankDetails(
        @Header("Authorization") token: String,
        @Part("accountHolderName") accountHolderName: RequestBody,
        @Part("bankName") bankName: RequestBody,
        @Part("ifscCode") ifscCode: RequestBody,
        @Part("accountNumber") accountNumber: RequestBody,
        @Part bankPassbook: MultipartBody.Part,
    ): Single<JsonObjectResponse<Any>>

    @Multipart
    @POST("api/uploadPanCardDetails")
    fun uploadPanCardDetails(
        @Header("Authorization") token: String,
        @Part("panName") panName: RequestBody,
        @Part("panNumber") panNumber: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part panCardImg: MultipartBody.Part,
    ): Single<JsonObjectResponse<Any>>

    @FormUrlEncoded
    @POST("api/uploadUpiDetails")
    fun uploadUpiDetails(
        @Header("Authorization") token: String,
        @Field("upiNumber") upiNumber: String,
    ): Single<JsonObjectResponse<Any>>


    @FormUrlEncoded
    @POST("api/withdrawalAmount")
    fun withdrawalAmount(
        @Header("Authorization") token: String,
        @Field("kycId") kycId: String,
        @Field("type") type : String,
        @Field("amount") amount: String,
    ): Single<JsonObjectResponse<WithdrawRequestResponse>>

    @FormUrlEncoded
    @POST("api/withdrawalreferAmount")
    fun withdrawalreferAmount(
        @Header("Authorization") token: String,
        @Field("kycId") kycId: String,
        @Field("type") type : String,
        @Field("amount") amount: String,
    ): Single<JsonObjectResponse<WithdrawRequestResponse>>


    @GET("api/getDoucmentDetails")
    fun getDoucmentDetails(
        @Header("Authorization") token: String
    ): Single<JsonObjectResponse<KYCDetailsResponse>>


    @GET("api/withdrawalList")
    fun getWithdrawalList(
        @Header("Authorization") token: String
    ): Single<JsonArrayResponse<WithdrawListResponseItem>>


    @FormUrlEncoded
    @POST("polls/getDrs")
    fun getDrs(
        @Field("match_id") match_id: Int
    ): Single<JsonObjectResponse<DRSResponse>>

    @FormUrlEncoded
    @POST("polls/getDls")
    fun getDls(
        @Field("match_id") match_id: Int
    ): Single<JsonObjectResponse<DLSResponse>>


    @FormUrlEncoded
    @POST("api/updateprofile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Field("name") firstName: String,
        @Field("email2") email2: String,
        @Field("mobile2") mobile2: String,
    ): Single<JsonObjectResponse<LoginResponse>>


    @POST("api/userDelete")
    fun deleteProfile(
        @Header("Authorization") token: String
    ): Single<JsonObjectResponse<Any>>


    /////////////////cricbuzz/////////////


    @GET("home")
    fun getHomeCrickBuzz(
        @Header("x-auth-user") header:String
    ): Single<HomeCricbuzzResponse>

    @GET("home")
    fun getHomeCrickBuzzs(
        @Header("x-auth-user") header:String
    ): Call<HomeCricbuzzResponse>

    @GET("news")
    fun getNewsCrickBuzz(
        @Header("x-auth-user") header:String
    ): Single<PlayerNewsResponse>

    @GET("news")
    fun getNewsPaginate(
        @Header("x-auth-user") header:String,
        @Query("lastId") lastId:Int,
    ): Single<PlayerNewsResponse>

    @GET("news/category")
    fun getNewsCategory(
        @Header("x-auth-user") header:String,
       // @Header("x-auth-token") header1:String
    ): Single<NewsCategoriesResponse>

    @GET("news/category/{id}")
    fun getNewsByCategory(
        @Header("x-auth-user") header:String,
        @Path("id") id: Int
    ): Single<PlayerNewsResponse>

    @GET("news/category/{id}")
    fun getNewsByCategoryPaginate(
        @Header("x-auth-user") header:String,
        @Path("id") id: Int,
        @Query("lastId") lastId:Int,
    ): Single<PlayerNewsResponse>

    @GET("news/topics")
    fun getNewsTopics(
        @Header("x-auth-user") header:String
    ): Single<NewsTopicsResponse>

    @GET("news/topics/{id}")
    fun getNewsByTopic(
        @Header("x-auth-user") header:String,
        @Path("id") id: Int
    ): Single<PlayerNewsResponse>

    @GET("news/topics/{id}")
    fun getNewsByTopicPaginate(
        @Header("x-auth-user") header:String,
        @Path("id") id: Int,
        @Query("lastId") lastId:Int,
    ): Single<PlayerNewsResponse>

    @GET("matches/upcoming")
    fun getUpcomingCrickBuzz(
        @Header("x-auth-user") header:String
    ): Single<MatchListCricBuzzResponse>

    @GET("matches/recent")
    fun getFinishedCrickBuzz(
        @Header("x-auth-user") header:String
    ): Single<MatchListCricBuzzResponse>

    @GET("matches/live")
    fun getLiveCrickBuzz(
        @Header("x-auth-user") header:String
    ): Single<MatchListCricBuzzResponse>

    @GET("match/{id}")
    fun getMatchInfoCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int
    ): Single<MatchByIdResponse>

    @GET("match/{id}/scorecard")
    fun getScorecardCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int
    ): Single<ScorecardCricbuzzResponse>

    @GET("match/{id}/commentary")
    fun getLiveCommentaryCric(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int,
    ): Single<LiveCommentryCricResponse>

    @GET("match/{id}/commentary")
    fun getLiveCommentaryCricPaginate(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int,
        @Query("inning") inning: Int,
        @Query("lastTimeStamp") lastTimeStamp: Long,
    ): Single<LiveCommentryCricResponse>


    @GET("match/{id}/highlights")
    fun getHighlightsCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int,
        @Query("inning") inning: Int,
        @Query("highLightType") highLightType: Int,
    ): Single<LiveCommentryCricResponse>


    @GET("match/{id}/overs")
    fun getOversCric(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int,
    ): Single<OversNewResponse>

    @GET("match/{id}/overs")
    fun getOversCricPaginate(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int,
        @Query("inning") inning: Int,
        @Query("lastTimeStamp") lastTimeStamp: Long,
    ): Single<OversNewResponse>


    @GET("match/{id}/squads")
    fun getMatchSquadCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") matchid: Int
    ): Single<SquadNewResponse>


    @GET("rankings/team")
    fun getTeamMensRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String
    ): Single<TeamRankingCricbuzzResponse>

    @GET("rankings/batsman")
    fun getBattingMenRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String
    ): Single<PlayerRankingCricbuzzResponse>

    @GET("rankings/bowlers")
    fun getBowlerMenRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String
    ): Single<PlayerRankingCricbuzzResponse>

    @GET("rankings/allrounders")
    fun getAllrounderMenRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String
    ): Single<PlayerRankingCricbuzzResponse>


    @GET("rankings/team")
    fun getTeamWomensRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String,
        @Query("women") women: Int,
    ): Single<TeamRankingCricbuzzResponse>

    @GET("rankings/batsman")
    fun getBattingWomensRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String,
        @Query("women") women: Int,
    ): Single<PlayerRankingCricbuzzResponse>

    @GET("rankings/bowlers")
    fun getBowlerWomensRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String,
        @Query("women") women: Int,
    ): Single<PlayerRankingCricbuzzResponse>

    @GET("rankings/allrounders")
    fun getAllrounderWomensRanking(
        @Header("x-auth-user") header:String,
        @Query("formatType") formatType: String,
        @Query("women") women: Int,
    ): Single<PlayerRankingCricbuzzResponse>


    @GET("browse/series/international")
    fun getSeriesInternational(
        @Header("x-auth-user") header:String
    ): Single<SeriesCricbuzzResponse>

    @GET("browse/series/league")
    fun getSeriesLeagues(
        @Header("x-auth-user") header:String
    ): Single<SeriesCricbuzzResponse>

    @GET("browse/series/domestic")
    fun getSeriesDomestic(
        @Header("x-auth-user") header:String
    ): Single<SeriesCricbuzzResponse>

    @GET("browse/series/women")
    fun getSeriesWomen(
        @Header("x-auth-user") header:String
    ): Single<SeriesCricbuzzResponse>


    @GET("series/{id}")
    fun getSeriesMatches(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<SeriesScheduleResponse>

    @GET("team/{id}/schedule")
    fun getTeamSchedule(
        @Header("x-auth-user") header:String,
        @Path("id") teamId: Int
    ): Single<TeamScheduleResponse>

    @GET("team/{id}/result")
    fun getTeamResults(
        @Header("x-auth-user") header:String,
        @Path("id") teamId: Int
    ): Single<TeamScheduleResponse>


    @GET("team/{id}/players")
    fun getTeamPlayers(
        @Header("x-auth-user") header:String,
        @Path("id") teamId: Int
    ): Single<TeamPlayersResponse>


    @GET("team/{id}/news")
    fun getTeamNews(
        @Header("x-auth-user") header:String,
        @Path("id") teamId: Int
    ): Single<PlayerNewsResponse>

    @GET("team/{id}/stats")
    fun getTeamsStats(
        @Header("x-auth-user") header:String,
        @Path("id") teamId: Int
    ): Single<SeriesStatsCricResponse>

    @GET("team/{id}/stats")
    fun getTeamStatsDetail(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int,
        @Query("stateType") stateType: String,
    ): Single<TeamStatsDetailResponse>


    @GET("schedules/international")
    fun getScheduleInternational(
        @Header("x-auth-user") header:String,
    ): Single<ScheduleMatchResponse>

    @GET("schedules/league")
    fun getScheduleLeague(
        @Header("x-auth-user") header:String,
    ): Single<ScheduleMatchResponse>

    @GET("schedules/domestic")
    fun getScheduleDomestic(
        @Header("x-auth-user") header:String,
    ): Single<ScheduleMatchResponse>

    @GET("schedules/women")
    fun getScheduleWomen(
        @Header("x-auth-user") header:String,
    ): Single<ScheduleMatchResponse>


   /* @GET("series/{id}/pointtable")
    fun getSeriesPointTable(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<PointTableCricResponse>*/

    @GET("series/{id}/pointtable")
    fun getSeriesPointTable(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<PointTableNewCricResponse>

    @GET("icc/wtc")
    fun getSeriesWTC(
        @Header("x-auth-user") header:String
    ): Single<WTCResponse>


    @GET("icc/superLeague")
    fun getSuperLeague(
        @Header("x-auth-user") header:String
    ): Single<SuperLeagueResponse>


    @GET("series/{id}/squads")
    fun getSeriesSquads(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<SeriesSquadCricResponse>


    @GET("series/{id}/stats")
    fun getSeriesStats(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<SeriesStatsCricResponse>

    @GET("series/{id}/venues")
    fun getSeriesVenues(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<SeriesVenuesResponse>

    @GET("series/{id}/news")
    fun getSeriesNews(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int
    ): Single<PlayerNewsResponse>

    @GET("series/{id}/squads/{team}")
    fun getSeriesSquadPlayer(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int,
        @Path("team") team: Int,
    ): Single<SeriesSquadMemberResponse>



    @GET("series/{id}/stats")
    fun getSeriesStatsDetail(
        @Header("x-auth-user") header:String,
        @Path("id") seriesid: Int,
        @Query("statType") statType: String,
    ): Single<SeriesStatsDetailResponse>


    @GET("archives/international")
    fun getArchivesInternational(
        @Header("x-auth-user") header:String
    ): Single<ArchivesCricbuzzResponse>

    @GET("archives/league")
    fun getArchivesLeagues(
        @Header("x-auth-user") header:String
    ): Single<ArchivesCricbuzzResponse>

    @GET("archives/domestic")
    fun getArchivesDomestic(
        @Header("x-auth-user") header:String
    ): Single<ArchivesCricbuzzResponse>

    @GET("archives/women")
    fun getArchivesWomen(
        @Header("x-auth-user") header:String
    ): Single<ArchivesCricbuzzResponse>

    @GET("browse/team/international")
    fun getTeamsInternational(
        @Header("x-auth-user") header:String
    ): Single<TeamsCricbuzzResponse>

    @GET("browse/team/league")
    fun getTeamsLeagues(
        @Header("x-auth-user") header:String
    ): Single<TeamsCricbuzzResponse>

    @GET("browse/team/domestic")
    fun getTeamsDomestic(
        @Header("x-auth-user") header:String
    ): Single<TeamsCricbuzzResponse>

    @GET("browse/team/women")
    fun getTeamsWomen(
        @Header("x-auth-user") header:String
    ): Single<TeamsCricbuzzResponse>


    @GET("browse/player")
    fun getBrowsePlayer(
        @Header("x-auth-user") header:String
    ): Single<BrowsePlayerResponse>



    @GET("browse/player")
    fun getBrowsePlayerSearch(
        @Header("x-auth-user") header:String,
        @Query("search") search:String
    ): Single<BrowsePlayerResponse>


    @GET("photos")
    fun getPhotoAlbum(
        @Header("x-auth-user") header:String
    ): Single<PhotosResponse>

    @GET("photos")
    fun getPhotoAlbumTime(
        @Header("x-auth-user") header:String,
        @Query("timestamp") timestamp:String,
    ): Single<PhotosResponse>

    @GET("photos/{id}")
    fun getPhotoGallery(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PhotoGalleryCricResponse>


    @GET("browse/player/{id}")
    fun getPlayerInfoCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PlayerInfoResponse>


    @GET("browse/player/{id}/batting")
    fun getPlayerBattingCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PlayerBattingResponse>


    @GET("browse/player/{id}/bowling")
    fun getPlayerBowlingCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PlayerBowlingResponse>


    @GET("browse/player/{id}/career")
    fun getPlayerCareerCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PlayerCareerResponse>

    @GET("browse/player/{id}/news")
    fun getPlayerNewsCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") playerId: Int
    ): Single<PlayerNewsResponse>

    @GET("news/details/{id}")
    fun getNewsDetailsCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") newsId: Int
    ): Single<NewsDetailResponse>

    @GET("venue/{id}")
    fun getVenueInfoCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") venueId: Int
    ): Single<StadiumInfoResponse>

    @GET("venue/{id}/matches")
    fun getVenueMatchesCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") venueId: Int
    ): Single<StadiumMatchesResponse>

    @GET("venue/{id}/stats")
    fun getVenueStatsCricbuzz(
        @Header("x-auth-user") header:String,
        @Path("id") venueId: Int
    ): Single<StadiumStatsResponse>

    @GET("checkApp")
    fun getAppCheck(
        @Query ("platform")platform: String,
        @Query ("packagename")packagename: String
    ): Call<AppCheckResponse>

}