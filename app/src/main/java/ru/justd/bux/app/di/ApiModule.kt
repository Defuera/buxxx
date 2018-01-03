package ru.justd.bux.app.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.justd.bux.BuildConfig
import ru.justd.bux.app.model.ApiService
import ru.justd.bux.app.model.WebsocketApi
import ru.justd.bux.app.model.WebsocketApiImpl
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        const val HEADER_BEARER = "Authorizateeion"
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
    internal fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BuildConfig.HOST)
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideWebsocketApi(okHttpClient: OkHttpClient): WebsocketApi = WebsocketApiImpl(okHttpClient)

}