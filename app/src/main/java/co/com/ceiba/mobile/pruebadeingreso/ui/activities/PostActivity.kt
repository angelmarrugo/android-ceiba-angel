package co.com.ceiba.mobile.pruebadeingreso.ui.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat.getExtras
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.App
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.ui.adapters.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.PostViewModel
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.PostViewModelFactory
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    private var userId: Int? = null

    private val postViewModel: PostViewModel by viewModels {
        PostViewModelFactory((application as App).postRepository)
    }

    private lateinit var dialogProgress: ProgressDialog
    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        getExtrasUser()
        initViews()
        observeDb()
    }

    private fun getExtrasUser() {
        val extras = intent.extras
        userId = extras?.getInt("id")

        //update UI
        name.text = extras?.getString("name")
        phone.text = extras?.getString("phone")
        email.text = extras?.getString("email")
    }

    private fun initViews() {
        initRecycler()
        showProgressDialog()
    }

    private fun observeDb() {
        userId?.let {
            postViewModel.getPostsFromDb(it).observe(this, { postsDb ->
                if (postsDb.isEmpty()) observeModel()
                else {
                    dialogProgress.hide()
                    postAdapter.updateData(postsDb)
                }
            })
        }

    }

    private fun observeModel() {
        postViewModel.callPosts(userId!!)

        postViewModel.getPosts().observe(this, { posts ->
            dialogProgress.hide()
            postAdapter.updateData(posts)
            postViewModel.savePosts(posts)
        })
    }

    private fun initRecycler() {
        recyclerViewPostsResults.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = postAdapter
        }
    }

    private fun showProgressDialog() {
        dialogProgress = ProgressDialog(this)
        dialogProgress.setMessage(getString(R.string.generic_message_progress))
        dialogProgress.show()
    }

    override fun onDestroy() {
        dialogProgress.dismiss()
        super.onDestroy()
    }
}