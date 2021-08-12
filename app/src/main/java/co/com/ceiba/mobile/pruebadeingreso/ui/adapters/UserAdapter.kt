package co.com.ceiba.mobile.pruebadeingreso.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.model.User

class UserAdapter(private val userListener: UserListener)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>(),
    Filterable
{
    var usersList = ArrayList<User>()
    var usersListFull = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.user_list_item,
            parent,
            false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]

        holder.tvUserName.text = user.name
        holder.tvUserPhone.text = user.phone
        holder.tvUserEmail.text = user.email

        //Listeners
        holder.btnUserViewPost.setOnClickListener {
            userListener.onViewPosts(user, position)
        }
    }

    override fun getItemCount(): Int = usersList.size

    fun updateData(data: List<User>) {
        usersList.clear()
        usersList.addAll(data)
        usersListFull = ArrayList(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val tvUserName: TextView = itemview.findViewById(R.id.name)
        val tvUserPhone: TextView = itemview.findViewById(R.id.phone)
        val tvUserEmail: TextView = itemview.findViewById(R.id.email)
        val btnUserViewPost: Button = itemview.findViewById(R.id.btn_view_post)
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<User>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(usersListFull)
            }else {
                usersListFull.forEach { user ->
                    if (user.name?.lowercase()?.startsWith(constraint.toString().lowercase())!!) {
                        filteredList.add(user)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            usersList.clear()
            usersList.addAll(results?.values as Collection<User>)
            notifyDataSetChanged()
            userListener.isEmpty(usersList.isEmpty())
        }
    }
}