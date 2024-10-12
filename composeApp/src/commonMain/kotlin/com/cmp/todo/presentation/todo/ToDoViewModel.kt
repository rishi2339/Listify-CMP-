package com.cmp.todo.presentation.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.domain.todo.usecase.ToDoUseCase
import com.cmp.todo.presentation.mvi.BaseViewModel
import com.cmp.todo.presentation.settings.SettingsRepository
import com.cmp.todo.util.BaseResult
import com.cmp.todo.util.ResourceUiState
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val toDoUseCase: ToDoUseCase,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<ToDoActivity.Event, ToDoActivity.State, ToDoActivity.Effect>() {
    override fun createInitialState(): ToDoActivity.State =
        ToDoActivity.State(todo = ResourceUiState.Idle)

    override fun handleEvent(event: ToDoActivity.Event) {
        when (event) {
            ToDoActivity.Event.OnLogoutClick -> setEffect {
                ToDoActivity.Effect.NavigateToLogin
            }
        }
    }

    fun removeToken() {
        settingsRepository.removeToken()
    }

    @Composable
    fun getTodo() {
        val viewModelCoroutineScope = rememberCoroutineScope()
        viewModelCoroutineScope.launch {
            toDoUseCase.getToDoExecute()
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Error -> {
                            setState {
                                copy(
                                    ResourceUiState.Error(
                                        baseResult.rawResponse.message!!
                                    )
                                )
                            }
                        }

                        is BaseResult.Success -> {
                            setState { copy(ResourceUiState.Success(baseResult.data.allTodo)) }
                        }
                    }
                }

        }
    }

    @Composable
    fun insertToDo(toDo: ToDo) {
        val viewModelCoroutineScope = rememberCoroutineScope()
        viewModelCoroutineScope.launch {
            toDoUseCase.insertToDoExecute(toDo)
        }
    }
}
