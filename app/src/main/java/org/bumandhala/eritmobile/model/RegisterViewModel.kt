package org.bumandhala.eritmobile.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.UserDao

class RegisterViewModel(private val dao: UserDao): ViewModel() {
    suspend fun register(name: String, userName:String, email: String, password: String, confirmation: String): Boolean {
        val user = dao.getUserByUserName(userName)
        if (user == null){
            val user = User(
                name = name,
                userName = userName,
                email = email,
                password = password,
                confirmation = confirmation,
                signedIn = false
            )
            viewModelScope.launch(Dispatchers.IO) {
                dao.insert(user)
            }
            return true
        }
        return false
    }
    suspend fun resetPassword(email: String, newPassword: String): Boolean {
        val user = dao.getUserByEmail(email)
        return if (user != null) {
            user.password = newPassword
            dao.update(user)
            true
        } else {
            false
        }
    }
}