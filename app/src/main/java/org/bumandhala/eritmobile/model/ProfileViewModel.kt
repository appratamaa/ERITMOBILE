package org.bumandhala.eritmobile.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.UserDao

class ProfileViewModel(private val userDao: UserDao) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            val currentUser = userDao.getUserById(1) // Ubah ID sesuai dengan pengguna saat ini
            _user.value = currentUser
        }
    }
}
