package com.avast.kotlinnativeandroidios.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    val id: String,
    val title: String,
    val body: String
)
