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
    
    var postId: Int32 = -1
    
    var post: Post?
    var user: User?
    var comments: [Comment]?
    
    var presenter: PostDetailPresenter?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 300
    
        presenter = PostDetailPresenter.init(api: ApiProvider.provideApi(), view: self)
        presenter?.onStart(postId: postId)
    }
    
    // MARK: UITableViewDataSource
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var count = 0
        if user != nil {
            count += 1
            if comments != nil {
                count += comments!.count
            }
        }
        return count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "postTitle") as! PostTitleViewCell
            if let post = self.post {
                cell.title.text = post.title
                cell.body.text = post.body
            }
            if let user = self.user {
                cell.authorName.text = user.name
                cell.authorEmail.text = user.email
            }
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "postComment") as! PostCommentViewCell
            if let comments = self.comments {
                let comment = comments[indexPath.row - 1]
                cell.comment.text = comment.body
                cell.author.text = comment.name
            }
            return cell
        }
    }
    
}

extension PostDetailTableViewController: PostDetailView {
    func showPost(post: Post) {
        self.post = post
        self.tableView.reloadData()
    }
    func showUserForPost(user: User) {
        self.user = user
        self.tableView.reloadData()
    }
    func showComments(comments: [Comment]) {
        self.comments = comments
        self.tableView.reloadData()
    }
    func showError(e: KotlinThrowable?) {
        // TODO not implemented
    }
}
