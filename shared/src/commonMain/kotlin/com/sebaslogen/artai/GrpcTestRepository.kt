interface GrpcTestRepository {
    suspend fun sduiRequest(screenId: String): String
}