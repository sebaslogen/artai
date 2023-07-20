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
        switch header {
        case is SectionHeader.Large:
            Text((header as! SectionHeader.Large).title)
        case is SectionHeader.Normal:
            Text((header as! SectionHeader.Normal).title)
        case is SectionHeader.SmallArt:
            Text((header as! SectionHeader.SmallArt).title)
        case is SectionHeader.Unknown:
            Text("TODO(Unknown)")
        default:
            Text("Unknown carousel item type")
        }
    }
}
