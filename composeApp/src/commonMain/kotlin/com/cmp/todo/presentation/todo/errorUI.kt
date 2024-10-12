package com.cmp.todo.presentation.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun errorUI(
    modifier: Modifier = Modifier,
    msg: String,
    onTryAgain: () -> Unit = {},
    toDoModel: ToDoViewModel
) {
    val getToDO = remember { mutableStateOf(false) }
    if (getToDO.value) {
        toDoModel.getTodo()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedButton(
                onClick = {
                    onTryAgain()
                    getToDO.value = true
                }
            ) {
                Text(
                    text = "Try Again",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
