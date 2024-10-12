package com.cmp.todo.presentation.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual class Setting {
    actual fun createSettings(): Settings {
        val settingsRepository: SettingsRepository by lazy { SettingsRepository(StorageSettings()) }
        return settingsRepository.settings
    }
}