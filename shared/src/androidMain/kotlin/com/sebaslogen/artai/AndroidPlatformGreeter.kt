package com.sebaslogen.artai

import com.sebaslogen.artai.di.scopes.Singleton
import me.tatarka.inject.annotations.Inject

@Inject
@Singleton
class AndroidPlatformGreeter : PlatformGreeter {
    override fun greet(): String = "Android ${android.os.Build.VERSION.SDK_INT} injected"
}
