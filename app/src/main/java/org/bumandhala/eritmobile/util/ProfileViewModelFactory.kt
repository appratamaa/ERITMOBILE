package org.bumandhala.eritmobile.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.eritmobile.database.UserDao
import org.bumandhala.eritmobile.model.ProfileViewModel

class ProfileViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}