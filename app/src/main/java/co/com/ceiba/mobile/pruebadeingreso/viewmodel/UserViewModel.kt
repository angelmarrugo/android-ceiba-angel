package co.com.ceiba.mobile.pruebadeingreso.viewmodel

import androidx.lifecycle.*
import co.com.ceiba.mobile.pruebadeingreso.model.User
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    val users: LiveData<List<User>> = userRepository.getUsers()

    val usersFromDb: LiveData<List<User>> = userRepository.getUsersFromDb()

    fun callUsers(){
        userRepository.callUsers()
    }

    fun saveUsers(users: List<User>) = viewModelScope.launch {
        userRepository.insertUsers(users)
    }

}

class UserViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}