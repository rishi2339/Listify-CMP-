package com.cmp.todo.presentation.settings

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSBundle

actual class Setting {
    @OptIn(ExperimentalSettingsImplementation::class)
    actual fun createSettings(): Settings {
        return KeychainSettings("${NSBundle.mainBundle.bundleIdentifier}.AUTH")
    }
}