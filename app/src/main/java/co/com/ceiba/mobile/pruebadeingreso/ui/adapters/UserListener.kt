package co.com.ceiba.mobile.pruebadeingreso.ui.adapters

import co.com.ceiba.mobile.pruebadeingreso.model.User

interface UserListener {
    fun onViewPosts(user: User, position: Int)
    fun isEmpty(sw: Boolean)
}