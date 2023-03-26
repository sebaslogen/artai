package com.sebaslogen.artai.android

import android.app.Application
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.ApplicationComponentProvider
import com.sebaslogen.artai.android.di.components.create
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class ArtaiApplication : Application(), ApplicationComponentProvider {
    override val component by lazy(LazyThreadSafetyMode.NONE) {
        ApplicationComponent::class.create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        setupAppLogger()
    }

    private fun setupAppLogger() {
        Napier.base(DebugAntilog())
    }
}