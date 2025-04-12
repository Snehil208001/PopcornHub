package com.example.movieapp.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.popcornhub.home.HomeState
import com.example.popcornhub.home.components.ContentGrid
import com.example.popcornhub.home.components.ErrorContent
import com.example.popcornhub.home.components.HomeTopBar
import com.example.popcornhub.home.components.ShimmerEffect
import com.example.popcornhub.presentation.home.HomeViewModel
import com.example.popcornhub.utils.ContentType
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetails: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val contentType by viewModel.contentType.collectAsState()

    Column {
        HomeTopBar(
            selectedType = contentType,
            onTypeSelected = viewModel::setContentType
        )

        AnimatedContent(targetState = state) { currentState ->
            when (currentState) {
                is HomeState.Loading -> ShimmerEffect()
                is HomeState.Success -> {
                    val content = when (contentType) {
                        ContentType.MOVIES -> currentState.movies
                        ContentType.TV_SHOWS -> currentState.tvShows
                    }
                    ContentGrid(
                        content = content, // ✅ fixed only this
                        onItemClick = onNavigateToDetails,
                    )
                }
                is HomeState.Error -> {
                    ErrorContent(
                        message = currentState.message,
                        onRetry = viewModel::loadContent,
                    )
                }
            }
        }
    }
}
