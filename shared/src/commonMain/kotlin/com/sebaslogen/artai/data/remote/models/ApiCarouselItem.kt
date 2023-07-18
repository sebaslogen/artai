package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class ApiCarouselItem {

    abstract val id: String?

    companion object {
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
        override val id: String,
        val image: String,
        val action: ApiAction,
    ) : ApiCarouselItem()

    @Serializable
    data class ApiUnknown(val type: String, override val id: String? = null) : ApiCarouselItem()
}