package com.avast.kotlinnativeandroidios

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.call.receive
import io.ktor.http.HttpMethod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceholderApi {

    val client = HttpClient()

    fun callGetPost(id: Int, success: (String) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            val call = client.call("$BASE_URL/posts/$id") {
                method = HttpMethod.Get
            }
            success(call.response.receive())
        }
    }

    companion object Constants {
        val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}