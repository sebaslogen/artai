import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}

class AppDelegate: NSObject, NSApplicationDelegate {
	func applicationDidFinishLaunching(_ notification: Notification) {
		#if DEBUG
		// Debug build
		
		// init napier
		NapierProxyKt.debugBuild()
		
		#else
		// Others(Release build)
		
//		// init firebase crashlytics
//		FirebaseApp.configure()
//
//		// init napier
//		NapierProxyKt.releaseBuild(antilog: CrashlyticsAntilog(
//			crashlyticsAddLog: { priority, tag, message in
//				Crashlytics.crashlytics().log("\(String(describing: tag)): \(String(describing: message))")
//		},
//			crashlyticsSendLog: { throwable in
//				Crashlytics.crashlytics().record(error: throwable)
//		}))
		#endif
	}
}