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
    var onAction: (_ action: Action) -> Void

    var body: some View {
        VStack {
            let screen = state.data
            Text("Got ViewModel result: \(screen.id)")
            Button(action: onRefresh) {
                Text("Refresh")
            }
            List(screen.sections, id: \.id) { section in
                switch onEnum(of: section) {
                case .carousel(let carousel):
                    CarouselView(section: carousel, onAction: onAction)
                case .footer(let footer):
                    Text("TODO(Footer)")
                case .listSection(let listSection):
                    Text("TODO(ListSection)")
                case .unknown(_):
                    Text("TODO(Unknown)")
                }
            }
        }
    }
}
