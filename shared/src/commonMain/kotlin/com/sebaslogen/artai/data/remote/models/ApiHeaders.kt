package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

sealed interface ApiSectionHeader {

    companion object {
        val serializersModule = SerializersModule {
            polymorphic(ApiSectionHeader::class) {
                subclass(ApiNormal::class)
                subclass(ApiSmallArt::class)
            }
        }
    }

    @Serializable
    @SerialName("normal")
    data class ApiNormal(
        val id: String,
        val title: String,
    ) : ApiSectionHeader

    @Serializable
    @SerialName("smallArt")
    data class ApiSmallArt(
        val id: String,
        val image: String,
        val title: String,
        val subtitle: String,
    ) : ApiSectionHeader
}