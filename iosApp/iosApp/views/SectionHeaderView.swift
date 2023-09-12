//
//  SectionHeaders.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SectionHeaderView: View {
    var header: SectionHeader
    
    var body: some View {
        switch onEnum(of: header) {
        case .large(let largeHeader):
            Text(largeHeader.title)
        case .normal(let normalHeader):
            Text(normalHeader.title)
        case .smallArt(let smallArtHeader):
            Text(smallArtHeader.title)
        case .unknown(_):
            Text("TODO(Unknown)")
        }
    }
}
