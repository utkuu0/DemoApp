package com.utkuaksu.demoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context

data class User(
    val displayName: String?,
    val photoUrl: String?
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val appContext = getApplication<Application>().applicationContext

    // Kullanıcıyı set et
    fun setUserFromGoogle(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?) {
        _user.value = account?.let {
            User(
                displayName = it.displayName,
                photoUrl = it.photoUrl?.toString()
            )
        }
    }

    // Arama query’sini sakla
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> get() = _searchQuery
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Logout fonksiyonu
    fun logout() {
        _user.value = null
        val prefs = appContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("auto_login", false).apply()
    }
}
