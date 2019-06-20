//
//  PostDetailTableViewController.swift
//  KotlinNativeAndroidIOS
//
//  Created by Boris Korec on 19/06/2019.
//  Copyright © 2019 Boris Korec. All rights reserved.
//

import UIKit
import Shared

class PostDetailTableViewController: UITableViewController {
    
    var postId: Int32?
    var data: PostUserComments?
    let api = ApiProvider.provideApi()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 300
        
        guard let postId = self.postId else {
            return
        }
        
        api.getPostUserComments(postId: postId, success: { result -> KotlinUnit in
            self.data = result
            self.tableView.reloadData()
            return KotlinUnit()
        }, failure: { error -> KotlinUnit in
            return KotlinUnit()
        })
    }
    
    // MARK: UITableViewDataSource
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data != nil ? data!.comments.count + 1 : 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "postTitle") as! PostTitleViewCell
            if let data = self.data {
                cell.title.text = data.post.title
                cell.authorName.text = data.user?.name
                cell.authorEmail.text = data.user?.email
                cell.body.text = data.post.body
            }
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "postComment") as! PostCommentViewCell
            if let data = self.data {
                let comment = data.comments[indexPath.row - 1]
                cell.comment.text = comment.body
                cell.author.text = comment.name
            }
            return cell
        }
    }
    
}
