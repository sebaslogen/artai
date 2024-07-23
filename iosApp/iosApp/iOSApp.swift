import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    private var rootHolder: RootHolder { appDelegate.rootHolder }
    
    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    init() {
        Napier.setupLogger()
    }

    var body: some Scene {
        WindowGroup {
            ContentView(rootHolder.root)
                .onChange(of: scenePhase) { newPhase in
                    switch newPhase {
                    case .background: LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                    case .inactive: LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                    case .active: LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                    @unknown default: break
                    }
                }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootHolder: RootHolder = RootHolder()
}

class RootHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    let root: RootComponent
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let iosAppDIComponent = InjectIOSApplicationDIComponent()
        
        root = NavRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            mainCoroutineContext: iosAppDIComponent.mainCoroutineContext(),
            appComponentsDIComponent: iosAppDIComponent.appComponentsDIComponent()
        )
        
        LifecycleRegistryExtKt.create(lifecycle)
    }
    
    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}
