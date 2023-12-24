package com.sebaslogen.artai.domain.models

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class Url(val value: String)