package com.sebaslogen.artai.logger

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Suppress("UNUSED") // Called from iOS Swift code
fun debugBuild() {
    Napier.base(DebugAntilog())
}

@Suppress("UNUSED") // Called from iOS Swift code
fun releaseBuild(antilog: Antilog) {
    Napier.base(antilog)
}