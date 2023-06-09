package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.local.SettingPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SettingViewModel(private val preferences: SettingPreferences) : ViewModel() {
    fun getUserPreferences(property: String): LiveData<String> {
        return when (property) {
            SettingPreferences.Companion.UserPreferences.UserID.name -> preferences.getUserId()
                .asLiveData()
            SettingPreferences.Companion.UserPreferences.UserToken.name -> preferences.getUserToken()
                .asLiveData()
            SettingPreferences.Companion.UserPreferences.UserName.name -> preferences.getUserName()
                .asLiveData()
            SettingPreferences.Companion.UserPreferences.UserEmail.name -> preferences.getUserEmail()
                .asLiveData()
            else -> preferences.getUserId().asLiveData()
        }
    }

    fun setUserPreferences(
        userToken: String,
        userId: String,
        userName: String,
        userEmail: String
    ) {
        viewModelScope.launch {
            preferences.saveLogin(userToken, userId, userName, userEmail)
        }
    }

    fun clearUserPreferences() {
        viewModelScope.launch {
            preferences.clearLogin()
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
        }
    }
}