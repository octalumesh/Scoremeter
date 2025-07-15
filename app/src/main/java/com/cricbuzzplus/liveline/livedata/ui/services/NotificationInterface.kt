package com.cricbuzzplus.liveline.livedata.ui.services

import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem

interface NotificationInterface {
    fun onClickNotify(model: HomeMatchResponseItem)
}