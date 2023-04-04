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
            Button(action: onRefresh) {
                Text("Refresh")
            }
            List(screen.sections, id: \.id) { section in
                switch section {
                case is ApiSection.ApiCarousel:
                    Carousel(section: section as! ApiSection.ApiCarousel)
                case is ApiSection.ApiFooter:
                    Text("TODO(ApiFooter)")
                case is ApiSection.ApiList:
                    Text("TODO(ApiList)")
                case is ApiSection.ApiUnknown:
                    Text("TODO(ApiUnknown)")
                default:
                    Text("Unknown section type")
                }

            }
        }
    }
}
