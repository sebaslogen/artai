package com.sebaslogen.artai.networking

import SDUIRpcSuspendClient
import com.connectrpc.ProtocolClientInterface
import com.connectrpc.ResponseMessage
import screen.v1.GetScreenRequest
import screen.v1.GetScreenResponse
import screen.v1.ScreenServiceClient

class ConnectGRPCClient(grpcClient: ProtocolClientInterface): SDUIRpcSuspendClient {
    private val sduiClient = ScreenServiceClient(grpcClient)

    override suspend fun sendRequest(request: GetScreenRequest): GetScreenResponse {
        return when (val response = sduiClient.getScreen(request = request, headers = emptyMap())) {
            is ResponseMessage.Failure -> TODO("Handle failure")
            is ResponseMessage.Success -> response.message
        }
    }
}