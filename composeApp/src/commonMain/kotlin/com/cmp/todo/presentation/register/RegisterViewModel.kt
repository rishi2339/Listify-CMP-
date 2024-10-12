package com.cmp.todo.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import com.cmp.todo.domain.register.usecase.RegisterUseCase
import com.cmp.todo.presentation.mvi.BaseViewModel
import com.cmp.todo.presentation.settings.SettingsRepository
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<RegisterActivity.Event, RegisterActivity.State, RegisterActivity.Effect>() {

    override fun createInitialState(): RegisterActivity.State =
        RegisterActivity.State(registerState = RegisterActivityState.Init)

    override fun handleEvent(event: RegisterActivity.Event) {
        when (event) {
            RegisterActivity.Event.OnRegisterClick -> setEffect {
                RegisterActivity.Effect.NavigateToHome
            }

            RegisterActivity.Event.OnLoginClick -> setEffect {
                RegisterActivity.Effect.NavigateToLogin
            }
        }
    }

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    @Composable
    fun register(registerRequest: RegisterRequest, onSuccessRegister: (token: String) -> Unit) {
        val viewModelCoroutineScope = rememberCoroutineScope()
        viewModelCoroutineScope.launch {
            registerUseCase.execute(registerRequest)
                .onStart {
                    setState { copy(registerState = RegisterActivityState.IsLoading(true)) }
                }
                .catch { exception ->
                    setState { copy(registerState = RegisterActivityState.IsLoading(false)) }
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Error -> {
                            setState {
                                copy(
                                    registerState = RegisterActivityState.ErrorRegister(
                                        baseResult.rawResponse
                                    )
                                )
                            }
                        }

                        is BaseResult.Success -> {
                            setState {
                                copy(
                                    registerState = RegisterActivityState.SuccessRegister(
                                        baseResult.data
                                    )
                                )
                            }
                            onSuccessRegister(baseResult.data.token)
                        }
                    }
                }
        }

    }

    fun saveToken(token: String) = settingsRepository.saveToken(token)
    fun getToken(): String {
        return settingsRepository.getToken()
    }

    fun signUp(registerRequest: RegisterRequest): Boolean {
        return validate(registerRequest)
    }

    private fun validate(registerRequest: RegisterRequest): Boolean {
        val isEmail =
            Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matches(
                registerRequest.email
            )
        if (!isEmail) {
            setError("Email is not valid")
            return false
        }
        if (registerRequest.password.length < 8) {
            setError("Password is not valid")
            return false
        }
        if (registerRequest.name.isEmpty()) {
            setError("Name can't be empty")
            return false
        }
        return true
    }

    private fun setError(errorMsg: String?) {
        _error.value = errorMsg!!
    }

}
