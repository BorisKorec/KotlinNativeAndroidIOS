//
//  ApiProvider.swift
//  KotlinNativeAndroidIOS
//
//  Created by Boris Korec on 20/06/2019.
//  Copyright © 2019 Boris Korec. All rights reserved.
//

import Foundation
import Shared

class ApiProvider {
    static var api: PlaceholderApi?
    
    static func provideApi() -> PlaceholderApi {
        if api == nil {
            api = PlaceholderApi()
        }
        return api!
    }
}
