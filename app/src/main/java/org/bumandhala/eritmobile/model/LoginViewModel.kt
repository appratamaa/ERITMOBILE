package org.bumandhala.eritmobile.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.single
import org.bumandhala.eritmobile.database.UserDao

class LoginViewModel(private val dao: UserDao): ViewModel() {
    suspend fun checkIsSignedIn():Boolean{
        val users: List<User> = dao.getAllUser().single()
        if (users[0].signedIn) return true
        return false
    }
    suspend fun login(email: String, password: String): Boolean{
        val user = dao.getUserByEmail(email)
        if (user != null && user.password == password) return true
        return false
    }
}