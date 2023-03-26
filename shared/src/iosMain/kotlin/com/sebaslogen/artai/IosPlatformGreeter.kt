package com.sebaslogen.artai

import com.sebaslogen.artai.di.scopes.Singleton
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIDevice

@Inject
@Singleton
class IosPlatformGreeter : PlatformGreeter {
    override fun greet(): String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion + " injected"

}
