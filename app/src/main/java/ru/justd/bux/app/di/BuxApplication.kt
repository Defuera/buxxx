package ru.justd.bux.app.di

import android.app.Application
import ru.justd.bux.app.model.WebsocketApi
import javax.inject.Inject

class BuxApplication : Application() {

    companion object {
        lateinit var component: BuxComponent
    }

    @Inject
    lateinit var websocketApi: WebsocketApi

    override fun onCreate() {
        super.onCreate()

        component = DaggerBuxComponent
                .builder()
                .build()

        component.inject(this)

        //let's make feed be connected throughout application lifecycle
        websocketApi.connect()
    }

}