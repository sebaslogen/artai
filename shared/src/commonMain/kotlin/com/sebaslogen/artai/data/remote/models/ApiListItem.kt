package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

sealed interface ApiListItem {

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        val serializers = SerializersModule {
            polymorphic(ApiListItem::class) {
                subclass(ApiBigArt::class)
                defaultDeserializer { ApiUnknown.serializer() }
            }
        }
    }

    @Serializable
    @SerialName("bigArt")
    data class ApiBigArt(
        val id: String,
        val title: String,
        val image: String,
    ) : ApiListItem

    @Serializable
    data class ApiUnknown(val id: String) : ApiListItem
}