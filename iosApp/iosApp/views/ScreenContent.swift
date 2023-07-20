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
            var screen = state.data
            Text("Got ViewModel result: \(screen.id)")
            Button(action: onRefresh) {
                Text("Refresh")
            }
            List(screen.sections, id: \.id) { section in
                switch section {
                case is KMPSection.Carousel:
                    CarouselView(section: section as! KMPSection.Carousel)
                case is KMPSection.Footer:
                    Text("TODO(Footer)")
                case is KMPSection.ListSection:
                    Text("TODO(ListSection)")
                case is KMPSection.Unknown:
                    Text("TODO(Unknown)")
                default:
                    Text("Unknown section type")
                }

            }
        }
    }
}
