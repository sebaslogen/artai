import SwiftUI
import shared
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct ContentView: View {
    private let platformGreeter = InjectApplicationComponent().platformGreeterCreator()
    @StateViewModel var viewModel = InjectApplicationComponent().dynamicUIViewModel
    let greet = Greeting().greet()

    var body: some View {
        Text(greet)
        Text(platformGreeter.greet())
        let state: DynamicUIViewState = viewModel.viewState
        switch state {
        case is DynamicUIViewState.Error:
            Text("Error loading data :(")
        case is DynamicUIViewState.Loading:
            Text("Loading...")
        case is DynamicUIViewState.Success:
            ScreenContent(state: state as! DynamicUIViewState.Success, onRefresh: {
                viewModel.onRefreshClicked()
            })
        default:
            Text("Unknown state")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
