package com.avast.kotlinnativeandroidios

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avast.kotlinnativeandroidios.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class PostsActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var recyclerView: RecyclerView
    private var adapter: PostsAdapter = PostsAdapter {
        Log.d("PostActivity", "position = $it")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val api = PlaceholderApi()
        api.getPosts(
            success = {
                launch {
                    adapter.posts = it
                    adapter.notifyDataSetChanged()
                }
            },
            failure = {
            }
        )
    }

    class PostsAdapter(private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
        var posts: List<Post> = Collections.emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            return PostViewHolder(this, LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        }

        override fun getItemCount(): Int {
            return this.posts.size
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bindPost(posts[position])
        }

        class PostViewHolder(private val adapter: PostsAdapter, view: View): RecyclerView.ViewHolder(view) {
            private val txtTitle: TextView = view.findViewById(R.id.txtTitle)

            init {
                view.setOnClickListener {
                    adapter.onItemClick(this@PostViewHolder.adapterPosition)
                }
            }

            fun bindPost(post: Post) {
                txtTitle.text = post.title
            }
        }

    }
}
