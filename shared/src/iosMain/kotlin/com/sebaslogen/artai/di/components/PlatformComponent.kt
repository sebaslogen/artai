package com.sebaslogen.artai.di.components

import com.sebaslogen.artai.IosPlatformGreeter
import com.sebaslogen.artai.PlatformGreeter
import me.tatarka.inject.annotations.Provides

interface PlatformComponent {
    @Provides
    fun IosPlatformGreeter.bind(): PlatformGreeter = this
}