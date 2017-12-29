package ru.justd.bux

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BuxModule {

    @Singleton
    @Provides
    fun provideRouter() = Router()

}