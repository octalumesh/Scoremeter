package com.cricbuzzplus.liveline.livedata.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricbuzzplus.liveline.livedata.apiservice.ApiInterface
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.response.newresponse.*
import com.cricbuzzplus.liveline.mPrefs
import com.cricbuzzplus.liveline.retrofitLogin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UsersViewModel : ViewModel() {

    var apiClientLogin: ApiInterface = retrofitLogin.create<ApiInterface>(ApiInterface::class.java)

    var loaderLiveData = MutableLiveData<Boolean>()
    var dataLoadError = MutableLiveData<String>()

    var predictionListLiveData = MutableLiveData<List<PredictionListResponseItem>>()
    var userLoginLiveData = MutableLiveData<LoginResponse>()
    var sliderPopUpLiveData = MutableLiveData<SliderResponse>()
    var socialLoginLiveData = MutableLiveData<LoginResponse>()
    var userProfileLiveData = MutableLiveData<LoginResponse>()
    var createPredictionLiveData = MutableLiveData<CreatePredictionResponse>()
    var predictionMatchWiseLiveData = MutableLiveData<PredictionMatchWiseResponse>()
    var topExpertsLiveData = MutableLiveData<TopExpertsResponse>()
    var expertProfileLiveData = MutableLiveData<LoginResponse>()
    var updateProfileLiveData = MutableLiveData<LoginResponse>()
    var allExpertsLiveData = MutableLiveData<List<AllExpertsResponseItem>>()
    var otpLiveData = MutableLiveData<Int>()
    var deleteProfileLiveData = MutableLiveData<Boolean>()
    var uploadAadhaarDetailLiveData = MutableLiveData<Boolean>()
    var uploadBankDetailLiveData = MutableLiveData<Boolean>()
    var uploadPanCardDetailLiveData = MutableLiveData<Boolean>()
    var uploadUpiDetailLiveData = MutableLiveData<Boolean>()
    var withdrawAmountLiveData = MutableLiveData<WithdrawRequestResponse>()
    var kycDocumetsLiveData = MutableLiveData<KYCDetailsResponse>()
    var withdrawListLiveData = MutableLiveData<List<WithdrawListResponseItem>>()

    var otpForgotPasswordLiveData = MutableLiveData<Any>()
    var forgotPasswordLiveData = MutableLiveData<Any>()



    fun registerUser(
        name: String,
        mobile: String,
        email: String,
        password: String,
        referBy: String
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.register(name, mobile, email, password, referBy)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    userLoginLiveData.value = result.data
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

    fun emailLogin(email: String, password: String, fcmToken: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.emailLogin(email, password, fcmToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    mPrefs.prefAuthToken = result.token
                    userLoginLiveData.value = result.data


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

    fun socialLogin(firstName: String, socialId: String, socialType: String, fcmToken: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.socialLogin(firstName, socialId, socialType, fcmToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    mPrefs.prefAuthToken = result.token
                    socialLoginLiveData.value = result.data

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

    fun mobileLogin(mobile: String, password: String, fcmToken: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.mobileLogin(mobile, password, fcmToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    mPrefs.prefAuthToken = result.token
                    userLoginLiveData.value = result.data

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


    fun updateProfile(token: String, firstName: String,email: String,mobile: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.updateProfile(token, firstName,email,mobile)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    updateProfileLiveData.value = result.data

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


    fun updateProfileWithImage(token: String, firstName: RequestBody, email: RequestBody, mobile: RequestBody, image: MultipartBody.Part) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.updateProfileWithImage(token, firstName,email,mobile, image)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    updateProfileLiveData.value = result.data

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


    fun getSliderPopup() {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getSliderList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {


                    sliderPopUpLiveData.value = result.data


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


    fun getTopExperts() {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getTopExperts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    topExpertsLiveData.value = result.data

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

    fun getAllExperts(page: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getAllExperts(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {
                    loaderLiveData.value = false

                    if (!result.list.isNullOrEmpty()) {
                        allExpertsLiveData.value = result.list
                        Log.e("TAG", "login : " + result.list)
                    }

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


    fun getExpertProfile(userId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getExpertProfile(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    expertProfileLiveData.value = result.data

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


    fun verifyOtp(mobile: String, otp: String, fcmToken: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.verifyOtp(mobile, otp, fcmToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    mPrefs.prefAuthToken = result.token
                    userLoginLiveData.value = result.data

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


    fun getMyProfile(token: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getMyProfile(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    userProfileLiveData.value = result.data

                    loaderLiveData.value = false

                    Log.e("TAG101", "login : " + result.data)
                } else {
                    loaderLiveData.value = false

                    Log.e("TAG101", "login err: " + result)

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
                Log.e("TAG101", "login err: " + error.message)
            })
    }


    fun getPredictionList(userId: Int) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.fetchAllPrediction(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    predictionListLiveData.value = result.list

                    loaderLiveData.value = false

                    Log.e("TAG1011", "login : " + result.list)
                } else {
                    loaderLiveData.value = false

                    Log.e("TAG1011", "else : " + result)

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
                Log.e("TAG1011", "login  err : " + error.message)
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

    fun uploadAadharDetail(
        token: String,
        aadharNumber: RequestBody,
        aadharFront: MultipartBody.Part,
        aadharBack: MultipartBody.Part
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.uploadAadharDetails(token, aadharNumber, aadharFront, aadharBack)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    uploadAadhaarDetailLiveData.value = result.status

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


    fun uploadBankDetail(
        token: String,
        accountHolderName: RequestBody,
        bankName: RequestBody,
        ifscCode: RequestBody,
        accountNumber: RequestBody,
        passbook: MultipartBody.Part
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.uploadBankDetails(
            token,
            accountHolderName,
            bankName,
            ifscCode,
            accountNumber,
            passbook
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    uploadBankDetailLiveData.value = result.status

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

    fun uploadPanCardDetails(
        token: String,
        panName: RequestBody,
        panNumber: RequestBody,
        dob: RequestBody,
        panCardImg: MultipartBody.Part
    ) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.uploadPanCardDetails(token, panName, panNumber, dob, panCardImg)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    uploadPanCardDetailLiveData.value = result.status

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
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

    fun uploadUpiDetails(token: String, upiId: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.uploadUpiDetails(token, upiId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    uploadUpiDetailLiveData.value = result.status

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
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

    fun withdrawalAmount(token: String, kycId: String, type: String, amount: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.withdrawalAmount(token, kycId, type, amount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    withdrawAmountLiveData.value = result.data

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
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

    fun withdrawalReferAmount(token: String, kycId: String, type: String, amount: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.withdrawalreferAmount(token, kycId, type, amount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    withdrawAmountLiveData.value = result.data

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
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

    fun getKycDocumentDetails(token: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getDoucmentDetails(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    kycDocumetsLiveData.value = result.data

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
                } else {
                    loaderLiveData.value = false

                    if (!result.errors.isNullOrEmpty()) {
                        //  dataLoadError.value = result.errors.get(0)
                    } else {
                        // dataLoadError.value = result.message
                    }
                    // Toast.makeText(MainApplication.applicationInstance.baseContext,result.message, Toast.LENGTH_LONG).show()
                }

            }, { error ->
                loaderLiveData.value = false
                //    Toast.makeText(MainApplication.applicationInstance.baseContext,error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "login : " + error.message)
            })
    }

    fun getWithdrawList(token: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.getWithdrawalList(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    withdrawListLiveData.value = result.list

                    loaderLiveData.value = false

                    // Log.e("TAG", "login : " + result.data)
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


    fun deleteAccount(token: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.deleteProfile(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    deleteProfileLiveData.value = result.status

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


    fun sendOtpForgetPasswordmobile(mobile: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.sendOtpForgetPasswordmobile(mobile)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    otpForgotPasswordLiveData.value = result.data

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


    fun forgetPasswordnumber(mobile: String, password: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.forgetPasswordnumber(mobile, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    forgotPasswordLiveData.value = result.data

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

    fun sendOtpForgetPasswordemail(email: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.sendOtpForgetPasswordemail(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    otpForgotPasswordLiveData.value = result.data

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


    fun emailverifyOtp(email: String, otp: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.emailverifyOtp(email, otp)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    userLoginLiveData.value = result.data

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

    fun forgetPasswordemail(email: String, password: String) {
        loaderLiveData.value = true
        dataLoadError.value = ""

        apiClientLogin.ForgetPasswordemail(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                if (result.status == true) {

                    forgotPasswordLiveData.value = result.data

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


}