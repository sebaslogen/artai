package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

sealed interface ApiListItem {

    companion object {
        val serializers = SerializersModule {
            polymorphic(ApiListItem::class) {
                subclass(ApiBigArt::class)
            }
        }
    }

    @Serializable
    @SerialName("bigArt")
    data class ApiBigArt(
        val id: String,
        val image: String,
    ) : ApiListItem
}