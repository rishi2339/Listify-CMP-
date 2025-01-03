package com.cmp.todo.presentation.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getStringFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class IntSettingConfig(settings: Settings, key: String, defaultValue: String) :
    SettingConfig<Int>(settings, key, defaultValue) {

    @OptIn(ExperimentalSettingsApi::class)
    override val value: Flow<String>
        get() {
            val observableSettings = settings as? ObservableSettings ?: return emptyFlow()
            return observableSettings.getStringFlow(key, defaultValue)
        }

    override fun getStringValue(settings: Settings, key: String, defaultValue: String): String =
        settings.getString(key, defaultValue)

    override fun setStringValue(settings: Settings, key: String, value: String) =
        settings.putString(key, value)
}