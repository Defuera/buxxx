package ru.justd.bux.app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import ru.justd.bux.BuildConfig
import ru.justd.bux.app.model.WebsocketApi
import ru.justd.bux.app.model.WebsocketApiImpl
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        const val HEADER_BEARER = "Authorization"
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                            .addHeader(HEADER_BEARER, BuildConfig.API_TOKEN)
                            .addHeader(HEADER_ACCEPT, "application/json")
                            .addHeader(HEADER_ACCEPT_LANGUAGE, "nl-NL,en;q=0.8")
                            .build()
                    val response = chain.proceed(newRequest)
                    response
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build()
    }

    @Provides
    @Singleton
    internal fun provideWebsocketApi(okHttpClient: OkHttpClient): WebsocketApi = WebsocketApiImpl(okHttpClient)

}