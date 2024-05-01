package com.clo.accloss

import android.app.Application
import com.clo.accloss.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ACCLOSSApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@ACCLOSSApp)
            androidLogger()
        }
    }
}
