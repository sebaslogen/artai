//
//  Carousel.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//


import SwiftUI
import shared

struct Carousel: View {
    var section: ApiSection.ApiCarousel
    
    var body: some View {
        
        SectionHeader(header: section.header)
        
        ScrollView(.horizontal) {
            LazyHStack {
                Text(section.id)
                ForEach(section.items, id: \.id) { item in
                    switch item {
                    case is ApiCarouselItem.ApiSmallArt:
                        Text("Img \((item as! ApiCarouselItem.ApiSmallArt).image)")
                    case is ApiCarouselItem.ApiUnknown:
                        Text("TODO(ApiUnknown)")
                    default:
                        Text("Unknown carousel item type")
                    }
                }
            }
        }
    }
}
