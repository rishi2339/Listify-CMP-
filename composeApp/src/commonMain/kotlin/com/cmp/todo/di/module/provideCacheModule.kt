package com.cmp.todo.di.module

import com.cmp.todo.database.DBHelper
import com.cmp.todo.database.repository.ToDoListLocalRepositoryImp
import com.cmp.todo.domain.todo.repository.ToDoListLocalRepository
import org.koin.dsl.module

val provideCacheModule = module {
    single { DBHelper(get()) }
    single<ToDoListLocalRepository> { ToDoListLocalRepositoryImp(get()) }
}