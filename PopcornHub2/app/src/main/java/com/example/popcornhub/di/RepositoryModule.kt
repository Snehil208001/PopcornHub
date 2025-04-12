package com.example.popcornhub.di

import com.example.popcornhub.data.mapper.WatchMapper
import com.example.popcornhub.data.repository.WatchRepository
import com.example.popcornhub.data.repository.WatchRepositoryimpl
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {
    single { WatchMapper() }
    single<WatchRepository> { WatchRepositoryimpl(get(), get()) }
}
