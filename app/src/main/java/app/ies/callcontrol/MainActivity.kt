package app.ies.callcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import app.ies.callcontrol.alarm.AlarmScheduler
import app.ies.callcontrol.ui.theme.CallControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AlarmScheduler().scheduleVolumeChangeAlarms(this)
        setContent {
            CallControlTheme {
                Surface {
                    Text("Running")
                }
            }
        }
    }
}

