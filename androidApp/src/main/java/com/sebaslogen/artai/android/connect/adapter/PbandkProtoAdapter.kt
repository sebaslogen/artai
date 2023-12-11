package com.sebaslogen.artai.android.connect.adapter

import com.connectrpc.Codec
import com.connectrpc.codecNameProto
import okio.Buffer
import okio.BufferedSource
import pbandk.ExperimentalProtoReflection
import pbandk.Message
import pbandk.decodeFromByteArray
import pbandk.encodeToByteArray
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

class PbandkProtoAdapter<E : Message>(clazz: KClass<E>) : Codec<E> {
    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalProtoReflection::class)
    val decoder by lazy {
        val companion = clazz.companionObjectInstance as Message.Companion<E>
        companion.descriptor.messageCompanion
    }

    override fun encodingName() = codecNameProto

    override fun deserialize(source: BufferedSource): E = decoder.decodeFromByteArray(source.readByteArray())

    override fun serialize(message: E) = Buffer().write(message.encodeToByteArray())

    // Looking at the pbandk serializer, it looks like this one is deterministic by default
    // On the other hand we don't use the idempotency features of connect so just satisfy the interface...
    override fun deterministicSerialize(message: E): Buffer = serialize(message)
}
