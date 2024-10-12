package com.cmp.todo.presentation.todo

import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.presentation.mvi.UiEffect
import com.cmp.todo.presentation.mvi.UiEvent
import com.cmp.todo.presentation.mvi.UiState
import com.cmp.todo.util.ResourceUiState

interface ToDoActivity {
    sealed interface Event : UiEvent {
        data object OnLogoutClick : Event
    }

    data class State(
        val todo: ResourceUiState<List<ToDo>>
    ) : UiState
    sealed interface Effect : UiEffect {
        data object NavigateToLogin : Effect
    }
}

