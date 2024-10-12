package com.cmp.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.cmp.todo.presentation.login.LoginScreenKoin
import com.cmp.todo.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = stringResource(Res.string.cyclone),
//            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
//            style = MaterialTheme.typography.displayLarge
//        )
//
//        var isRotating by remember { mutableStateOf(false) }
//
//        val rotate = remember { Animatable(0f) }
//        val target = 360f
//        if (isRotating) {
//            LaunchedEffect(Unit) {
//                while (isActive) {
//                    val remaining = (target - rotate.value) / target
//                    rotate.animateTo(target, animationSpec = tween((1_000 * remaining).toInt(), easing = LinearEasing))
//                    rotate.snapTo(0f)
//                }
//            }
//        }
//
//        Image(
//            modifier = Modifier
//                .size(250.dp)
//                .padding(16.dp)
//                .run { rotate(rotate.value) },
//            imageVector = vectorResource(Res.drawable.ic_cyclone),
//            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
//            contentDescription = null
//        )
//
//        ElevatedButton(
//            modifier = Modifier
//                .padding(horizontal = 8.dp, vertical = 4.dp)
//                .widthIn(min = 200.dp),
//            onClick = { isRotating = !isRotating },
//            content = {
//                Icon(vectorResource(Res.drawable.ic_rotate_right), contentDescription = null)
//                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                Text(
//                    stringResource(if (isRotating) Res.string.stop else Res.string.run)
//                )
//            }
//        )
//
//        var isDark by LocalThemeIsDark.current
//        val icon = remember(isDark) {
//            if (isDark) Res.drawable.ic_light_mode
//            else Res.drawable.ic_dark_mode
//        }
//
//        ElevatedButton(
//            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
//            onClick = { isDark = !isDark },
//            content = {
//                Icon(vectorResource(icon), contentDescription = null)
//                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                Text(stringResource(Res.string.theme))
//            }
//        )
//
//        val uriHandler = LocalUriHandler.current
//        TextButton(
//            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
//            onClick = { uriHandler.openUri("https://github.com/terrakok") },
//        ) {
//            Text(stringResource(Res.string.open_github))
//        }
        Navigator(LoginScreenKoin())
    }
}
