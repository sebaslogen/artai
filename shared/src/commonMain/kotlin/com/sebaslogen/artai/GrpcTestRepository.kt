import com.sebaslogen.artai.McDScreen

interface GrpcTestRepository {
    suspend fun sduiRequest(screenId: String): McDScreen
}