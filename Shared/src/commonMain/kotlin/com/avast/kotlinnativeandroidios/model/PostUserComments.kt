package com.avast.kotlinnativeandroidios.model

data class PostUserComments(
    val post: Post,
    val user: User? = null,
    val comments: List<Comment> = emptyList()
)