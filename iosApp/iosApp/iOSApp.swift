import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        Napier.setupLogger()
//        let gRPCClient = SDUIRpcClient()
//        let repo = ExperimentKt.getRepo(gRPCClient)
//
//        let request = Screen_V1_GetScreenRequest(screenID: "home")//, unknownFields: OkioByteString.companion.EMPTY)
//        gRPCClient.sendRequest(kmpRequest: request) { reply, error in
//            print("Reply: \(reply?.screenTitle) - Error: \(error?.message)")
//        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
