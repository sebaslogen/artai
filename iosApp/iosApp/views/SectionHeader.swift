//
//  SectionHeaders.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SectionHeader: View {
    var header: ApiSectionHeader
    
    var body: some View {
        switch header {
        case is ApiSectionHeader.ApiLarge:
            Text((header as! ApiSectionHeader.ApiLarge).title)
        case is ApiSectionHeader.ApiNormal:
            Text((header as! ApiSectionHeader.ApiNormal).title)
        case is ApiSectionHeader.ApiSmallArt:
            Text((header as! ApiSectionHeader.ApiSmallArt).title)
        case is ApiSectionHeader.ApiUnknown:
            Text("TODO(ApiUnknown)")
        default:
            Text("Unknown carousel item type")
        }
    }
}
