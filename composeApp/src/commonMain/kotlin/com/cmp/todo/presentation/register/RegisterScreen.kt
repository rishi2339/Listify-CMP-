package com.cmp.todo.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.cmp.todo.data_remote.register.remote.dto.RegisterRequest
import com.cmp.todo.presentation.login.LoginScreenKoin
import com.cmp.todo.presentation.todo.ToDoScreenKoin
import kotlinx.coroutines.flow.collectLatest

class RegisterScreenKoin : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val registerScreenModel = getScreenModel<RegisterViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit) {
            registerScreenModel.effect.collectLatest { effect ->
                when (effect) {
                    RegisterActivity.Effect.NavigateToHome ->
                        navigator.push(ToDoScreenKoin())

                    RegisterActivity.Effect.NavigateToLogin ->
                        navigator.push(LoginScreenKoin())
                }
            }
        }
        if (registerScreenModel.getToken() == "") {
            registerScreen(registerScreenModel)
        } else {
            registerScreenModel.setEvent(
                RegisterActivity.Event.OnRegisterClick
            )
        }
    }
}

@Composable
fun registerScreen(
    registerScreenModel: RegisterViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    val isRegistered = remember { mutableStateOf(false) }
    if (isRegistered.value) {
        errorText = ""
        val validationResult = registerScreenModel.signUp(
            RegisterRequest(
                email = email,
                password = password,
                name = name
            )
        )
        if (validationResult) {
            registerScreenModel.register(
                RegisterRequest(
                    email = email,
                    password = password,
                    name = name
                )
            ) { token ->
                registerScreenModel.saveToken(token)
                registerScreenModel.setEvent(
                    RegisterActivity.Event.OnRegisterClick
                )
            }
        } else {
            errorText = registerScreenModel.error.value
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorText.isNotEmpty()) {
            Text(
                textAlign = TextAlign.Center,
                text = errorText,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        Button(
            onClick = { isRegistered.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
            Button(
                onClick = {
                    registerScreenModel.setEvent(
                        RegisterActivity.Event.OnLoginClick
                    )
                },
            ) {
                Text("Already have an account? Log in!")
        }
    }
}
