package com.avast.kotlinnativeandroidios.modules.postDetail

import com.avast.kotlinnativeandroidios.PlaceholderApi

class PostDetailPresenter(private val api: PlaceholderApi, private val view: PostDetailView) {

    fun onStart(postId: Int) {
        if (postId >= 0) {
            loadPost(postId)
            loadCommentsForPost(postId)
        } else {
            view.showError(null)
        }
    }

    fun loadPost(postId: Int) {
        api.getPost(postId, {
            view.showPost(it)
            loadUserForPost(it.userId)
        }, {
            view.showError(it)
        })
    }

    fun loadUserForPost(userId: Int) {
        api.getUserById(userId, {
            if (it != null) {
                view.showUserForPost(it)
            }
        }, {
            view.showError(it)
        })
    }

    fun loadCommentsForPost(postId: Int) {
        api.getCommentsForPost(postId, {
            view.showComments(it)
        }, {
            view.showError(it)
        })
    }
}