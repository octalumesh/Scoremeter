package com.cricbuzzplus.liveline.livedata.ui.interfaces

import com.cricbuzzplus.liveline.livedata.response.HomeMatchResponseItem

interface HomeMatchClickInterface {
    fun onClick(item : HomeMatchResponseItem,index:Int)
}