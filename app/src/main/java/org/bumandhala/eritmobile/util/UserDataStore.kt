package org.bumandhala.eritmobile.util


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bumandhala.eritmobile.model.User


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class UserDataStore(private val context: Context) {
    companion object {
        private val USER_NAME = stringPreferencesKey("name")
        private val USER_USERNAME = stringPreferencesKey("userName")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_SIGNED_IN = booleanPreferencesKey("signedIn")
    }

    val userFlow: Flow<User> = context.userDataStore.data.map { preferences ->
        User(
            name = preferences[USER_NAME] ?: "",
            userName = preferences[USER_USERNAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            signedIn = preferences[USER_SIGNED_IN] ?: false,
            password = "",
            confirmation = "",
        )
    }

    suspend fun saveData(user: User) {
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_USERNAME] = user.userName
            preferences[USER_EMAIL] = user.email
            preferences[USER_SIGNED_IN] = user.signedIn
        }
    }
}