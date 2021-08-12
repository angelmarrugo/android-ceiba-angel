package co.com.ceiba.mobile.pruebadeingreso.viewmodel

import androidx.lifecycle.*
import co.com.ceiba.mobile.pruebadeingreso.model.User
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val users: LiveData<List<User>> = userRepository.getUsers()

    val usersFromDb: LiveData<List<User>> = userRepository.getUsersFromDb()

    fun callUsers(){
        userRepository.callUsers()
    }

    fun saveUsers(users: List<User>) = viewModelScope.launch {
        userRepository.insertUsers(users)
    }

}
