import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        Napier.setupLogger()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
