package com.cmp.todo.presentation.todo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.cmp.todo.domain.todo.entity.ToDo
import com.cmp.todo.presentation.login.LoginScreenKoin
import com.cmp.todo.util.ResourceUiState
import kotlinx.coroutines.flow.collectLatest
import listify.composeapp.generated.resources.Res
import listify.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

class ToDoScreenKoin : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val toDoScreenModel = getScreenModel<ToDoViewModel>()
        val state by toDoScreenModel.uiState.collectAsState()
        toDoScreenModel.getTodo()
        LaunchedEffect(key1 = Unit) {
            toDoScreenModel.effect.collectLatest { effect ->
                when (effect) {
                    ToDoActivity.Effect.NavigateToLogin -> {
                        navigator.push(LoginScreenKoin())
                    }
                }
            }
        }
        toDoScreen(toDoScreenModel, state)
    }
}

@Composable
fun toDoScreen(
    toDoModel: ToDoViewModel,
    state: ToDoActivity.State
) {
    val resourceUiState = state.todo
    Scaffold(
        topBar = { LogOutAppBar(toDoModel) },
        bottomBar = { TodoInputBar(toDoModel) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (resourceUiState) {
                is ResourceUiState.Empty -> {
                    emptyUI(
                        modifier = Modifier,
                        onCheckAgain = {},
                        msg = "No tasks available. Start adding some!",
                        toDoModel = toDoModel
                    )
                }
                is ResourceUiState.Error -> {
                    errorUI(
                        modifier = Modifier,
                        onTryAgain = {},
                        msg = "An error occurred. Please try again.",
                        toDoModel = toDoModel
                    )
                }
                is ResourceUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is ResourceUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(resourceUiState.data.size) { index ->
                            val data = resourceUiState.data[index]
                            toDoItem(data.title, data.completed)
                        }
                    }
                }
                ResourceUiState.Idle -> Unit
            }
        }
    }
}

@Composable
fun toDoItem(title: String, checked: Boolean) {
    var completed by remember { mutableStateOf(checked) }
    val cardElevation by animateDpAsState(targetValue = if (completed) 4.dp else 0.dp)
    val cardBackgroundColor by animateColorAsState(
        targetValue = if (completed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { completed = !completed },
        shape = RoundedCornerShape(size = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                onCheckedChange = { completed = it },
                checked = completed,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (completed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun LogOutAppBar(
    toDoModel: ToDoViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Left
            ) },
        navigationIcon = {
            IconButton(
                onClick = {
                    toDoModel.setEvent(
                        ToDoActivity.Event.OnLogoutClick
                    )
                    toDoModel.removeToken()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        contentColor = Color.White,
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colorScheme.primary
    )
}
@Composable
fun TodoInputBar(
    toDoModel: ToDoViewModel
) {
    val input = remember { mutableStateOf("") }
    var isToDoAdded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier
            .height(76.dp)
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = input.value,
                onValueChange = { newText -> input.value = newText },
                placeholder = {
                    Text(
                        text = "Add a task",
                        color = Color.White,
                        textDecoration = TextDecoration.None
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            FloatingActionButton(
                onClick = {
                    if (input.value.isNotBlank()) {
                        isToDoAdded = true
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 8.dp
                ),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = Color.White
                )
            }
        }
    }
    if (isToDoAdded) {
        toDoModel.insertToDo(
            ToDo(
                title = input.value,
                completed = false,
                userId = 3
            )
        )
        isToDoAdded = false
        toDoModel.getTodo()
    }
}