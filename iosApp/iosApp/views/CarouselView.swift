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
        let s = KMPSection.CarouselCarouselStyle.squared
        if s == section.style {
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
                    switch item {
                    case is CarouselItem.SmallArt:
                        if #available(iOS 15.0, *) {
                            let url = URL(string: (item as! CarouselItem.SmallArt).image.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")
                            
                            if section.style == KMPSection.CarouselCarouselStyle.squared {
                                AsyncImage(url: url) { image in
                                    image.resizable()
                                } placeholder: {
                                    ProgressView()
                                }
                                .frame(width: 120, height: 120)
                                .clipShape(Rectangle())
                            } else if section.style == KMPSection.CarouselCarouselStyle.circle {
                                AsyncImage(url: url) { image in
                                    image.resizable()
                                } placeholder: {
                                    ProgressView()
                                }
                                .frame(width: 120, height: 120)
                                .clipShape(Circle())
                            } else if section.style == KMPSection.CarouselCarouselStyle.roundedsquares {
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
                            // Fallback on earlier versions
                        }
                    case is CarouselItem.Unknown:
                        Text("TODO(Unknown)")
                    default:
                        Text("Unknown carousel item type")
                    }
                }
            }
        }
    }
}
