package com.sebaslogen.artai.logger

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

/**
 * Class to send logs to Crashlytics
 * TODO: Add your own implementation of Crashlytics logs in "when" statement
 */
class CrashlyticsAntilog(
    private val crashlyticsAddLog: (priority: Int, tag: String?, message: String?) -> Unit,
    private val crashlyticsSendLog: (throwable: Throwable) -> Unit
) : Antilog() {

    override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        // send only error log
        if (priority < LogLevel.ERROR) return

        crashlyticsAddLog.invoke(priority.ordinal, tag, message)

        throwable?.let {
            when {
                // e.g. http exception, add a customized your exception message
//                it is KtorException -> {
//                    crashlyticsAddLog.invoke(priority.ordinal, "HTTP Exception", it.response?.errorBody.toString())
//                }
            }
            crashlyticsSendLog.invoke(it)
        }
    }
}