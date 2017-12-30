package ru.justd.bux.app.di

import dagger.Module
import dagger.Provides
import ru.justd.bux.app.Router
import javax.inject.Singleton

@Module
class BuxModule {

    @Singleton
    @Provides
    fun provideRouter() = Router()

}