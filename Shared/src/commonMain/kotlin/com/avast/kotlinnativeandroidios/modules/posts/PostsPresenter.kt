package com.avast.kotlinnativeandroidios.modules.posts

import com.avast.kotlinnativeandroidios.PlaceholderApi

class PostsPresenter(private val api: PlaceholderApi, private val view: PostsView) {

    fun onStart() {
        api.getPosts(
            {
                view.showPosts(it)
            },
            {
                view.showError(it)
            }
        )
    }

    fun onPostClick(postId: Int) {
        view.showDetail(postId)
    }
}