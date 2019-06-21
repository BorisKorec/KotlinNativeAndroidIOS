package com.avast.kotlinnativeandroidios

import com.avast.kotlinnativeandroidios.model.Comment
import com.avast.kotlinnativeandroidios.model.Post
import com.avast.kotlinnativeandroidios.model.User
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class PlaceholderApi {

    val client = HttpClient()
    val userMap: HashMap<Int, User> = HashMap()

    fun getPost(id: Int, success: (Post) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val json = client.get<String>("$BASE_URL/posts/$id")
                val post = Json.nonstrict.parse(Post.serializer(), json)
                success(post)
            } catch (e: Exception) {
                failure(e)
            }
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

    fun getCommentsForPost(id: Int, success: (List<Comment>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val json = client.get<String>("$BASE_URL/comments?postId=$id")
                val comments = Json.nonstrict.parse(Comment.serializer().list, json)
                success(comments)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    fun getUserById(userId: Int, success: (User?) -> Unit, failure: (Throwable?) -> Unit) {
        if (userMap.isEmpty()) {
            getUsers(success = {
                for (user in it) {
                    userMap.set(user.id, user)
                }
                success(userMap.get(userId))
            }, failure = {
                failure(it)
            })
        } else {
            success(userMap.get(userId))
        }
    }

    fun getUsers(success: (List<User>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val json = client.get<String>("$BASE_URL/users")
                val users = Json.nonstrict.parse(User.serializer().list, json)
                success(users)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    companion object Constants {
        val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}