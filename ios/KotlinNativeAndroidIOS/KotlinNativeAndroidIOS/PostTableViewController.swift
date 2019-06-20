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
    let api = ApiProvider.provideApi()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        api.getPosts(success: { posts -> KotlinUnit in
            self.posts = posts
            self.tableView.reloadData()
            return KotlinUnit()
        }) { throwable -> KotlinUnit in
            return KotlinUnit()
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let controller = segue.destination as? PostDetailTableViewController {
            if let cell = sender as? PostViewCell {
                controller.postId = cell.postId
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
        // TODO
    }
}
