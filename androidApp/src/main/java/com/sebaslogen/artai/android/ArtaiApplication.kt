package com.sebaslogen.artai.android

import android.app.Application
import com.sebaslogen.artai.android.di.components.ApplicationComponent
import com.sebaslogen.artai.android.di.components.ApplicationComponentProvider
import com.sebaslogen.artai.android.di.components.create

class ArtaiApplication : Application(), ApplicationComponentProvider {
    override val component by lazy(LazyThreadSafetyMode.NONE) {
        ApplicationComponent::class.create(applicationContext)
    }
}