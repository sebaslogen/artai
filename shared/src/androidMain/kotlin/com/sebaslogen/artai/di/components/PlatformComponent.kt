package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.AndroidPlatformGreeter
import com.sebaslogen.artai.PlatformGreeter
import me.tatarka.inject.annotations.Provides

interface PlatformComponent {
    @Provides
    fun AndroidPlatformGreeter.bind(): PlatformGreeter = this
}