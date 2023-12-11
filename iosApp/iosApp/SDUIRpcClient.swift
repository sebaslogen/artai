//
//  SDUIRpcClient.swift
//  iosApp
//
//  Created by Sebastian Lobato Genco on 30/11/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

 import Foundation
// import NIOHPACK
// import Logging
 import shared
 import Connect
 import ConnectNIO
// import NIO
 import SwiftProtobuf

 class SDUIRpcClient: SDUIRpcCallbackClient {
     private var screenClient: Screen_V1_ScreenServiceClient?

     init() {
         
         let host = "https://connect-poc-server-qpkwvfricq-ez.a.run.app"
         // ProtocolClient is usually stored and passed to generated clients.
         let protocolClient = ProtocolClient(
             httpClient: NIOHTTPClient(host: host),
             config: ProtocolClientConfig(
                 host: host, // Base URL for APIs
                 networkProtocol: .connect, // Or .grpcWeb
                 codec: ProtoCodec() // Or JSONCodec()
             )
         )
         screenClient = Screen_V1_ScreenServiceClient(client: protocolClient)
     }

     func sendRequest(kmpRequest: GetScreenRequest, callback: @escaping (GetScreenResponse?, KotlinException?) -> Void) {
         //Check that everything is going according to plan
         guard let client = screenClient else {
             callback(nil, nil)
             return
         }
         
         // Performed within an async context.
         let request = Screen_V1_GetScreenRequest.with { $0.screenID = kmpRequest.screenId }

         //Get a call instance
         Task.detached {
             //In the background we wait for the result of the call
             let response = await client.getScreen(request: request, headers: [:])
             switch response.result {
             case .success(let success):
                 DispatchQueue.main.async {
                     //Convert SwiftProtobuf.Message to KmpMessage (the Kotlin function can parse a specific KMP Message class from a binary format)
                     let (kmpMessage, mappingError) = success.toKmpMessage()
                     //Be sure to call the callback on the same thread on which the KmpMessage was actually created, otherwise we will get an error in KotlinNative runtime
                     callback(kmpMessage, mappingError)
                 }
             case .failure(let failure):
                 DispatchQueue.main.async {
                     callback(nil, KotlinException(message: failure.localizedDescription))
                 }
             }
         }
     }
 }

fileprivate extension SwiftProtobuf.Message {
    // Take the view SwiftMessage in the form of NSData, translates into KotlinByteArray and gives it as input to the adapter
    func toKmpMessage() -> (GetScreenResponse?, KotlinException?) {
        do {
            let data = try self.serializedData()
            let result = ExperimentKt.decodeGRPCResponse(rawResponse: data.toKotlinByteArray())

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
