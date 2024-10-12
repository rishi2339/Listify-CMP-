package com.cmp.todo.di.module

import com.cmp.todo.data_remote.login.remote.api.LoginApi
import com.cmp.todo.data_remote.login.repository.LoginRepositoryImp
import com.cmp.todo.data_remote.register.remote.api.RegisterApi
import com.cmp.todo.data_remote.register.repository.RegisterRepositoryImp
import com.cmp.todo.data_remote.todo.remote.api.ToDoApi
import com.cmp.todo.data_remote.todo.repository.ToDoListRepositoryImpl
import com.cmp.todo.domain.login.repository.LoginRepository
import com.cmp.todo.domain.login.usecase.LoginUseCase
import com.cmp.todo.domain.register.repository.RegisterRepository
import com.cmp.todo.domain.register.usecase.RegisterUseCase
import com.cmp.todo.domain.todo.repository.ToDoListRepository
import com.cmp.todo.domain.todo.usecase.ToDoUseCase
import com.cmp.todo.presentation.login.LoginViewModel
import com.cmp.todo.presentation.register.RegisterViewModel
import com.cmp.todo.presentation.settings.SettingsRepository
import com.cmp.todo.presentation.todo.ToDoViewModel
import org.koin.dsl.module

val provideViewModelModule = module{
    factory { LoginViewModel(get(), get()) }
    factory { RegisterViewModel(get(), get()) }
    factory { ToDoViewModel(get(), get()) }
}
val provideRepositoryModule = module {
    single<LoginRepository> { LoginRepositoryImp(get()) }
    single { LoginApi(get()) }
    single<RegisterRepository> { RegisterRepositoryImp(get()) }
    single { RegisterApi(get()) }
    single<ToDoListRepository> { ToDoListRepositoryImpl(get()) }
    single { ToDoApi(get()) }
    single { SettingsRepository(get()) }
}
val provideUseCaseModule = module{
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { ToDoUseCase(get()) }
}