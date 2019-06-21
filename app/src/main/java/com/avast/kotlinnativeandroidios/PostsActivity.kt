package com.avast.kotlinnativeandroidios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avast.kotlinnativeandroidios.model.Post
import com.avast.kotlinnativeandroidios.modules.posts.PostsPresenter
import com.avast.kotlinnativeandroidios.modules.posts.PostsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class PostsActivity : AppCompatActivity(), CoroutineScope, PostsView {

    override fun showPosts(posts: List<Post>) {
        launch {
            adapter.posts = posts
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(e: Throwable?) {
        TODO("not implemented")
    }

    override fun showDetail(postId: Int) {
        startActivity(PostDetailActivity.prepareIntent(this@PostsActivity, postId))
    }

    private lateinit var presenter: PostsPresenter

    private lateinit var recyclerView: RecyclerView

    private var adapter: PostsAdapter = PostsAdapter {
        presenter.onPostClick(it.id)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        presenter = PostsPresenter((application as KotlinNativeApp).api, this)
        presenter.onStart()
    }

    class PostsAdapter(private val onItemClick: (Post) -> Unit): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
        public var posts: List<Post> = Collections.emptyList()

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
                    adapter.onItemClick(adapter.posts[this@PostViewHolder.adapterPosition])
                }
            }

            fun bindPost(post: Post) {
                txtTitle.text = post.title
            }
        }

    }
}
