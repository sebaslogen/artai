package com.sebaslogen.artai.android

import android.app.Application
import com.sebaslogen.artai.android.di.components.AndroidApplicationDIComponent
import com.sebaslogen.artai.android.di.components.ApplicationDIComponentProvider
import com.sebaslogen.artai.android.di.components.create
import com.sebaslogen.artai.di.components.ApplicationDIComponent
import com.sebaslogen.artai.di.components.create
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class ArtaiApplication : Application(), ApplicationDIComponentProvider {
    override val dependencyInjectionComponent by lazy(LazyThreadSafetyMode.NONE) {
        AndroidApplicationDIComponent::class.create(ApplicationDIComponent::class.create(), applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        setupAppLogger()
    }

    private fun setupAppLogger() {
        if (BuildConfig.DEBUG) {
            // Debug build
// TODO: Add and enable Crashlytics
//            // disable firebase crashlytics
//            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            // init napier
            Napier.base(DebugAntilog())
        } else {
            // Others(Release build)

// TODO: Add and enable Crashlytics
//            // enable firebase crashlytics
//            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            // init napier
            Napier.base(CrashlyticsAntilog(this))
        }
    }
}