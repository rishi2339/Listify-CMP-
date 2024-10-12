package com.cmp.todo.presentation.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.cmp.todo.presentation.login.LoginScreenKoin
import com.cmp.todo.util.ResourceUiState
import kotlinx.coroutines.flow.collectLatest

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
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {
            when (resourceUiState) {
                is ResourceUiState.Empty -> {
                    emptyUI(
                        modifier = Modifier,
                        onCheckAgain = {},
                        msg = "An error has occurred",
                        toDoModel = toDoModel
                    )
                }
                is ResourceUiState.Error -> {
                    errorUI(
                        modifier = Modifier,
                        onTryAgain = {},
                        msg = "No data to show",
                        toDoModel = toDoModel
                    )
                }
                is ResourceUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ResourceUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(size = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                onCheckedChange = { completed = it },
                checked = completed,
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                maxLines = 1,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogOutAppBar(
    toDoModel: ToDoViewModel
) {
//    TopAppBar(title = { Text("this title") }) {
//        IconButton(
//            onClick = {
//                toDoModel.setEvent(
//                    ToDoActivity.Event.OnLogoutClick
//                )
//                toDoModel.removeToken()
//            }
//        ) {
//            Row {
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    color = Color.White,
//                    text = "Log Out"
//                )
//            }
//        }
//    }
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
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4E00BE)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = input.value,
                visualTransformation = VisualTransformation.None,
                onValueChange = { newText -> input.value = newText },
                placeholder = {
                    Text(
                        text = "Add ToDo",
                        color = Color.White,
                        textDecoration = TextDecoration.None
                    )
                },
                singleLine = true,
            )
            FloatingActionButton(
                onClick = {
                    if (input.value.isEmpty()) return@FloatingActionButton
                    isToDoAdded = true
                },
                shape = CircleShape,
                modifier = Modifier.size(40.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null
//                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
//    if (isToDoAdded) {
//        toDoModel.insertToDo(
//            ToDo(
//                title = input.value,
//                completed = false,
//                userId = 3
//            )
//        )
//        isToDoAdded = false
//        toDoModel.getTodo()
//    }
}