package com.avast.kotlinnativeandroidios.modules.postDetail

import com.avast.kotlinnativeandroidios.model.Comment
import com.avast.kotlinnativeandroidios.model.Post
import com.avast.kotlinnativeandroidios.model.User

interface PostDetailView {
    fun showPost(post: Post)
    fun showUserForPost(user: User)
    fun showComments(comments: List<Comment>)
    fun showError(e: Throwable?)
}