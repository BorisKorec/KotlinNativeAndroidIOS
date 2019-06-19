package com.avast.kotlinnativeandroidios.model

data class PostUserComments(
    val post: Post,
    val user: User?,
    val comments: List<Comment>
)