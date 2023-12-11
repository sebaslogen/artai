package com.sebaslogen.artai.android.connect.adapter

import com.connectrpc.Codec
import com.connectrpc.SerializationStrategy
import com.connectrpc.codecNameProto
import com.connectrpc.extensions.GoogleJavaProtobufStrategy
import com.google.protobuf.GeneratedMessageV3
import pbandk.Message
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class PbandkStrategy : SerializationStrategy {
    private val googleProtobufStrat by lazy { GoogleJavaProtobufStrategy() }

    override fun serializationName() = codecNameProto
    override fun errorDetailParser() = PbandkErrorDetailParser

    @Suppress("UNCHECKED_CAST")
    override fun <E : Any> codec(clazz: KClass<E>): Codec<E> {
        // If we are passed a com.google.protobuf Message, let them handle it.
        if (clazz.isSubclassOf(GeneratedMessageV3::class)) {
            return googleProtobufStrat.codec(clazz)
        }
        // Otherwise use our own pbandk adapter
        return PbandkProtoAdapter(clazz as KClass<Message>) as Codec<E>
    }
}