package com.avast.kotlinnativeandroidios

import com.avast.kotlinnativeandroidios.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import kotlinx.serialization.parseList

class PlaceholderApi {

    val client = HttpClient()

    fun callGetPost(id: Int, success: (Post) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val json = client.get<String>("$BASE_URL/posts/$id")
                val post = Json.nonstrict.parse(Post.serializer(), json)
                success(post)
            } catch (e: Exception) {
                // TODO failure
            }

//            val call = client.call("$BASE_URL/posts/$id") {
//                method = HttpMethod.Get
//            }
//            success(call.response.receive())
        }
    }

    fun getPosts(success: (List<Post>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val json = client.get<String>("$BASE_URL/posts")
                val posts = Json.nonstrict.parse(Post.serializer().list, json)
                success(posts)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    companion object Constants {
        val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}