package ru.w0rng.dnd

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationManagerCompat
import ru.w0rng.dnd.ui.theme.AndroindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        if (!serviceIsRunning()) {
            val intent = Intent(this, BackgroundService::class.java)
            startForegroundService(intent)
        }
    }

    fun serviceIsRunning(): Boolean {
        val acticityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in acticityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (BackgroundService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroindTheme {
        Greeting("Android")
    }
}
