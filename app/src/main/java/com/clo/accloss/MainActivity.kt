package com.clo.accloss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.clo.accloss.core.modules.app.App
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()
        AppUpdate()
    }

    private fun AppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    registerForActivityResult(
                        ActivityResultContracts.StartIntentSenderForResult()
                    ) { result: ActivityResult ->
                        if (result.resultCode != RESULT_OK) {
                            println("Update flow failed! Result code: " + result.resultCode.toString())
                            // If the update is canceled or fails,
                            // you can request to start the update again.
                        }
                    },
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }
}
