package com.cmp.todo.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.Button
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
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
import com.cmp.todo.data_remote.login.remote.dto.LoginRequest
import com.cmp.todo.presentation.register.RegisterScreenKoin
import com.cmp.todo.presentation.todo.ToDoScreenKoin
import kotlinx.coroutines.flow.collectLatest

class LoginScreenKoin : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val loginScreenModel = getScreenModel<LoginViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit) {
            loginScreenModel.effect.collectLatest { effect ->
                when (effect) {
                    LoginActivity.Effect.NavigateToHome -> {
                        navigator.push(ToDoScreenKoin())
                    }

                    LoginActivity.Effect.NavigateToRegister ->
                        navigator.push(RegisterScreenKoin())
                }
            }
        }
        if (loginScreenModel.getToken() == "") {
            loginScreen(
                loginScreenModel = loginScreenModel
            )
        } else {
            loginScreenModel.setEvent(
                LoginActivity.Event.OnLoginClick
            )
        }
    }
}

@Composable
fun loginScreen(
    loginScreenModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoggedIn = remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    if (isLoggedIn.value) {
        errorText = ""
        val validator = loginScreenModel.signIn(
            LoginRequest(
                email = email,
                password = password
            )
        )
        if (validator) {
            loginScreenModel.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            ) { token ->
                loginScreenModel.saveToken(token)
                loginScreenModel.setEvent(
                    LoginActivity.Event.OnLoginClick
                )
            }
        } else {
            errorText = loginScreenModel.error.value
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                },
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
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (errorText.isNotEmpty()) {
                Card(
                    backgroundColor = Color(0xFFFFCDD2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorText,
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    isLoggedIn.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) { Text(text = "Log in", color = Color.White) }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = {
                    loginScreenModel.setEvent(
                        LoginActivity.Event.OnRegisterClick
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("I don't have an account!")
            }
        }
    }
}
