//
//  SDUIScreen.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 25/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMPNativeCoroutinesCombine

struct SDUIScreen: View {
    
    var component: SDUIScreenComponent
    @ObservedObject var viewStateProvider : SDUIComponentViewStateProvider
    
    var body: some View {
        let state: DynamicUIViewState = viewStateProvider.state
        switch onEnum(of: state) {
        case .error(_):
            Text("Error loading data :(")
        case .loading(_):
            Text("Loading...")
        case .success(let sucessState):
            ScreenContent(state: sucessState, onRefresh: {
                component.onRefreshClicked()
            })
        }
    }
}
