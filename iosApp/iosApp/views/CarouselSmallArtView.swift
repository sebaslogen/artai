//
//  CarouselSmallArtView.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 04/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//


import SwiftUI
import shared

struct CarouselSmallArtView: View {
    var smallArt: CarouselItem.SmallArt
    var sectionStyle: KMPSection.CarouselCarouselStyle
    
    var body: some View {
        ZStack(alignment: .topTrailing) {
            if #available(iOS 15.0, *) {
                let url = URL(string: smallArt.image.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")
                
                if sectionStyle == KMPSection.CarouselCarouselStyle.squared {
                    AsyncImage(url: url) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 120, height: 120)
                    .clipShape(Rectangle())
                } else if sectionStyle == KMPSection.CarouselCarouselStyle.circle {
                    AsyncImage(url: url) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 120, height: 120)
                    .clipShape(Circle())
                } else if sectionStyle == KMPSection.CarouselCarouselStyle.roundedsquares {
                    AsyncImage(url: url) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 120, height: 120)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                } else {
                    AsyncImage(url: url) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 120, height: 120)
                    .clipShape(Rectangle())
                }
            } else {
                // Fallback on earlier iOS versions
            }
            
            FavoriteView(favorite: smallArt.favorite)
        }
    }
}
