package com.avast.kotlinnativeandroidios

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avast.kotlinnativeandroidios.model.Comment
import com.avast.kotlinnativeandroidios.model.Post
import com.avast.kotlinnativeandroidios.model.PostUserComments
import com.avast.kotlinnativeandroidios.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.*
import kotlin.coroutines.CoroutineContext

class PostDetailActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var recyclerView: RecyclerView

    private val adapter = PostDetailAdapter()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    companion object Constructor {
        val EXTEA_POST_ID = "EXTEA_POST_ID"

        fun prepareIntent(context: Context, postId: Int): Intent {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(EXTEA_POST_ID, postId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val postId = intent.getIntExtra(EXTEA_POST_ID, -1)
        if (postId >= 0) {
            val api = PlaceholderApi()
            api.getPostUserComments(postId, {
                launch {
                    adapter.data = it
                    adapter.notifyDataSetChanged()
                }
            }, {})
        }
    }



    class PostDetailAdapter(): RecyclerView.Adapter<PostDetailAdapter.PostDetailViewHolder>() {
        public var data: PostUserComments? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
            if (viewType == 0) {
                return PostDetailTitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_title, parent, false))
            } else if (viewType == 1) {
                return PostDetailBodyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_body, parent, false))
            } else {
                return PostDetailCommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_comment, parent, false))
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position < 2) {
                return position
            } else {
                return 2
            }
        }

        override fun getItemCount(): Int {
            if (data != null) {
                return data!!.comments.size + 2
            } else {
                return 0
            }
        }

        override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
            if (position == 0) {
                (holder as PostDetailTitleViewHolder).bindData(data!!.post.title, data!!.user?.name, data!!.user?.email)
            } else if (position == 1) {
                (holder as PostDetailBodyViewHolder).bindBody(data!!.post.body)
            } else {

                (holder as PostDetailCommentViewHolder).bindComment(data!!.comments[position - 2].body, data!!.comments[position - 2].name)
            }
        }

        abstract class PostDetailViewHolder(view: View): RecyclerView.ViewHolder(view)

        class PostDetailTitleViewHolder(view: View): PostDetailViewHolder(view) {
            private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
            private val txtAuthorName: TextView = view.findViewById(R.id.txtAuthorName)
            private val txtAuthorEmail: TextView = view.findViewById(R.id.txtAuthorEmail)
            fun bindData(title: String?, name: String?, email: String?) {
                txtTitle.text = title
                txtAuthorName.text = name
                txtAuthorEmail.text = email
            }
        }

        class PostDetailBodyViewHolder(view: View): PostDetailViewHolder(view) {
            private val txtBody: TextView = view.findViewById(R.id.txtBody)
            fun bindBody(body: String?) {
                txtBody.text = body
            }
        }

        class PostDetailCommentViewHolder(view: View): PostDetailViewHolder(view) {
            private val txtComment: TextView = view.findViewById(R.id.txtComment)
            private val txtAuthorName: TextView = view.findViewById(R.id.txtAuthorName)
            fun bindComment(comment: String, author: String) {
                txtComment.text = comment
                txtAuthorName.text = author
            }
        }

    }
}
