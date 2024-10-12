package com.cmp.todo.di

import com.cmp.todo.di.module.provideCacheModule
import com.cmp.todo.di.module.provideRepositoryModule
import com.cmp.todo.di.module.provideUseCaseModule
import com.cmp.todo.di.module.provideViewModelModule
import com.cmp.todo.di.module.providehttpClientModule
import com.cmp.todo.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin() = initKoin {}
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule())
    }

fun appModule() = listOf(
    platformModule(),
    provideCacheModule,
    providehttpClientModule,
    provideRepositoryModule,
    provideUseCaseModule,
    provideViewModelModule,
)