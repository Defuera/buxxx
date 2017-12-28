package ru.justd.bux

import android.app.Application
import ru.justd.fundaassignment.BuxComponent
import ru.justd.fundaassignment.DaggerBuxComponent

/**
 * Created by defuera on 05/02/2017.
 */
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