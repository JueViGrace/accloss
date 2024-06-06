package com.clo.accloss

import android.app.Application
import com.clo.accloss.core.di.Koin
import com.clo.accloss.core.modules.contact.di.contactsModule
import com.clo.accloss.core.modules.dashboard.di.dashboardModule
import com.clo.accloss.core.modules.home.di.homeModule
import com.clo.accloss.core.modules.profile.di.profileModule
import com.clo.accloss.core.modules.syncronize.di.synchronizeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ACCLOSSApp : Application() {

    private val koin = Koin()

    override fun onCreate() {
        super.onCreate()

        koin.init(
            additionalModules = listOf(
                homeModule,
                dashboardModule,
                profileModule,
                contactsModule,
                synchronizeModule
            )
        ) {
            androidContext(this@ACCLOSSApp)
            androidLogger()
        }
    }
}
