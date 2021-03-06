package co.com.ceiba.mobile.pruebadeingreso.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.model.User
import co.com.ceiba.mobile.pruebadeingreso.ui.adapters.UserAdapter
import co.com.ceiba.mobile.pruebadeingreso.ui.adapters.UserListener
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_post.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserListener {

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var dialogProgress: ProgressDialog
    private val userAdapter: UserAdapter = UserAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        observeDb()
    }

    private fun initViews() {
        configureRecycler()
        showProgressDialog()
        initSearch()
    }

    private fun configureRecycler() {
        recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun initSearch() {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                userAdapter.filter.filter(s.toString())
            }

        })
    }

    private fun observeDb(){
        userViewModel.usersFromDb.observe(this,  { usersDb ->
            if (usersDb.isEmpty()) observeModel()
            else {
                dialogProgress.hide()
                userAdapter.updateData(usersDb)
            }
        })
    }

    private fun observeModel() {
        userViewModel.callUsers()

        userViewModel.users.observe(this,  { users ->
            dialogProgress.hide()
            userAdapter.updateData(users)
            userViewModel.saveUsers(users)
        })
    }

    private fun showProgressDialog() {
        dialogProgress = ProgressDialog(this)
        dialogProgress.setMessage(getString(R.string.generic_message_progress))
        dialogProgress.show()
    }

    override fun onViewPosts(user: User, position: Int) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("name", user.name)
            putExtra("phone", user.phone)
            putExtra("email", user.email)
            putExtra("id", user.id)
        }
        startActivity(intent)
    }

    override fun isEmpty(sw: Boolean) {
        if (sw)
            editTextSearch.error  = "List is empty"
        else
            editTextSearch.error = null
    }

    override fun onDestroy() {
        dialogProgress.dismiss()
        super.onDestroy()
    }

}