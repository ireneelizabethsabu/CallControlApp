package app.ies.callcontrol.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import java.util.Calendar
import kotlin.jvm.java

class AlarmController {
    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun checkExactAlarmPermission(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val alarmManager = getAlarmManager(context)
            if(!alarmManager.canScheduleExactAlarms()){
                val intent = Intent().apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                }
                context.startActivity(intent)
            }else{
                println("CANNOT SCHEDULE EXACT ALARMS")
            }
        }
    }

    private fun setAlarm(context: Context, hour: Int, minute: Int, pendingIntent: PendingIntent) {
        val alarmManager = getAlarmManager(context)
        val timeInMillis = getTimeInMillis(hour, minute)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    fun scheduleVolumeChangeAlarms(context: Context){
        //mute
        val muteIntent = Intent(context, MuteReceiver::class.java)
        val mutePending = PendingIntent.getBroadcast(
            context,
            1001,
            muteIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        setAlarm(context,9,18,mutePending)

        // Unmute at 6:00 PM
        val unmuteIntent = Intent(context, UnMuteReceiver::class.java)
        val unmutePending = PendingIntent.getBroadcast(
            context, 1, unmuteIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        setAlarm(context,9,23,unmutePending)
    }

    private fun getTimeInMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1) // schedule for next day if time already passed
            }
        }

        return calendar.timeInMillis
    }
}