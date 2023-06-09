package academy.bangkit.sifresh.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val _themeKey = booleanPreferencesKey("theme_setting")
    private val token = stringPreferencesKey(UserPreferences.UserToken.name)
    private val id = stringPreferencesKey(UserPreferences.UserID.name)
    private val name = stringPreferencesKey(UserPreferences.UserName.name)
    private val email = stringPreferencesKey(UserPreferences.UserEmail.name)

    fun getUserToken(): Flow<String> = dataStore.data.map { it[token] ?: "" }
    fun getUserId(): Flow<String> = dataStore.data.map { it[id] ?: "" }
    fun getUserName(): Flow<String> = dataStore.data.map { it[name] ?: "" }
    fun getUserEmail(): Flow<String> = dataStore.data.map { it[email] ?: "" }

    suspend fun saveLogin(userToken: String, userid: String, userName: String, userEmail: String) {
        dataStore.edit { preferences ->
            preferences[token] = userToken
            preferences[id] = userid
            preferences[name] = userName
            preferences[email] = userEmail
        }
    }

    suspend fun clearLogin() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[_themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[_themeKey] = isDarkMode
        }
    }

    companion object {
        enum class UserPreferences {
            UserID, UserName, UserEmail, UserToken
        }

        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}