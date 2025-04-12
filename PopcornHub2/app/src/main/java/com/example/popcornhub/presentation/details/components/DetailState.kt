package com.example.popcornhub.presentation.details.components

import com.example.popcornhub.data.model.WatchContent

sealed class DetailState {
    data object Loading : DetailState()
    data class Success(val content: WatchContent):DetailState()
    data class Error(val message: String):DetailState()
}