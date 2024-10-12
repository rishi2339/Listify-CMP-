package com.cmp.todo

import com.cmp.todo.database.DriverFactory
import com.cmp.todo.presentation.settings.Setting
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single { DriverFactory() }
    single<Settings> { Setting().createSettings() }
}