package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class NewsListResponse(

	@field:SerializedName("NewsListResponse")
	val newsListResponse: List<NewsListResponseItem?>? = null
)

data class NewsListResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("pub_date")
	val pubDate: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("news_id")
	val newsId: Int? = null
)
