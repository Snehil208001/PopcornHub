package com.example.popcornhub.di

import com.example.popcornhub.data.api.WatchmodeApi
import com.facebook.shimmer.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        provideAuthIntercepter()
    }
    single {
        provideOkHttpClient(get())
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(WatchmodeApi::class.java)
    }
}

private fun provideAuthIntercepter() = Interceptor{chain ->
    val original = chain.request()
    val originalUrl=original.url

    val url = originalUrl.newBuilder()
        .addQueryParameter("api_key","1c9cbb0fe02a14f4ebcdcde0712d4b6c")
        .build()
    val request=original.newBuilder()
        .url(url)
        .build()
    chain.proceed(request)
}
private  fun provideOkHttpClient(authInterceptor: Interceptor) = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY}
        else{
            HttpLoggingInterceptor.Level.NONE
        }
    })

    .connectTimeout(15,TimeUnit.SECONDS)
    .writeTimeout(15,TimeUnit.SECONDS)
    .readTimeout(15,TimeUnit.SECONDS)
    .build()
