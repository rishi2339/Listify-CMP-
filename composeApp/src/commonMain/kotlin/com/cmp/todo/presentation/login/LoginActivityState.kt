package com.cmp.todo.presentation.login

import com.cmp.todo.data_remote.login.remote.dto.LoginResponseDto
import com.cmp.todo.domain.login.entity.LoginEntity
import com.cmp.todo.presentation.mvi.UiEffect
import com.cmp.todo.presentation.mvi.UiEvent
import com.cmp.todo.presentation.mvi.UiState

interface LoginActivity {
    sealed interface Event : UiEvent {
        object OnRegisterClick : Event
        object OnLoginClick : Event
    }

    data class State(
        val loginState: LoginActivityState
    ) : UiState
    sealed interface Effect : UiEffect {
        object NavigateToRegister : Effect
        object NavigateToHome : Effect
    }
}
sealed class LoginActivityState {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginActivityState()
    data class ErrorLogin(val rawResponse: LoginResponseDto) : LoginActivityState()
}
