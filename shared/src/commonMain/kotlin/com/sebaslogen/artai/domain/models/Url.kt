package com.sebaslogen.artai.domain.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlin.jvm.JvmInline

@JvmInline
@Parcelize
value class Url(val value: String) : Parcelable