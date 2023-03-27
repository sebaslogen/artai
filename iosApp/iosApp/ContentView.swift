import SwiftUI
import shared

struct ContentView: View {
	private let platformGreeter = InjectApplicationComponent().platformGreeterCreator()
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
		Text(platformGreeter.greet())
        Button("log me", action: {
            Napier.d("Welcome to KT logging")
        })
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
