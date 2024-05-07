package com.clo.accloss

import android.app.Application
import com.clo.accloss.core.di.initKoin
import com.clo.accloss.core.presentation.auth.login.di.authModule
import com.clo.accloss.core.presentation.dashboard.di.dashboardModule
import com.clo.accloss.core.presentation.home.di.homeModule
import com.clo.accloss.core.presentation.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ACCLOSSApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            additionalModules = listOf(
                homeModule,
                authModule,
                dashboardModule,
                profileModule
            )
        ) {
            androidContext(this@ACCLOSSApp)
            androidLogger()
        }
    }
}
