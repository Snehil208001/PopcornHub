package com.example.popcornhub.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.popcornhub.WatchApplication
import com.example.popcornhub.domain.usecase.GetContentDetailsUseCase
import com.example.popcornhub.domain.usecase.GetMoviesAndTvShowsUseCase
import com.example.popcornhub.presentation.home.HomeViewModel
import com.example.popcornhub.presentation.details.DetailsViewModel
//import org.koin.androidx.viewmodel.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {
    single { WatchApplication() }

    factory { GetContentDetailsUseCase(get()) }
    factory { GetMoviesAndTvShowsUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}
