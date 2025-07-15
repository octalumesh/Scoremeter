package com.cricbuzzplus.liveline.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.cricbuzzplus.liveline.livedata.model.MatchListModel
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.response.MyPolls


class AppPrefs(context: Context) {

    val APP_PREF_FILE = "com.cricket.billy247.prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(APP_PREF_FILE, 0)

    /** 0 --> MODE_PRIVATE **/

    val PREF_DEVICE_TOKEN = "pref_token"
    val PREF_LANGUAGE = "pref_language"
    val PREF_HEADER_LANG = "pref_header_lang"
    val PREF_AUTH_TOKEN = "pref_auth_token"
    val PREF_JWT_TOKEN = "pref_jwt_token"
    val PREF_ADVERTISEMENT_ID = "pref_advertisement_id"
    val PREF_USER_DETAILS = "pref_user_details"
    val PREF_APP_SETTINGS = "pref_app_settings"
    val PREF_NAVIGATION_DETAILS = "pref_navigation_details"
    val PREF_EDIT_PROFILE_DETAILS = "pref_editprofile_details"
    val PREF_DARK_CHECK = "pref_dark_check"
    val PREF_AUDIO_LAUNGUAGE = "pref_audio_language"
    val PREF_DISABLE_DIALOG = "pref_disable_dialog"
    val PREF_MY_PREDICTION = "pref_my_prediction"
    val PREF_MATCH_LIST_HOME = "pref_match_list_home"
    val PREF_MATCH_LIST_UPCOMING = "pref_match_list_upcoming"


    var prefApiToken: String?
        get() = prefs.getString(PREF_DEVICE_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_DEVICE_TOKEN, value).apply()
    /* var prefDeviceToken: String
        get() = prefs.getString(PREF_DEVICE_TOKEN, "djfhdsfdsmjfhj")
        set(value) = prefs.edit().putString(PREF_DEVICE_TOKEN, value).apply()

    var prefLanguage: String
        get() = prefs.getString(PREF_LANGUAGE, StaticData.LANG_ENGLISH)
        set(value) = prefs.edit().putString(PREF_LANGUAGE, value).apply()

    var prefHeaderLang: String
        get() = prefs.getString(PREF_HEADER_LANG, "en")
        set(value) = prefs.edit().putString(PREF_HEADER_LANG, value).apply()
*/


    var prefDarkCheck: Boolean
        get() = prefs.getBoolean(PREF_DARK_CHECK, false)
        set(value) = prefs.edit().putBoolean(PREF_DARK_CHECK, value).apply()

    var prefAuthToken: String?
        get() = prefs.getString(PREF_AUTH_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_AUTH_TOKEN, value).apply()

    var prefAudioLanguage: String?
        get() = prefs.getString(PREF_AUDIO_LAUNGUAGE, "")
        set(value) = prefs.edit().putString(PREF_AUDIO_LAUNGUAGE, value).apply()

    var prefDisableDialog: String?
        get() = prefs.getString(PREF_DISABLE_DIALOG, "")
        set(value) = prefs.edit().putString(PREF_DISABLE_DIALOG, value).apply()

    var prefAdvertisementId: String?
        get() = prefs.getString(PREF_ADVERTISEMENT_ID, "")
        set(value) = prefs.edit().putString(PREF_ADVERTISEMENT_ID, value).apply()

    var prefJwtToken: String?
        get() = prefs.getString(PREF_JWT_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_JWT_TOKEN, value).apply()


    var prefMatchList: MatchListModel?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_MATCH_LIST_HOME, ""))) {
                return Gson().fromJson(
                    prefs.getString(PREF_MATCH_LIST_HOME, ""),
                    MatchListModel::class.java
                )
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_MATCH_LIST_HOME, inString).apply()
        }

    var prefMatchListUpcoming: MatchListModel?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_MATCH_LIST_UPCOMING, ""))) {
                return Gson().fromJson(
                    prefs.getString(PREF_MATCH_LIST_UPCOMING, ""),
                    MatchListModel::class.java
                )
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_MATCH_LIST_UPCOMING, inString).apply()
        }


    var prefMyPrediction: MyPolls?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_MY_PREDICTION, ""))) {
                return Gson().fromJson(prefs.getString(PREF_MY_PREDICTION, ""), MyPolls::class.java)
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_MY_PREDICTION, inString).apply()
        }

    var prefUserDetails: LoginResponse?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_USER_DETAILS, ""))) {
                return Gson().fromJson(
                    prefs.getString(PREF_USER_DETAILS, ""),
                    LoginResponse::class.java
                )
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_USER_DETAILS, inString).apply()
        }


    fun clearUserDetails() {
        prefs.edit().clear().apply()
    }
}
