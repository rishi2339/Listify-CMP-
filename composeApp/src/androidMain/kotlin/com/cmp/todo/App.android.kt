package com.cmp.todo

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cmp.todo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }
    }
}
fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
class AppActivity : ComponentActivity() {
    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}
