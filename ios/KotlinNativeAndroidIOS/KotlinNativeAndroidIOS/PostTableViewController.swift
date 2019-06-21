//
//  PostTableViewController.swift
//  KotlinNativeAndroidIOS
//
//  Created by Boris Korec on 19/06/2019.
//  Copyright © 2019 Boris Korec. All rights reserved.
//

import UIKit
import Shared

class PostsTableViewController: UITableViewController {
    
    var posts: [Post]?

    var presenter: PostsPresenter?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter = PostsPresenter.init(api: ApiProvider.provideApi(), view: self)
        presenter?.onStart()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let controller = segue.destination as? PostDetailTableViewController {
            if let postId = sender as? Int32 {
                controller.postId = postId
            }
        }
    }
    
    // MARK: UITableViewDataSource
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return posts != nil ? posts!.count : 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "postViewCell") as! PostViewCell
        if let post = posts?[indexPath.row] {
            cell.label.text = post.title
            cell.postId = post.id
        }
        return cell
    }
    
    // MARK: UITableViewDelegate
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let post = posts?[indexPath.row] {
            presenter?.onPostClick(postId: post.id)
        }
    }
}

extension PostsTableViewController: PostsView {
    func showPosts(posts: [Post]) {
        self.posts = posts
        self.tableView.reloadData()
    }
    
    func showError(e: KotlinThrowable?) {
        // TODO not implemented
    }
    
    func showDetail(postId: Int32) {
        performSegue(withIdentifier: "showPostDetail", sender: postId)
    }
}
