
import screen.v1.GetScreenRequest
import screen.v1.GetScreenResponse
import kotlin.coroutines.suspendCoroutine

interface SDUIRpcSuspendClient {
    suspend fun sendRequest(request: GetScreenRequest): GetScreenResponse
}

interface SDUIRpcCallbackClient {
    fun sendRequest(kmpRequest: GetScreenRequest, callback: (GetScreenResponse?, Exception?) -> Unit)
}

class SDUIRpcSuspendClientImpl(
    private val callbackClientCalls: SDUIRpcCallbackClient
): SDUIRpcSuspendClient {

    private suspend fun <In, Out> convertCallbackCallToSuspend(
        input: In,
        callbackClosure: ((In, ((Out?, Throwable?) -> Unit)) -> Unit),
    ): Out {
        return suspendCoroutine { continuation ->
            callbackClosure(input) { result, error ->
                Unit
                when {
                    error != null -> {
                        continuation.resumeWith(Result.failure(error))
                    }
                    result != null -> {
                        continuation.resumeWith(Result.success(result))
                    }
                    else -> {
                        continuation.resumeWith(Result.failure(IllegalStateException("Incorrect grpc call processing")))
                    }
                }
            }
        }
    }

    override suspend fun sendRequest(request: GetScreenRequest): GetScreenResponse {
        return convertCallbackCallToSuspend(request, callbackClosure = { input, callback ->
            callbackClientCalls.sendRequest(input, callback)
        })
    }
}