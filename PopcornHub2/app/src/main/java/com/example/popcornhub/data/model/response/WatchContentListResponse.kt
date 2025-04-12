package com.example.popcornhub.data.model.response

data class WatchContentListResponse(
    val page: String,
    val results: List<WatchContentResponse>,
    val total_pages: Int,
    val total_results: Int
)
