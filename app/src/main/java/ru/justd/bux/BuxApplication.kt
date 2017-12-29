package ru.justd.bux

import android.app.Application

class BuxApplication : Application() {

    companion object {
        lateinit var component: BuxComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerBuxComponent
                .builder()
                .build()
    }

}