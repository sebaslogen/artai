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
            
            
            //                            AsyncImage(url: url) { image in
            //                                image.resizable()
            //                            } placeholder: {
            //                                ProgressView()
            //                            }
            //                            .frame(width: 120, height: 120)
            //                            .clipShape(AnyShape(Circle())) // iOS16
            //                            .mask(RoundedRectangle(cornerRadius: 8))
            //                            .mask(RoundedRectangle(cornerRadius: cornerRadius))
            
            //                            Text(((item as! CarouselItem.SmallArt).image))
            //                                .padding()
            //                                .background(Color(
            //                                    red: .random(in: 0...1),
            //                                    green: .random(in: 0...1),
            //                                    blue: .random(in: 0...1),
            //                                    opacity: 1
            //                                ))
            //                                .cornerRadius(10)
            
            //                            AsyncImage(url: url, transaction: .init(animation: .spring())) { phase in
            //                                switch phase {
            //                                case .empty:
            //                                    randomPlaceholderColor()
            //                                    .opacity(0.2)
            //                                    .transition(.opacity.combined(with: .scale))
            //                                case .success(let image):
            //                                    image
            //                                    .resizable()
            //                                    .aspectRatio(contentMode: .fill)
            //                                    .transition(.opacity.combined(with: .scale))
            //                                case .failure(let error):
            //                                    ErrorView(error)
            //                                @unknown default:
            //                                    ErrorView()
            //                                }
            //                            }
            //                            .frame(width: 120, height: 120)
            //                            .mask(RoundedRectangle(cornerRadius: 16))
        } else {
            // Fallback on earlier iOS versions
        }
    }
}
