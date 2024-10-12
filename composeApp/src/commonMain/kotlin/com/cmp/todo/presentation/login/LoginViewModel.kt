package com.cmp.todo.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import com.cmp.todo.domain.login.usecase.LoginUseCase
import com.cmp.todo.presentation.mvi.BaseViewModel
import com.cmp.todo.presentation.settings.SettingsRepository
import com.cmp.todo.util.BaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<LoginActivity.Event, LoginActivity.State, LoginActivity.Effect>() {

    override fun createInitialState(): LoginActivity.State =
        LoginActivity.State(loginState = LoginActivityState.Init)

    override fun handleEvent(event: LoginActivity.Event) {
        when (event) {
            LoginActivity.Event.OnLoginClick -> setEffect {
                LoginActivity.Effect.NavigateToHome
            }

            LoginActivity.Event.OnRegisterClick -> setEffect {
                LoginActivity.Effect.NavigateToRegister
            }
        }
    }

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    @Composable
    fun login(loginRequest: LoginRequest, onSuccessLogin: (token: String) -> Unit) {
        val viewModelCoroutineScope = rememberCoroutineScope()
        viewModelCoroutineScope.launch {
            loginUseCase.execute(loginRequest)
                .onStart {
                    setState { copy(loginState = LoginActivityState.IsLoading(true)) }
                }
                .catch {
                    setState { copy(loginState = LoginActivityState.IsLoading(false)) }
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Error -> {
                            setState {
                                copy(
                                    loginState = LoginActivityState.ErrorLogin(
                                        baseResult.rawResponse
                                    )
                                )
                            }
                        }

                        is BaseResult.Success -> {
                            setState {
                                copy(
                                    loginState = LoginActivityState.SuccessLogin(
                                        baseResult.data
                                    )
                                )
                            }
                            onSuccessLogin(baseResult.data.token)
                        }
                    }
                }
        }

    }

    fun saveToken(token: String) = settingsRepository.saveToken(token)
    fun getToken(): String {
        return settingsRepository.getToken()
    }

    fun signIn(loginRequest: LoginRequest): Boolean {
        return validate(loginRequest)
    }

    private fun validate(loginRequest: LoginRequest): Boolean {
        val isEmail =
            Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matches(
                loginRequest.email
            )
        if (!isEmail) {
            setError("Email is not valid")
            return false
        }
        if (loginRequest.password.length < 8) {
            setError("Password is not valid")
            return false
        }
        return true
    }

    private fun setError(errorMsg: String?) {
        _error.value = errorMsg!!
    }

}
