import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        Napier.setupLogger()
        let gRPCClient = SDUIRpcClient()
        let repo = ExperimentKt.getRepo(client: gRPCClient)
        ExperimentKt.globalRepo = repo
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
