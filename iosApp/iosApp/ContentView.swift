import SwiftUI
import shared

struct ContentView: View {
    private let root: RootComponent
    
    init(_ root: RootComponent) {
        self.root = root
    }
    
    var body: some View {
        StackView(
            stackValue: StateValue(root.childrenStack),
            getTitle: {
                switch $0 {
                case is RootComponentChild.HomeScreen: return "HomeScreen"
                case is RootComponentChild.RemoteScreen: return "RemoteScreen"
                default: return ""
                }
            },
            onBack: {toIndex in },//root.onBackClicked, // TODO
            childContent: {
                switch $0 {
                case let child as RootComponentChild.HomeScreen:
                    SDUIScreen(component: child.component, viewStateProvider: SDUIComponentViewStateProvider(component: child.component))
                case let child as RootComponentChild.RemoteScreen:
                    SDUIScreen(component: child.component, viewStateProvider: SDUIComponentViewStateProvider(component: child.component))
                default: EmptyView()
                }
            }
        )
    }
}
