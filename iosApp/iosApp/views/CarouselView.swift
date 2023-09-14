//
//  Carousel.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//


import SwiftUI
import shared

struct CarouselView: View {
    var section: KMPSection.Carousel
    
    var body: some View {
        
        SectionHeaderView(header: section.header)
        if section.style == KMPSection.CarouselCarouselStyle.squared {
            Text("Style parsed")
        }
//        var itemShapeStyle: AnyShape = AnyShape(Rectangle())
//            if section.style == KMPSection.CarouselCarouselStyle.squared {
//                itemShapeStyle = AnyShape(RoundedRectangle(cornerRadius: 0))
//            } else if section.style == KMPSection.CarouselCarouselStyle.circle {
//                itemShapeStyle = AnyShape(Circle())
//            } else if section.style == KMPSection.CarouselCarouselStyle.roundedsquares {
//                itemShapeStyle = AnyShape(RoundedRectangle(cornerRadius: 8))
//            } else {
//                itemShapeStyle = AnyShape(Rectangle())
//            }
        
        ScrollView(.horizontal) {
            LazyHStack {
                ForEach(section.items, id: \.id) { item in
                    switch onEnum(of: item) {
                    case .smallArt(let smallArt):
                        CarouselSmallArtView(smallArt: smallArt, sectionStyle: section.style)
                    case .unknown(_):
                        Text("TODO(Unknown)")
                    }
                }
            }
        }
    }
}
