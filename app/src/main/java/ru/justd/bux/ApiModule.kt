package ru.justd.bux

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.justd.bux.model.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        const val HEADER_BEARER = "Authorization: Bearer"
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
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.BASIC)
                )
                .addInterceptor { chain ->
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                            .addHeader(HEADER_BEARER, BuildConfig.API_BEARER)
                            .addHeader(HEADER_ACCEPT, "application/json")
                            .addHeader(HEADER_ACCEPT_LANGUAGE, "nl-NL,en;q=0.8")
                            .build()
                    chain.proceed(newRequest)
                }
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
    }

    @Provides
    @Singleton
    internal fun provideApiService(builder: Retrofit.Builder): ApiService {
        return builder
                .baseUrl(BuildConfig.HOST)
                .build()
                .create(ApiService::class.java)
    }
}