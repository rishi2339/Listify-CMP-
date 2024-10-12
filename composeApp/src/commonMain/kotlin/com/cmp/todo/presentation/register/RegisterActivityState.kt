package com.cmp.todo.presentation.register

import com.cmp.todo.data_remote.register.remote.dto.RegisterResponseDto
import com.cmp.todo.domain.register.entity.RegisterEntity
import com.cmp.todo.presentation.mvi.UiEffect
import com.cmp.todo.presentation.mvi.UiEvent
import com.cmp.todo.presentation.mvi.UiState

interface RegisterActivity {
    sealed interface Event : UiEvent {
        object OnRegisterClick : Event
        object OnLoginClick : Event
    }

    data class State(
        val registerState: RegisterActivityState
    ) : UiState
    sealed interface Effect : UiEffect {
        object NavigateToHome : Effect
        object NavigateToLogin : Effect
    }
}
sealed class RegisterActivityState {
    object Init : RegisterActivityState()
    data class IsLoading(val isLoading: Boolean) : RegisterActivityState()
    data class SuccessRegister(val registerEntity: RegisterEntity) : RegisterActivityState()
    data class ErrorRegister(val rawResponse: RegisterResponseDto) : RegisterActivityState()
}
