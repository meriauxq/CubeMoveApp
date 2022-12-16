package com.quentinmeriaux.cubemove

import android.app.Application
import com.quentinmeriaux.cubemove.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MyApp)
            // Load modules
            modules(appModule)
        }

    }
}