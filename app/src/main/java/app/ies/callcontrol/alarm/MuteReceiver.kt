package app.ies.callcontrol.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager

class MuteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        print("here muting")
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0 , AudioManager.FLAG_SHOW_UI)
    }
}