import androidx.compose.ui.window.ComposeUIViewController
import com.cmp.todo.App
import com.cmp.todo.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    App()
}
