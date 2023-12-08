package com.sebaslogen.artai

import GrpcTestRepository
import SDUIRpcCallbackClient
import SDUIRpcSuspendClientImpl
import pbandk.decodeFromByteArray
import screen.v1.GetScreenRequest
import screen.v1.GetScreenResponse

var globalRepo: GrpcTestRepository? = null
fun getRepo(client: SDUIRpcCallbackClient): GrpcTestRepository {
    return object : GrpcTestRepository {

        override suspend fun sduiRequest(screenId: String): McDScreen {
            val callbackClient = SDUIRpcSuspendClientImpl(client)
            val sendRequest: GetScreenResponse = callbackClient.sendRequest(GetScreenRequest(screenId))
            val mcDScreen = sendRequest.toScreen()
            return mcDScreen
        }

    }
}

fun decodeGRPCResponse(rawResponse: ByteArray): GetScreenResponse? {
    val decodeFromByteArray = GetScreenResponse.decodeFromByteArray(rawResponse)
    return if (decodeFromByteArray != GetScreenResponse.defaultInstance) decodeFromByteArray else null
}