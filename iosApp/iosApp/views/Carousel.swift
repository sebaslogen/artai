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
                ForEach(section.items, id: \.id) { item in
                    switch item {
                    case is ApiCarouselItem.ApiSmallArt:
                        if #available(iOS 15.0, *) {
                            Text("Img \((item as! ApiCarouselItem.ApiSmallArt).image)")
                                .padding()
                                .background(Color(
                                    red: .random(in: 0...1),
                                    green: .random(in: 0...1),
                                    blue: .random(in: 0...1),
                                    opacity: 1
                                ))
                                .cornerRadius(10)
                        } else {
                            // Fallback on earlier versions
                        }
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
