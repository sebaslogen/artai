//
//  ScreenContent.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ScreenContent: View {
    var state: DynamicUIViewState.Success
    var onRefresh: () -> Void

    var body: some View {
        VStack {
            var screen = state.data.screen
            Text("Got ViewModel result: \(screen.id)")
        }
    }
}
