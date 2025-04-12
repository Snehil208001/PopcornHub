package com.example.popcornhub.data.mapper

import com.example.popcornhub.data.model.WatchContent
import com.example.popcornhub.data.model.response.WatchContentResponse

class WatchMapper {

    fun mapResponseToWatchContent(input: WatchContentResponse): WatchContent {
        val isTvShow = input.first_air_date != null
        return WatchContent(
            id = input.id.toString(),
            title = input.title ?: input.name ?: "",
            posterUrl = input.poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
            description = input.overview.orEmpty(),
            releaseDate = input.release_date ?: input.first_air_date ?: "",
            type = if (isTvShow) "tv_show" else "movie",
            isMovie = !isTvShow
        )
    }

    fun mapResponseListToWatchContent(input: List<WatchContentResponse>): List<WatchContent> {
        return input.map { mapResponseToWatchContent(it) }
    }
}
