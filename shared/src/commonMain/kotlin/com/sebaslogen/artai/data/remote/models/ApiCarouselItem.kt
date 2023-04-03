package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

sealed interface ApiCarouselItem {

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        val serializers = SerializersModule {
            polymorphic(ApiCarouselItem::class) {
                subclass(ApiSmallArt::class)
                defaultDeserializer { ApiUnknown.serializer() }
            }
        }
    }

    @Serializable
    @SerialName("smallArt")
    data class ApiSmallArt(
        val id: String,
        val image: String,
    ) : ApiCarouselItem

    @Serializable
    data class ApiUnknown(val id: String) : ApiCarouselItem
}