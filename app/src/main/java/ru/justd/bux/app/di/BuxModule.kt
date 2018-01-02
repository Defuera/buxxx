package ru.justd.bux.app.di

import dagger.Module
import dagger.Provides
import ru.justd.bux.app.Router
import ru.justd.bux.app.model.WebsocketApi
import ru.justd.bux.product.model.Feed
import javax.inject.Singleton

@Module
class BuxModule {

    @Singleton
    @Provides
    fun provideRouter() = Router()

    @Singleton
    @Provides
    fun provideFeed(websocketApi: WebsocketApi) = Feed(websocketApi)

}