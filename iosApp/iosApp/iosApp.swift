import UIKit
import ComposeApp
import SwiftUI

// @main
// class AppDelegate: UIResponder, UIApplicationDelegate {
//     var window: UIWindow?
//
//     func application(
//         _ application: UIApplication,
//         didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
//     ) -> Bool {
//         window = UIWindow(frame: UIScreen.main.bounds)
//         if let window = window {
//             window.rootViewController = MainKt.MainViewController()
//             window.makeKeyAndVisible()
//         }
//         return true
//     }
// }

@main
struct iosApp: App {
    init() {
        KoinKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
