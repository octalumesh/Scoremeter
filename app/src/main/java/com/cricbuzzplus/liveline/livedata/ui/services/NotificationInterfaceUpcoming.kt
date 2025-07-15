package com.cricbuzzplus.liveline.livedata.ui.services

import com.cricbuzzplus.liveline.livedata.response.UpcomingResponseItem

interface NotificationInterfaceUpcoming {
    fun onClickNotify(model: UpcomingResponseItem)
}