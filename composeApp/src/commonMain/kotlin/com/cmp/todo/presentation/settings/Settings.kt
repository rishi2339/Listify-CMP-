package com.cmp.todo.presentation.settings

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow

expect class Setting {
    fun createSettings(): Settings
}

class SettingsRepository(val settings: Settings) {
    private val _token = IntSettingConfig(settings, "user_token", "")
    val token: Flow<String> = _token.value
    fun saveToken(token: String) {
        _token.set(token)
    }

    fun getToken(): String {
        return _token.get()
    }

    fun removeToken() {
        _token.remove()
    }
}
