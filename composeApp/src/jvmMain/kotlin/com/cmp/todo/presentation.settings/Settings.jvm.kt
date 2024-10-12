package com.cmp.todo.presentation.settings

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class Setting {
    actual fun createSettings(): Settings {
        val settingRepo: SettingsRepository by lazy {
            val preferences = Preferences.userRoot()
            val settings = PreferencesSettings(preferences)
            SettingsRepository(settings)
        }
        return settingRepo.settings
    }
}
