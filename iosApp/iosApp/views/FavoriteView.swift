//
//  FavoriteView.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 14/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FavoriteView: View {
    var favorite: Favorite
    
    var body: some View {
        let icon = (true ? "heart.fill" : "heart")
        var onAction: () -> Void = {
            print("Favorite button was tapped")
        }
        Button(action: onAction) {
            Image(systemName: icon)
        }.foregroundColor(.red).padding(8)
    }
}
