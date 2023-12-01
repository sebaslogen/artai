//
//  SDUIRpcClient.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 30/11/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

 import Foundation
 import GRPC
 import NIOHPACK
 import Logging
 import shared
 import NIO
 import SwiftProtobuf

 class SDUIRpcClient: SDUIRpcCallbackClient {
     private var commonChannel: GRPCChannel?

     private var screenClient: Screen_V1_ScreenServiceClient?

     init() {

         //I'm setting up the logger
         var logger = Logger(label: "gRPC", factory: StreamLogHandler.standardOutput(label:))
         logger.logLevel = .debug

         //loopCount - how many independent loops within the group work within the channel (can simultaneously send/receive messages)
         let eventGroup = PlatformSupport.makeEventLoopGroup(loopCount: 4)

         //Create a channel, specify the security type, host and port
         let newChannel = ClientConnection
             //You can use .usingTLS instead of .insecure, but you won’t be able to connect to our test server this way, it doesn’t have a certificate
 //            .usingTLS(group: eventGroup)
//             .insecure(group: eventGroup)
             .secure(group: eventGroup)
             .withTLS(certificateVerification: .noHostnameVerification)
             .withBackgroundActivityLogger(logger)   //Logging the events of the channel itself
             .connect(host: "connect-poc-server-qpkwvfricq-ez.a.run.app", port: 443)

         //We work without additional headers, logging requests
         let callOptions = CallOptions(
             customMetadata: HPACKHeaders([]),
             logger: logger
         )

         //Create and save a client instance
         screenClient = Screen_V1_ScreenServiceClient(
             channel: newChannel,
             defaultCallOptions: callOptions,
             interceptors: nil
         )
         //Save the channel
         commonChannel = newChannel
     }

     func sendRequest(kmpRequest: GetScreenRequest, callback: @escaping (GetScreenResponse?, KotlinException?) -> Void) {
         //Check that everything is going according to plan
         guard let client = screenClient else {
             callback(nil, nil)
             return
         }

         //Create SwiftProtobuf.Message from WireMessage
         var request = Screen_V1_GetScreenRequest()
          request.screenID = kmpRequest.screen_id

         //Get a call instance
         let responseCall = client.getScreen(request)
         DispatchQueue.global().async {
             do {
                 //In the background we wait for the result of the call
                 let swiftMessage = try responseCall.response.wait()
                 DispatchQueue.main.async {
                     //Convert SwiftProtobuf.Message to WireMessage (the ADAPTER object can parse a specific WireMessage class from a binary format)
                     let (wireMessage, mappingError) = swiftMessage.toWireMessage(adapter: GetScreenResponse.companion.ADAPTER)
                     //Be sure to call the callback on the same thread on which the wireMessage was actually created, otherwise we will get an error in KotlinNative runtime
                     callback(wireMessage, mappingError)
                 }
             } catch let err {
                 DispatchQueue.main.async {
                     callback(nil, KotlinException(message: err.localizedDescription))
                 }
             }
         }
     }
 }

 fileprivate extension SwiftProtobuf.Message {
     // Take the view SwiftMessage in the form of NSData, translates into KotlinByteArray and gives it as input to the adapter
     func toWireMessage<WireMessage, Adapter: Wire_runtimeProtoAdapter<WireMessage>>(adapter: Adapter) -> (WireMessage?, KotlinException?) {
         do {
             let data = try self.serializedData()
             let result = adapter.decode(bytes: data.toKotlinByteArray())

             if let nResult = result {
                 return (nResult, nil)
             } else {
                 return (nil, KotlinException(message: "Cannot parse message data"))
             }
         } catch let err {
             return (nil, KotlinException(message: err.localizedDescription))
         }
     }
 }

 fileprivate extension Data {
     //The most primitive way to convert NSData to KotlinByteArray:
     func toKotlinByteArray() -> KotlinByteArray {
         let nsData = NSData(data: self)

         return KotlinByteArray(size: Int32(self.count)) { index -> KotlinByte in
             let byte = nsData.bytes.load(fromByteOffset: Int(truncating: index), as: Int8.self)
             return KotlinByte(value: byte)
         }
     }
 }
