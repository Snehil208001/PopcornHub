package com.example.popcornhub

import android.app.Application
import com.example.popcornhub.di.appModule
import com.example.popcornhub.di.repositoryModule
import com.example.popcornhub.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WatchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WatchApplication)
            modules(appModule, repositoryModule, networkModule)
        }
    }
}
