package com.avast.kotlinnativeandroidios.modules.posts

import com.avast.kotlinnativeandroidios.model.Post

interface PostsView {
    fun showPosts(posts: List<Post>)
    fun showError(e: Throwable?)
    fun showDetail(postId: Int)
}