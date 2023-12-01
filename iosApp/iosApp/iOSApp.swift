import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        Napier.setupLogger()
        let gRPCClient = SDUIRpcClient()
        let repo = ExperimentKt.getRepo(client: gRPCClient)
        ExperimentKt.globalRepo = repo
    // TODO: pass repo to KT VM and make a call to KT VM network function
//        repo.sduiRequest(screenId: "home", completionHandler: )
//
//        var request = Screen_V1_GetScreenRequest()
//        request.screenID = "home"
//        gRPCClient.sendRequest(kmpRequest: GetScreenRequest(screen_id: "home") { reply, error in
//            print("Reply: \(reply?.screenTitle) - Error: \(error?.message)")
//        }
//        repo.sduiRequest(kmpRequest: request) { reply, error in
//            print("Reply: \(reply?.screenTitle) - Error: \(error?.message)")
//        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
