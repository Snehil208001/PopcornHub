package com.example.popcornhub.domain.usecase

import com.example.popcornhub.data.model.WatchContent
import com.example.popcornhub.data.repository.WatchRepository

class GetContentDetailsUseCase(private val repository: WatchRepository ) {
    suspend operator fun invoke(contentId:String, isMovie:Boolean):WatchContent{
        return repository.getContentDetails(contentId,isMovie)
    }
}