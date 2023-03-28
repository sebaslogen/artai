package com.sebaslogen.artai.android

import android.content.Context
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

/**
 * Class to send logs to Crashlytics
 * TODO: Add your own implementation of Crashlytics logs in "when" statement
 */
class CrashlyticsAntilog(private val context: Context) : Antilog() {

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        // send only error log
        if (priority < LogLevel.ERROR) return

        throwable?.let {
//                TODO: Add your own implementation of Crashlytics logs in "when" statement
//            when (it) {
            // e.g. http exception, add a customized your exception message
//                is KtorException -> {
//                    FirebaseCrashlytics.getInstance()
//                        .log("${priority.ordinal}, HTTP Exception, ${it.response?.errorBody}")
//                }
//                else -> FirebaseCrashlytics.getInstance().recordException(it)
//            }
        }
    }
}