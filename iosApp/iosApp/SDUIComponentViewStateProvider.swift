import shared
import KMPNativeCoroutinesAsync

@MainActor
class SDUIComponentViewStateProvider: ObservableObject {
    
    @Published private(set) var state: DynamicUIViewState = DynamicUIViewState.Loading.shared
//    private var cancellable: AnyCancellable? = nil
    
    init(component: SDUIScreenComponent) {
        let cancellable = Task {
            do {
                let sequence = asyncSequence(for: component.viewStateFlow)
                for try await newState in sequence {
                    self.state = newState
                }
            } catch {
                print("sequence exception error: \(error)")
            }
        }
    }
    
    // To cancel the flow (collection) just cancel the publisher
//    cancellable.cancel()
}
