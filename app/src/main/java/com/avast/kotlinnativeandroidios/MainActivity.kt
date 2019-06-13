package com.avast.kotlinnativeandroidios

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.txtSharedText).text = getSharedModuleString()

        val api = PlaceholderApi()

        api.callGetPost(1) {
            Log.d("MainActivity", "title: ${it.title}")
            launch {
                findViewById<TextView>(R.id.txtSharedText).text = it.title
            }
        }
    }
}
