package com.sebaslogen.artai

import GrpcTestRepository
import SDUIRpcCallbackClient
import SDUIRpcSuspendClientImpl
import screen.v1.GetScreenRequest

var globalRepo: GrpcTestRepository? = null
fun getRepo(client: SDUIRpcCallbackClient): GrpcTestRepository {
    return object : GrpcTestRepository {

        override suspend fun sduiRequest(screenId: String): String {
            val callbackClient = SDUIRpcSuspendClientImpl(client)
            val sendRequest = callbackClient.sendRequest(GetScreenRequest(screenId))
            return sendRequest.screen_title
        }

    }
}