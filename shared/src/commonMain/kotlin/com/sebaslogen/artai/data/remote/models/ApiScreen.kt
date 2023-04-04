package com.sebaslogen.artai.data.remote.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.*


@Serializable
data class ApiScreenResponse(
    val screen: ApiScreen
)

@Serializable
data class ApiScreen(
    val id: String,
    val sections: List<ApiSection>
)

sealed interface ApiSection {

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        val serializers = SerializersModule {
            polymorphic(ApiSection::class) {
                subclass(ApiCarousel::class)
                subclass(ApiFooter::class)
                defaultDeserializer { ApiUnknown.serializer() }
            }
        }
    }

    @Serializable
    @SerialName("carousel")
    data class ApiCarousel(
        val id: String,
        val header: ApiSectionHeader,
        val style: ApiCarouselStyle,
        val items: List<ApiCarouselItem>
    ) : ApiSection {
        enum class ApiCarouselStyle {
            Squared, Circle, RoundedSquares
        }
    }

    @Serializable
    @SerialName("footer")
    data class ApiFooter(
        val id: String,
        val text: String
    ) : ApiSection

    @Serializable
    @SerialName("list")
    data class ApiList(
        val id: String,
        val header: ApiSectionHeader,
        val items: List<ApiListItem>
    ) : ApiSection

    @Serializable
    data class ApiUnknown(val type: String, val id: String? = null) : ApiSection
}


// TODO: Delete alternative when sure that the above works. Reference https://iamgideon.medium.com/gentle-introduction-to-polymorphic-serialization-in-kotlin-9e561b54a351
//@Serializable(with = ApiSectionSerializer::class)
//sealed class ApiSection {
//    // Enforcing that every subclass should have a variable called type.
//    @SerialName("type")
//    abstract val type: ApiSectionType
//}
//
//object ApiSectionSerializer :
//    JsonContentPolymorphicSerializer<ApiSection>(
//        ApiSection::class
//    ) {
//    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out ApiSection> {
//        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
//            "header" -> HeaderSectionVO.serializer()
//            "video" -> VideoSectionVO.serializer()
//            "logo" -> LogoSectionVO.serializer()
//            else -> throw Exception("ERROR: No Serializer found. Serialization failed.")
//        }
//    }
//}