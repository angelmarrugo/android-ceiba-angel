package co.com.ceiba.mobile.pruebadeingreso.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.model.User

class PostAdapter: RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var postList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.post_list_item,
            parent,
            false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]

        holder.tvPostTitle.text = post.title
        holder.tvPostBody.text = post.body
    }

    override fun getItemCount(): Int = postList.size

    fun updateData(data: List<Post>) {
        postList.clear()
        postList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val tvPostTitle: TextView = itemview.findViewById(R.id.title)
        val tvPostBody: TextView = itemview.findViewById(R.id.body)
    }
}