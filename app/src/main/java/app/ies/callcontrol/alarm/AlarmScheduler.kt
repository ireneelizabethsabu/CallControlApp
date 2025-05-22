package app.ies.callcontrol.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import kotlin.jvm.java

class AlarmScheduler {
    fun scheduleVolumeChangeAlarms(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //mute
        val muteIntent = Intent(context, MuteReceiver::class.java)
        val mutePending = PendingIntent.getBroadcast(
            context,
            0,
            muteIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            getTimeInMillis(hour = 16, minute = 0),
            AlarmManager.INTERVAL_DAY,
            mutePending
        )

        // Unmute at 6:00 PM
        val unmuteIntent = Intent(context, UnMuteReceiver::class.java)
        val unmutePending = PendingIntent.getBroadcast(
            context, 1, unmuteIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            getTimeInMillis(hour = 18, minute = 0),
            AlarmManager.INTERVAL_DAY,
            unmutePending
        )
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