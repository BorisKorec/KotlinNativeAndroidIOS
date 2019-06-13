//
//  ViewController.swift
//  KotlinNativeAndroidIOS
//
//  Created by Boris Korec on 12/06/2019.
//  Copyright © 2019 Boris Korec. All rights reserved.
//

import UIKit
import Shared

class ViewController: UIViewController {

    @IBOutlet var label: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        label.text = CommonKt.getSharedModuleString()
        
        let api = PlaceholderApi()
        api.callGetPost(id: 1) { post -> KotlinUnit in
            print(post.title)
            self.label.text = post.title
            return KotlinUnit()
        }
    }


}

