package app.ies.callcontrol

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import app.ies.callcontrol.alarm.AlarmController
import app.ies.callcontrol.ui.theme.CallControlTheme

class MainActivity : ComponentActivity() {
    private val alarmController = AlarmController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CallControlTheme {
                Surface {
                    Text("Running")
                    val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        alarmController.checkExactAlarmPermission(context)
                        alarmController.scheduleVolumeChangeAlarms(context)
                    }
                }
            }
        }
    }


}

