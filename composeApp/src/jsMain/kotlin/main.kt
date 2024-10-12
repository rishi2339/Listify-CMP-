import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.cmp.todo.App
import com.cmp.todo.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val body = document.body ?: return@onWasmReady
        initKoin()
        ComposeViewport(body) {
            App()
        }
    }
}
