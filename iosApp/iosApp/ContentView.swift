import SwiftUI
import shared
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct ContentView: View {
    @StateViewModel var viewModel = InjectApplicationComponent().dynamicUIViewModel

    var body: some View {
        let state: DynamicUIViewState = viewModel.viewState
        switch onEnum(of: state) {
        case .error(_):
            Text("Error loading data :(")
        case .loading(_):
            Text("Loading...")
        case .success(let sucessState):
            ScreenContent(state: sucessState, onRefresh: {
                viewModel.onRefreshClicked()
            })
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
